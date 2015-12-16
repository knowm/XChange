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
import com.xeiam.xchange.currency.CurrencyPair;
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

import java.math.BigDecimal;
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
  public void setUp() throws Exception {
    BleutradeExchange exchange = (BleutradeExchange) ExchangeFactory.INSTANCE.createExchange(BleutradeExchange.class.getCanonicalName());
    exchange.getExchangeSpecification().setUserName("admin");
    exchange.getExchangeSpecification().setApiKey("publicKey");
    exchange.getExchangeSpecification().setSecretKey("secretKey");

    marketDataService = new BleutradeMarketDataService(exchange);
  }

  @Test
  public void constructor() throws Exception {
    assertThat(Whitebox.getInternalState(marketDataService, "apiKey")).isEqualTo("publicKey");
  }

  @Test
  public void shouldGetTicker() throws Exception {
    // given
    BleutradeTickerReturn tickerReturn = new BleutradeTickerReturn();
    tickerReturn.setSuccess(true);
    tickerReturn.setMessage("test message");
    tickerReturn.setResult(Arrays.asList(createBleutradeTicker("BLEU_BTC", new BigDecimal("0.00095000"), new BigDecimal("0.00105000"), new BigDecimal("0.00086000"), new BigDecimal("0.00101977"),
            new BigDecimal("0.00103455"), new BigDecimal("2450.97496015"), new BigDecimal("2.40781647"), "2014-07-29 11:19:30", new BigDecimal("0.00100000"), new BigDecimal("0.00101977"), true)
    ));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeTicker("BLEU_BTC")).thenReturn(tickerReturn);
    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    // when
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BLEU", "BTC"));

    // then
    assertThat(ticker.toString()).isEqualTo("Ticker [currencyPair=BLEU/BTC, last=0.00101977, bid=0.00100000, ask=0.00101977, high=0.00105000, low=0.00086000,avg=0.00103455, volume=2450.97496015, timestamp=1406632770000]");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccesfulGetTicker() throws Exception {
    // given
    BleutradeTickerReturn tickerReturn = new BleutradeTickerReturn();
    tickerReturn.setSuccess(false);
    tickerReturn.setMessage("test message");
    tickerReturn.setResult(Arrays.asList(createBleutradeTicker("BLEU_BTC", new BigDecimal("0.00095000"), new BigDecimal("0.00105000"), new BigDecimal("0.00086000"), new BigDecimal("0.00101977"),
            new BigDecimal("0.00103455"), new BigDecimal("2450.97496015"), new BigDecimal("2.40781647"), "2014-07-29 11:19:30", new BigDecimal("0.00100000"), new BigDecimal("0.00101977"), true)
    ));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeTicker("BLEU_BTC")).thenReturn(tickerReturn);
    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    // when
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BLEU", "BTC"));

    // then
    fail("BleutradeMarketDataService should throw ExchangeException when ticker request was unsuccessful");
  }

  @Test
  public void shouldGetOrderBook() throws Exception {
    // given
    BleutradeOrderBookReturn orderBookReturn1 = new BleutradeOrderBookReturn();
    orderBookReturn1.setSuccess(true);
    orderBookReturn1.setMessage("test message");
    orderBookReturn1.setResult(createBleutradeOrderBook(
        Arrays.asList(createBleutradeLevel(new BigDecimal("4.99400000"), new BigDecimal("3.00650900")), createBleutradeLevel(new BigDecimal("50.00000000"), new BigDecimal("3.50000000"))), Arrays
            .asList(createBleutradeLevel(new BigDecimal("12.44147454"), new BigDecimal("5.13540000")), createBleutradeLevel(new BigDecimal("100.00000000"), new BigDecimal("6.25500000")),
                createBleutradeLevel(new BigDecimal("30.00000000"), new BigDecimal("6.75500001")), createBleutradeLevel(new BigDecimal("13.49989999"), new BigDecimal("6.76260099")))));

    BleutradeOrderBookReturn orderBookReturn2 = new BleutradeOrderBookReturn();
    orderBookReturn2.setSuccess(true);
    orderBookReturn2.setMessage("");
    orderBookReturn2.setResult(createBleutradeOrderBook(Collections.EMPTY_LIST, Collections.EMPTY_LIST));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeOrderBook("BLEU_BTC", "ALL", 30)).thenReturn(orderBookReturn1);
    PowerMockito.when(bleutrade.getBleutradeOrderBook("BLEU_BTC", "ALL", 50)).thenReturn(orderBookReturn2);
    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    // when
    OrderBook orderBook1 = marketDataService.getOrderBook(new CurrencyPair("BLEU", "BTC"), 30);
    OrderBook orderBook2 = marketDataService.getOrderBook(new CurrencyPair("BLEU", "BTC"), "test parameter");

    // then
    assertThat(orderBook1.toString()).isEqualTo("OrderBook [timestamp: null, "
        + "asks=["
          + "LimitOrder [limitPrice=5.13540000, Order [type=ASK, tradableAmount=12.44147454, currencyPair=BLEU/BTC, id=null, timestamp=null]], "
          + "LimitOrder [limitPrice=6.25500000, Order [type=ASK, tradableAmount=100.00000000, currencyPair=BLEU/BTC, id=null, timestamp=null]], "
          + "LimitOrder [limitPrice=6.75500001, Order [type=ASK, tradableAmount=30.00000000, currencyPair=BLEU/BTC, id=null, timestamp=null]], "
          + "LimitOrder [limitPrice=6.76260099, Order [type=ASK, tradableAmount=13.49989999, currencyPair=BLEU/BTC, id=null, timestamp=null]]"
        + "], "
        + "bids=["
          + "LimitOrder [limitPrice=3.00650900, Order [type=BID, tradableAmount=4.99400000, currencyPair=BLEU/BTC, id=null, timestamp=null]], "
          + "LimitOrder [limitPrice=3.50000000, Order [type=BID, tradableAmount=50.00000000, currencyPair=BLEU/BTC, id=null, timestamp=null]]]"
        + "]");

    assertThat(orderBook2.getAsks()).isEmpty();
    assertThat(orderBook2.getBids()).isEmpty();
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulGetOrderBook() throws Exception {
    // given
    BleutradeOrderBookReturn orderBookReturn = new BleutradeOrderBookReturn();
    orderBookReturn.setSuccess(false);
    orderBookReturn.setMessage("test message");
    orderBookReturn.setResult(createBleutradeOrderBook(
        Arrays.asList(
            createBleutradeLevel(new BigDecimal("4.99400000"), new BigDecimal("3.00650900"))
        ),
        Arrays.asList(
            createBleutradeLevel(new BigDecimal("12.44147454"), new BigDecimal("5.13540000"))
        )
    ));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeOrderBook("BLEU_BTC", "ALL", 50)).thenReturn(orderBookReturn);
    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    // when
    marketDataService.getOrderBook(new CurrencyPair("BLEU", "BTC"));

    // then
    fail("BleutradeMarketDataService should throw ExchangeException when order book request was unsuccessful");
  }

  @Test
  public void shouldGetTrades() throws Exception {
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
    marketHistoryReturn1.setResult(
        Arrays.asList(
            createBleutradeTrade("2014-07-29 18:08:00", new BigDecimal("654971.69417461"), new BigDecimal("0.00000055"), new BigDecimal("0.360234432"), "BUY"),
            createBleutradeTrade("2014-07-29 18:12:35", new BigDecimal("120.00000000"), new BigDecimal("0.00006600"), new BigDecimal("0.360234432"), "SELL")
        )
    );

    BleutradeMarketHistoryReturn marketHistoryReturn2 = new BleutradeMarketHistoryReturn();
    marketHistoryReturn2.setSuccess(true);
    marketHistoryReturn2.setMessage("");
    marketHistoryReturn2.setResult(Collections.EMPTY_LIST);

    BleutradeMarketHistoryReturn marketHistoryReturn3 = new BleutradeMarketHistoryReturn();
    marketHistoryReturn3.setSuccess(true);
    marketHistoryReturn3.setMessage("test message");
    marketHistoryReturn3.setResult(
        Arrays.asList(
            createBleutradeTrade("2014-07-29 18:08:00", new BigDecimal("654971.69417461"), new BigDecimal("0.00000055"), new BigDecimal("0.360234432"), "BUY")
        )
    );

    BleutradeMarketHistoryReturn marketHistoryReturn4 = new BleutradeMarketHistoryReturn();
    marketHistoryReturn4.setSuccess(true);
    marketHistoryReturn4.setMessage("test message");
    marketHistoryReturn4.setResult(
        Arrays.asList(
            createBleutradeTrade("2014-07-29 18:12:35", new BigDecimal("120.00000000"), new BigDecimal("0.00006600"), new BigDecimal("0.360234432"), "SELL")
        )
    );

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeMarketHistory("BLEU_BTC", 30)).thenReturn(marketHistoryReturn1);
    PowerMockito.when(bleutrade.getBleutradeMarketHistory("BLEU_BTC", 50)).thenReturn(marketHistoryReturn2);
    PowerMockito.when(bleutrade.getBleutradeMarketHistory("BLEU_BTC", 1)).thenReturn(marketHistoryReturn3);
    PowerMockito.when(bleutrade.getBleutradeMarketHistory("BLEU_BTC", 200)).thenReturn(marketHistoryReturn4);

    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    // when
    Trades trades1 = marketDataService.getTrades(new CurrencyPair("BLEU", "BTC"), 30);
    Trades trades2 = marketDataService.getTrades(new CurrencyPair("BLEU", "BTC"), "test parameter");
    Trades trades3 = marketDataService.getTrades(new CurrencyPair("BLEU", "BTC"), 0);
    Trades trades4 = marketDataService.getTrades(new CurrencyPair("BLEU", "BTC"), 201);

    // then
    assertThat(trades1.toString()).isEqualTo(String.format("Trades\n" + "lastID= 0\n"
        + "[trade=Trade [type=BID, tradableAmount=654971.69417461, currencyPair=BLEU/BTC, price=5.5E-7, timestamp=%s, id=null]]\n"
        + "[trade=Trade [type=ASK, tradableAmount=120.00000000, currencyPair=BLEU/BTC, price=0.00006600, timestamp=%s, id=null]]\n",
        expectedTimestamp1, expectedTimestamp2));

    assertThat(trades2.getTrades()).isEmpty();
    assertThat(trades3.toString()).isEqualTo(String.format("Trades\n" + "lastID= 0\n"
                + "[trade=Trade [type=BID, tradableAmount=654971.69417461, currencyPair=BLEU/BTC, price=5.5E-7, timestamp=%s, id=null]]\n",
            expectedTimestamp1)
    );
    assertThat(trades4.toString()).isEqualTo(String.format("Trades\n" + "lastID= 0\n"
                + "[trade=Trade [type=ASK, tradableAmount=120.00000000, currencyPair=BLEU/BTC, price=0.00006600, timestamp=%s, id=null]]\n",
            expectedTimestamp2)
    );
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulGetTrades() throws Exception {
    // given
    BleutradeMarketHistoryReturn marketHistoryReturn = new BleutradeMarketHistoryReturn();
    marketHistoryReturn.setSuccess(false);
    marketHistoryReturn.setMessage("test message");
    marketHistoryReturn.setResult(Arrays.asList(createBleutradeTrade("2014-07-29 18:08:00", new BigDecimal("654971.69417461"), new BigDecimal("0.00000055"), new BigDecimal("0.360234432"), "BUY"),
            createBleutradeTrade("2014-07-29 18:12:35", new BigDecimal("120.00000000"), new BigDecimal("0.00006600"), new BigDecimal("0.360234432"), "SELL")));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeMarketHistory("BLEU_BTC", 50)).thenReturn(marketHistoryReturn);

    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    // when
    marketDataService.getTrades(new CurrencyPair("BLEU", "BTC"));

    // then
    fail("BleutradeMarketDataService should throw ExchangeException when trades request was unsuccessful");
  }

  @Test
  public void shouldGetTickers() throws Exception {
    // given
    BleutradeTickerReturn tickerReturn = new BleutradeTickerReturn();
    tickerReturn.setSuccess(true);
    tickerReturn.setMessage("test message");
    tickerReturn.setResult(Arrays.asList(
        createBleutradeTicker("BLEU_BTC", new BigDecimal("0.00095000"), new BigDecimal("0.00105000"), new BigDecimal("0.00086000"), new BigDecimal("0.00101977"),
            new BigDecimal("0.00103455"), new BigDecimal("2450.97496015"), new BigDecimal("2.40781647"), "2014-07-29 11:19:30", new BigDecimal("0.00100000"), new BigDecimal("0.00101977"), true),
        createBleutradeTicker("LTC_BTC", new BigDecimal("0.00095000"), new BigDecimal("0.01333000"), new BigDecimal("0.01167001"), new BigDecimal("0.01333000"),
            new BigDecimal("0.01235000"), new BigDecimal("14.46077245"), new BigDecimal("0.18765956"), "2014-07-29 11:48:02", new BigDecimal("0.01268311"), new BigDecimal("0.01333000"), true)
    ));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeTickers()).thenReturn(tickerReturn);
    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    // when
    List<BleutradeTicker> tickers = marketDataService.getBleutradeTickers();

    // then
    assertThat(tickers).hasSize(2);
    assertThat(tickers.get(0).toString()).isEqualTo("BleutradeTicker [MarketName=BLEU_BTC, PrevDay=0.00095000, High=0.00105000, Low=0.00086000, Last=0.00101977, Average=0.00103455, "
        + "Volume=2450.97496015, BaseVolume=2.40781647, TimeStamp=2014-07-29 11:19:30, Bid=0.00100000, Ask=0.00101977, IsActive=true, additionalProperties={}]");
    assertThat(tickers.get(1).toString()).isEqualTo("BleutradeTicker [MarketName=LTC_BTC, PrevDay=0.00095000, High=0.01333000, Low=0.01167001, Last=0.01333000, Average=0.01235000, "
        + "Volume=14.46077245, BaseVolume=0.18765956, TimeStamp=2014-07-29 11:48:02, Bid=0.01268311, Ask=0.01333000, IsActive=true, additionalProperties={}]");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccesfulGetTickers() throws Exception {
    // given
    BleutradeTickerReturn tickerReturn = new BleutradeTickerReturn();
    tickerReturn.setSuccess(false);
    tickerReturn.setMessage("test message");
    tickerReturn.setResult(Arrays.asList(
        createBleutradeTicker("BLEU_BTC", new BigDecimal("0.00095000"), new BigDecimal("0.00105000"), new BigDecimal("0.00086000"), new BigDecimal("0.00101977"),
            new BigDecimal("0.00103455"), new BigDecimal("2450.97496015"), new BigDecimal("2.40781647"), "2014-07-29 11:19:30", new BigDecimal("0.00100000"), new BigDecimal("0.00101977"), true),
        createBleutradeTicker("LTC_BTC", new BigDecimal("0.00095000"), new BigDecimal("0.01333000"), new BigDecimal("0.01167001"), new BigDecimal("0.01333000"),
            new BigDecimal("0.01235000"), new BigDecimal("14.46077245"), new BigDecimal("0.18765956"), "2014-07-29 11:48:02", new BigDecimal("0.01268311"), new BigDecimal("0.01333000"), true)
    ));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeTickers()).thenReturn(tickerReturn);
    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    // when
    marketDataService.getBleutradeTickers();

    // then
    fail("BleutradeMarketDataService should throw ExchangeException when tickers request was unsuccessful");
  }

  @Test
  public void shouldGetCurrencies() throws Exception {
    // given
    BleutradeCurrenciesReturn currenciesReturn = new BleutradeCurrenciesReturn();
    currenciesReturn.setSuccess(true);
    currenciesReturn.setMessage("test message");
    currenciesReturn.setResult(Arrays.asList(
        createBleutradeCurrency("BTC", "Bitcoin", 2, new BigDecimal("0.00080000"), true, "BITCOIN"),
        createBleutradeCurrency("LTC", "Litecoin", 4, new BigDecimal("0.02000000"), true, "BITCOIN")
    ));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeCurrencies()).thenReturn(currenciesReturn);
    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    // when
    List<BleutradeCurrency> currencies = marketDataService.getBleutradeCurrencies();

    // then
    assertThat(currencies).hasSize(2);
    assertThat(currencies.get(0).toString()).isEqualTo("BleutradeCurrency [Currency=BTC, CurrencyLong=Bitcoin, MinConfirmation=2, TxFee=0.00080000, IsActive=true, CoinType=BITCOIN, additionalProperties={}]");
    assertThat(currencies.get(1).toString()).isEqualTo("BleutradeCurrency [Currency=LTC, CurrencyLong=Litecoin, MinConfirmation=4, TxFee=0.02000000, IsActive=true, CoinType=BITCOIN, additionalProperties={}]");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccesfulGetCurrencies() throws Exception {
    // given
    BleutradeCurrenciesReturn currenciesReturn = new BleutradeCurrenciesReturn();
    currenciesReturn.setSuccess(false);
    currenciesReturn.setMessage("test message");
    currenciesReturn.setResult(Arrays.asList(
        createBleutradeCurrency("BTC", "Bitcoin", 2, new BigDecimal("0.00080000"), true, "BITCOIN"),
        createBleutradeCurrency("LTC", "Litecoin", 4, new BigDecimal("0.02000000"), true, "BITCOIN")
    ));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeCurrencies()).thenReturn(currenciesReturn);
    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    // when
    marketDataService.getBleutradeCurrencies();

    // then
    fail("BleutradeMarketDataService should throw ExchangeException when currencies request was unsuccessful");
  }


  @Test
  public void shouldGetMarkets() throws Exception {
    // given
    BleutradeMarketsReturn marketsReturn = new BleutradeMarketsReturn();
    marketsReturn.setSuccess(true);
    marketsReturn.setMessage("test message");
    marketsReturn.setResult(Arrays.asList(
        createBleutradeMarket("DOGE", "BTC", "Dogecoin", "Bitcoin", new BigDecimal("0.10000000"), "DOGE_BTC", true),
        createBleutradeMarket("BLEU", "BTC", "Bleutrade Share", "Bitcoin", new BigDecimal("0.00000001"), "BLEU_BTC", true)
    ));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeMarkets()).thenReturn(marketsReturn);
    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    // when
    List<BleutradeMarket> markets = marketDataService.getBleutradeMarkets();

    // then
    assertThat(markets).hasSize(2);
    assertThat(markets.get(0).toString()).isEqualTo("BleutradeMarket [MarketCurrency=DOGE, BaseCurrency=BTC, MarketCurrencyLong=Dogecoin, BaseCurrencyLong=Bitcoin, MinTradeSize=0.10000000, MarketName=DOGE_BTC, IsActive=true, additionalProperties={}]");
    assertThat(markets.get(1).toString()).isEqualTo("BleutradeMarket [MarketCurrency=BLEU, BaseCurrency=BTC, MarketCurrencyLong=Bleutrade Share, BaseCurrencyLong=Bitcoin, MinTradeSize=1E-8, MarketName=BLEU_BTC, IsActive=true, additionalProperties={}]");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccesfulGetMarkets() throws Exception {
    // given
    BleutradeMarketsReturn marketsReturn = new BleutradeMarketsReturn();
    marketsReturn.setSuccess(false);
    marketsReturn.setMessage("test message");
    marketsReturn.setResult(Arrays.asList(
        createBleutradeMarket("DOGE", "BTC", "Dogecoin", "Bitcoin", new BigDecimal("0.10000000"), "DOGE_BTC", true),
        createBleutradeMarket("BLEU", "BTC", "Bleutrade Share", "Bitcoin", new BigDecimal("0.00000001"), "BLEU_BTC", true)
    ));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBleutradeMarkets()).thenReturn(marketsReturn);
    Whitebox.setInternalState(marketDataService, "bleutrade", bleutrade);

    // when
    marketDataService.getBleutradeMarkets();

    // then
    fail("BleutradeMarketDataService should throw ExchangeException when markets request was unsuccessful");
  }

}
