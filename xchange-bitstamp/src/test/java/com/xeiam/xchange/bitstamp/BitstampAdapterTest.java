/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.xeiam.xchange.bitstamp.dto.account.BitstampBalance;
import com.xeiam.xchange.bitstamp.dto.marketdata.BitstampOrderBook;
import com.xeiam.xchange.bitstamp.dto.marketdata.BitstampTicker;
import com.xeiam.xchange.bitstamp.dto.marketdata.BitstampTransaction;
import com.xeiam.xchange.currency.MoneyUtils;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
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
    assertThat(accountInfo.getUsername(), is(equalTo("Joe Mama")));
    assertThat(accountInfo.getWallets().get(0).getCurrency(), is(equalTo("USD")));
    assertThat(accountInfo.getWallets().get(0).getBalance(), is(equalTo(MoneyUtils.parse("USD 172.87"))));
    assertThat(accountInfo.getWallets().get(1).getCurrency(), is(equalTo("BTC")));
    assertThat(accountInfo.getWallets().get(1).getBalance(), is(equalTo(MoneyUtils.parse("BTC 6.99990000"))));
  }

  @Test
  public void testOrderBookAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitstampAdapterTest.class.getResourceAsStream("/marketdata/example-full-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitstampOrderBook bitstampOrderBook = mapper.readValue(is, BitstampOrderBook.class);

    OrderBook orderBook = BitstampAdapters.adaptOrders(bitstampOrderBook, "USD", "BTC");
    assertThat(orderBook.getBids().size(), is(equalTo(107)));

    // verify all fields filled
    assertThat(orderBook.getBids().get(0).getLimitPrice().getAmount(), is(equalTo(new BigDecimal("13.07"))));
    assertThat(orderBook.getBids().get(0).getType(), is(equalTo(OrderType.BID)));
    assertThat(orderBook.getBids().get(0).getTradableAmount(), is(equalTo(new BigDecimal("7.43517000"))));
    assertThat(orderBook.getBids().get(0).getTradableIdentifier(), is(equalTo("BTC")));
    assertThat(orderBook.getBids().get(0).getTransactionCurrency(), is(equalTo("USD")));

  }

  @Test
  public void testTradeAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitstampAdapterTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitstampTransaction[] transactions = mapper.readValue(is, BitstampTransaction[].class);

    Trades trades = BitstampAdapters.adaptTrades(transactions, "USD", "BTC");
    assertThat(trades.getTrades().size(), is(equalTo(125)));

    // verify all fields filled
    assertThat(trades.getTrades().get(0).getPrice(), is(equalTo(MoneyUtils.parse("USD 13.14"))));
    assertThat(trades.getTrades().get(0).getType(), is(equalTo(null)));
    assertThat(trades.getTrades().get(0).getTradableAmount(), is(equalTo(new BigDecimal("10.11643836"))));
    assertThat(trades.getTrades().get(0).getTradableIdentifier(), is(equalTo("BTC")));
    assertThat(trades.getTrades().get(0).getTransactionCurrency(), is(equalTo("USD")));
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitstampAdapterTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitstampTicker bitstampTicker = mapper.readValue(is, BitstampTicker.class);

    Ticker ticker = BitstampAdapters.adaptTicker(bitstampTicker, "USD", "BTC");

    assertThat(ticker.getLast(), is(equalTo(MoneyUtils.parse("USD 13.06"))));
    assertThat(ticker.getBid(), is(equalTo(MoneyUtils.parse("USD 13.06"))));
    assertThat(ticker.getAsk(), is(equalTo(MoneyUtils.parse("USD 13.14"))));
    assertThat(ticker.getVolume(), is(equalTo(new BigDecimal("1127.55649327"))));

  }
}
