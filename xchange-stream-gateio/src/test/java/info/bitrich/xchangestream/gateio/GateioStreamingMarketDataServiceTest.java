package info.bitrich.xchangestream.gateio;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.response.GateioWsNotification;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.FundingRate;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GateioStreamingMarketDataServiceTest {

  @Mock
  GateioStreamingService gateioStreamingService;
  @Mock
  ExchangeMetaData exchangeMetaData;
  GateioStreamingMarketDataService gateioStreamingMarketDataService;

  ObjectMapper objectMapper = Config.getInstance().getObjectMapper();
  Instrument instrumentBtc = new FuturesContract(CurrencyPair.BTC_USDT, "PERP");

  @BeforeEach
  public void setup() {
    Map<Instrument, InstrumentMetaData> instruments = new HashMap<>();
    instruments.put(instrumentBtc, InstrumentMetaData.builder()
        .contractValue(new BigDecimal("0.0001"))
        .build());
    exchangeMetaData = new ExchangeMetaData(instruments, null, null, null, null);
    gateioStreamingMarketDataService = new GateioStreamingMarketDataService(gateioStreamingService, exchangeMetaData);
  }

  @Test
  void order_book_legacy() throws Exception {
    GateioWsNotification notification = readNotification("spot.order_book.update.json");
    when(gateioStreamingService.subscribeChannel(
        eq("spot.order_book"), eq(CurrencyPair.BTC_USDT), eq(10), eq(Duration.ofMillis(100))))
        .thenReturn(Observable.just(notification));

    Observable<OrderBook> observable =
        gateioStreamingMarketDataService.getOrderBookLegacy(
            CurrencyPair.BTC_USDT, 10, Duration.ofMillis(100));

    TestObserver<OrderBook> testObserver = observable.test();

    OrderBook actual = testObserver.awaitCount(1).values().get(0);

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

    Observable<Ticker> observable =
        gateioStreamingMarketDataService.getTicker(CurrencyPair.BTC_USDT);

    TestObserver<Ticker> testObserver = observable.test();

    Ticker actual = testObserver.awaitCount(1).values().get(0);

    testObserver.dispose();

    Ticker expected =
        new Ticker.Builder()
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
    Instrument instrumentGT = new CurrencyPair("GT", "USDT");
    when(gateioStreamingService.subscribeChannel(eq("spot.trades"), eq(instrumentGT)))
        .thenReturn(Observable.just(notification));

    Observable<Trade> observable =
        gateioStreamingMarketDataService.getTrades(instrumentGT);

    TestObserver<Trade> testObserver = observable.test();

    Trade actual = testObserver.awaitCount(1).values().get(0);

    testObserver.dispose();

    Trade expected =
        Trade.builder()
            .instrument(instrumentGT)
            .id("309143071")
            .originalAmount(new BigDecimal("16.47"))
            .price(new BigDecimal("0.4705"))
            .timestamp(Date.from(Instant.ofEpochMilli(1606292218213L)))
            .type(OrderType.ASK)
            .build();

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void getTradesInstrumentCurrencyPair() throws Exception {
    GateioWsNotification notification = readNotification("spot.trades.update.json");
    Instrument instrumentGT = new CurrencyPair("GT", "USDT");
    when(gateioStreamingService.subscribeChannel(eq("spot.trades"), eq(instrumentGT)))
        .thenReturn(Observable.just(notification));

    Observable<Trade> observable =
        gateioStreamingMarketDataService.getTrades((Instrument) instrumentGT);

    TestObserver<Trade> testObserver = observable.test();

    Trade actual = testObserver.awaitCount(1).values().get(0);

    testObserver.dispose();

    assertThat(actual.getInstrument()).isEqualTo(instrumentGT);
  }

  @Test
  void getTradesUnsupportedInstrument() {
    Instrument unsupported = org.mockito.Mockito.mock(Instrument.class);
    assertThatThrownBy(() -> gateioStreamingMarketDataService.getTrades(unsupported))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void futuresTrades() throws Exception {
    GateioWsNotification notification = readNotification("futures.trades.update.json");
    when(gateioStreamingService.subscribeChannel(eq("futures.trades"), eq(CurrencyPair.BTC_USDT)))
        .thenReturn(Observable.just(notification));

    Observable<Trade> observable =
        gateioStreamingMarketDataService.getTrades(instrumentBtc);

    TestObserver<Trade> testObserver = observable.test();

    Trade actual = testObserver.awaitCount(1).values().get(0);

    testObserver.dispose();

    assertThat(actual.getInstrument()).isEqualTo(instrumentBtc);
    assertThat(actual.getOriginalAmount().equals(new BigDecimal("108"))).isTrue();
    assertThat(actual.getPrice().equals(new BigDecimal("96.4"))).isTrue();
    assertThat(actual.getId()).isEqualTo("27753479");
    assertThat(actual.getTimestamp().getTime()).isEqualTo(1545136464123L);
  }

  @Test
  void futuresOrderBook() throws Exception {
    GateioWsNotification snapshot = readNotification("futures.obu.snapshot.json");
    GateioWsNotification update = readNotification("futures.obu.update.json");
    FuturesContract futuresContract = new FuturesContract(CurrencyPair.BTC_USDT, "PERP");
    when(gateioStreamingService.subscribeChannel(
        eq("futures.obu"), eq(instrumentBtc), eq(400)))
        .thenReturn(Observable.just(snapshot, update));

    Observable<OrderBook> observable =
        gateioStreamingMarketDataService.getOrderBook(
            instrumentBtc, 400);

    TestObserver<OrderBook> testObserver = observable.test();

    testObserver.awaitCount(2);
    OrderBook actual = testObserver.values().get(1);

    testObserver.dispose();

    assertThat(actual.getTimeStamp()).isEqualTo(Date.from(Instant.ofEpochMilli(1743673027017L)));
    assertThat(actual.getBids()).hasSize(4);
    assertThat(actual.getAsks()).hasSize(1);
    assertThat(actual.getBids().get(0).getLimitPrice()).isEqualByComparingTo("83705.9");
    assertThat(actual.getBids().get(0).getOriginalAmount()).isEqualByComparingTo("3.0166");
    assertThat(actual.getBids().get(1).getLimitPrice()).isEqualByComparingTo("83702.2");
    assertThat(actual.getBids().get(1).getOriginalAmount()).isEqualByComparingTo("0.0062");
    assertThat(actual.getBids().get(3).getLimitPrice()).isEqualByComparingTo("83685");
  }

  @Test
  void funding() throws Exception {
    GateioWsNotification notification = readNotification("futures.tickers");
    when(gateioStreamingService.subscribeChannel(
        eq("futures.obu"), eq(instrumentBtc), eq(400)))
        .thenReturn(Observable.just(notification));

    Observable<FundingRate> observable =
        gateioStreamingMarketDataService.getFundingRate(
            instrumentBtc);

    TestObserver<FundingRate> testObserver = observable.test();

    FundingRate actual = testObserver.awaitCount(1).values().get(0);

    testObserver.dispose();

//    assertThat(actual.getTimeStamp()).isEqualTo(Date.from(Instant.ofEpochMilli(1743673027017L)));

  }

  private GateioWsNotification readNotification(String resourceName) throws IOException {
    return objectMapper.readValue(
        getClass().getClassLoader().getResourceAsStream(resourceName), GateioWsNotification.class);
  }
}
