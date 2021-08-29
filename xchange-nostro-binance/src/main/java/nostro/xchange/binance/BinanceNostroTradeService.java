package nostro.xchange.binance;

import info.bitrich.xchangestream.core.StreamingTradeService;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.processors.PublishProcessor;
import nostro.xchange.persistence.OrderEntity;
import nostro.xchange.persistence.OrderRepository;
import nostro.xchange.persistence.TransactionFactory;
import nostro.xchange.utils.NostroUtils;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

public class BinanceNostroTradeService implements TradeService, StreamingTradeService {
    private static final Logger LOG = LoggerFactory.getLogger(BinanceNostroTradeService.class);

    private final BinanceTradeService inner;
    private final TransactionFactory txFactory;

    final PublishProcessor<Order> orderPublisher = PublishProcessor.create();
    final PublishProcessor<UserTrade> tradePublisher = PublishProcessor.create();

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
        String document = NostroUtils.writeOrderDocument(order);

        txFactory.execute(tx -> tx.getOrderRepository().insert(id, document));
        
        return txFactory.executeAndGet(tx -> {
            tx.getOrderRepository().lockById(id);
            String externalId = placeOrderCallable.call();
            tx.getOrderRepository().update(id, externalId);
            return externalId;
        });
    }

    @Override
    public boolean cancelOrder(CancelOrderParams params) {
        String externalId = ((CancelOrderByIdParams) params).getOrderId();
        return txFactory.executeAndGet(tx -> {
            Optional<OrderEntity> orderEntity = tx.getOrderRepository().lockByExternalId(externalId);
            boolean cancelled = inner.cancelOrder(params);
            if (orderEntity.isPresent()) {
                Order order = NostroUtils.readOrderDocument(orderEntity.get().getDocument());
                order.setOrderStatus(Order.OrderStatus.CANCELED);
                String document = NostroUtils.writeOrderDocument(order);
                tx.getOrderRepository().updateByExternalId(externalId, document, true);
            } else {
                LOG.warn("Cancelled non-existing order: externalId={}", externalId);
            }
            return cancelled;
        });
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

    @Override
    public Flowable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
        return orderPublisher;
    }

    @Override
    public Flowable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
        return tradePublisher;
    }

    public void synchronizeOpenOrders() throws IOException {
        new SynchronizeOpenOrdersTask(inner, txFactory).call();
    }
}
