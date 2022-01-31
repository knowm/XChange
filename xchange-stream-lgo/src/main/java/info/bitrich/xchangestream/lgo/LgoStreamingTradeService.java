package info.bitrich.xchangestream.lgo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.lgo.domain.LgoGroupedUserUpdate;
import info.bitrich.xchangestream.lgo.domain.LgoMatchOrderEvent;
import info.bitrich.xchangestream.lgo.domain.LgoOrderEvent;
import info.bitrich.xchangestream.lgo.dto.LgoAckUpdate;
import info.bitrich.xchangestream.lgo.dto.LgoSocketPlaceOrder;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
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

public class LgoStreamingTradeService implements StreamingTradeService {

  private final LgoStreamingService streamingService;
  private final LgoKeyService keyService;
  private final LgoSignatureService signatureService;
  private final SynchronizedValueFactory<Long> nonceFactory;
  private final Map<CurrencyPair, LgoUserBatchSubscription> batchSubscriptions =
      new ConcurrentHashMap<>();
  private Observable<LgoOrderEvent> afrSubscription;

  LgoStreamingTradeService(
      LgoStreamingService streamingService,
      LgoKeyService keyService,
      LgoSignatureService signatureService,
      SynchronizedValueFactory<Long> nonceFactory) {
    this.streamingService = streamingService;
    this.keyService = keyService;
    this.signatureService = signatureService;
    this.nonceFactory = nonceFactory;
  }

  /** {@inheritDoc} First sent orders will be current open orders. */
  @Override
  public Observable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
    return getOrderBatchChanges(currencyPair).flatMap(Observable::fromIterable);
  }

  /**
   * Get an up-to-date view of all your open orders after each LGO batch execution. First sending
   * will be the actual open orders list.
   */
  public Observable<OpenOrders> getOpenOrders(CurrencyPair currencyPair) {
    return getOrderUpdates(currencyPair)
        .map(
            u ->
                u.getAllOpenOrders().values().stream()
                    .filter(order -> order instanceof LimitOrder)
                    .map(order -> (LimitOrder) order)
                    .collect(Collectors.toList()))
        .map(OpenOrders::new);
  }

  /**
   * Receive all updated orders, for each LGO batches. First sending will be the actual open orders
   * list.
   */
  public Observable<Collection<Order>> getOrderBatchChanges(CurrencyPair currencyPair) {
    return getOrderUpdates(currencyPair).map(LgoGroupedUserUpdate::getUpdatedOrders);
  }

  private Observable<LgoGroupedUserUpdate> getOrderUpdates(CurrencyPair currencyPair) {
    return batchSubscriptions
        .computeIfAbsent(currencyPair, this::createBatchSubscription)
        .getPublisher();
  }

  private LgoUserBatchSubscription createBatchSubscription(CurrencyPair currencyPair) {
    return LgoUserBatchSubscription.create(streamingService, currencyPair);
  }

  @Override
  public Observable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
    return getRawBatchOrderEvents(currencyPair)
        .filter(lgoOrderEvent -> "match".equals(lgoOrderEvent.getType()))
        .map(
            matchEvent -> LgoAdapter.adaptUserTrade(currencyPair, (LgoMatchOrderEvent) matchEvent));
  }

  /**
   * Receive all events for the selected currency pairs. Merges batch order events and ack (AFR)
   * events.
   */
  public Observable<LgoOrderEvent> getRawAllOrderEvents(Collection<CurrencyPair> currencyPairs) {
    Observable<LgoOrderEvent> ackObservable = getRawReceivedOrderEvents();
    return currencyPairs.stream()
        .map(this::getRawBatchOrderEvents)
        .reduce(Observable::mergeWith)
        .map(ackObservable::mergeWith)
        .orElse(ackObservable);
  }

  /**
   * Get ack for your placed orders. "received" events indicate the orderId associated to your
   * order, if you set a reference on order placement you will have it in the event. "failed" events
   * indicate that the order could not be read or was invalid and not added to a batch.
   */
  public Observable<LgoOrderEvent> getRawReceivedOrderEvents() {
    if (afrSubscription == null) {
      createAfrSubscription();
    }
    return afrSubscription;
  }

  private void createAfrSubscription() {
    final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
    afrSubscription =
        streamingService
            .subscribeChannel("afr")
            .map(s -> mapper.readValue(s.toString(), LgoAckUpdate.class))
            .map(LgoAckUpdate::getData)
            .flatMap(Observable::<LgoOrderEvent>fromIterable)
            .share();
  }

  /**
   * Get all events of your orders happened during batch execution, for a currency pair. "pending"
   * events indicate that the order was added to a batch and received by the execution engine.
   * "invalid" events indicate that the order was not suitable for execution. "match" events
   * indicate that the order did match against another order. "open" events indicate that the order
   * entered the order book. "done" events indicate that the order was filled, canceled or rejected.
   */
  public Observable<LgoOrderEvent> getRawBatchOrderEvents(CurrencyPair currencyPair) {
    return getOrderUpdates(currencyPair)
        .map(LgoGroupedUserUpdate::getEvents)
        .flatMap(Observable::fromIterable);
  }

  /**
   * Place a market order
   *
   * @return the order reference
   */
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    Long ref = nonceFactory.createValue();
    LgoPlaceOrder lgoOrder = LgoAdapters.adaptEncryptedMarketOrder(marketOrder);
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
    LgoSocketPlaceOrder placeOrder =
        new LgoSocketPlaceOrder(
            new LgoEncryptedOrder(lgoKey.getId(), encryptedOrder, signature, ref));
    String payload = StreamingObjectMapperHelper.getObjectMapper().writeValueAsString(placeOrder);
    streamingService.sendMessage(payload);
    return ref.toString();
  }
}
