/*
 * Copyright (C) 2013 Matija Mazi
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.bitfloor;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitfloor.dto.account.BitfloorBalance;
import com.xeiam.xchange.bitfloor.dto.marketdata.BitfloorDayInfo;
import com.xeiam.xchange.bitfloor.dto.marketdata.BitfloorOrderBook;
import com.xeiam.xchange.bitfloor.dto.marketdata.BitfloorTicker;
import com.xeiam.xchange.bitfloor.dto.marketdata.BitfloorTransaction;
import com.xeiam.xchange.currency.MoneyUtils;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the BitfloorAdapter class
 */
public class BitfloorAdapterTest {

  @Test
  public void testAccountInfoAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitfloorAdapterTest.class.getResourceAsStream("/account/example-accountinfo-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitfloorBalance[] bitfloorBalance = mapper.readValue(is, BitfloorBalance[].class);

    AccountInfo accountInfo = BitfloorAdapters.adaptAccountInfo(bitfloorBalance, "Joe Mama");
    assertThat(accountInfo.getUsername(), is(equalTo("Joe Mama")));
    assertThat(accountInfo.getWallets().get(0).getCurrency(), is(equalTo("BTC")));
    assertThat(accountInfo.getWallets().get(0).getBalance(), is(equalTo(MoneyUtils.parse("BTC 0.350000000000000000000000"))));
    assertThat(accountInfo.getWallets().get(1).getCurrency(), is(equalTo("USD")));
    assertThat(accountInfo.getWallets().get(1).getBalance(), is(equalTo(MoneyUtils.parse("USD 0.810345600000000000000000"))));
  }

  @Test
  public void testOrderBookAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitfloorAdapterTest.class.getResourceAsStream("/marketdata/example-full-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitfloorOrderBook bitfloorOrderBook = mapper.readValue(is, BitfloorOrderBook.class);

    OrderBook orderBook = BitfloorAdapters.adaptOrders(bitfloorOrderBook, "USD", "BTC");
    assertThat(orderBook.getBids().size(), is(equalTo(50)));

    // verify all fields filled
    assertThat(orderBook.getBids().get(0).getLimitPrice().getAmount(), is(equalTo(new BigDecimal("27.35000000"))));
    assertThat(orderBook.getBids().get(0).getType(), is(equalTo(OrderType.BID)));
    assertThat(orderBook.getBids().get(0).getTradableAmount(), is(equalTo(new BigDecimal("837.27084000"))));
    assertThat(orderBook.getBids().get(0).getTradableIdentifier(), is(equalTo("BTC")));
    assertThat(orderBook.getBids().get(0).getTransactionCurrency(), is(equalTo("USD")));

  }

  @Test
  public void testTradeAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitfloorAdapterTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitfloorTransaction[] transactions = mapper.readValue(is, BitfloorTransaction[].class);

    Trades trades = BitfloorAdapters.adaptTrades(transactions, "USD", "BTC");
    assertThat(trades.getTrades().size(), is(equalTo(100)));

    // verify all fields filled
    Trade secondLastTrade = trades.getTrades().get(1);
    assertThat(secondLastTrade.getType(), is(equalTo(null)));
    assertThat(secondLastTrade.getTradableIdentifier(), is(equalTo("BTC")));
    assertThat(secondLastTrade.getTransactionCurrency(), is(equalTo("USD")));
    assertThat(secondLastTrade.getPrice(), is(equalTo(MoneyUtils.parse("USD 27.98000000"))));
    assertThat(secondLastTrade.getTradableAmount(), is(equalTo(new BigDecimal("0.83995000"))));
    assertThat(secondLastTrade.getTimestamp(), is(equalTo(new Date(1361059502895L))));
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream tickerIn = BitfloorAdapterTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");
    InputStream dayInfoIn = BitfloorAdapterTest.class.getResourceAsStream("/marketdata/example-day-info-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitfloorTicker bitfloorTicker = mapper.readValue(tickerIn, BitfloorTicker.class);
    BitfloorDayInfo dayInfo = mapper.readValue(dayInfoIn, BitfloorDayInfo.class);

    Ticker ticker = BitfloorAdapters.adaptTicker(bitfloorTicker, dayInfo, "USD", "BTC");

    assertThat(ticker.getLast(), is(equalTo(MoneyUtils.parse("USD 27.40000000"))));
    assertThat(ticker.getVolume(), is(equalTo(new BigDecimal("524.7550799999987"))));
    assertThat(ticker.getLow(), is(equalTo(MoneyUtils.parse("USD 26.67"))));
    assertThat(ticker.getHigh(), is(equalTo(MoneyUtils.parse("USD 27.99"))));

  }
}
