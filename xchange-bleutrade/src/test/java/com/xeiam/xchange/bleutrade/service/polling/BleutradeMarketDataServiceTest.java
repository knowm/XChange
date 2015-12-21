package com.xeiam.xchange.bleutrade.service.polling;

import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bleutrade.BleutradeAuthenticated;
import com.xeiam.xchange.bleutrade.BleutradeExchange;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeCurrenciesReturn;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeCurrency;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeMarket;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeMarketHistoryReturn;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeMarketsReturn;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeOrderBookReturn;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeTicker;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeTickerReturn;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.ExchangeException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
public class BleutradeMarketDataServiceTest extends BleutradeServiceTestSupport {

  private BleutradeMarketDataService marketDataService;

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
    tickerReturn.setResult(BLEUTRADE_TICKER);

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeTicker("BLEU_BTC")).thenReturn(tickerReturn);
    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    // when
    Ticker ticker = marketDataService.getTicker(BLEU_BTC_CP);

    // then
    assertThat(ticker.toString()).isEqualTo(BLEUTRADE_TICKER_STR);
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccesfulGetTicker() throws IOException {
    // given
    BleutradeTickerReturn tickerReturn = new BleutradeTickerReturn();
    tickerReturn.setSuccess(false);
    tickerReturn.setMessage("test message");
    tickerReturn.setResult(BLEUTRADE_TICKER);

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
    orderBookReturn1.setResult(createBleutradeOrderBook(BLEUTRADE_LEVEL_BUYS, BLEUTRADE_LEVEL_SELLS));

    BleutradeOrderBookReturn orderBookReturn2 = new BleutradeOrderBookReturn();
    orderBookReturn2.setSuccess(true);
    orderBookReturn2.setMessage("");
    orderBookReturn2.setResult(createBleutradeOrderBook(Collections.EMPTY_LIST, Collections.EMPTY_LIST));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeOrderBook("BLEU_BTC", "ALL", 30)).thenReturn(orderBookReturn1);
    PowerMockito.when(bleutrade.getBleutradeOrderBook("BLEU_BTC", "ALL", 50)).thenReturn(orderBookReturn2);
    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    // when
    OrderBook orderBook1 = marketDataService.getOrderBook(BLEU_BTC_CP, 30);
    OrderBook orderBook2 = marketDataService.getOrderBook(BLEU_BTC_CP, "test parameter");

    // then
    assertThat(orderBook1.toString()).isEqualTo("OrderBook [timestamp: null, "
        + "asks=["
          + SELLS_STR[0] + ", "
          + SELLS_STR[1] + ", "
          + SELLS_STR[2] + ", "
          + SELLS_STR[3]
        + "], "
        + "bids=[" + BUYS_STR[0] +", " + BUYS_STR[1] + "]"
        + "]");

    assertThat(orderBook2.getAsks()).isEmpty();
    assertThat(orderBook2.getBids()).isEmpty();
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulGetOrderBook() throws IOException {
    // given
    BleutradeOrderBookReturn orderBookReturn = new BleutradeOrderBookReturn();
    orderBookReturn.setSuccess(false);
    orderBookReturn.setMessage("test message");
    orderBookReturn.setResult(createBleutradeOrderBook(BLEUTRADE_LEVEL_BUYS, BLEUTRADE_LEVEL_SELLS));

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
    Date expectedTimestamp1;
    Date expectedTimestamp2;

    GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
    calendar.set(GregorianCalendar.MILLISECOND, 0);

    calendar.set(2014, GregorianCalendar.JULY, 29, 18, 8, 0);
    expectedTimestamp1 = calendar.getTime();
    calendar.set(2014, GregorianCalendar.JULY, 29, 18, 12, 35);
    expectedTimestamp2 = calendar.getTime();


    BleutradeMarketHistoryReturn marketHistoryReturn1 = new BleutradeMarketHistoryReturn();
    marketHistoryReturn1.setSuccess(true);
    marketHistoryReturn1.setMessage("test message");
    marketHistoryReturn1.setResult(BLEUTRADE_TRADES);

    BleutradeMarketHistoryReturn marketHistoryReturn2 = new BleutradeMarketHistoryReturn();
    marketHistoryReturn2.setSuccess(true);
    marketHistoryReturn2.setMessage("");
    marketHistoryReturn2.setResult(Collections.EMPTY_LIST);

    BleutradeMarketHistoryReturn marketHistoryReturn3 = new BleutradeMarketHistoryReturn();
    marketHistoryReturn3.setSuccess(true);
    marketHistoryReturn3.setMessage("test message");
    marketHistoryReturn3.setResult(Arrays.asList(BLEUTRADE_TRADES.get(0)));

    BleutradeMarketHistoryReturn marketHistoryReturn4 = new BleutradeMarketHistoryReturn();
    marketHistoryReturn4.setSuccess(true);
    marketHistoryReturn4.setMessage("test message");
    marketHistoryReturn4.setResult(Arrays.asList(BLEUTRADE_TRADES.get(1)));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeMarketHistory("BLEU_BTC", 30)).thenReturn(marketHistoryReturn1);
    PowerMockito.when(bleutrade.getBleutradeMarketHistory("BLEU_BTC", 50)).thenReturn(marketHistoryReturn2);
    PowerMockito.when(bleutrade.getBleutradeMarketHistory("BLEU_BTC", 1)).thenReturn(marketHistoryReturn3);
    PowerMockito.when(bleutrade.getBleutradeMarketHistory("BLEU_BTC", 200)).thenReturn(marketHistoryReturn4);

    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    // when
    Trades trades1 = marketDataService.getTrades(BLEU_BTC_CP, 30);
    Trades trades2 = marketDataService.getTrades(BLEU_BTC_CP, "test parameter");
    Trades trades3 = marketDataService.getTrades(BLEU_BTC_CP, 0);
    Trades trades4 = marketDataService.getTrades(BLEU_BTC_CP, 201);

    // then
    assertThat(trades1.toString()).isEqualTo(String.format("Trades\n" + "lastID= 0\n"
        + TRADES_STR[0] + "\n"
        + TRADES_STR[1] + "\n",
        expectedTimestamp1, expectedTimestamp2));

    assertThat(trades2.getTrades()).isEmpty();
    assertThat(trades3.toString()).isEqualTo(String.format("Trades\n" + "lastID= 0\n"
                + TRADES_STR[0] + "\n",
            expectedTimestamp1)
    );
    assertThat(trades4.toString()).isEqualTo(String.format("Trades\n" + "lastID= 0\n"
                + TRADES_STR[1] + "\n",
            expectedTimestamp2)
    );
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulGetTrades() throws IOException {
    // given
    BleutradeMarketHistoryReturn marketHistoryReturn = new BleutradeMarketHistoryReturn();
    marketHistoryReturn.setSuccess(false);
    marketHistoryReturn.setMessage("test message");
    marketHistoryReturn.setResult(BLEUTRADE_TRADES);

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
    BleutradeTickerReturn tickerReturn = new BleutradeTickerReturn();
    tickerReturn.setSuccess(true);
    tickerReturn.setMessage("test message");
    tickerReturn.setResult(BLEUTRADE_TICKERS);

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeTickers()).thenReturn(tickerReturn);
    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    // when
    List<BleutradeTicker> tickers = marketDataService.getBleutradeTickers();

    // then
    assertThat(tickers).hasSize(2);
    assertThat(tickers.get(0).toString()).isEqualTo(BLEUTRADE_TICKERS_STR[0]);
    assertThat(tickers.get(1).toString()).isEqualTo(BLEUTRADE_TICKERS_STR[1]);
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccesfulGetTickers() throws IOException {
    // given
    BleutradeTickerReturn tickerReturn = new BleutradeTickerReturn();
    tickerReturn.setSuccess(false);
    tickerReturn.setMessage("test message");
    tickerReturn.setResult(BLEUTRADE_TICKERS);

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
    BleutradeCurrenciesReturn currenciesReturn = new BleutradeCurrenciesReturn();
    currenciesReturn.setSuccess(true);
    currenciesReturn.setMessage("test message");
    currenciesReturn.setResult(BLEUTRADE_CURRENCIES);

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeCurrencies()).thenReturn(currenciesReturn);
    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    // when
    List<BleutradeCurrency> currencies = marketDataService.getBleutradeCurrencies();

    // then
    assertThat(currencies).hasSize(2);
    assertThat(currencies.get(0).toString()).isEqualTo(CURRENCIES_STR[0]);
    assertThat(currencies.get(1).toString()).isEqualTo(CURRENCIES_STR[1]);
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccesfulGetCurrencies() throws IOException {
    // given
    BleutradeCurrenciesReturn currenciesReturn = new BleutradeCurrenciesReturn();
    currenciesReturn.setSuccess(false);
    currenciesReturn.setMessage("test message");
    currenciesReturn.setResult(BLEUTRADE_CURRENCIES);

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
    BleutradeMarketsReturn marketsReturn = new BleutradeMarketsReturn();
    marketsReturn.setSuccess(true);
    marketsReturn.setMessage("test message");
    marketsReturn.setResult(BLEUTRADE_MARKETS);

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeMarkets()).thenReturn(marketsReturn);
    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    // when
    List<BleutradeMarket> markets = marketDataService.getBleutradeMarkets();

    // then
    assertThat(markets).hasSize(2);
    assertThat(markets.get(0).toString()).isEqualTo(MARKETS_STR[0]);
    assertThat(markets.get(1).toString()).isEqualTo(MARKETS_STR[1]);
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccesfulGetMarkets() throws IOException {
    // given
    BleutradeMarketsReturn marketsReturn = new BleutradeMarketsReturn();
    marketsReturn.setSuccess(false);
    marketsReturn.setMessage("test message");
    marketsReturn.setResult(BLEUTRADE_MARKETS);

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeMarkets()).thenReturn(marketsReturn);
    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    // when
    marketDataService.getBleutradeMarkets();

    // then
    fail("BleutradeMarketDataService should throw ExchangeException when markets request was unsuccessful");
  }
}
