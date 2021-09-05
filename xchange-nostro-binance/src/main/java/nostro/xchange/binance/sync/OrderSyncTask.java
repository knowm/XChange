package nostro.xchange.binance.sync;

import nostro.xchange.binance.BinanceNostroUtils;
import nostro.xchange.persistence.OrderEntity;
import nostro.xchange.persistence.TransactionFactory;
import org.knowm.xchange.binance.dto.trade.BinanceOrder;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

public class OrderSyncTask implements Callable<Long> {
    private static final Logger LOG = LoggerFactory.getLogger(OrderSyncTask.class);

    private static final int LIMIT = 1000;
    
    private final TransactionFactory txFactory;
    private final BinanceTradeService service;
    private final CurrencyPair pair;
    private long fromId;

    public OrderSyncTask(TransactionFactory txFactory, BinanceTradeService service, CurrencyPair pair, long fromId) {
        this.txFactory = txFactory;
        this.service = service;
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
            orders = getOrders(fromId);

            if (orders.size() > 0) {
                last = orders.get(orders.size() - 1);
                fromId = last.orderId;

                for (BinanceOrder o : orders) {
                    updated += syncOrder(o) ? 1 : 0;
                }    
            }
            
        } while (!orders.isEmpty() && orders.size() >= LIMIT);

        long toId = last != null ? (last.orderId + 1) : fromId;

        LOG.info("Finished OrderSyncTask(symbol={}): toId={}, updated={})", pair, toId, updated);
        return toId;
    }

    private boolean syncOrder(BinanceOrder binanceOrder) {
        return txFactory.executeAndGet(tx -> {
            String orderId = binanceOrder.clientOrderId;
            Optional<OrderEntity> o = tx.getOrderRepository().lockById(orderId);
            if (o.isPresent()) {
                if (BinanceNostroUtils.updateRequired(o.get(), binanceOrder)) {
                    OrderEntity e2 = BinanceNostroUtils.toEntity(binanceOrder);
                    LOG.info("Updating order(id={})", orderId);
                    tx.getOrderRepository().updateById(orderId, e2.getDocument(), e2.isTerminal(), e2.getUpdated());
                    return true;
                }
                
                return false;
            } else {
                LOG.info("Inserting new order(id={})", orderId);
                tx.getOrderRepository().insert(BinanceNostroUtils.toEntity(binanceOrder));
                return true;    
            }
        });
    }

    private List<BinanceOrder> getOrders(long fromId) throws IOException {
        try {
            List<BinanceOrder> orders = service.allOrders(pair, fromId, LIMIT);
            LOG.info("Service returned {} orders", orders.size());
            return orders;
        } catch (Throwable th) {
            LOG.error("Error while querying orders", th);
            throw th;
        }
    }
    
}
