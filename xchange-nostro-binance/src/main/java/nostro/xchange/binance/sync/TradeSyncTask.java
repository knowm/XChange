package nostro.xchange.binance.sync;

import nostro.xchange.binance.BinanceNostroUtils;
import nostro.xchange.persistence.OrderEntity;
import nostro.xchange.persistence.TradeEntity;
import nostro.xchange.persistence.TransactionFactory;
import org.knowm.xchange.binance.dto.trade.BinanceOrder;
import org.knowm.xchange.binance.dto.trade.BinanceTrade;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class TradeSyncTask implements Callable<Long> {
    private static final Logger LOG = LoggerFactory.getLogger(TradeSyncTask.class);

    private static final int LIMIT = 1000;
    
    private final TransactionFactory txFactory;
    private final BinanceTradeService service;
    private final CurrencyPair pair;
    private long fromId;

    public TradeSyncTask(TransactionFactory txFactory, BinanceTradeService service, CurrencyPair pair, long fromId) {
        this.txFactory = txFactory;
        this.service = service;
        this.pair = pair;
        this.fromId = fromId;
    }
    
    @Override
    public Long call() throws Exception {
        LOG.info("Starting TradeSyncTask(symbol={}): fromId={}", pair, fromId);

        int updated = 0;
        List<BinanceTrade> trades;
        BinanceTrade last = null;
        do {
            trades = getTrades(fromId);

            if (trades.size() > 0) {
                last = trades.get(trades.size() - 1);
                fromId = last.id;
                
                Map<Long, List<BinanceTrade>> map = trades.stream().collect(Collectors.groupingBy(t -> t.orderId));

                for (Map.Entry<Long, List<BinanceTrade>> e : map.entrySet()) {
                    updated += syncTrades(e.getKey(), e.getValue());
                }
            }
            
        } while (!trades.isEmpty() && trades.size() >= LIMIT);

        long toId = last != null ? (last.id + 1) : fromId;

        LOG.info("Finished TradeSyncTask(symbol={}): toId={}, updated={})", pair, toId, updated);
        return toId;
    }

    private int syncTrades(long binanceOrderId, List<BinanceTrade> binanceTrades) {
        return txFactory.executeAndGet(tx -> {

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
                BinanceOrder binanceOrder = service.orderStatus(pair, binanceOrderId, null);
                OrderEntity e = BinanceNostroUtils.toEntity(binanceOrder);
                orderId = e.getId();
                
                LOG.info("Inserting new order(id={})", orderId);
                tx.getOrderRepository().insert(e);

                existingTradeIds = Collections.emptySet();
            }

            int updated = 0;
            for (BinanceTrade binanceTrade : binanceTrades) {
                if (!existingTradeIds.contains(Long.toString(binanceTrade.id))) {
                    LOG.info("Inserting trade(external_id={}, order_id={})", binanceTrade.id, orderId);
                    tx.getTradeRepository().insert(BinanceNostroUtils.toEntity(binanceTrade, orderId, pair));

                    updated += 1;
                }
            }

            return updated;
        });
    }

    private List<BinanceTrade> getTrades(Long fromId) throws IOException {
        try {
            List<BinanceTrade> trades = service.myTrades(pair, LIMIT, null, null, fromId);
            LOG.info("Service returned {} trades", trades.size());
            return trades;
        } catch (Throwable th) {
            LOG.error("Error while querying trades", th);
            throw th;
        }
    }
}
