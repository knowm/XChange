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
package com.xeiam.xchange.mtgox.v0.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.currency.MoneyUtils;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.mtgox.v0.MtGoxAdapters;
import com.xeiam.xchange.mtgox.v0.dto.marketdata.MtGoxDepth;
import com.xeiam.xchange.mtgox.v0.dto.marketdata.MtGoxTicker;
import com.xeiam.xchange.mtgox.v0.dto.marketdata.MtGoxTrades;
import com.xeiam.xchange.mtgox.v0.service.marketdata.FullDepthJSONTest;
import com.xeiam.xchange.utils.DateUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Tests the VirtExAdapter class
 */
public class MtGoxAdapterTest {

  @Test
  public void testOrderAdapterWithDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = FullDepthJSONTest.class.getResourceAsStream("/marketdata/example-depth-data-v0.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    MtGoxDepth mtGoxDepth = mapper.readValue(is, MtGoxDepth.class);

    List<LimitOrder> asks = MtGoxAdapters.adaptOrders(mtGoxDepth.getAsks(), "USD", "ask", "");
    // System.out.println(openorders.size());
    // assertTrue("ASKS size should be 1582", asks.size() == 1582);

    // verify all fields filled
    assertTrue("limit price should be 16.634", asks.get(0).getLimitPrice().getAmount().doubleValue() == 16.634);
    assertTrue("order type should be ASK", asks.get(0).getType() == OrderType.ASK);
    assertTrue("tradableAmount should be 21.989000000000001", asks.get(0).getTradableAmount().doubleValue() == 21.989000000000001);
    assertTrue("tradableIdentifier should be BTC", asks.get(0).getTradableIdentifier().equals("BTC"));
    assertTrue("transactionCurrency should be USD", asks.get(0).getTransactionCurrency().equals("USD"));

  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = MtGoxAdapterTest.class.getResourceAsStream("/marketdata/example-ticker-data-v0.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    MtGoxTicker mtGoxTicker = mapper.readValue(is, MtGoxTicker.class);

    Ticker ticker = MtGoxAdapters.adaptTicker(mtGoxTicker, "USD", "BTC");
    // System.out.println(ticker.toString());

    assertThat(ticker.getLast(), is(equalTo(MoneyUtils.parse("USD 16.800000000000001"))));
    assertThat(ticker.getBid(), is(equalTo(MoneyUtils.parse("USD 16.79036"))));
    assertThat(ticker.getAsk(), is(equalTo(MoneyUtils.parse("USD 16.800000000000001"))));
    assertThat(ticker.getVolume(), is(equalTo(new BigDecimal("60418"))));

  }

  @Test
  public void testTradeAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = MtGoxAdapterTest.class.getResourceAsStream("/marketdata/example-trades-data-v0.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    MtGoxTrades[] mtGoxTrades = mapper.readValue(is, MtGoxTrades[].class);

    Trades trades = MtGoxAdapters.adaptTrades(mtGoxTrades);
    System.out.println(trades.getTrades().size());
    assertTrue("Trades size should be 609", trades.getTrades().size() == 609);

    // verify all fields filled
    // System.out.println(trades.getTrades().get(0).toString());
    assertTrue("price should be 16.75", trades.getTrades().get(0).getPrice().getAmount().doubleValue() == 16.75);
    assertTrue("order type should be Bid", trades.getTrades().get(0).getType() == OrderType.BID);
    assertTrue("tradableAmount should be 0.09910328", trades.getTrades().get(0).getTradableAmount().doubleValue() == 0.09910328);
    assertTrue("tradableIdentifier should be BTC", trades.getTrades().get(0).getTradableIdentifier().equals("BTC"));
    assertTrue("transactionCurrency should be USD", trades.getTrades().get(0).getTransactionCurrency().equals("USD"));
    // Unix 1358803625 = Mon, 21 Jan 2013 21:27:05 GMT
    assertThat("2013-01-21 21:27:05 GMT", is(equalTo(DateUtils.toUTCString(trades.getTrades().get(0).getTimestamp()))));

  }

}
