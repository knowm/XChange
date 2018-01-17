package org.knowm.xchange.bitcointoyou;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import org.assertj.core.api.SoftAssertions;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.bitcointoyou.dto.account.BitcointoyouBalance;
import org.knowm.xchange.bitcointoyou.dto.account.BitcointoyouBalanceTest;
import org.knowm.xchange.bitcointoyou.dto.marketdata.BitcointoyouMarketData;
import org.knowm.xchange.bitcointoyou.dto.marketdata.BitcointoyouOrderBook;
import org.knowm.xchange.bitcointoyou.dto.marketdata.BitcointoyouPublicTrade;
import org.knowm.xchange.bitcointoyou.dto.marketdata.BitcointoyouTicker;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Tests the {@link BitcointoyouAdapters} class.
 *
 * @author Danilo Guimaraes
 */
public class BitcointoyouAdaptersTest {

  private static BitcointoyouOrderBook bitcointoyouOrderBook;
  private static OrderBook orderBook;
  private static Ticker ticker;
  private static BitcointoyouPublicTrade[] bitcointoyouPublicTrades;
  private static BitcointoyouBalance bitcointoyouBalance;


  @BeforeClass
  public static void setUp() throws Exception {

    bitcointoyouOrderBook = loadBitcointoyouOrderBookFromExampleData();
    orderBook = BitcointoyouAdapters.adaptBitcointoyouOrderBook(bitcointoyouOrderBook, null);

    BitcointoyouTicker bitcointoyouTicker = loadBitcointoyouTickerFromExampleData();
    ticker = BitcointoyouAdapters.adaptBitcointoyouTicker(bitcointoyouTicker, bitcointoyouTicker.getCurrencyPair());

    bitcointoyouPublicTrades = loadBitcointoyouPublicTradesFromExampleData();

    BitcointoyouBalanceTest.setUp();
    bitcointoyouBalance = BitcointoyouBalanceTest.bitcointoyouBalance;
  }

  @Test
  public void testOrderBookAdapter() throws Exception {

    final SoftAssertions softly = new SoftAssertions();

    List<LimitOrder> bidsLimitOrders = orderBook.getBids();
    softly.assertThat(bidsLimitOrders.size()).isEqualTo(325);
    softly.assertThat(bidsLimitOrders.get(300).getOriginalAmount()).isEqualTo(new BigDecimal("0.000104710000000"));

    List<LimitOrder> asksLimitOrders = orderBook.getAsks();
    softly.assertThat(asksLimitOrders.size()).isEqualTo(515);
    softly.assertThat(asksLimitOrders.get(300).getLimitPrice()).isEqualTo(new BigDecimal("55900.230000000000000"));

    softly.assertAll();
  }

  @Test
  public void testPublicOrdersAdapter() throws Exception {

    final SoftAssertions softly = new SoftAssertions();

    List<LimitOrder> bidsOrders = BitcointoyouAdapters.adaptBitcointoyouPublicOrders(bitcointoyouOrderBook.getBids(), Order.OrderType.BID, null);
    softly.assertThat(bidsOrders.size()).isEqualTo(325);
    softly.assertThat(bidsOrders.get(300).getOriginalAmount()).isEqualTo(new BigDecimal("0.000104710000000"));

    List<LimitOrder> asksOrders = BitcointoyouAdapters.adaptBitcointoyouPublicOrders(bitcointoyouOrderBook.getAsks(), Order.OrderType.ASK, null);
    softly.assertThat(asksOrders.size()).isEqualTo(515);
    softly.assertThat(asksOrders.get(300).getLimitPrice()).isEqualTo(new BigDecimal("55900.230000000000000"));

    softly.assertAll();
  }

  @Test
  public void testTickerAdapter() throws IOException {

    final SoftAssertions softly = new SoftAssertions();

    softly.assertThat(ticker.getLast().toString()).isEqualTo("49349.16");
    softly.assertThat(ticker.getBid().toString()).isEqualTo("48968.290000000000000");
    softly.assertThat(ticker.getAsk().toString()).isEqualTo("49349.150000000000000");
    softly.assertThat(ticker.getHigh().toString()).isEqualTo("52990.00");
    softly.assertThat(ticker.getLow().toString()).isEqualTo("47000.00");
    softly.assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("136.99427076"));
    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    f.setTimeZone(TimeZone.getTimeZone("UTC"));
    String dateString = f.format(ticker.getTimestamp());
    softly.assertThat(dateString).isEqualTo("2018-01-10 23:11:41");

    softly.assertAll();
  }

  @Test
  public void testPublicTradesAdapter() throws Exception {

    final SoftAssertions softly = new SoftAssertions();

    softly.assertThat(bitcointoyouPublicTrades).isNotNull();
    softly.assertThat(bitcointoyouPublicTrades).isNotEmpty();
    softly.assertThat(bitcointoyouPublicTrades.length).isEqualTo(1000);

    softly.assertAll();
  }

  @Test
  public void testBalancesAdapter() throws Exception {

    final SoftAssertions softly = new SoftAssertions();

    List<Balance> balances = BitcointoyouAdapters.adaptBitcointoyouBalances(bitcointoyouBalance);

    softly.assertThat(balances).isNotNull();
    softly.assertThat(balances.size()).isEqualTo(5);
    softly.assertThat(balances).contains(
        new Balance(Currency.BRL, new BigDecimal("8657.531311027634275")),
        new Balance(Currency.BTC, new BigDecimal("35.460074025529646")),
        new Balance(Currency.LTC, new BigDecimal("9.840918628667236")),
        new Balance(Currency.DOGE, new BigDecimal("5419.490003406479187")),
        new Balance(Currency.DRK, new BigDecimal("0.121461143982142"))
    );

    softly.assertAll();
  }

  private static BitcointoyouOrderBook loadBitcointoyouOrderBookFromExampleData() throws IOException {

    InputStream is = BitcointoyouAdaptersTest.class.getResourceAsStream("/trade/example-orderbook-data.json");

    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(is, BitcointoyouOrderBook.class);
  }

  private static BitcointoyouTicker loadBitcointoyouTickerFromExampleData() throws IOException {

    InputStream is = BitcointoyouAdaptersTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    ObjectMapper mapper = new ObjectMapper();
    BitcointoyouMarketData marketData = mapper.readValue(is, BitcointoyouMarketData.class);
    return new BitcointoyouTicker(marketData, CurrencyPair.BTC_BRL);
  }

  private static BitcointoyouPublicTrade[] loadBitcointoyouPublicTradesFromExampleData() throws IOException {

    InputStream is = BitcointoyouAdaptersTest.class.getResourceAsStream("/marketdata/example-public-trades-data.json");

    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(is, BitcointoyouPublicTrade[].class);

  }

}