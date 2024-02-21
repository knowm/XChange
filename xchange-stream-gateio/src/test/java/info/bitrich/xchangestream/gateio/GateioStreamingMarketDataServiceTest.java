package info.bitrich.xchangestream.gateio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.response.GateioWsNotification;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GateioStreamingMarketDataServiceTest {

  @Mock
  GateioStreamingService gateioStreamingService;
  GateioStreamingMarketDataService gateioStreamingMarketDataService;

  ObjectMapper objectMapper = Config.getInstance().getObjectMapper();

  @BeforeEach
  public void setup() {
    gateioStreamingMarketDataService = new GateioStreamingMarketDataService(gateioStreamingService);
  }


  @Test
  void order_book() throws Exception {
    GateioWsNotification notification = readNotification("spot.order_book.update.json");
    when(gateioStreamingService.subscribeChannel(eq("spot.order_book"), eq(CurrencyPair.BTC_USDT), eq(10), eq(Duration.ofMillis(100))))
        .thenReturn(Observable.just(notification));

    Observable<OrderBook> observable = gateioStreamingMarketDataService
        .getOrderBook(CurrencyPair.BTC_USDT, 10, Duration.ofMillis(100));

    TestObserver<OrderBook> testObserver = observable.test();

    OrderBook actual = testObserver
        .assertSubscribed()
        .awaitCount(1)
        .assertNoTimeout()
        .values().get(0);

    testObserver.dispose();

    assertThat(actual.getTimeStamp()).isEqualTo(Date.from(Instant.ofEpochMilli(1691757151367L)));
    assertThat(actual.getBids()).hasSize(10);
    assertThat(actual.getAsks()).hasSize(10);
  }


  @Test
  void ticker() throws Exception {
    GateioWsNotification notification = readNotification("spot.ticker.update.json");
    when(gateioStreamingService.subscribeChannel(eq("spot.tickers"), eq(CurrencyPair.BTC_USDT)))
        .thenReturn(Observable.just(notification));

    Observable<Ticker> observable = gateioStreamingMarketDataService.getTicker(CurrencyPair.BTC_USDT);

    TestObserver<Ticker> testObserver = observable.test();

    Ticker actual = testObserver
        .assertSubscribed()
        .awaitCount(1)
        .assertNoTimeout()
        .values().get(0);

    testObserver.dispose();

    Ticker expected = new Ticker.Builder()
        .instrument(CurrencyPair.BTC_USDT)
        .timestamp(Date.from(Instant.ofEpochMilli(1691620566926L)))
        .ask(new BigDecimal("29573.7"))
        .bid(new BigDecimal("29573.6"))
        .high(new BigDecimal("30232.8"))
        .low(new BigDecimal("29176.1"))
        .last(new BigDecimal("29573.7"))
        .percentageChange(new BigDecimal("-0.6601"))
        .quoteVolume(new BigDecimal("171784719.492586746"))
        .volume(new BigDecimal("5777.7777606776"))
        .build();

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void trades() throws Exception {
    GateioWsNotification notification = readNotification("spot.trades.update.json");
    when(gateioStreamingService.subscribeChannel(eq("spot.trades"), eq(CurrencyPair.BTC_USDT)))
        .thenReturn(Observable.just(notification));

    Observable<Trade> observable = gateioStreamingMarketDataService.getTrades(CurrencyPair.BTC_USDT);

    TestObserver<Trade> testObserver = observable.test();

    Trade actual = testObserver
        .assertSubscribed()
        .awaitCount(1)
        .assertNoTimeout()
        .values().get(0);

    testObserver.dispose();

    Trade expected = new Trade.Builder()
        .instrument(CurrencyPair.BTC_USDT)
        .id("6064666343")
        .originalAmount(new BigDecimal("0.0003009"))
        .price(new BigDecimal("29573.7"))
        .timestamp(Date.from(Instant.ofEpochMilli(1691620568789L)))
        .type(OrderType.BID)
        .build();

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }


  private GateioWsNotification readNotification(String resourceName) throws IOException {
    return objectMapper.readValue(
        getClass().getClassLoader().getResourceAsStream(resourceName),
        GateioWsNotification.class
    );
  }

}