package nostro.xchange.binance;

import nostro.xchange.persistence.OrderEntity;
import nostro.xchange.persistence.OrderRepository;
import nostro.xchange.persistence.TransactionFactory;
import nostro.xchange.utils.NostroUtils;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

public class BinanceNostroTradeService implements TradeService {
    private static final Logger LOG = LoggerFactory.getLogger(BinanceNostroTradeService.class);

    private final BinanceTradeService inner;
    private final TransactionFactory txFactory;

    // TODO: check if need to unwrap original IOException's happening in transactions
    public BinanceNostroTradeService(BinanceTradeService inner, TransactionFactory txFactory) {
        this.inner = inner;
        this.txFactory = txFactory;
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
        
        return txFactory.executeAndGet(tx -> {
            tx.getOrderRepository().lockById(id);
            String externalId = placeOrderCallable.call();
            tx.getOrderRepository().update(id, externalId);
            return externalId;
        });
    }

    @Override
    public boolean cancelOrder(CancelOrderParams params) throws IOException {
        return inner.cancelOrder(params);
    }

    @Override
    public Collection<Order> getOrder(String... orderIds) {
        List<OrderEntity> entities = new ArrayList<>();
        txFactory.execute(tx -> {
            OrderRepository repo = tx.getOrderRepository();
            for (String orderId : orderIds) {
                entities.add(repo.findByExternalId(orderId)
                        .orElseThrow(() -> new IllegalArgumentException("Order with external_id=\"" + orderId + "\" not found")));
            }
        });
        return new ArrayList<>(NostroUtils.readOrderList(entities));
    }

    @Override
    public OpenOrders getOpenOrders() {
        List<OrderEntity> entities = txFactory.executeAndGet(tx -> tx.getOrderRepository().findAllOpen());
        return NostroUtils.adaptOpenOrders(NostroUtils.readOrderList(entities));
    }
}
