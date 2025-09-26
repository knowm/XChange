package org.knowm.xchange.coinsph.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.coinsph.Coinsph;
import org.knowm.xchange.coinsph.CoinsphAuthenticated;
import org.knowm.xchange.coinsph.CoinsphExchange;
import org.knowm.xchange.coinsph.dto.marketdata.CoinsphOrderBook;
import org.knowm.xchange.coinsph.dto.marketdata.CoinsphOrderBookEntry;
import org.knowm.xchange.coinsph.dto.marketdata.CoinsphPublicTrade;
import org.knowm.xchange.coinsph.dto.marketdata.CoinsphTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.params.Params;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

public class CoinsphMarketDataServiceTest {

  private CoinsphMarketDataService marketDataService;
  private Coinsph coinsph;
  private CoinsphExchange exchange;

  @BeforeEach
  public void setUp() {
    exchange = mock(CoinsphExchange.class);
    coinsph = mock(Coinsph.class);
    CoinsphAuthenticated coinsphAuthenticated = mock(CoinsphAuthenticated.class);
    ResilienceRegistries resilienceRegistries = mock(ResilienceRegistries.class);
    ParamsDigest signatureCreator = mock(ParamsDigest.class);
    SynchronizedValueFactory<Long> timestampFactory = mock(SynchronizedValueFactory.class);

    // Mock exchange specification
    org.knowm.xchange.ExchangeSpecification exchangeSpec =
        mock(org.knowm.xchange.ExchangeSpecification.class);
    when(exchange.getExchangeSpecification()).thenReturn(exchangeSpec);
    when(exchangeSpec.getApiKey()).thenReturn("dummyApiKey");
    when(exchangeSpec.getSecretKey()).thenReturn("dummySecretKey");
    when(exchange.getRecvWindow()).thenReturn(5000L);

    // Mock exchange methods
    when(exchange.getPublicApi()).thenReturn(coinsph);
    when(exchange.getAuthenticatedApi()).thenReturn(coinsphAuthenticated);
    when(exchange.getSignatureCreator()).thenReturn(signatureCreator);
    when(exchange.getNonceFactory()).thenReturn(timestampFactory);

    // Create the service
    marketDataService = new CoinsphMarketDataService(exchange, resilienceRegistries);
  }

  @Test
  public void testGetTicker() throws IOException {
    // given
    CurrencyPair currencyPair = CurrencyPair.BTC_PHP;
    CoinsphTicker mockTicker =
        new CoinsphTicker(
            "BTCPHP", // symbol
            new BigDecimal("50000.0"), // priceChange
            new BigDecimal("0.87"), // priceChangePercent
            new BigDecimal("5780000.0"), // weightedAvgPrice
            new BigDecimal("5750000.0"), // prevClosePrice
            new BigDecimal("5800000.0"), // lastPrice
            new BigDecimal("0.1"), // lastQty
            new BigDecimal("5799000.0"), // bidPrice
            new BigDecimal("0.5"), // bidQty
            new BigDecimal("5801000.0"), // askPrice
            new BigDecimal("0.3"), // askQty
            new BigDecimal("5750000.0"), // openPrice
            new BigDecimal("5900000.0"), // highPrice
            new BigDecimal("5700000.0"), // lowPrice
            new BigDecimal("10.5"), // volume
            new BigDecimal("60900000.0"), // quoteVolume
            1621234560000L, // openTime
            1621320960000L, // closeTime
            12345L, // firstId
            12445L, // lastId
            100L // count
            );

    // when
    when(coinsph.getTicker24hr(eq("BTCPHP"))).thenReturn(mockTicker);

    // then
    Ticker ticker = marketDataService.getTicker(currencyPair);

    assertThat(ticker).isNotNull();
    assertThat(ticker.getCurrencyPair()).isEqualTo(currencyPair);
    assertThat(ticker.getLast()).isEqualByComparingTo(new BigDecimal("5800000.0"));
    assertThat(ticker.getVolume()).isEqualByComparingTo(new BigDecimal("10.5"));
    assertThat(ticker.getHigh()).isEqualByComparingTo(new BigDecimal("5900000.0"));
    assertThat(ticker.getLow()).isEqualByComparingTo(new BigDecimal("5700000.0"));
    assertThat(ticker.getBid()).isEqualByComparingTo(new BigDecimal("5799000.0"));
    assertThat(ticker.getAsk()).isEqualByComparingTo(new BigDecimal("5801000.0"));
    assertThat(ticker.getOpen()).isEqualByComparingTo(new BigDecimal("5750000.0"));
  }

  @Test
  public void testGetTickers() throws IOException {
    // given
    List<CoinsphTicker> mockTickers = new ArrayList<>();

    CoinsphTicker btcTicker =
        new CoinsphTicker(
            "BTCPHP", // symbol
            new BigDecimal("50000.0"), // priceChange
            new BigDecimal("0.87"), // priceChangePercent
            new BigDecimal("5780000.0"), // weightedAvgPrice
            new BigDecimal("5750000.0"), // prevClosePrice
            new BigDecimal("5800000.0"), // lastPrice
            new BigDecimal("0.1"), // lastQty
            new BigDecimal("5799000.0"), // bidPrice
            new BigDecimal("0.5"), // bidQty
            new BigDecimal("5801000.0"), // askPrice
            new BigDecimal("0.3"), // askQty
            new BigDecimal("5750000.0"), // openPrice
            new BigDecimal("5900000.0"), // highPrice
            new BigDecimal("5700000.0"), // lowPrice
            new BigDecimal("10.5"), // volume
            new BigDecimal("60900000.0"), // quoteVolume
            1621234560000L, // openTime
            1621320960000L, // closeTime
            12345L, // firstId
            12445L, // lastId
            100L // count
            );
    mockTickers.add(btcTicker);

    CoinsphTicker ethTicker =
        new CoinsphTicker(
            "ETHPHP", // symbol
            new BigDecimal("5000.0"), // priceChange
            new BigDecimal("1.45"), // priceChangePercent
            new BigDecimal("348000.0"), // weightedAvgPrice
            new BigDecimal("345000.0"), // prevClosePrice
            new BigDecimal("350000.0"), // lastPrice
            new BigDecimal("0.5"), // lastQty
            new BigDecimal("349500.0"), // bidPrice
            new BigDecimal("1.0"), // bidQty
            new BigDecimal("350500.0"), // askPrice
            new BigDecimal("0.8"), // askQty
            new BigDecimal("345000.0"), // openPrice
            new BigDecimal("355000.0"), // highPrice
            new BigDecimal("340000.0"), // lowPrice
            new BigDecimal("50.2"), // volume
            new BigDecimal("17569800.0"), // quoteVolume
            1621234560000L, // openTime
            1621320960000L, // closeTime
            22345L, // firstId
            22545L, // lastId
            200L // count
            );
    mockTickers.add(ethTicker);

    // when
    when(coinsph.getTicker24hr()).thenReturn(mockTickers);

    // then
    List<Ticker> tickers = marketDataService.getTickers(mock(Params.class));

    assertThat(tickers).hasSize(2);

    Ticker btcPhpTicker = tickers.get(0);
    assertThat(btcPhpTicker.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_PHP);
    assertThat(btcPhpTicker.getLast()).isEqualByComparingTo(new BigDecimal("5800000.0"));
    assertThat(btcPhpTicker.getVolume()).isEqualByComparingTo(new BigDecimal("10.5"));

    Ticker ethPhpTicker = tickers.get(1);
    assertThat(ethPhpTicker.getCurrencyPair()).isEqualTo(new CurrencyPair("ETH", "PHP"));
    assertThat(ethPhpTicker.getLast()).isEqualByComparingTo(new BigDecimal("350000.0"));
    assertThat(ethPhpTicker.getVolume()).isEqualByComparingTo(new BigDecimal("50.2"));
  }

  @Test
  public void testGetOrderBook() throws IOException {
    // given
    CurrencyPair currencyPair = CurrencyPair.BTC_PHP;

    List<CoinsphOrderBookEntry> bids = new ArrayList<>();
    bids.add(
        new CoinsphOrderBookEntry(
            Arrays.asList(new BigDecimal("5799000.0"), new BigDecimal("0.5"))));
    bids.add(
        new CoinsphOrderBookEntry(
            Arrays.asList(new BigDecimal("5798000.0"), new BigDecimal("1.2"))));

    List<CoinsphOrderBookEntry> asks = new ArrayList<>();
    asks.add(
        new CoinsphOrderBookEntry(
            Arrays.asList(new BigDecimal("5801000.0"), new BigDecimal("0.3"))));
    asks.add(
        new CoinsphOrderBookEntry(
            Arrays.asList(new BigDecimal("5802000.0"), new BigDecimal("0.8"))));

    CoinsphOrderBook mockOrderBook = new CoinsphOrderBook(12345L, bids, asks);

    // when
    when(coinsph.getOrderBook(eq("BTCPHP"), any())).thenReturn(mockOrderBook);

    // then
    OrderBook orderBook = marketDataService.getOrderBook(currencyPair);

    assertThat(orderBook).isNotNull();
    assertThat(orderBook.getBids()).hasSize(2);
    assertThat(orderBook.getAsks()).hasSize(2);

    assertThat(orderBook.getBids().get(0).getLimitPrice())
        .isEqualByComparingTo(new BigDecimal("5799000.0"));
    assertThat(orderBook.getBids().get(0).getOriginalAmount())
        .isEqualByComparingTo(new BigDecimal("0.5"));

    assertThat(orderBook.getAsks().get(0).getLimitPrice())
        .isEqualByComparingTo(new BigDecimal("5801000.0"));
    assertThat(orderBook.getAsks().get(0).getOriginalAmount())
        .isEqualByComparingTo(new BigDecimal("0.3"));
  }

  @Test
  public void testGetTrades() throws IOException {
    // given
    CurrencyPair currencyPair = CurrencyPair.BTC_PHP;
    List<CoinsphPublicTrade> mockTrades = new ArrayList<>();

    long currentTime = new Date().getTime();

    CoinsphPublicTrade trade1 =
        new CoinsphPublicTrade(
            12345L, // id
            new BigDecimal("5800000.0"), // price
            new BigDecimal("0.1"), // qty
            new BigDecimal("580000.0"), // quoteQty
            currentTime, // time
            true // isBuyerMaker
            );
    mockTrades.add(trade1);

    CoinsphPublicTrade trade2 =
        new CoinsphPublicTrade(
            12346L, // id
            new BigDecimal("5801000.0"), // price
            new BigDecimal("0.2"), // qty
            new BigDecimal("1160200.0"), // quoteQty
            currentTime + 1000, // time
            false // isBuyerMaker
            );
    mockTrades.add(trade2);

    // when
    when(coinsph.getTrades(eq("BTCPHP"), any())).thenReturn(mockTrades);

    // then
    Trades trades = marketDataService.getTrades(currencyPair);

    assertThat(trades).isNotNull();
    assertThat(trades.getTrades()).hasSize(2);

    assertThat(trades.getTrades().get(0).getId()).isEqualTo("12345");
    assertThat(trades.getTrades().get(0).getPrice())
        .isEqualByComparingTo(new BigDecimal("5800000.0"));
    assertThat(trades.getTrades().get(0).getOriginalAmount())
        .isEqualByComparingTo(new BigDecimal("0.1"));
    // When isBuyerMaker is true, the type should be ASK (seller is maker, buyer is taker)
    assertThat(trades.getTrades().get(0).getType().toString()).isEqualTo("ASK");

    assertThat(trades.getTrades().get(1).getId()).isEqualTo("12346");
    assertThat(trades.getTrades().get(1).getPrice())
        .isEqualByComparingTo(new BigDecimal("5801000.0"));
    assertThat(trades.getTrades().get(1).getOriginalAmount())
        .isEqualByComparingTo(new BigDecimal("0.2"));
    // When isBuyerMaker is false, the type should be BID (buyer is maker, seller is taker)
    assertThat(trades.getTrades().get(1).getType().toString()).isEqualTo("BID");
  }
}
