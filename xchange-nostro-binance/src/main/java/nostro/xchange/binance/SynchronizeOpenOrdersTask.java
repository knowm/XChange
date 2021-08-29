package nostro.xchange.binance;

import nostro.xchange.persistence.OrderEntity;
import nostro.xchange.persistence.Transaction;
import nostro.xchange.persistence.TransactionFactory;
import nostro.xchange.utils.NostroUtils;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Collectors;

class SynchronizeOpenOrdersTask implements Callable<OpenOrders> {
    private static final Logger LOG = LoggerFactory.getLogger(SynchronizeOpenOrdersTask.class);
    
    private final BinanceTradeService service;
    private final TransactionFactory txFactory;

    SynchronizeOpenOrdersTask(BinanceTradeService service, TransactionFactory txFactory) {
        this.service = service;
        this.txFactory = txFactory;
    }

    @Override
    public OpenOrders call() throws IOException {
        Timestamp timestamp = txFactory.executeAndGet(Transaction::getTimestamp);
        LOG.info("Synchronization start, timestamp={}", timestamp);

        OpenOrders openOrders;
        try {
            openOrders = service.getOpenOrders();
        } catch (IOException e) {
            LOG.error("Error on open order request", e);
            throw e;
        }

        Map<String, Order> serviceOrders = openOrders.getAllOpenOrders().stream()
                .collect(Collectors.toMap(Order::getId, Function.identity()));
        
        List<OrderEntity> entities = txFactory.executeAndGet(tx -> tx.getOrderRepository().findAllOpen());
        List<Order> dbOrders = NostroUtils.readOrderList(entities);

        LOG.info("Service open orders = {}, DB open orders = {}", serviceOrders.size(), dbOrders.size());

        for (Order dbOrder : dbOrders) {
            Order serviceOrder = serviceOrders.remove(dbOrder.getUserReference());
            
            if (serviceOrder == null) {
                updateFromService(dbOrder, timestamp);
            } else if (dbOrder.getStatus() != serviceOrder.getStatus() ||
                    0 != dbOrder.getCumulativeAmount().compareTo(serviceOrder.getCumulativeAmount())) {
                update(serviceOrder, timestamp);
            }
        }
        
        for(Map.Entry<String, Order> e : serviceOrders.entrySet()) {
            insert(e.getValue());
        }
        
        LOG.info("Synchronization finished", timestamp);
        return openOrders;
    }

    private void updateFromService(Order order, Timestamp timestamp) {
        try {
            txFactory.execute(tx -> {
                String orderId = order.getUserReference();
                OrderEntity e = tx.getOrderRepository().lockById(orderId).get();
                if (e.getUpdated().before(timestamp)) {
                    Order updated = BinanceAdapters.adaptOrder(service.orderStatus(order.getCurrencyPair(), Long.valueOf(order.getId()), null));
                    tx.getOrderRepository().updateById(orderId, NostroUtils.writeOrderDocument(updated), updated.getStatus().isFinal());
                    LOG.info("Updated open order(id={}, status={})", orderId, updated.getStatus());
                } else {
                    LOG.info("Concurrent updated detected for order(id={})", orderId);
                }
            });
        } catch (Exception e) {
            LOG.error("Error when updating order, id=" + order.getId(), e);
        }
    }

    private void update(Order order, Timestamp timestamp) {
        txFactory.execute(tx -> {
            String orderId = order.getUserReference();
            OrderEntity e = tx.getOrderRepository().lockById(orderId).get();
            if (e.getUpdated().before(timestamp)) {
                tx.getOrderRepository().updateById(orderId, NostroUtils.writeOrderDocument(order), order.getStatus().isFinal());
                LOG.info("Updated open order(id={}, status={})", orderId, order.getStatus());
            } else {
                LOG.info("Concurrent updated detected for order(id={})", orderId);
            }
        });
    }

    private void insert(Order order) {
        try {
            OrderEntity entity = new OrderEntity.Builder()
                    .id(order.getUserReference())
                    .externalId(order.getId())
                    .terminal(order.getStatus().isFinal())
                    .document(NostroUtils.writeOrderDocument(order))
                    .build();
            
            txFactory.execute(tx -> tx.getOrderRepository().insert(entity));
            LOG.info("Updated open order(id={}, status={})", order.getId(), order.getStatus());
        } catch (Exception e) {
            LOG.warn("Error when inserting order, id=" + order.getId(), e);
        }
    }
}
