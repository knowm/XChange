package info.bitrich.xchangestream.binance;

import static info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction.BinanceWebSocketTypes.EXECUTION_REPORT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.rxjava3.observers.TestObserver;
import io.reactivex.rxjava3.subjects.PublishSubject;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.service.BinanceTradeService.BinanceOrderFlags;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.MarketOrder;

class BinanceStreamingTradeServiceTest {

  private final ObjectMapper mapper = new ObjectMapper();
  private final BinanceExchange binanceExchange = mock(BinanceExchange.class);
  private final BinanceStreamingExchange exchange = mock(BinanceStreamingExchange.class);
  private final BinanceUserDataSpotStreamingService spotStreamingService =
      mock(BinanceUserDataSpotStreamingService.class);
  private final PublishSubject<JsonNode> executionReportSubject = PublishSubject.create();

  private BinanceStreamingTradeService streamingTradeService;

  @BeforeEach
  void setUp() {
    org.knowm.xchange.binance.BinanceAdapters.putSymbolMapping(
        "ASTRUSDT", new CurrencyPair("ASTR/USDT"));
    when(binanceExchange.isFuturesEnabled()).thenReturn(false);
    when(binanceExchange.isSpotEnabled()).thenReturn(true);
    when(spotStreamingService.isSocketOpen()).thenReturn(true);
    when(spotStreamingService.subscribeChannel(EXECUTION_REPORT))
        .thenReturn(executionReportSubject);

    streamingTradeService =
        new BinanceStreamingTradeService(
            binanceExchange, null, spotStreamingService, null, new ResilienceRegistries());
    streamingTradeService.openSubscriptions();

    when(exchange.getStreamingTradeService()).thenReturn(streamingTradeService);
  }

  @Test
  void testGetOrderChangesPartiallyFilled() throws Exception {
    CurrencyPair currencyPair = new CurrencyPair("ASTR/USDT");
    TestObserver<Order> testObserver =
        exchange.getStreamingTradeService().getOrderChanges(currencyPair).test();

    JsonNode node;
    try (InputStream stream =
        getClass().getResourceAsStream("executionReportPartiallyFilled.json")) {
      node = mapper.readTree(stream);
    }
    executionReportSubject.onNext(node.get("event"));

    testObserver.assertValueCount(1);
    testObserver.assertNoErrors();

    Order order = testObserver.values().get(0);
    assertThat(order).isInstanceOf(MarketOrder.class);
    assertThat(order.getType()).isEqualTo(Order.OrderType.BID);
    assertThat(order.getInstrument()).isEqualTo(currencyPair);
    assertThat(order.getId()).isEqualTo("969447355");
    assertThat(order.getUserReference()).isEqualTo("nNpwwu8V2B5UwBlKIVrQ");
    assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.PARTIALLY_FILLED);
    assertThat(order.getOriginalAmount()).isEqualByComparingTo(new BigDecimal("11000.00000000"));
    assertThat(order.getCumulativeAmount()).isEqualByComparingTo(new BigDecimal("10000.00000000"));
    assertThat(order.getAveragePrice()).isEqualByComparingTo(new BigDecimal("0.005614"));
    assertThat(order.getTimestamp()).isEqualTo(new Date(1793652977111L));
    assertThat(order.hasFlag(BinanceOrderFlags.withClientId("nNpwwu8V2B5UwBlKIVrQ"))).isTrue();
  }

  @Test
  void testGetOrderChangesFilled() throws Exception {
    CurrencyPair currencyPair = new CurrencyPair("ASTR/USDT");
    TestObserver<Order> testObserver =
        exchange.getStreamingTradeService().getOrderChanges(currencyPair).test();

    JsonNode node;
    try (InputStream stream = getClass().getResourceAsStream("executionReportFilled.json")) {
      node = mapper.readTree(stream);
    }
    executionReportSubject.onNext(node.get("event"));

    testObserver.assertValueCount(1);
    testObserver.assertNoErrors();

    Order order = testObserver.values().get(0);
    assertThat(order).isInstanceOf(MarketOrder.class);
    assertThat(order.getType()).isEqualTo(Order.OrderType.BID);
    assertThat(order.getInstrument()).isEqualTo(currencyPair);
    assertThat(order.getId()).isEqualTo("969447355");
    assertThat(order.getUserReference()).isEqualTo("nNpwwu8V2B5UwBlKIVrQ");
    assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.FILLED);
    assertThat(order.getOriginalAmount()).isEqualByComparingTo(new BigDecimal("11000.00000000"));
    assertThat(order.getCumulativeAmount()).isEqualByComparingTo(new BigDecimal("11000.00000000"));
    assertThat(order.getAveragePrice()).isEqualByComparingTo(new BigDecimal("0.005614364"));
    assertThat(order.getTimestamp()).isEqualTo(new Date(1793652977111L));
    assertThat(order.hasFlag(BinanceOrderFlags.withClientId("nNpwwu8V2B5UwBlKIVrQ"))).isTrue();
  }

  @Test
  void testGetOrderChangesFiltersOtherPairs() throws Exception {
    CurrencyPair ethPair = new CurrencyPair("ETH/USDT");

    TestObserver<Order> testObserver =
        exchange.getStreamingTradeService().getOrderChanges(ethPair).test();

    JsonNode node;
    try (InputStream stream =
        getClass().getResourceAsStream("executionReportPartiallyFilled.json")) {
      node = mapper.readTree(stream);
    }
    executionReportSubject.onNext(node.get("event"));

    testObserver.assertNoValues();
    testObserver.assertNoErrors();
  }
}
