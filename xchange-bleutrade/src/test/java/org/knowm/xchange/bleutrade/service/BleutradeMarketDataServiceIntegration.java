package org.knowm.xchange.bleutrade.service;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import static org.powermock.api.mockito.PowerMockito.mock;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bleutrade.BleutradeAssert;
import org.knowm.xchange.bleutrade.BleutradeAuthenticated;
import org.knowm.xchange.bleutrade.BleutradeExchange;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeCurrenciesReturn;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeCurrency;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeMarket;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeMarketHistoryReturn;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeMarketsReturn;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeOrderBookReturn;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeTicker;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeTickerReturn;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
public class BleutradeMarketDataServiceIntegration extends BleutradeServiceTestSupport {

  private BleutradeMarketDataService marketDataService;

  private static final Ticker TICKER = new Ticker.Builder().currencyPair(BLEU_BTC_CP).last(new BigDecimal("0.00101977"))
      .bid(new BigDecimal("0.00100000")).ask(new BigDecimal("0.00101977")).high(new BigDecimal("0.00105000")).low(new BigDecimal("0.00086000"))
      .vwap(new BigDecimal("0.00103455")).volume(new BigDecimal("2450.97496015")).timestamp(new Date(1406632770000L)).build();

  @Before
  public void setUp() {
    BleutradeExchange exchange = (BleutradeExchange) ExchangeFactory.INSTANCE.createExchange(BleutradeExchange.class.getCanonicalName());
    exchange.getExchangeSpecification().setUserName(SPECIFICATION_USERNAME);
    exchange.getExchangeSpecification().setApiKey(SPECIFICATION_API_KEY);
    exchange.getExchangeSpecification().setSecretKey(SPECIFICATION_SECRET_KEY);

    marketDataService = new BleutradeMarketDataService(exchange);
  }

  @Test
  public void constructor() {
    assertThat(Whitebox.getInternalState(marketDataService, "apiKey")).isEqualTo(SPECIFICATION_API_KEY);
  }

  @Test
  public void shouldGetTicker() throws IOException {
    // given
    BleutradeTickerReturn tickerReturn = new BleutradeTickerReturn();
    tickerReturn.setSuccess(true);
    tickerReturn.setMessage("test message");
    tickerReturn.setResult(expectedBleutradeTicker());

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeTicker("BLEU_BTC")).thenReturn(tickerReturn);
    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    // when
    Ticker ticker = marketDataService.getTicker(BLEU_BTC_CP);

    // then
    assertThat(ticker.toString()).isEqualTo(EXPECTED_BLEUTRADE_TICKER_STR);
    BleutradeAssert.assertEquals(ticker, TICKER);
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccesfulGetTicker() throws IOException {
    // given
    BleutradeTickerReturn tickerReturn = new BleutradeTickerReturn();
    tickerReturn.setSuccess(false);
    tickerReturn.setMessage("test message");
    tickerReturn.setResult(expectedBleutradeTicker());

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeTicker("BLEU_BTC")).thenReturn(tickerReturn);
    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    // when
    marketDataService.getTicker(BLEU_BTC_CP);

    // then
    fail("BleutradeMarketDataService should throw ExchangeException when ticker request was unsuccessful");
  }

  @Test
  public void shouldGetOrderBook() throws IOException {
    // given
    BleutradeOrderBookReturn orderBookReturn1 = new BleutradeOrderBookReturn();
    orderBookReturn1.setSuccess(true);
    orderBookReturn1.setMessage("test message");
    orderBookReturn1.setResult(createBleutradeOrderBook(expectedBleutradeLevelBuys(), expectedBleutradeLevelSells()));

    BleutradeOrderBookReturn orderBookReturn2 = new BleutradeOrderBookReturn();
    orderBookReturn2.setSuccess(true);
    orderBookReturn2.setMessage("");
    orderBookReturn2.setResult(createBleutradeOrderBook(Collections.EMPTY_LIST, Collections.EMPTY_LIST));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeOrderBook("BTC_AUD", "ALL", 30)).thenReturn(orderBookReturn1);
    PowerMockito.when(bleutrade.getBleutradeOrderBook("BLEU_BTC", "ALL", 50)).thenReturn(orderBookReturn2);
    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    final LimitOrder[] expectedAsks = expectedAsks();
    final LimitOrder[] expectedBids = expectedBids();

    // when
    OrderBook orderBook1 = marketDataService.getOrderBook(CurrencyPair.BTC_AUD, 30);
    OrderBook orderBook2 = marketDataService.getOrderBook(BLEU_BTC_CP, "test parameter");

    // then
    List<LimitOrder> asks = orderBook1.getAsks();
    assertThat(asks).hasSize(4);
    for (int i = 0; i < asks.size(); i++) {
      BleutradeAssert.assertEquals(asks.get(i), expectedAsks[i]);
    }

    List<LimitOrder> bids = orderBook1.getBids();
    assertThat(bids).hasSize(2);
    for (int i = 0; i < bids.size(); i++) {
      BleutradeAssert.assertEquals(bids.get(i), expectedBids[i]);
    }

    assertThat(orderBook2.getAsks()).isEmpty();
    assertThat(orderBook2.getBids()).isEmpty();
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulGetOrderBook() throws IOException {
    // given
    BleutradeOrderBookReturn orderBookReturn = new BleutradeOrderBookReturn();
    orderBookReturn.setSuccess(false);
    orderBookReturn.setMessage("test message");
    orderBookReturn.setResult(createBleutradeOrderBook(expectedBleutradeLevelBuys(), expectedBleutradeLevelSells()));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeOrderBook("BLEU_BTC", "ALL", 50)).thenReturn(orderBookReturn);
    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    // when
    marketDataService.getOrderBook(BLEU_BTC_CP);

    // then
    fail("BleutradeMarketDataService should throw ExchangeException when order book request was unsuccessful");
  }

  @Test
  public void shouldGetTrades() throws IOException {
    // given
    final List<BleutradeTrade> expectedBleutradeTrades = expectedBleutradeTrades();

    BleutradeMarketHistoryReturn marketHistoryReturn1 = new BleutradeMarketHistoryReturn();
    marketHistoryReturn1.setSuccess(true);
    marketHistoryReturn1.setMessage("test message");
    marketHistoryReturn1.setResult(expectedBleutradeTrades);

    BleutradeMarketHistoryReturn marketHistoryReturn2 = new BleutradeMarketHistoryReturn();
    marketHistoryReturn2.setSuccess(true);
    marketHistoryReturn2.setMessage("");
    marketHistoryReturn2.setResult(Collections.EMPTY_LIST);

    BleutradeMarketHistoryReturn marketHistoryReturn3 = new BleutradeMarketHistoryReturn();
    marketHistoryReturn3.setSuccess(true);
    marketHistoryReturn3.setMessage("test message");
    marketHistoryReturn3.setResult(Arrays.asList(expectedBleutradeTrades.get(0)));

    BleutradeMarketHistoryReturn marketHistoryReturn4 = new BleutradeMarketHistoryReturn();
    marketHistoryReturn4.setSuccess(true);
    marketHistoryReturn4.setMessage("test message");
    marketHistoryReturn4.setResult(Arrays.asList(expectedBleutradeTrades.get(1)));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeMarketHistory("BTC_AUD", 30)).thenReturn(marketHistoryReturn1);
    PowerMockito.when(bleutrade.getBleutradeMarketHistory("BTC_AUD", 50)).thenReturn(marketHistoryReturn2);
    PowerMockito.when(bleutrade.getBleutradeMarketHistory("BTC_AUD", 1)).thenReturn(marketHistoryReturn3);
    PowerMockito.when(bleutrade.getBleutradeMarketHistory("BTC_AUD", 200)).thenReturn(marketHistoryReturn4);

    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    final Trade[] expectedTrades = expectedTrades();

    // when
    Trades trades1 = marketDataService.getTrades(CurrencyPair.BTC_AUD, 30);
    Trades trades2 = marketDataService.getTrades(CurrencyPair.BTC_AUD, "test parameter");
    Trades trades3 = marketDataService.getTrades(CurrencyPair.BTC_AUD, 0);
    Trades trades4 = marketDataService.getTrades(CurrencyPair.BTC_AUD, 201);

    // then
    List<Trade> tradeList = trades1.getTrades();
    assertThat(tradeList).hasSize(2);

    for (int i = 0; i < tradeList.size(); i++) {
      BleutradeAssert.assertEquals(tradeList.get(i), expectedTrades[i]);
    }

    assertThat(trades2.getTrades()).isEmpty();

    assertThat(trades3.getTrades()).hasSize(1);
    BleutradeAssert.assertEquals(trades3.getTrades().get(0), expectedTrades[0]);

    assertThat(trades4.getTrades()).hasSize(1);
    BleutradeAssert.assertEquals(trades4.getTrades().get(0), expectedTrades[1]);
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulGetTrades() throws IOException {
    // given
    BleutradeMarketHistoryReturn marketHistoryReturn = new BleutradeMarketHistoryReturn();
    marketHistoryReturn.setSuccess(false);
    marketHistoryReturn.setMessage("test message");
    marketHistoryReturn.setResult(expectedBleutradeTrades());

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeMarketHistory("BLEU_BTC", 50)).thenReturn(marketHistoryReturn);

    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    // when
    marketDataService.getTrades(BLEU_BTC_CP);

    // then
    fail("BleutradeMarketDataService should throw ExchangeException when trades request was unsuccessful");
  }

  @Test
  public void shouldGetTickers() throws IOException {
    // given
    final List<BleutradeTicker> expectedBleutradeTickers = expectedBleutradeTickers();
    final String[] expectedBleutradeTickersStr = expectedBleutradeTickersStr();

    BleutradeTickerReturn tickerReturn = new BleutradeTickerReturn();
    tickerReturn.setSuccess(true);
    tickerReturn.setMessage("test message");
    tickerReturn.setResult(expectedBleutradeTickers);

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeTickers()).thenReturn(tickerReturn);
    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    // when
    List<BleutradeTicker> tickers = marketDataService.getBleutradeTickers();

    // then
    assertThat(tickers).hasSize(2);

    for (int i = 0; i < tickers.size(); i++) {
      BleutradeAssert.assertEquals(tickers.get(i), expectedBleutradeTickers.get(i));
      assertThat(tickers.get(i).toString()).isEqualTo(expectedBleutradeTickersStr[i]);
    }
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccesfulGetTickers() throws IOException {
    // given
    BleutradeTickerReturn tickerReturn = new BleutradeTickerReturn();
    tickerReturn.setSuccess(false);
    tickerReturn.setMessage("test message");
    tickerReturn.setResult(expectedBleutradeTickers());

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeTickers()).thenReturn(tickerReturn);
    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    // when
    marketDataService.getBleutradeTickers();

    // then
    fail("BleutradeMarketDataService should throw ExchangeException when tickers request was unsuccessful");
  }

  @Test
  public void shouldGetCurrencies() throws IOException {
    // given
    final List<BleutradeCurrency> expectedBleutradeCurrencies = expectedBleutradeCurrencies();
    final String[] expectedBleutradeCurrenciesStr = expectedBleutradeCurrenciesStr();

    BleutradeCurrenciesReturn currenciesReturn = new BleutradeCurrenciesReturn();
    currenciesReturn.setSuccess(true);
    currenciesReturn.setMessage("test message");
    currenciesReturn.setResult(expectedBleutradeCurrencies);

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeCurrencies()).thenReturn(currenciesReturn);
    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    // when
    List<BleutradeCurrency> currencies = marketDataService.getBleutradeCurrencies();

    // then
    assertThat(currencies).hasSize(2);

    for (int i = 0; i < currencies.size(); i++) {
      BleutradeAssert.assertEquals(currencies.get(i), expectedBleutradeCurrencies.get(i));
      assertThat(currencies.get(i).toString()).isEqualTo(expectedBleutradeCurrenciesStr[i]);
    }
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccesfulGetCurrencies() throws IOException {
    // given
    BleutradeCurrenciesReturn currenciesReturn = new BleutradeCurrenciesReturn();
    currenciesReturn.setSuccess(false);
    currenciesReturn.setMessage("test message");
    currenciesReturn.setResult(expectedBleutradeCurrencies());

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeCurrencies()).thenReturn(currenciesReturn);
    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    // when
    marketDataService.getBleutradeCurrencies();

    // then
    fail("BleutradeMarketDataService should throw ExchangeException when currencies request was unsuccessful");
  }

  @Test
  public void shouldGetMarkets() throws IOException {
    // given
    final List<BleutradeMarket> expectedBleutradeMarkets = expectedBleutradeMarkets();
    final String[] expectedBleutradeMarketsStr = expectedBleutradeMarketsStr();

    BleutradeMarketsReturn marketsReturn = new BleutradeMarketsReturn();
    marketsReturn.setSuccess(true);
    marketsReturn.setMessage("test message");
    marketsReturn.setResult(expectedBleutradeMarkets);

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeMarkets()).thenReturn(marketsReturn);
    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    // when
    List<BleutradeMarket> markets = marketDataService.getBleutradeMarkets();

    // then
    assertThat(markets).hasSize(2);

    for (int i = 0; i < markets.size(); i++) {
      BleutradeAssert.assertEquals(markets.get(i), expectedBleutradeMarkets.get(i));
      assertThat(markets.get(i).toString()).isEqualTo(expectedBleutradeMarketsStr[i]);
    }
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccesfulGetMarkets() throws IOException {
    // given
    BleutradeMarketsReturn marketsReturn = new BleutradeMarketsReturn();
    marketsReturn.setSuccess(false);
    marketsReturn.setMessage("test message");
    marketsReturn.setResult(expectedBleutradeMarkets());

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeMarkets()).thenReturn(marketsReturn);
    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    // when
    marketDataService.getBleutradeMarkets();

    // then
    fail("BleutradeMarketDataService should throw ExchangeException when markets request was unsuccessful");
  }
}
