package nostro.xchange.binance.sync;

import nostro.xchange.binance.NostroBinanceUtils;
import nostro.xchange.persistence.OrderEntity;
import nostro.xchange.utils.NostroUtils;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.trade.BinanceOrder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class OpenOrderSyncTask implements Callable<Void> {
    private static final Logger LOG = LoggerFactory.getLogger(OpenOrderSyncTask.class);
    
    private final BinanceSyncService syncService;
    private final CurrencyPair pair;
    private long toId;

    public OpenOrderSyncTask(BinanceSyncService syncService, CurrencyPair pair, long toId) {
        this.syncService = syncService;
        this.pair = pair;
        this.toId = toId;
    }

    @Override
    public Void call() throws Exception {
        LOG.info("Starting OpenOrderSyncTask(symbol={}, toId={})", pair, toId);

        List<OrderEntity> dbOrders = syncService.getTXFactory().executeAndGet(tx -> tx.getOrderRepository().findOpenByInstrument(pair.toString()));
        if (dbOrders.isEmpty()) {
            LOG.info("Finished OpenOrderSyncTask(symbol={}, toId={}): no open orders found in DB", pair, toId);
            return null;
        }

        Map<Long, BinanceOrder> serviceOrders = syncService.getOpenOrders(pair).stream()
                .filter(o -> o.orderId < toId)
                .collect(Collectors.toMap(o -> o.orderId, o -> o));

        int updated = 0;
        for (OrderEntity e : dbOrders) {
            if (e.getExternalId() != null) {
                long binanceId = Long.valueOf(e.getExternalId());
                if (binanceId < toId) {
                    BinanceOrder binanceOrder = serviceOrders.get(binanceId);
                    if (binanceOrder == null) {
                        binanceOrder = syncService.getOrder(pair, binanceId);
                    }

                    if (syncOrder(binanceOrder)) {
                        syncService.getPublisher().publish(BinanceAdapters.adaptOrder(binanceOrder));
                        ++updated;
                    }
                }
            } else {
                BinanceOrder binanceOrder = syncService.getOrder(pair, e.getId());
                LOG.warn("Trying to sync db order(id={}) with no external_id, service returned {}", e.getId(), binanceOrder);
                if (binanceOrder != null) {
                    if (syncOrder(binanceOrder)) {
                        syncService.getPublisher().publish(BinanceAdapters.adaptOrder(binanceOrder));
                        ++updated;
                    }
                } else {
                    syncService.getPublisher().publish(markRejected(e.getId()));
                    ++updated;
                }
            }
        }

        LOG.info("Finished OpenOrderSyncTask(symbol={}, toId={}): updated={}", pair, toId, updated);
        return null;
    }

    private boolean syncOrder(BinanceOrder binanceOrder) {
        return syncService.getTXFactory().executeAndGet(tx -> {
            String orderId = String.valueOf(binanceOrder.clientOrderId);
            OrderEntity e = tx.getOrderRepository().lockById(orderId).get();
            
            if (NostroBinanceUtils.updateRequired(e, binanceOrder)) {
                OrderEntity e2 = NostroBinanceUtils.toEntity(binanceOrder);
                LOG.info("Updating order(id={})", orderId);
                tx.getOrderRepository().updateById(orderId, e2.getDocument(), e2.isTerminal(), e2.getUpdated());
                return true;
            }
            return false;
        });
    }

    private Order markRejected(String id) {
        return syncService.getTXFactory().executeAndGet(tx -> {
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
