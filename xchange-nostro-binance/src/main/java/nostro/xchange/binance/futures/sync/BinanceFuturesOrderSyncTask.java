package nostro.xchange.binance.futures.sync;

import nostro.xchange.binance.futures.NostroBinanceFuturesUtils;
import nostro.xchange.persistence.OrderEntity;
import nostro.xchange.sync.SyncTask;
import org.knowm.xchange.binance.futures.BinanceFuturesAdapter;
import org.knowm.xchange.binance.futures.dto.trade.BinanceFuturesOrder;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

class BinanceFuturesOrderSyncTask extends SyncTask<Long, BinanceFuturesSyncService> {
    private static final Logger LOG = LoggerFactory.getLogger(BinanceFuturesOrderSyncTask.class);
    static int LIMIT = 1000;

    private final CurrencyPair pair;
    private long fromId;

    public BinanceFuturesOrderSyncTask(BinanceFuturesSyncService syncService, CurrencyPair pair, long fromId) {
        super(syncService);
        this.pair = pair;
        this.fromId = fromId;
    }

    @Override
    public Long call() throws Exception {
        LOG.info("Starting task (symbol={}): fromId={})", pair, fromId);

        long startId = fromId;
        int updated = 0;
        List<BinanceFuturesOrder> orders;
        BinanceFuturesOrder last = null;
        do {
            if (fromId != startId) LOG.info("Request next page fromId={}, limit={}", fromId, LIMIT);
            else LOG.info("Request first page fromId={}, limit={}", fromId, LIMIT);
            orders = getSyncService().getOrders(pair, fromId, LIMIT);

            if (orders.size() > 0) {
                for (BinanceFuturesOrder o : orders) {
                    if (syncOrder(o)) {
                        getSyncService().getPublisher().publish(BinanceFuturesAdapter.adaptOrder(o));
                        ++updated;
                    }
                }

                last = orders.get(orders.size() - 1);
                if (fromId == last.orderId && orders.size() != 1) {
                    LOG.error("Logical error found! External service returned unexpected order list - fromId={} points to the last order from the list - skip sync next page", fromId);
                    break;
                }
                fromId = last.orderId;
            }

        } while (!orders.isEmpty() && orders.size() >= LIMIT);

        long toId = last != null ? (last.orderId + 1) : fromId;

        LOG.info("Finished task (symbol={}): toId={}, updated={}", pair, toId, updated);
        return toId;
    }

    private boolean syncOrder(BinanceFuturesOrder order) {
        return getSyncService().getTXFactory().executeAndGet(tx -> {
            String orderId = order.clientOrderId;
            Optional<OrderEntity> o = tx.getOrderRepository().lockById(orderId);
            if (o.isPresent()) {
                if (NostroBinanceFuturesUtils.updateRequired(o.get(), order)) {
                    OrderEntity e2 = NostroBinanceFuturesUtils.toEntity(order);
                    LOG.info("Updating order(id={})", orderId);
                    tx.getOrderRepository().updateById(orderId, e2.getDocument(), e2.isTerminal(), e2.getUpdated());
                    return true;
                }

                return false;
            } else {
                LOG.info("Inserting new order(id={})", orderId);
                tx.getOrderRepository().insert(NostroBinanceFuturesUtils.toEntity(order));
                return true;
            }
        });
    }
}
