package nostro.xchange.binance.futures.sync;

import nostro.xchange.binance.futures.NostroBinanceFuturesUtils;
import nostro.xchange.persistence.OrderEntity;
import nostro.xchange.sync.SyncTask;
import nostro.xchange.utils.NostroUtils;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.futures.BinanceFuturesAdapter;
import org.knowm.xchange.binance.futures.dto.trade.BinanceFuturesOrder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.instrument.Instrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BinanceFuturesOpenOrderSyncTask extends SyncTask <Void, BinanceFuturesSyncService> {
    private static final Logger LOG = LoggerFactory.getLogger(BinanceFuturesOpenOrderSyncTask.class);

    private final Instrument instrument;
    private final CurrencyPair pair;
    private final long toId;

    public BinanceFuturesOpenOrderSyncTask(BinanceFuturesSyncService syncService, CurrencyPair pair, long toId) {
        super(syncService);
        this.pair = pair;
        this.instrument = BinanceFuturesAdapter.adaptInstrument(BinanceAdapters.toSymbol(pair));
        this.toId = toId;
    }

    @Override
    public Void call() throws Exception {
        LOG.info("Starting task (symbol={}, instrument={}, toId={})", pair, instrument, toId);

        List<OrderEntity> dbOrders = getSyncService().getTXFactory().executeAndGet(tx -> tx.getOrderRepository().findOpenByInstrument(instrument.toString()));
        if (dbOrders.isEmpty()) {
            LOG.info("Finished task (symbol={}, instrument={}, toId={}): no open orders found in DB", pair, instrument, toId);
            return null;
        }

        Map<Long, BinanceFuturesOrder> serviceOrders = getSyncService().getOpenOrders(pair).stream()
                .filter(o -> o.orderId < toId)
                .collect(Collectors.toMap(o -> o.orderId, o -> o));

        int updated = 0;
        for (OrderEntity e : dbOrders) {
            if (e.getExternalId() != null) {
                long binanceId = Long.parseLong(e.getExternalId());
                if (binanceId < toId) {
                    BinanceFuturesOrder binanceOrder = serviceOrders.get(binanceId);
                    if (binanceOrder == null) {
                        binanceOrder = getSyncService().getOrder(pair, binanceId);
                    }

                    if (binanceOrder == null) {
                        LOG.warn("Cannot resolve binance order(external_id={}): order not found in both db and remote service - skip sync", binanceId);
                        continue;
                    }

                    if (syncOrder(binanceOrder)) {
                        getSyncService().getPublisher().publish(BinanceFuturesAdapter.adaptOrder(binanceOrder));
                        ++updated;
                    }
                }
            } else {
                BinanceFuturesOrder binanceOrder = getSyncService().getOrder(pair, e.getId());
                LOG.warn("Trying to sync db order(id={}) with no external_id, service returned {}", e.getId(), binanceOrder);
                if (binanceOrder != null) {
                    if (syncOrder(binanceOrder)) {
                        getSyncService().getPublisher().publish(BinanceFuturesAdapter.adaptOrder(binanceOrder));
                        ++updated;
                    }
                } else {
                    getSyncService().getPublisher().publish(markRejected(e.getId()));
                    ++updated;
                }
            }
        }

        LOG.info("Finished task (symbol={}, instrument={}, toId={}): updated={}", pair, instrument, toId, updated);
        return null;
    }

    private boolean syncOrder(BinanceFuturesOrder binanceOrder) {
        return getSyncService().getTXFactory().executeAndGet(tx -> {
            final String orderId = String.valueOf(binanceOrder.clientOrderId);
            final OrderEntity e = tx.getOrderRepository().lockById(orderId).get();
            if (NostroBinanceFuturesUtils.updateRequired(e, binanceOrder)) {
                OrderEntity e2 = NostroBinanceFuturesUtils.toEntity(binanceOrder);
                LOG.info("Updating order(id={})", orderId);
                tx.getOrderRepository().updateById(orderId, e2.getDocument(), e2.isTerminal(), e2.getUpdated());
                return true;
            }
            return false;
        });
    }

    private Order markRejected(String id) {
        return getSyncService().getTXFactory().executeAndGet(tx -> {
            OrderEntity e = tx.getOrderRepository().lockById(id).get();
            LOG.info("Marking order(id={}) rejected", id);

            Order order = NostroUtils.readOrderDocument(e.getDocument());
            order.setOrderStatus(Order.OrderStatus.REJECTED);
            String document = NostroUtils.writeOrderDocument(order);

            tx.getOrderRepository().updateById(id, document, true, tx.getTimestamp());
            return order;
        });
    }
}
