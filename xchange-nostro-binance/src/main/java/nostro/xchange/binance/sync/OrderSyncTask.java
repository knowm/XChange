package nostro.xchange.binance.sync;

import nostro.xchange.binance.NostroBinanceUtils;
import nostro.xchange.persistence.OrderEntity;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.trade.BinanceOrder;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

public class OrderSyncTask implements Callable<Long> {
    private static final Logger LOG = LoggerFactory.getLogger(OrderSyncTask.class);

    private static final int LIMIT = 1000;
    
    private final BinanceSyncService syncService;
    private final CurrencyPair pair;
    private long fromId;

    public OrderSyncTask(BinanceSyncService syncService, CurrencyPair pair, long fromId) {
        this.syncService = syncService;
        this.pair = pair;
        this.fromId = fromId;
    }
    
    @Override
    public Long call() throws Exception {
        LOG.info("Starting OrderSyncTask(symbol={}): fromId={})", pair, fromId);

        int updated = 0;
        List<BinanceOrder> orders;
        BinanceOrder last = null;
        do {
            orders = syncService.getOrders(pair, fromId, LIMIT);

            if (orders.size() > 0) {
                last = orders.get(orders.size() - 1);
                fromId = last.orderId;

                for (BinanceOrder o : orders) {
                    if (syncOrder(o)) {
                        syncService.publisher.publish(BinanceAdapters.adaptOrder(o));
                        ++updated;
                    }
                }    
            }
            
        } while (!orders.isEmpty() && orders.size() >= LIMIT);

        long toId = last != null ? (last.orderId + 1) : fromId;

        LOG.info("Finished OrderSyncTask(symbol={}): toId={}, updated={}", pair, toId, updated);
        return toId;
    }

    private boolean syncOrder(BinanceOrder binanceOrder) {
        return syncService.txFactory.executeAndGet(tx -> {
            String orderId = binanceOrder.clientOrderId;
            Optional<OrderEntity> o = tx.getOrderRepository().lockById(orderId);
            if (o.isPresent()) {
                if (NostroBinanceUtils.updateRequired(o.get(), binanceOrder)) {
                    OrderEntity e2 = NostroBinanceUtils.toEntity(binanceOrder);
                    LOG.info("Updating order(id={})", orderId);
                    tx.getOrderRepository().updateById(orderId, e2.getDocument(), e2.isTerminal(), e2.getUpdated());
                    return true;
                }
                
                return false;
            } else {
                LOG.info("Inserting new order(id={})", orderId);
                tx.getOrderRepository().insert(NostroBinanceUtils.toEntity(binanceOrder));
                return true;    
            }
        });
    }
}
