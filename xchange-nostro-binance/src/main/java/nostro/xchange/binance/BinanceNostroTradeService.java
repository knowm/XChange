package nostro.xchange.binance;

import nostro.xchange.persistence.OrderEntity;
import nostro.xchange.persistence.OrderRepository;
import nostro.xchange.persistence.TransactionFactory;
import nostro.xchange.utils.NostroUtils;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByUserReferenceParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultCancelOrderByUserReferenceParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParam;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

public class BinanceNostroTradeService implements TradeService {
    private static final Logger LOG = LoggerFactory.getLogger(BinanceNostroTradeService.class);

    private final BinanceTradeService inner;
    private final TransactionFactory txFactory;
    private final BinanceCancelService cancelService;
    
    public BinanceNostroTradeService(BinanceTradeService inner, TransactionFactory txFactory) {
        this.inner = inner;
        this.txFactory = txFactory;
        this.cancelService = new BinanceCancelService(txFactory, inner);
    }
    
    @Override
    public String placeMarketOrder(MarketOrder marketOrder) {
        return executePlaceOrder(marketOrder, () -> inner.placeMarketOrder(marketOrder));
    }

    @Override
    public String placeLimitOrder(LimitOrder limitOrder) {
        return executePlaceOrder(limitOrder, () -> inner.placeLimitOrder(limitOrder));
    }

    @Override
    public String placeStopOrder(StopOrder stopOrder) {
        return executePlaceOrder(stopOrder, () -> inner.placeStopOrder(stopOrder));
    }
    
    private String executePlaceOrder(Order order, Callable<String> placeOrderCallable) {
        String id = order.getUserReference();
        OrderEntity e = new OrderEntity.Builder()
                .id(id)
                .instrument(order.getInstrument().toString())
                .document(NostroUtils.writeOrderDocument(order))
                .externalId(null)
                .terminal(false)
                .created(new Timestamp(0))
                .updated(new Timestamp(0))
                .build();

        txFactory.execute(tx -> tx.getOrderRepository().insert(e));
        LOG.info("Placing order {}", order);
        
        try {
            return txFactory.executeAndGet(tx -> {
                tx.getOrderRepository().lockById(id);
                String externalId = placeOrderCallable.call();
                tx.getOrderRepository().update(id, externalId);
                return externalId;
            });
        } catch (RuntimeException th) {
            if (shouldCancelOrder(th)) {
                LOG.error("Error while placing order, cancelling", th);
                cancelService.cancelOrder(id);
            } else {
                LOG.error("Error while placing order, no cancel, marking rejected", th);
                // TODO: cancelService.markRejected(id);
            }
            throw th;
        }
    }

    private boolean shouldCancelOrder(Throwable th) {
        // TODO: implement when retry is moved here
        return false;
    }

    @Override
    public Collection<Order> getOrder(String... orderIds) {
        List<OrderEntity> entities = txFactory.executeAndGet(tx -> {
            OrderRepository repo = tx.getOrderRepository();
            List<OrderEntity> list = new ArrayList<>();
            for (String orderId : orderIds) {
                list.add(repo.findByExternalId(orderId)
                        .orElseThrow(() -> new IllegalArgumentException("Order with external_id=\"" + orderId + "\" not found")));
            }
            return list;
        });
        return NostroUtils.readOrderList(entities);
    }

    @Override
    public OpenOrders getOpenOrders() {
        List<OrderEntity> entities = txFactory.executeAndGet(tx -> tx.getOrderRepository().findAllOpen());
        return NostroUtils.adaptOpenOrders(NostroUtils.readOrderList(entities));
    }

    @Override
    public OpenOrders getOpenOrders(OpenOrdersParams params) {
        return getOpenOrders();
    }

    @Override
    public OpenOrdersParams createOpenOrdersParams() {
        return new DefaultOpenOrdersParam();
    }

    @Override
    public boolean cancelOrder(String orderId) {
        String userReference = txFactory.executeAndGet(tx ->
                tx.getOrderRepository().findByExternalId(orderId)
                        .orElseThrow(() -> new IllegalArgumentException("Order with external_id=\"" + orderId + "\" not found")).getId()
        );
        
        return cancelOrder(new DefaultCancelOrderByUserReferenceParams(userReference));
    }

    @Override
    public boolean cancelOrder(CancelOrderParams params) {
        if (!(params instanceof CancelOrderByUserReferenceParams)) {
            throw new ExchangeException("You need to provide user reference to cancel an order.");
        }
        return cancelService.cancelOrder(((CancelOrderByUserReferenceParams) params).getUserReference());
    }
}
