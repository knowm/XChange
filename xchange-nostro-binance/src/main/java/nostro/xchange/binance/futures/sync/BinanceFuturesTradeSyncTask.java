package nostro.xchange.binance.futures.sync;

import nostro.xchange.binance.futures.NostroBinanceFuturesUtils;
import nostro.xchange.persistence.OrderEntity;
import nostro.xchange.persistence.TradeEntity;
import nostro.xchange.sync.SyncTask;
import org.knowm.xchange.binance.futures.dto.trade.BinanceFuturesOrder;
import org.knowm.xchange.binance.futures.dto.trade.BinanceFuturesTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

class BinanceFuturesTradeSyncTask extends SyncTask<Long, BinanceFuturesSyncService> {
    private static final Logger LOG = LoggerFactory.getLogger(BinanceFuturesTradeSyncTask.class);
    static int LIMIT = 1000;

    private final CurrencyPair pair;
    private long fromId;

    public BinanceFuturesTradeSyncTask(BinanceFuturesSyncService syncService, CurrencyPair pair, long fromId) {
        super(syncService);
        this.pair = pair;
        this.fromId = fromId;
    }

    @Override
    public Long call() throws Exception {
        LOG.info("Starting task (symbol={}): fromId={}", pair, fromId);

        int updatedCount = 0;
        List<BinanceFuturesTrade> trades;
        BinanceFuturesTrade last = null;
        do {
            trades = getSyncService().getTrades(pair, fromId, LIMIT);

            if (trades.size() > 0) {
                last = trades.get(trades.size() - 1);
                fromId = last.id;

                Map<Long, List<BinanceFuturesTrade>> map = trades.stream().collect(Collectors.groupingBy(t -> t.orderId));

                for (Map.Entry<Long, List<BinanceFuturesTrade>> e : map.entrySet()) {
                    List<BinanceFuturesTrade> updated = syncTrades(e.getKey(), e.getValue());

                    for(BinanceFuturesTrade u : updated) {
                        getSyncService().getPublisher().publish(NostroBinanceFuturesUtils.adaptTrade(u, pair));
                    }

                    updatedCount += updated.size();
                }
            }

        } while (!trades.isEmpty() && trades.size() >= LIMIT);

        long toId = last != null ? (last.id + 1) : fromId;

        LOG.info("Finished task (symbol={}): toId={}, updated={}", pair, toId, updatedCount);
        return toId;
    }

    private List<BinanceFuturesTrade> syncTrades(long binanceOrderId, List<BinanceFuturesTrade> binanceTrades) {
        return getSyncService().getTXFactory().executeAndGet(tx -> {

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
                BinanceFuturesOrder binanceOrder = getSyncService().getOrder(pair, binanceOrderId);
                OrderEntity e = NostroBinanceFuturesUtils.toEntity(binanceOrder);
                orderId = e.getId();

                LOG.info("Inserting new order(id={})", orderId);
                tx.getOrderRepository().insert(e);

                existingTradeIds = Collections.emptySet();
            }

            List<BinanceFuturesTrade> updated = new ArrayList<>();
            for (BinanceFuturesTrade binanceTrade : binanceTrades) {
                if (!existingTradeIds.contains(Long.toString(binanceTrade.id))) {
                    LOG.info("Inserting trade(external_id={}, order_id={})", binanceTrade.id, orderId);
                    tx.getTradeRepository().insert(NostroBinanceFuturesUtils.toEntity(binanceTrade, orderId, pair));

                    updated.add(binanceTrade);
                }
            }

            return updated;
        });
    }
}
