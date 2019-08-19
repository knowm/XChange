package info.bitrich.xchangestream.lgo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.lgo.domain.LgoMatchOrderEvent;
import info.bitrich.xchangestream.lgo.domain.LgoOrderEvent;
import info.bitrich.xchangestream.lgo.dto.*;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.lgo.LgoAdapters;
import org.knowm.xchange.lgo.dto.key.LgoKey;
import org.knowm.xchange.lgo.dto.order.LgoEncryptedOrder;
import org.knowm.xchange.lgo.dto.order.LgoOrderSignature;
import org.knowm.xchange.lgo.dto.order.LgoPlaceCancelOrder;
import org.knowm.xchange.lgo.dto.order.LgoPlaceOrder;
import org.knowm.xchange.lgo.service.CryptoUtils;
import org.knowm.xchange.lgo.service.LgoKeyService;
import org.knowm.xchange.lgo.service.LgoSignatureService;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class LgoStreamingTradeService implements StreamingTradeService {

    private final LgoStreamingService streamingService;
    private final LgoKeyService keyService;
    private final LgoSignatureService signatureService;
    private final SynchronizedValueFactory<Long> nonceFactory;
    private final Map<CurrencyPair, Map<String, Order>> allOrders = new HashMap<>();
    private final Map<CurrencyPair, Observable<LgoBatchUpdate>> orderBatchChangesSubscriptions = new HashMap<>();
    private Observable<LgoOrderEvent> afrSubscription;

    public LgoStreamingTradeService(LgoStreamingService streamingService, LgoKeyService keyService, LgoSignatureService signatureService, SynchronizedValueFactory<Long> nonceFactory) {
        this.streamingService = streamingService;
        this.keyService = keyService;
        this.signatureService = signatureService;
        this.nonceFactory = nonceFactory;
    }

    /**
     * {@inheritDoc}
     * First sent orders will be current open orders.
     */
    @Override
    public Observable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
        return getOrderBatchChanges(currencyPair)
                .flatMap(Observable::fromIterable);
    }

    /**
     * Get an up-to-date view of all your open orders after each LGO batch execution.
     * First sending will be the actual open orders list.
     */
    public Observable<OpenOrders> getOpenOrders(CurrencyPair currencyPair) {
        return getOrderBatchChanges(currencyPair)
                .map(updatedOrders -> allOrders.get(currencyPair)
                        .values().stream()
                        .filter(order -> order instanceof LimitOrder)
                        .map(order -> (LimitOrder)order)
                        .collect(Collectors.toList())
                )
                .map(OpenOrders::new);
    }

    /**
     * Receive all updated orders, for each LGO batches.
     * First sending will be the actual open orders list.
     */
    public Observable<Collection<Order>> getOrderBatchChanges(CurrencyPair currencyPair) {
        return getOrderUpdates(currencyPair)
                .map(LgoBatchUpdate::getUpdatedOrders);
    }

    private Observable<LgoBatchUpdate> getOrderUpdates(CurrencyPair currencyPair) {
        if (!orderBatchChangesSubscriptions.containsKey(currencyPair)) {
            allOrders.put(currencyPair, new HashMap<>());
            createOrderBatchChangesSubscription(currencyPair);
        }
        return orderBatchChangesSubscriptions.get(currencyPair).share();
    }

    private void createOrderBatchChangesSubscription(CurrencyPair currencyPair) {
        final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
        Observable<LgoBatchUpdate> orderBatchChanges = streamingService
                .subscribeChannel(LgoAdapter.channelName("user", currencyPair))
                .map(s -> mapper.readValue(s.toString(), LgoUserUpdate.class))
                .map(s -> {
                    List<LgoOrderEvent> events = new ArrayList<>();
                    List<Order> updatedOrders = handleUserUpdate(currencyPair, s);
                    if (s.getType().equals("update")) {
                        events.addAll(LgoAdapter.adaptOrderEvent(s.getOrderEvents(), s.getBatchId(), updatedOrders));
                    }
                    return new LgoBatchUpdate(updatedOrders, events, s.getBatchId(), s.getType());
                });
        orderBatchChangesSubscriptions.put(currencyPair, orderBatchChanges);
    }

    private List<Order> handleUserUpdate(CurrencyPair currencyPair, LgoUserUpdate s) {
        List<Order> result = new ArrayList<>();
        if (s.getType().equals("snapshot")) {
            allOrders.get(currencyPair).clear();
            Collection<LimitOrder> openOrders = LgoAdapter.adaptOrdersSnapshot(s.getOrderEvents(), currencyPair);
            allOrders.get(currencyPair).putAll(openOrders.stream().collect(toMap(LimitOrder::getId, this::copyOrder)));
            result.addAll(openOrders);
        } else {
            List<Order> updatedOrders = updateAllOrders(currencyPair, s.getOrderEvents());
            result.addAll(updatedOrders);
        }
        return result;
    }

    private List<Order> updateAllOrders(CurrencyPair currencyPair, List<LgoUserUpdateData> orderEvents) {
        List<Order> updatedOrders = new ArrayList<>();
        for (LgoUserUpdateData orderEvent : orderEvents) {
            switch (orderEvent.getType()) {
                case "pending":
                    updatedOrders.add(addPendingOrder(currencyPair, orderEvent));
                    break;
                case "open":
                    updatedOrders.add(openOrder(currencyPair, orderEvent));
                    break;
                case "match":
                    updatedOrders.add(matchOrder(currencyPair, orderEvent));
                    break;
                case "invalid":
                case "done":
                    updatedOrders.add(removeDoneOrder(currencyPair, orderEvent));
                    break;
            }
        }
        return updatedOrders;
    }

    private Order removeDoneOrder(CurrencyPair currencyPair, LgoUserUpdateData orderEvent) {
        Order doneOrder = allOrders.get(currencyPair).remove(orderEvent.getOrderId());
        if (orderEvent.getReason().equals("canceledBySelfTradePrevention") || orderEvent.getReason().equals("canceled")) {
            doneOrder.setOrderStatus(doneOrder.getStatus() == Order.OrderStatus.PARTIALLY_FILLED ? Order.OrderStatus.PARTIALLY_CANCELED : Order.OrderStatus.CANCELED);
        } else if (orderEvent.getReason().equals("filled")) {
            doneOrder.setOrderStatus(Order.OrderStatus.FILLED);
        } else {
            doneOrder.setOrderStatus(Order.OrderStatus.REJECTED);
        }
        return copyOrder(doneOrder);
    }

    private Order matchOrder(CurrencyPair currencyPair, LgoUserUpdateData orderEvent) {
        Order matchedOrder = allOrders.get(currencyPair).get(orderEvent.getOrderId());
        matchedOrder.setOrderStatus(Order.OrderStatus.PARTIALLY_FILLED);
        matchedOrder.setCumulativeAmount(matchedOrder.getOriginalAmount().subtract(orderEvent.getRemainingQuantity()));
        BigDecimal fee = matchedOrder.getFee() == null ? orderEvent.getFees() : matchedOrder.getFee().add(orderEvent.getFees());
        matchedOrder.setFee(fee);
        return copyOrder(matchedOrder);
    }

    private Order openOrder(CurrencyPair currencyPair, LgoUserUpdateData orderEvent) {
        Order pendingOrder = allOrders.get(currencyPair).get(orderEvent.getOrderId());
        Order.OrderStatus status = pendingOrder.getStatus().equals(Order.OrderStatus.PARTIALLY_FILLED) ? pendingOrder.getStatus() : Order.OrderStatus.NEW;
        pendingOrder.setOrderStatus(status);
        return copyOrder(pendingOrder);
    }

    private Order addPendingOrder(CurrencyPair currencyPair, LgoUserUpdateData orderEvent) {
        Order order = LgoAdapter.adaptPendingOrder(orderEvent, currencyPair);
        allOrders.get(currencyPair).put(order.getId(), order);
        return copyOrder(order);
    }

    private Order copyOrder(Order order) {
        Order copy = order instanceof LimitOrder ? LimitOrder.Builder.from(order).build() : MarketOrder.Builder.from(order).build();
        // because actual released version of xchange-core has buggy Builder.from methods
        copy.setFee(order.getFee());
        copy.setCumulativeAmount(order.getCumulativeAmount());
        // https://github.com/knowm/XChange/pull/3163
        return copy;
    }

    @Override
    public Observable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
        return getBatchOrderEvents(currencyPair)
                .filter(lgoOrderEvent -> lgoOrderEvent.getType().equals("match"))
                .map(matchEvent -> LgoAdapter.adaptUserTrade(currencyPair, (LgoMatchOrderEvent) matchEvent));
    }

    /**
     * Receive all events for the selected currency pairs. Merges batch order events and ack (AFR)
     * events.
     */
    public Observable<LgoOrderEvent> getAllOrderEvents(Collection<CurrencyPair> currencyPairs) {
        Observable<LgoOrderEvent> ackObservable = getReceivedOrderEvents();
        Optional<Observable<LgoOrderEvent>> batchObservable = currencyPairs.stream()
                .map(this::getBatchOrderEvents)
                .reduce(Observable::mergeWith);
        return batchObservable.isPresent() ? ackObservable.mergeWith(batchObservable.get()) : ackObservable;
    }

    /**
     * Get ack for your placed orders. "received" events indicate the orderId associated to your
     * order, if you set a reference on order placement you will have it in the event. "failed" events
     * indicate that the order could not be read or was invalid and not added to a batch.
     */
    public Observable<LgoOrderEvent> getReceivedOrderEvents() {
        if (afrSubscription == null) {
            createAfrSubscription();
        }
        return afrSubscription.share();
    }

    private void createAfrSubscription() {
        final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
        afrSubscription = streamingService
                .subscribeChannel("afr")
                .map(s -> mapper.readValue(s.toString(), LgoAckUpdate.class))
                .map(LgoAckUpdate::getData)
                .flatMap(Observable::fromIterable)
                .map(LgoAdapter::adaptReceivedEvent);
    }

    /**
     * Get all events of your orders happened during batch execution, for a currency pair. "pending"
     * events indicate that the order was added to a batch and received by the execution engine.
     * "invalid" events indicate that the order was not suitable for execution. "match" events
     * indicate that the order did match against another order. "open" events indicate that the order
     * entered the order book. "done" events indicate that the order was filled, canceled or
     * rejected.
     */
    public Observable<LgoOrderEvent> getBatchOrderEvents(CurrencyPair currencyPair) {
        return getOrderUpdates(currencyPair)
                .map(LgoBatchUpdate::getEvents)
                .flatMap(Observable::fromIterable);
    }

    /**
     * Place a market order
     *
     * @return the order reference
     */
    public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
        Long ref = nonceFactory.createValue();
        LgoPlaceOrder lgoOrder = LgoAdapters.adaptMarketOrder(marketOrder);
        return placeOrder(ref, lgoOrder);
    }

    /**
     * Place a limit order
     *
     * @return the order reference
     */
    public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
        Long ref = nonceFactory.createValue();
        LgoPlaceOrder lgoOrder = LgoAdapters.adaptLimitOrder(limitOrder);
        return placeOrder(ref, lgoOrder);
    }

    /**
     * Place a cancel order
     *
     * @return true
     */
    public boolean cancelOrder(String orderId) throws IOException {
        Long ref = nonceFactory.createValue();
        LgoPlaceCancelOrder lgoOrder = new LgoPlaceCancelOrder(ref, orderId, new Date().toInstant());
        placeOrder(ref, lgoOrder);
        return true;
    }

    private String placeOrder(Long ref, LgoPlaceOrder lgoOrder) throws JsonProcessingException {
        LgoKey lgoKey = keyService.selectKey();
        String encryptedOrder = CryptoUtils.encryptOrder(lgoKey, lgoOrder);
        LgoOrderSignature signature = signatureService.signOrder(encryptedOrder);
        LgoSocketPlaceOrder placeOrder = new LgoSocketPlaceOrder(new LgoEncryptedOrder(lgoKey.getId(), encryptedOrder, signature, ref));
        String payload = StreamingObjectMapperHelper.getObjectMapper().writeValueAsString(placeOrder);
        streamingService.sendMessage(payload);
        return ref.toString();
    }
}
