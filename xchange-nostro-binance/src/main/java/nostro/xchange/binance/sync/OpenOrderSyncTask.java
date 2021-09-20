package nostro.xchange.binance.sync;

import nostro.xchange.binance.BinanceNostroUtils;
import nostro.xchange.persistence.OrderEntity;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.trade.BinanceOrder;
import org.knowm.xchange.currency.CurrencyPair;
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

        List<OrderEntity> dbOrders = syncService.txFactory.executeAndGet(tx -> tx.getOrderRepository().findOpenByInstrument(pair.toString()));
        if (dbOrders.isEmpty()) {
            LOG.info("Finished OpenOrderSyncTask(symbol={}, toId={}): no open orders found in DB", pair, toId);
            return null;
        }

        Map<Long, BinanceOrder> serviceOrders = syncService.getOpenOrders(pair).stream()
                .filter(o -> o.orderId < toId)
                .collect(Collectors.toMap(o -> o.orderId, o -> o));

        int updated = 0;
        for (OrderEntity e : dbOrders) {
            long binanceId = Long.valueOf(e.getExternalId());
            if (binanceId < toId) {
                BinanceOrder binanceOrder = serviceOrders.get(binanceId);
                if (binanceOrder == null) {
                    binanceOrder = syncService.getOrder(pair, binanceId);
                }

                if (syncOrder(binanceOrder)) {
                    syncService.publisher.publish(BinanceAdapters.adaptOrder(binanceOrder));
                    ++updated;
                }
            }
        }

        LOG.info("Finished OpenOrderSyncTask(symbol={}, toId={}): updated={}", pair, toId, updated);
        return null;
    }

    private boolean syncOrder(BinanceOrder binanceOrder) {
        return syncService.txFactory.executeAndGet(tx -> {
            String orderId = String.valueOf(binanceOrder.clientOrderId);
            OrderEntity e = tx.getOrderRepository().lockById(orderId).get();
            
            if (BinanceNostroUtils.updateRequired(e, binanceOrder)) {
                OrderEntity e2 = BinanceNostroUtils.toEntity(binanceOrder);
                LOG.info("Updating order(id={})", orderId);
                tx.getOrderRepository().updateById(orderId, e2.getDocument(), e2.isTerminal(), e2.getUpdated());
                return true;
            }
            return false;
        });
    }
    
}
