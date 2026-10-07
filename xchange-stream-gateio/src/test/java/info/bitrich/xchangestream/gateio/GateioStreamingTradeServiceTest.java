package info.bitrich.xchangestream.gateio;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.response.GateioWsNotification;
import info.bitrich.xchangestream.gateio.dto.response.order.GateioMultipleOrderFuturesNotification;
import info.bitrich.xchangestream.gateio.dto.response.order.GateioMultipleOrderSpotNotification;
import info.bitrich.xchangestream.gateio.dto.response.usertrade.GateioMultipleUserTradeNotification;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.TestObserver;
import io.reactivex.rxjava3.subjects.PublishSubject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.gateio.GateioResilience;
import org.knowm.xchange.instrument.Instrument;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.knowm.xchange.dto.Order.OrderStatus.FILLED;
import static org.knowm.xchange.dto.Order.OrderStatus.OPEN;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GateioStreamingTradeServiceTest {

  @Mock GateioStreamingService gateioStreamingService;
  @Mock
  GateioUserTradeStreamingService gateioUserTradeStreamingService;
  GateioStreamingTradeService gateioStreamingTradeService;
  Map<Instrument, InstrumentMetaData> instrumentsMetaData = new HashMap<>();
  ExchangeMetaData exchangeMetaData;
  ObjectMapper objectMapper = Config.getInstance().getObjectMapper();
  Instrument instrumentFuture = new FuturesContract(CurrencyPair.ETH_USDT, "PERP");

  @BeforeEach
  void setup() {
    InstrumentMetaData instrumentMetaData = InstrumentMetaData.builder().contractValue(new BigDecimal("0.01")).build();
    instrumentsMetaData.put(instrumentFuture, instrumentMetaData);
    exchangeMetaData = new ExchangeMetaData(instrumentsMetaData, null, null, null, null);
    gateioStreamingTradeService = new GateioStreamingTradeService(gateioStreamingService, exchangeMetaData
        , gateioUserTradeStreamingService, GateioResilience.createRegistries(true));
  }

  @Test
  void user_trades_btc() throws Exception {
    GateioWsNotification multipleNotification = readNotification("spot.usertrades.update.json");
    assertThat(multipleNotification).isInstanceOf(GateioMultipleUserTradeNotification.class);
    GateioWsNotification notification =
        ((GateioMultipleUserTradeNotification) multipleNotification).toSingleNotifications().get(0);

    when(gateioStreamingService.subscribeChannel(eq("spot.usertrades"), eq(CurrencyPair.BTC_USDT)))
        .thenReturn(Observable.just(notification));

    Observable<UserTrade> observable =
        gateioStreamingTradeService.getUserTrades(CurrencyPair.BTC_USDT);

    TestObserver<UserTrade> testObserver = observable.test();

    UserTrade actual = testObserver.awaitCount(1).values().get(0);

    testObserver.dispose();

    UserTrade expected =
        UserTrade.builder()
            .instrument(CurrencyPair.BTC_USDT)
            .id("6068323582")
            .orderId("381004078014")
            .orderUserReference("3")
            .originalAmount(new BigDecimal("0.00003"))
            .feeAmount(new BigDecimal("0.00000006"))
            .feeCurrency(Currency.BTC)
            .price(new BigDecimal("29441.1"))
            .timestamp(Date.from(Instant.ofEpochMilli(1605176741123L)))
            .type(OrderType.BID)
            .build();

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void order_changes_btc() throws Exception {
    GateioWsNotification multipleNotification = readNotification("spot.orders.update.json");
    assertThat(multipleNotification).isInstanceOf(GateioMultipleOrderSpotNotification.class);
    GateioWsNotification notification =
        ((GateioMultipleOrderSpotNotification) multipleNotification).toSingleNotifications().get(0);
    when(gateioStreamingService.subscribeChannel(eq("spot.orders"), eq(CurrencyPair.BTC_USDT)))
        .thenReturn(Observable.just(notification));

    Observable<Order> observable =
        gateioStreamingTradeService.getOrderChanges(CurrencyPair.BTC_USDT);

    TestObserver<Order> testObserver = observable.test();

    testObserver.awaitCount(1).assertNoErrors().assertValueCount(1);
    Order actual = testObserver.values().get(0);

    testObserver.dispose();

    assertThat(actual.getId()).isEqualTo("399123456");
    assertThat(actual.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
    assertThat(actual.getType()).isEqualTo(OrderType.ASK);
    assertThat(actual.getOriginalAmount()).isEqualByComparingTo("0.0001");
    assertThat(actual.getStatus()).isEqualTo(OPEN);
    assertThat(actual.getUserReference()).isEqualTo("t-testtext");
    assertThat(actual.getTimestamp().getTime()).isEqualTo(1694655225315L);
    assertThat(actual instanceof LimitOrder).isTrue();
    assertThat(((LimitOrder) actual).getLimitPrice()).isEqualTo(new BigDecimal("26253.3"));
  }

  @Test
  void order_changes_future_market() throws Exception {
    GateioWsNotification multipleNotification = readNotification("futures.market.orders.update.json");
    assertThat(multipleNotification).isInstanceOf(GateioMultipleOrderFuturesNotification.class);
    GateioWsNotification notification =
        ((GateioMultipleOrderFuturesNotification) multipleNotification).toSingleNotifications().get(0);

    Instrument futuresContract = new FuturesContract(CurrencyPair.ETH_USDT, "PERP");

    when(gateioStreamingService.subscribeChannel(eq("futures.orders"), eq(futuresContract)))
        .thenReturn(Observable.just(notification));

    Observable<Order> observable =
        gateioStreamingTradeService.getOrderChanges(futuresContract);

    TestObserver<Order> testObserver = observable.test();

    Order actual = testObserver.awaitCount(1).values().get(0);

    testObserver.dispose();

    assertThat(actual.getId()).isEqualTo("63894852561217706");
    assertThat(actual.getType()).isEqualTo(OrderType.BID);
    assertThat(actual.getOriginalAmount()).isEqualByComparingTo("0.001");
    assertThat(actual.getTimestamp().getTime()).isEqualTo(1781206401495L);
    assertThat(actual.getInstrument()).isEqualTo(futuresContract);
    assertThat(actual.getRemainingAmount()).isEqualByComparingTo(BigDecimal.ZERO);
    assertThat(actual.getAveragePrice()).isEqualTo(new BigDecimal("1688.72"));
    assertThat(actual instanceof MarketOrder).isEqualTo(true);
    assertThat(actual.getFee()).isEqualTo(new BigDecimal("0.00084436"));
  }

  @Test
  void order_changes_future_limit_new() throws Exception {
    GateioWsNotification multipleNotification = readNotification("futures.limit.new.orders.update.json");
    assertThat(multipleNotification).isInstanceOf(GateioMultipleOrderFuturesNotification.class);
    GateioWsNotification notification =
        ((GateioMultipleOrderFuturesNotification) multipleNotification).toSingleNotifications().get(0);

    Instrument futuresContract = new FuturesContract(CurrencyPair.ETH_USDT, "PERP");

    when(gateioStreamingService.subscribeChannel(eq("futures.orders"), eq(futuresContract)))
        .thenReturn(Observable.just(notification));

    Observable<Order> observable =
        gateioStreamingTradeService.getOrderChanges(futuresContract);

    TestObserver<Order> testObserver = observable.test();

    Order actual = testObserver.awaitCount(1).values().get(0);

    testObserver.dispose();

    assertThat(actual.getId()).isEqualTo("63894852562023389");
    assertThat(actual.getType()).isEqualTo(OrderType.BID);
    assertThat(actual.getOriginalAmount()).isEqualByComparingTo("0.001");
    assertThat(actual.getTimestamp().getTime()).isEqualTo(1781207041467L);
    assertThat(actual.getInstrument()).isEqualTo(futuresContract);
    assertThat(actual.getRemainingAmount()).isEqualByComparingTo(new BigDecimal("0.001"));
    assertThat(actual.getAveragePrice()).isEqualByComparingTo(BigDecimal.ZERO);
    assertThat(actual instanceof LimitOrder).isEqualTo(true);
    assert actual instanceof LimitOrder;
    assertThat(((LimitOrder) actual).getLimitPrice()).isEqualTo(new BigDecimal("1686"));
    assertThat(actual.getFee()).isEqualByComparingTo(BigDecimal.ZERO);
    assertThat(actual.getStatus().compareTo(OPEN));
  }

  @Test
  void order_changes_future_limit_filled() throws Exception {
    GateioWsNotification multipleNotification = readNotification("futures.limit.filled.orders.update.json");
    assertThat(multipleNotification).isInstanceOf(GateioMultipleOrderFuturesNotification.class);
    GateioWsNotification notification =
        ((GateioMultipleOrderFuturesNotification) multipleNotification).toSingleNotifications().get(0);

    Instrument futuresContract = new FuturesContract(CurrencyPair.ETH_USDT, "PERP");

    when(gateioStreamingService.subscribeChannel(eq("futures.orders"), eq(futuresContract)))
        .thenReturn(Observable.just(notification));

    Observable<Order> observable =
        gateioStreamingTradeService.getOrderChanges(futuresContract);

    TestObserver<Order> testObserver = observable.test();

    Order actual = testObserver.awaitCount(1).values().get(0);

    testObserver.dispose();

    assertThat(actual.getId()).isEqualTo("63894852562023389");
    assertThat(actual.getType()).isEqualTo(OrderType.BID);
    assertThat(actual.getOriginalAmount()).isEqualByComparingTo("0.001");
    assertThat(actual.getTimestamp().getTime()).isEqualTo(1781207048448L);
    assertThat(actual.getInstrument()).isEqualTo(futuresContract);
    assertThat(actual.getRemainingAmount()).isEqualByComparingTo(BigDecimal.ZERO);
    assertThat(actual.getAveragePrice()).isEqualTo(new BigDecimal("1686"));
    assertThat(actual instanceof LimitOrder).isEqualTo(true);
    assertThat(actual.getFee()).isEqualTo(new BigDecimal("0.0003372"));
    assertThat(actual.getStatus().compareTo(FILLED));
  }

  @Test
  void place_limit_order_future_first_message_error() throws Exception {
    String errorJson = "{\"header\":{\"response_time\":\"1791112811535\",\"status\":\"400\"," +
        "\"event\":\"api\",\"client_id\":\"0xc2487f6708\",\"conn_id\":\"a2b43cee21da6bac\"" +
        ",\"conn_trace_id\":\"197b19d544a92b8fe0ff5474de9ed973\",\"trace_id\":\"e1d9972b115b00848ea79e174558772a\"" +
        ",\"x_in_time\":1791112811535779,\"x_out_time\":1791112811535823},\"data\":{\"errs\":{\"label\":\"PARAM_ERROR\"" +
        ",\"message\":\"req channel not support\"}},\"request_id\":\"1791112811\"}";
    JsonNode errorNode = objectMapper.readTree(errorJson);

    when(gateioUserTradeStreamingService.subscribeChannel(eq(Config.FUTURES_ORDER_PLACE_CHANNEL), anyString(), any(), any()))
        .thenReturn(Observable.just(errorNode));

    LimitOrder order = new LimitOrder(OrderType.BID, new BigDecimal("1"), instrumentFuture, "1", new Date(), new BigDecimal("100"));
    Single<Integer> single = gateioStreamingTradeService.placeLimitOrder(order);

    TestObserver<Integer> testObserver = single.test();
    testObserver.awaitDone(500, TimeUnit.MILLISECONDS);
    testObserver.assertComplete();
    testObserver.assertValue(-1);
  }

  @Test
  void place_limit_order_future_two_messages_both_success() throws Exception {
    String ackJson = """
        {
          "request_id": "123",
          "header": {
            "status": "200",
            "channel": "futures.order_place"
          },
          "data": {
            "result": {}
          }
        }
        """;
    String orderResultJson = """
        {
          "request_id": "123",
          "header": {
            "status": "200",
            "channel": "futures.order_place"
          },
          "data": {
            "result": {}
          }
        }
        """;
    JsonNode ackNode = objectMapper.readTree(ackJson);
    JsonNode resultNode = objectMapper.readTree(orderResultJson);

    PublishSubject<JsonNode> subject = PublishSubject.create();
    when(gateioUserTradeStreamingService.subscribeChannel(eq(Config.FUTURES_ORDER_PLACE_CHANNEL), anyString(), any(), any()))
        .thenReturn(subject);

    LimitOrder order = new LimitOrder(OrderType.BID, new BigDecimal("1"), instrumentFuture, "1", new Date(), new BigDecimal("100"));
    Single<Integer> single = gateioStreamingTradeService.placeLimitOrder(order);

    TestObserver<Integer> testObserver = single.test();
    subject.onNext(ackNode);
    subject.onNext(resultNode);

    testObserver.awaitDone(500, TimeUnit.MILLISECONDS);
    testObserver.assertComplete();
    testObserver.assertValue(0);
  }

  @Test
  void place_limit_order_future_second_message_error() throws Exception {
    String ackJson = """
        {
          "request_id": "123",
          "header": {
            "status": "200",
            "channel": "futures.order_place"
          },
          "data": {
            "result": {}
          }
        }
        """;
    String errorJson = """
        {
          "request_id": "123",
          "header": {
            "status": "400",
            "channel": "futures.order_place"
          },
          "data": {
            "errs": {
              "label": "INVALID_PARAM_VALUE",
              "message": "order size too small"
            }
          }
        }
        """;
    JsonNode ackNode = objectMapper.readTree(ackJson);
    JsonNode errorNode = objectMapper.readTree(errorJson);

    PublishSubject<JsonNode> subject = PublishSubject.create();
    when(gateioUserTradeStreamingService.subscribeChannel(eq(Config.FUTURES_ORDER_PLACE_CHANNEL), anyString(), any(), any()))
        .thenReturn(subject);

    LimitOrder order = new LimitOrder(OrderType.BID, new BigDecimal("1"), instrumentFuture, "1", new Date(), new BigDecimal("100"));
    Single<Integer> single = gateioStreamingTradeService.placeLimitOrder(order);

    TestObserver<Integer> testObserver = single.test();
    subject.onNext(ackNode);
    subject.onNext(errorNode);

    testObserver.awaitDone(500, TimeUnit.MILLISECONDS);
    testObserver.assertComplete();
    testObserver.assertValue(2);
  }

  @Test
  void place_limit_order_future_single_success_message_timeout() throws Exception {
    String ackJson = """
        {
          "request_id": "123",
          "header": {
            "status": "200",
            "channel": "futures.order_place"
          },
          "data": {
            "result": {}
          }
        }
        """;
    JsonNode ackNode = objectMapper.readTree(ackJson);

    PublishSubject<JsonNode> subject = PublishSubject.create();
    when(gateioUserTradeStreamingService.subscribeChannel(eq(Config.FUTURES_ORDER_PLACE_CHANNEL), anyString(), any(), any()))
        .thenReturn(subject);

    LimitOrder order = new LimitOrder(OrderType.BID, new BigDecimal("1"), instrumentFuture, "1", new Date(), new BigDecimal("100"));
    Single<Integer> single = gateioStreamingTradeService.placeLimitOrder(order);

    TestObserver<Integer> testObserver = single.test();
    subject.onNext(ackNode);

    testObserver.awaitDone(1500, TimeUnit.MILLISECONDS);
    testObserver.assertComplete();
    testObserver.assertValue(-1);
  }

  @Test
  void place_limit_order_future_single_success_message_error() throws Exception {
    String ackJson = """
        {
          "request_id": "123",
          "header": {
            "status": "200",
            "channel": "futures.order_place"
          },
          "data": {
            "result": {}
          }
        }
        """;
    JsonNode ackNode = objectMapper.readTree(ackJson);

    when(gateioUserTradeStreamingService.subscribeChannel(eq(Config.FUTURES_ORDER_PLACE_CHANNEL), anyString(), any(), any()))
        .thenReturn(Observable.just(ackNode));

    LimitOrder order = new LimitOrder(OrderType.BID, new BigDecimal("1"), instrumentFuture, "1", new Date(), new BigDecimal("100"));
    Single<Integer> single = gateioStreamingTradeService.placeLimitOrder(order);

    TestObserver<Integer> testObserver = single.test();

    testObserver.awaitDone(500, TimeUnit.MILLISECONDS);
    testObserver.assertComplete();
    testObserver.assertValue(-1);
  }

  @Test
  void place_market_order_future_first_message_error() throws Exception {
    String errorJson = """
        {
          "request_id": "123",
          "header": {
            "status": "400",
            "channel": "futures.order_place"
          },
          "data": {
            "errs": {
              "label": "INSUFFICIENT_AVAILABLE",
              "message": "insufficient balance"
            }
          }
        }
        """;
    JsonNode errorNode = objectMapper.readTree(errorJson);

    when(gateioUserTradeStreamingService.subscribeChannel(eq(Config.FUTURES_ORDER_PLACE_CHANNEL), anyString(), any(), any()))
        .thenReturn(Observable.just(errorNode));

    MarketOrder order = new MarketOrder(OrderType.BID, new BigDecimal("1"), instrumentFuture, "1", new Date());
    Single<Integer> single = gateioStreamingTradeService.placeMarketOrder(order);

    TestObserver<Integer> testObserver = single.test();
    testObserver.awaitDone(500, TimeUnit.MILLISECONDS);
    testObserver.assertComplete();
    testObserver.assertValue(1);
  }

  @Test
  void place_market_order_future_two_messages_both_success() throws Exception {
    String ackJson = """
        {
          "request_id": "123",
          "header": {
            "status": "200",
            "channel": "futures.order_place"
          },
          "data": {
            "result": {}
          }
        }
        """;
    String orderResultJson = """
        {
          "request_id": "123",
          "header": {
            "status": "200",
            "channel": "futures.order_place"
          },
          "data": {
            "result": {}
          }
        }
        """;
    JsonNode ackNode = objectMapper.readTree(ackJson);
    JsonNode resultNode = objectMapper.readTree(orderResultJson);

    PublishSubject<JsonNode> subject = PublishSubject.create();
    when(gateioUserTradeStreamingService.subscribeChannel(eq(Config.FUTURES_ORDER_PLACE_CHANNEL), anyString(), any(), any()))
        .thenReturn(subject);

    MarketOrder order = new MarketOrder(OrderType.BID, new BigDecimal("1"), instrumentFuture, "1", new Date());
    Single<Integer> single = gateioStreamingTradeService.placeMarketOrder(order);

    TestObserver<Integer> testObserver = single.test();
    subject.onNext(ackNode);
    subject.onNext(resultNode);

    testObserver.awaitDone(500, TimeUnit.MILLISECONDS);
    testObserver.assertComplete();
    testObserver.assertValue(0);
  }

  @Test
  void place_market_order_future_second_message_error() throws Exception {
    String ackJson = """
        {
          "request_id": "123",
          "header": {
            "status": "200",
            "channel": "futures.order_place"
          },
          "data": {
            "result": {}
          }
        }
        """;
    String errorJson = """
        {
          "request_id": "123",
          "header": {
            "status": "400",
            "channel": "futures.order_place"
          },
          "data": {
            "errs": {
              "label": "INVALID_PARAM_VALUE",
              "message": "order size too small"
            }
          }
        }
        """;
    JsonNode ackNode = objectMapper.readTree(ackJson);
    JsonNode errorNode = objectMapper.readTree(errorJson);

    PublishSubject<JsonNode> subject = PublishSubject.create();
    when(gateioUserTradeStreamingService.subscribeChannel(eq(Config.FUTURES_ORDER_PLACE_CHANNEL), anyString(), any(), any()))
        .thenReturn(subject);

    MarketOrder order = new MarketOrder(OrderType.BID, new BigDecimal("1"), instrumentFuture, "1", new Date());
    Single<Integer> single = gateioStreamingTradeService.placeMarketOrder(order);

    TestObserver<Integer> testObserver = single.test();
    subject.onNext(ackNode);
    subject.onNext(errorNode);

    testObserver.awaitDone(500, TimeUnit.MILLISECONDS);
    testObserver.assertComplete();
    testObserver.assertValue(2);
  }

  @Test
  void place_market_order_future_single_success_message_timeout() throws Exception {
    String ackJson = """
        {
          "request_id": "123",
          "header": {
            "status": "200",
            "channel": "futures.order_place"
          },
          "data": {
            "result": {}
          }
        }
        """;
    JsonNode ackNode = objectMapper.readTree(ackJson);

    PublishSubject<JsonNode> subject = PublishSubject.create();
    when(gateioUserTradeStreamingService.subscribeChannel(eq(Config.FUTURES_ORDER_PLACE_CHANNEL), anyString(), any(), any()))
        .thenReturn(subject);

    MarketOrder order = new MarketOrder(OrderType.BID, new BigDecimal("1"), instrumentFuture, "1", new Date());
    Single<Integer> single = gateioStreamingTradeService.placeMarketOrder(order);

    TestObserver<Integer> testObserver = single.test();
    subject.onNext(ackNode);

    testObserver.awaitDone(1500, TimeUnit.MILLISECONDS);
    testObserver.assertComplete();
    testObserver.assertValue(-1);
  }

  @Test
  void place_market_order_future_single_success_message_error() throws Exception {
    String ackJson = """
        {
          "request_id": "123",
          "header": {
            "status": "200",
            "channel": "futures.order_place"
          },
          "data": {
            "result": {}
          }
        }
        """;
    JsonNode ackNode = objectMapper.readTree(ackJson);

    when(gateioUserTradeStreamingService.subscribeChannel(eq(Config.FUTURES_ORDER_PLACE_CHANNEL), anyString(), any(), any()))
        .thenReturn(Observable.just(ackNode));

    MarketOrder order = new MarketOrder(OrderType.BID, new BigDecimal("1"), instrumentFuture, "1", new Date());
    Single<Integer> single = gateioStreamingTradeService.placeMarketOrder(order);

    TestObserver<Integer> testObserver = single.test();

    testObserver.awaitDone(500, TimeUnit.MILLISECONDS);
    testObserver.assertComplete();
    testObserver.assertValue(-1);
  }

  private GateioWsNotification readNotification(String resourceName) throws IOException {
    return objectMapper.readValue(
        getClass().getClassLoader().getResourceAsStream(resourceName), GateioWsNotification.class);
  }
}
