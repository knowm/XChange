package org.knowm.xchange.bitstamp;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import org.junit.Test;
import org.knowm.xchange.bitstamp.dto.account.BitstampBalance;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampOrderBook;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampTicker;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampTransaction;
import org.knowm.xchange.bitstamp.dto.trade.BitstampOrderStatusResponse;
import org.knowm.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import org.knowm.xchange.bitstamp.order.dto.BitstampGenericOrder;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.instrument.Instrument;

/** Tests the BitstampAdapter class */
public class BitstampAdapterTest {

  @Test
  public void testAccountInfoAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BitstampAdapterTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitstamp/dto/account/example-accountinfo-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitstampBalance bitstampBalance = mapper.readValue(is, BitstampBalance.class);

    AccountInfo accountInfo = BitstampAdapters.adaptAccountInfo(bitstampBalance, "Joe Mama");
    assertThat(accountInfo.getUsername()).isEqualTo("Joe Mama");
    assertThat(accountInfo.getTradingFee()).isEqualTo(new BigDecimal("0.5000"));
    assertThat(accountInfo.getWallet().getBalance(Currency.USD).getCurrency())
        .isEqualTo(Currency.USD);
    assertThat(accountInfo.getWallet().getBalance(Currency.USD).getTotal()).isEqualTo("172.87");
    assertThat(accountInfo.getWallet().getBalance(Currency.USD).getAvailable()).isEqualTo("0.00");
    assertThat(accountInfo.getWallet().getBalance(Currency.USD).getFrozen()).isEqualTo("172.87");
    assertThat(accountInfo.getWallet().getBalance(Currency.BTC).getCurrency())
        .isEqualTo(Currency.BTC);
    assertThat(accountInfo.getWallet().getBalance(Currency.BTC).getTotal()).isEqualTo("6.99990000");
    assertThat(accountInfo.getWallet().getBalance(Currency.BTC).getAvailable())
        .isEqualTo("6.99990000");
    assertThat(accountInfo.getWallet().getBalance(Currency.BTC).getFrozen()).isEqualTo("0");
    assertThat(accountInfo.getWallet().getBalance(Currency.XRP).getCurrency())
        .isEqualTo(Currency.XRP);
    assertThat(accountInfo.getWallet().getBalance(Currency.XRP).getTotal()).isEqualTo("7771.05654");
  }

  @Test
  public void testOrderBookAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BitstampAdapterTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitstamp/dto/marketdata/example-full-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitstampOrderBook bitstampOrderBook = mapper.readValue(is, BitstampOrderBook.class);

    OrderBook orderBook = BitstampAdapters.adaptOrderBook(bitstampOrderBook, CurrencyPair.BTC_USD);
    assertThat(orderBook.getBids().size()).isEqualTo(1281);

    // verify all fields filled
    assertThat(orderBook.getBids().get(0).getLimitPrice().toString()).isEqualTo("123.09");
    assertThat(orderBook.getBids().get(0).getType()).isEqualTo(OrderType.BID);
    assertThat(orderBook.getBids().get(0).getOriginalAmount())
        .isEqualTo(new BigDecimal("0.16248274"));
    assertThat(orderBook.getBids().get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    f.setTimeZone(TimeZone.getTimeZone("UTC"));
    String dateString = f.format(orderBook.getTimeStamp());
    assertThat(dateString).isEqualTo("2013-09-10 12:31:44");
  }

  @Test
  public void testTradeAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BitstampAdapterTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitstamp/dto/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitstampTransaction[] transactions = mapper.readValue(is, BitstampTransaction[].class);

    Trade trade = BitstampAdapters.adaptTrade(transactions[3], CurrencyPair.BTC_USD, 1000);

    // verify all fields filled
    assertThat(trade.getPrice().toString()).isEqualTo("13.14");
    assertThat(trade.getType()).isEqualTo(OrderType.BID);
    assertThat(trade.getOriginalAmount()).isEqualTo(new BigDecimal("23.66362253"));
    assertThat(trade.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
  }

  @Test
  public void testTradesAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BitstampAdapterTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitstamp/dto/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitstampTransaction[] transactions = mapper.readValue(is, BitstampTransaction[].class);

    Trades trades = BitstampAdapters.adaptTrades(transactions, CurrencyPair.BTC_USD);
    assertThat(trades.getTrades().size()).isEqualTo(125);
    assertThat(trades.getlastID()).isEqualTo(122260);
    // verify all fields filled
    assertThat(trades.getTrades().get(0).getId()).isEqualTo("121984");
    assertThat(trades.getTrades().get(0).getPrice().toString()).isEqualTo("13.14");
    assertThat(trades.getTrades().get(0).getType()).isEqualTo(OrderType.BID);
    assertThat(trades.getTrades().get(0).getOriginalAmount())
        .isEqualTo(new BigDecimal("10.11643836"));
    assertThat(trades.getTrades().get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BitstampAdapterTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitstamp/dto/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitstampTicker bitstampTicker = mapper.readValue(is, BitstampTicker.class);

    Ticker ticker = BitstampAdapters.adaptTicker(bitstampTicker, CurrencyPair.BTC_USD);

    assertThat(ticker.getLast().toString()).isEqualTo("134.89");
    assertThat(ticker.getBid().toString()).isEqualTo("134.89");
    assertThat(ticker.getAsk().toString()).isEqualTo("134.92");
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("21982.44926674"));
    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    f.setTimeZone(TimeZone.getTimeZone("UTC"));
    String dateString = f.format(ticker.getTimestamp());
    assertThat(dateString).isEqualTo("2013-10-14 21:45:33");
  }

  @Test
  public void testUserTradeHistoryAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BitstampAdapterTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitstamp/dto/trade/example-user-transactions.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitstampUserTransaction[] bitstampUserTransactions =
        mapper.readValue(is, BitstampUserTransaction[].class);

    UserTrades userTradeHistory = BitstampAdapters.adaptTradeHistory(bitstampUserTransactions);

    assertThat(userTradeHistory.getUserTrades().get(0).getId()).isEqualTo("1296712");
    assertThat(userTradeHistory.getUserTrades().get(0).getType()).isEqualTo(OrderType.BID);
    assertThat(userTradeHistory.getUserTrades().get(0).getPrice().toString()).isEqualTo("131.50");
    assertThat(userTradeHistory.getUserTrades().get(0).getFeeAmount().toString()).isEqualTo("0.06");

    assertThat(userTradeHistory.getUserTrades().get(1).getPrice().toString()).isEqualTo("131.50");
    assertThat(userTradeHistory.getUserTrades().get(1).getType()).isEqualTo(OrderType.ASK);
    assertThat(userTradeHistory.getUserTrades().get(1).getFeeAmount().toString()).isEqualTo("0.06");

    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    f.setTimeZone(TimeZone.getTimeZone("UTC"));
    String dateString = f.format(userTradeHistory.getTrades().get(0).getTimestamp());
    assertThat(dateString).isEqualTo("2013-09-02 13:17:49");
  }

  @Test
  public void testOrderAdapter() throws IOException {

    // Read in the JSON from the example resources
    String order =
        "{\"status\":\"Finished\",\"id\":1337944912351237,\"transactions\":[{\"datetime\":\"2020-09-01 05:55:04\",\"tid\":156881358,\"type\":2,\"usd\":null,\"btc\":0.00838324,\"ltc\":null,\"eth\":null,\"eur\":397.20059384,\"xrp\":null,\"bch\":null,\"xlm\":null,\"link\":null,\"price\":47380.32000000,\"fee\":0.43692}],\"error\":null}";

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitstampOrderStatusResponse bitstampOrder =
        mapper.readValue(order, BitstampOrderStatusResponse.class);

    List<Instrument> currencyPairs = new ArrayList<>();
    currencyPairs.add(CurrencyPair.BTC_USD);
    currencyPairs.add(CurrencyPair.LTC_USD);
    currencyPairs.add(CurrencyPair.ETH_USD);
    currencyPairs.add(CurrencyPair.XRP_USD);
    currencyPairs.add(CurrencyPair.BCH_USD);

    currencyPairs.add(CurrencyPair.BTC_EUR);
    currencyPairs.add(CurrencyPair.LTC_EUR);
    currencyPairs.add(CurrencyPair.ETH_EUR);
    currencyPairs.add(CurrencyPair.XRP_EUR);
    currencyPairs.add(CurrencyPair.BCH_EUR);

    currencyPairs.add(CurrencyPair.LTC_BTC);
    currencyPairs.add(CurrencyPair.ETH_BTC);
    currencyPairs.add(CurrencyPair.XRP_BTC);
    currencyPairs.add(CurrencyPair.BCH_BTC);

    currencyPairs.add(CurrencyPair.XLM_BTC);
    currencyPairs.add(CurrencyPair.XLM_USD);
    currencyPairs.add(CurrencyPair.XLM_EUR);
    currencyPairs.add(CurrencyPair.XLM_ETH);

    currencyPairs.add(CurrencyPair.LINK_BTC);
    currencyPairs.add(CurrencyPair.LINK_USD);
    currencyPairs.add(CurrencyPair.LINK_EUR);
    currencyPairs.add(CurrencyPair.LINK_ETH);

    BitstampGenericOrder genericOrder =
        BitstampAdapters.adaptOrder(
            String.valueOf(bitstampOrder.getId()), bitstampOrder, currencyPairs);

    assertThat(genericOrder.getType()).isNull();
    assertThat(genericOrder.getOriginalAmount()).isNull();
    assertThat(genericOrder.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_EUR);
    assertThat(genericOrder.getId()).isEqualTo("1337944912351237");
    assertThat(genericOrder.getTimestamp()).isEqualTo("2020-09-01T05:55:04Z");
    assertThat(genericOrder.getAveragePrice()).isEqualTo(new BigDecimal("47380.32000000"));
    assertThat(genericOrder.getCumulativeAmount()).isEqualTo(new BigDecimal("0.00838324"));
    assertThat(genericOrder.getFee()).isEqualTo(new BigDecimal("0.43692"));
    assertThat(genericOrder.getStatus()).isEqualTo(Order.OrderStatus.FILLED);
  }
}
