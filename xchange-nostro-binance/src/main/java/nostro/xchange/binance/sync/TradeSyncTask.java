package nostro.xchange.binance.sync;

import nostro.xchange.binance.BinanceNostroUtils;
import nostro.xchange.persistence.OrderEntity;
import nostro.xchange.persistence.TradeEntity;
import org.knowm.xchange.binance.dto.trade.BinanceOrder;
import org.knowm.xchange.binance.dto.trade.BinanceTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class TradeSyncTask implements Callable<Long> {
    private static final Logger LOG = LoggerFactory.getLogger(TradeSyncTask.class);

    private static final int LIMIT = 1000;
    
    private final BinanceSyncService syncService;
    private final CurrencyPair pair;
    private long fromId;

    public TradeSyncTask(BinanceSyncService syncService, CurrencyPair pair, long fromId) {
        this.syncService = syncService;
        this.pair = pair;
        this.fromId = fromId;
    }
    
    @Override
    public Long call() throws Exception {
        LOG.info("Starting TradeSyncTask(symbol={}): fromId={}", pair, fromId);

        int updatedCount = 0;
        List<BinanceTrade> trades;
        BinanceTrade last = null;
        do {
            trades = syncService.getTrades(pair, fromId, LIMIT);

            if (trades.size() > 0) {
                last = trades.get(trades.size() - 1);
                fromId = last.id;
                
                Map<Long, List<BinanceTrade>> map = trades.stream().collect(Collectors.groupingBy(t -> t.orderId));

                for (Map.Entry<Long, List<BinanceTrade>> e : map.entrySet()) {
                    List<BinanceTrade> updated = syncTrades(e.getKey(), e.getValue());
                    
                    for(BinanceTrade u : updated) {
                        syncService.publisher.publish(BinanceNostroUtils.adapt(u, pair));
                    }

                    updatedCount += updated.size();
                }
            }
            
        } while (!trades.isEmpty() && trades.size() >= LIMIT);

        long toId = last != null ? (last.id + 1) : fromId;

        LOG.info("Finished TradeSyncTask(symbol={}): toId={}, updated={})", pair, toId, updatedCount);
        return toId;
    }

    private List<BinanceTrade> syncTrades(long binanceOrderId, List<BinanceTrade> binanceTrades) {
        return syncService.txFactory.executeAndGet(tx -> {

            String orderId;
            Set<String> existingTradeIds;

            Optional<OrderEntity> o = tx.getOrderRepository().findByExternalId(String.valueOf(binanceOrderId));
            if (o.isPresent()) {
                orderId = o.get().getId();
                tx.getOrderRepository().lockById(orderId);

                existingTradeIds = tx.getTradeRepository().findAllByOrderId(orderId).stream()
                        .map(TradeEntity::getExternalId)
                        .collect(Collectors.toSet());
            } else {
                BinanceOrder binanceOrder = syncService.getOrder(pair, binanceOrderId);
                OrderEntity e = BinanceNostroUtils.toEntity(binanceOrder);
                orderId = e.getId();
                
                LOG.info("Inserting new order(id={})", orderId);
                tx.getOrderRepository().insert(e);

                existingTradeIds = Collections.emptySet();
            }

            List<BinanceTrade> updated = new ArrayList<>();
            for (BinanceTrade binanceTrade : binanceTrades) {
                if (!existingTradeIds.contains(Long.toString(binanceTrade.id))) {
                    LOG.info("Inserting trade(external_id={}, order_id={})", binanceTrade.id, orderId);
                    tx.getTradeRepository().insert(BinanceNostroUtils.toEntity(binanceTrade, orderId, pair));

                    updated.add(binanceTrade);
                }
            }

            return updated;
        });
    }
}
