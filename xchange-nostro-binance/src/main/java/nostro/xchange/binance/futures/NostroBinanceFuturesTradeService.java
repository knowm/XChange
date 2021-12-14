package nostro.xchange.binance.futures;

import info.bitrich.xchangestream.binance.dto.ExecutionReportBinanceUserTransaction.*;
import info.bitrich.xchangestream.binance.futures.dto.OrderTradeUpdate;
import info.bitrich.xchangestream.binance.futures.dto.OrderTradeUpdateBinanceUserTransaction;
import nostro.xchange.binance.BinanceCancelService;
import nostro.xchange.binance.NostroBinanceUtils;
import nostro.xchange.persistence.OrderEntity;
import nostro.xchange.persistence.OrderRepository;
import nostro.xchange.persistence.TransactionFactory;
import nostro.xchange.utils.NostroUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.knowm.xchange.binance.futures.BinanceFuturesAdapter;
import org.knowm.xchange.binance.futures.service.BinanceFuturesTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.*;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class NostroBinanceFuturesTradeService implements TradeService {
    private static final Logger LOG = LoggerFactory.getLogger(NostroBinanceFuturesTradeService.class);

    private final BinanceFuturesTradeService inner;
    private final TransactionFactory txFactory;
    private final BinanceCancelService cancelService;

    public NostroBinanceFuturesTradeService(BinanceFuturesTradeService inner, TransactionFactory txFactory) {
        this.inner = inner;
        this.txFactory = txFactory;
        this.cancelService = new BinanceCancelService(txFactory, inner);
    }

    @Override
    public OpenOrders getOpenOrders() throws IOException {
        List<OrderEntity> entities = txFactory.executeAndGet(tx -> tx.getOrderRepository().findAllOpen());
        return NostroUtils.adaptOpenOrders(NostroUtils.readOrderList(entities));
    }

    @Override
    public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
        OpenOrders openOrders = getOpenOrders();
        if (params != null) {
            openOrders = new OpenOrders(
                    openOrders.getOpenOrders().stream().filter(params::accept).collect(Collectors.toList()),
                    openOrders.getHiddenOrders().stream().filter(params::accept).collect(Collectors.toList())
            );
        }
        return openOrders;
    }

    @Override
    public OpenOrdersParams createOpenOrdersParams() {
        return inner.createOpenOrdersParams();
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
        Instrument instrument = order.getInstrument();
        if (!(instrument instanceof FuturesContract)) {
            throw new NotAvailableFromExchangeException("not supported instrument " + instrument);
        }

        String id = order.getUserReference();
        OrderEntity e = new OrderEntity.Builder()
                .id(id)
                .instrument(instrument.toString())
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
    public boolean cancelOrder(String orderId) throws IOException {
        LOG.info("cancel order id={}", orderId);
        String userReference = txFactory.executeAndGet(tx ->
                tx.getOrderRepository().findByExternalId(orderId)
                        .orElseThrow(() -> new IllegalArgumentException("Order with external_id=\"" + orderId + "\" not found")).getId()
        );

        return cancelOrder(new DefaultCancelOrderByUserReferenceParams(userReference));
    }

    @Override
    public boolean cancelOrder(CancelOrderParams params) throws IOException {
        if (!(params instanceof CancelOrderByUserReferenceParams)) {
            throw new ExchangeException("You need to provide user reference to cancel an order.");
        }
        return cancelService.cancelOrder(((CancelOrderByUserReferenceParams) params).getUserReference());
    }

    @Override
    public Collection<Order> getOrder(String... orderIds) throws IOException {
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

    Pair<Order, UserTrade> saveExecutionReport(OrderTradeUpdateBinanceUserTransaction report) {
        return txFactory.executeAndGet(tx -> {
            final OrderTradeUpdate orderTradeUpdate = report.getOrderTradeUpdate();
            final Order order = report.toOrder();
            String orderId = order.getUserReference();
            String document = NostroUtils.writeOrderDocument(order);
            boolean terminal = NostroBinanceUtils.isTerminal(orderTradeUpdate.getCurrentOrderStatus());
            Timestamp updated = new Timestamp(orderTradeUpdate.getTimestamp());
            Timestamp created = order.getStatus() == Order.OrderStatus.NEW ? updated : null;

            Optional<OrderEntity> orderEntity = tx.getOrderRepository().lockById(orderId);
            if (orderEntity.isPresent()) {
                if (created != null && orderEntity.get().getCreated().before(created)) {
                    LOG.debug("update order create date: id={}, created={}", orderId, created);
                    tx.getOrderRepository().updateCreatedById(orderId, created);
                }
                LOG.debug("update order id={}, updated={}, terminal={}", orderId, updated, terminal);
                tx.getOrderRepository().updateById(orderId, document, terminal, updated);
            } else {
                LOG.warn("Received transaction of non-existing order: id={}, externalId={}", orderId, orderTradeUpdate.getOrderId());

                tx.getOrderRepository().insert(new OrderEntity.Builder()
                        .id(orderId)
                        .externalId(Long.toString(orderTradeUpdate.getOrderId()))
                        .instrument(order.getInstrument().toString())
                        .terminal(terminal)
                        .document(document)
                        .created(created)
                        .updated(updated)
                        .build());
            }

            UserTrade trade = null;
            if (ExecutionType.TRADE == orderTradeUpdate.getExecutionType()) {
                trade = report.toUserTrade();
                String externalId = Long.toString(orderTradeUpdate.getTradeId());
                LOG.debug("insert trade orderId={}, externalId={}, updated={}", orderId, externalId, updated);
                tx.getTradeRepository().insert(orderId, externalId, updated, NostroUtils.writeTradeDocument(trade));
            }

            return Pair.of(order, trade);
        });
    }

    @Override
    public TradeHistoryParams createTradeHistoryParams() {
        return inner.createTradeHistoryParams();
    }
}
