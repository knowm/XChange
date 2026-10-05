package info.bitrich.xchangestream.binance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.rxjava3.core.Observable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.binance.dto.marketdata.BinanceBookTicker;
import org.knowm.xchange.binance.dto.marketdata.BinanceFundingRateInfo;
import org.knowm.xchange.binance.dto.marketdata.BinanceKline;
import org.knowm.xchange.binance.dto.marketdata.BinanceTicker24h;
import org.knowm.xchange.binance.dto.marketdata.KlineInterval;
import org.knowm.xchange.binance.service.BinanceMarketDataService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.FundingRate;
import org.knowm.xchange.dto.marketdata.FundingRate.FundingRateInterval;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;

/** Pairs in these tests are not in the symbol mapping, as if listed after initialization. */
class BinanceStreamingMarketDataServiceTest {

  private static final CurrencyPair UNMAPPED_PAIR = new CurrencyPair("NEW", "USDT");
  private static final FuturesContract UNMAPPED_CONTRACT =
      new FuturesContract(UNMAPPED_PAIR, "PERP");
  private static final String TICKER_DATA =
      "{\"e\":\"24hrTicker\",\"E\":1516135684559,\"s\":\"NEWUSDT\",\"p\":\"0.1\","
          + "\"P\":\"7.1\",\"w\":\"1.45\",\"x\":\"1.4\",\"c\":\"1.5\",\"Q\":\"3\",\"b\":\"1.49\","
          + "\"B\":\"10\",\"a\":\"1.51\",\"A\":\"12\",\"o\":\"1.4\",\"h\":\"1.6\",\"l\":\"1.3\","
          + "\"v\":\"1000\",\"q\":\"1450\",\"O\":1516049284557,\"C\":1516135684557,"
          + "\"F\":1,\"L\":100,\"n\":100}";
  private static final String TRADE_MESSAGE =
      "{\"stream\":\"newusdt@trade\",\"data\":"
          + "{\"e\":\"trade\",\"E\":123456789,\"s\":\"NEWUSDT\",\"t\":1,\"p\":\"1.5\","
          + "\"q\":\"2\",\"T\":123456785,\"m\":true}}";
  private static final String BOOK_TICKER_MESSAGE =
      "{\"stream\":\"newusdt@bookTicker\",\"data\":"
          + "{\"u\":400900217,\"s\":\"NEWUSDT\",\"b\":\"1.49\",\"B\":\"10\","
          + "\"a\":\"1.51\",\"A\":\"12\"}}";

  private final ObjectMapper mapper = new ObjectMapper();
  private final BinanceStreamingService streamingService = mock(BinanceStreamingService.class);
  private final BinanceMarketDataService binanceMarketDataService =
      mock(BinanceMarketDataService.class);
  private BinanceStreamingMarketDataService marketDataService;

  @BeforeEach
  void setUp() {
    when(streamingService.isLiveSubscriptionEnabled()).thenReturn(true);
    marketDataService = createMarketDataService(false);
  }

  @Test
  void tickerOfUnmappedPair() throws Exception {
    givenChannelMessages(
        "newusdt@ticker", "{\"stream\":\"newusdt@ticker\",\"data\":" + TICKER_DATA + "}");

    Ticker ticker = marketDataService.getTicker(UNMAPPED_PAIR).blockingFirst();

    assertThat(ticker.getInstrument()).isEqualTo(UNMAPPED_PAIR);
    assertThat(ticker.getLast()).isEqualByComparingTo(new BigDecimal("1.5"));
  }

  @Test
  void rollingWindowTickerOfUnmappedPair() throws Exception {
    givenChannelMessages(
        "newusdt@ticker_1h", "{\"stream\":\"newusdt@ticker_1h\",\"data\":" + TICKER_DATA + "}");

    BinanceTicker24h ticker =
        marketDataService.rollingWindow(UNMAPPED_PAIR, KlineInterval.h1).blockingFirst();

    assertThat(ticker.getSymbol()).isEqualTo("NEWUSDT");
    assertThat(ticker.getLastPrice()).isEqualByComparingTo(new BigDecimal("1.5"));
  }

  @Test
  void bookTickerOfUnmappedPair() throws Exception {
    givenChannelMessages("newusdt@bookTicker", BOOK_TICKER_MESSAGE);

    Ticker ticker = createMarketDataService(true).getTicker(UNMAPPED_PAIR).blockingFirst();

    assertThat(ticker.getInstrument()).isEqualTo(UNMAPPED_PAIR);
    assertThat(ticker.getBid()).isEqualByComparingTo(new BigDecimal("1.49"));
    assertThat(ticker.getAsk()).isEqualByComparingTo(new BigDecimal("1.51"));
  }

  @Test
  void bookTickerIsRebuiltForAnotherInstrument() throws Exception {
    givenChannelMessages("newusdt@bookTicker", BOOK_TICKER_MESSAGE);
    BinanceBookTicker bookTicker =
        marketDataService.getRawBookTicker(UNMAPPED_CONTRACT).blockingFirst();

    assertThat(bookTicker.toTicker(true).getInstrument()).isNotEqualTo(UNMAPPED_CONTRACT);
    assertThat(bookTicker.toTicker(UNMAPPED_CONTRACT).getInstrument())
        .isEqualTo(UNMAPPED_CONTRACT);
  }

  @Test
  void klineOfUnmappedFuturesContract() throws Exception {
    givenChannelMessages(
        "newusdt@kline_1m",
        "{\"stream\":\"newusdt@kline_1m\",\"data\":"
            + "{\"e\":\"kline\",\"E\":123456789,\"s\":\"NEWUSDT\",\"k\":"
            + "{\"t\":123400000,\"T\":123460000,\"s\":\"NEWUSDT\",\"i\":\"1m\","
            + "\"o\":\"1\",\"c\":\"2\",\"h\":\"3\",\"l\":\"0.5\",\"v\":\"10\",\"n\":1,"
            + "\"x\":false,\"q\":\"15\",\"V\":\"5\",\"Q\":\"7.5\"}}}");

    BinanceKline kline =
        marketDataService.getKlines(UNMAPPED_CONTRACT, KlineInterval.m1).blockingFirst();

    assertThat(kline.getInstrument()).isEqualTo(UNMAPPED_CONTRACT);
    assertThat(kline.getInterval()).isEqualTo(KlineInterval.m1);
  }

  @Test
  void fundingRateOfUnmappedContractUsesItsInterval() throws Exception {
    // Two unmapped symbols resolve to the same instrument, so they must not collide
    when(binanceMarketDataService.getBinanceFundingRateInfo())
        .thenReturn(
            List.of(
                new BinanceFundingRateInfo("NEWUSDT", null, null, 4),
                new BinanceFundingRateInfo("OTHERUSDT", null, null, 1)));
    givenChannelMessages(
        "newusdt@markPrice",
        "{\"stream\":\"newusdt@markPrice\",\"data\":"
            + "{\"e\":\"markPriceUpdate\",\"E\":1562305380000,\"s\":\"NEWUSDT\","
            + "\"p\":\"1.5\",\"i\":\"1.49\",\"P\":\"1.48\",\"r\":\"0.0004\","
            + "\"T\":1562306400000}}");

    FundingRate fundingRate = marketDataService.getFundingRate(UNMAPPED_CONTRACT).blockingFirst();

    assertThat(fundingRate.getInstrument()).isEqualTo(UNMAPPED_CONTRACT);
    assertThat(fundingRate.getFundingRateInterval()).isEqualTo(FundingRateInterval.H4);
    assertThat(fundingRate.getFundingRate1h()).isEqualByComparingTo(new BigDecimal("0.0001"));
  }

  @Test
  void tradeOfUnmappedPair() throws Exception {
    givenChannelMessages("newusdt@trade", TRADE_MESSAGE);

    Trade trade = marketDataService.getTrades(UNMAPPED_PAIR).blockingFirst();

    assertThat(trade.getInstrument()).isEqualTo(UNMAPPED_PAIR);
    assertThat(trade.getPrice()).isEqualByComparingTo(new BigDecimal("1.5"));
  }

  @Test
  void messageWithoutSymbolIsSkippedAndStreamContinues() throws Exception {
    givenChannelMessages(
        "newusdt@trade",
        "{\"stream\":\"newusdt@trade\",\"data\":"
            + "{\"e\":\"trade\",\"E\":123456789,\"t\":1,\"p\":\"1.5\",\"q\":\"2\"}}",
        TRADE_MESSAGE);

    marketDataService.getTrades(UNMAPPED_PAIR).test().assertValueCount(1).assertNoErrors();
  }

  @Test
  void messageOfOtherSymbolIsSkipped() throws Exception {
    givenChannelMessages(
        "newusdt@trade", TRADE_MESSAGE.replace("\"s\":\"NEWUSDT\"", "\"s\":\"OTHERUSDT\""));

    marketDataService.getTrades(UNMAPPED_PAIR).test().assertNoValues().assertNoErrors();
  }

  @Test
  void perpetualMessageIsSkippedForDatedContract() throws Exception {
    givenChannelMessages("newusdt@trade", TRADE_MESSAGE);

    marketDataService
        .getTrades(new FuturesContract(UNMAPPED_PAIR, "250328"))
        .test()
        .assertNoValues()
        .assertNoErrors();
  }

  private BinanceStreamingMarketDataService createMarketDataService(
      boolean realtimeOrderBookTicker) {
    return new BinanceStreamingMarketDataService(
        streamingService, binanceMarketDataService, () -> {}, "", realtimeOrderBookTicker, 1000);
  }

  private void givenChannelMessages(String channel, String... messages) throws Exception {
    List<JsonNode> nodes = new ArrayList<>();
    for (String message : messages) {
      nodes.add(mapper.readTree(message));
    }
    when(streamingService.subscribeChannel(channel)).thenReturn(Observable.fromIterable(nodes));
  }
}
