/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.bitstamp;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitstamp.dto.account.BitstampBalance;
import com.xeiam.xchange.bitstamp.dto.marketdata.BitstampOrderBook;
import com.xeiam.xchange.bitstamp.dto.marketdata.BitstampTicker;
import com.xeiam.xchange.bitstamp.dto.marketdata.BitstampTransaction;
import com.xeiam.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;

/**
 * Tests the BitstampAdapter class
 */
public class BitstampAdapterTest {

  @Test
  public void testAccountInfoAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitstampAdapterTest.class.getResourceAsStream("/account/example-accountinfo-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitstampBalance bitstampBalance = mapper.readValue(is, BitstampBalance.class);

    AccountInfo accountInfo = BitstampAdapters.adaptAccountInfo(bitstampBalance, "Joe Mama");
    assertThat(accountInfo.getUsername()).isEqualTo("Joe Mama");
    assertThat(accountInfo.getTradingFee()).isEqualTo(new BigDecimal("0.5000"));
    assertThat(accountInfo.getWallets().get(0).getCurrency()).isEqualTo("USD");
    assertThat(accountInfo.getWallets().get(0).getBalance().toString()).isEqualTo("172.87");
    assertThat(accountInfo.getWallets().get(1).getCurrency()).isEqualTo("BTC");
    assertThat(accountInfo.getWallets().get(1).getBalance().toString()).isEqualTo("6.99990000");
  }

  @Test
  public void testOrderBookAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitstampAdapterTest.class.getResourceAsStream("/marketdata/example-full-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitstampOrderBook bitstampOrderBook = mapper.readValue(is, BitstampOrderBook.class);

    OrderBook orderBook = BitstampAdapters.adaptOrders(bitstampOrderBook, CurrencyPair.BTC_USD, 1000);
    assertThat(orderBook.getBids().size()).isEqualTo(1281);

    // verify all fields filled
    assertThat(orderBook.getBids().get(0).getLimitPrice().toString()).isEqualTo("123.09");
    assertThat(orderBook.getBids().get(0).getType()).isEqualTo(OrderType.BID);
    assertThat(orderBook.getBids().get(0).getTradableAmount()).isEqualTo(new BigDecimal("0.16248274"));
    assertThat(orderBook.getBids().get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    f.setTimeZone(TimeZone.getTimeZone("UTC"));
    String dateString = f.format(orderBook.getTimeStamp());
    assertThat(dateString).isEqualTo("2013-09-10 12:31:44");
  }

  @Test
  public void testTradeAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitstampAdapterTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitstampTransaction[] transactions = mapper.readValue(is, BitstampTransaction[].class);

    Trade trade = BitstampAdapters.adaptTrade(transactions[3], CurrencyPair.BTC_USD, 1000);

    // verify all fields filled
    assertThat(trade.getPrice().toString()).isEqualTo("13.14");
    assertThat(trade.getType()).isNull();
    assertThat(trade.getTradableAmount()).isEqualTo(new BigDecimal("23.66362253"));
    assertThat(trade.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
  }

  @Test
  public void testTradesAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitstampAdapterTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitstampTransaction[] transactions = mapper.readValue(is, BitstampTransaction[].class);

    Trades trades = BitstampAdapters.adaptTrades(transactions, CurrencyPair.BTC_USD);
    assertThat(trades.getTrades().size()).isEqualTo(125);

    // verify all fields filled
    assertThat(trades.getTrades().get(0).getPrice().toString()).isEqualTo("13.14");
    assertThat(trades.getTrades().get(0).getType()).isNull();
    assertThat(trades.getTrades().get(0).getTradableAmount()).isEqualTo(new BigDecimal("10.11643836"));
    assertThat(trades.getTrades().get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitstampAdapterTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

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
    InputStream is = BitstampAdapterTest.class.getResourceAsStream("/trade/example-user-transactions.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitstampUserTransaction[] bitstampUserTransactions = mapper.readValue(is, BitstampUserTransaction[].class);

    Trades userTradeHistory = BitstampAdapters.adaptTradeHistory(bitstampUserTransactions);

    assertThat(userTradeHistory.getTrades().get(0).getId()).isEqualTo("1296712");
    assertThat(userTradeHistory.getTrades().get(0).getType()).isEqualTo(OrderType.BID);
    assertThat(userTradeHistory.getTrades().get(0).getPrice().toString()).isEqualTo("131.50");

    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String dateString = f.format(userTradeHistory.getTrades().get(0).getTimestamp());
    assertThat(dateString).isEqualTo("2013-09-02 13:17:49");
  }
}
