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
package com.xeiam.xchange.campbx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.campbx.dto.marketdata.CampBXOrderBook;
import com.xeiam.xchange.campbx.dto.marketdata.CampBXTicker;
import com.xeiam.xchange.currency.MoneyUtils;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the BitstampAdapter class
 */
public class CampBXAdapterTest {

  @Test
  public void testOrderAdapterWithDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CampBXAdapterTest.class.getResourceAsStream("/marketdata/example-full-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CampBXOrderBook bitstampOrderBook = mapper.readValue(is, CampBXOrderBook.class);

    OrderBook orderBook = CampBXAdapters.adaptOrders(bitstampOrderBook, "USD", "BTC");
    assertThat(orderBook.getBids().size(), is(equalTo(36)));

    // verify all fields filled
    assertThat(orderBook.getBids().get(0).getLimitPrice().getAmount(), is(equalTo(new BigDecimal("13.3"))));
    assertThat(orderBook.getBids().get(0).getType(), is(equalTo(OrderType.BID)));
    assertThat(orderBook.getBids().get(0).getTradableAmount(), is(equalTo(new BigDecimal("0.00021609"))));
    assertThat(orderBook.getBids().get(0).getTradableIdentifier(), is(equalTo("BTC")));
    assertThat(orderBook.getBids().get(0).getTransactionCurrency(), is(equalTo("USD")));

  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CampBXAdapterTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CampBXTicker campBXTicker = mapper.readValue(is, CampBXTicker.class);

    Ticker ticker = CampBXAdapters.adaptTicker(campBXTicker, "USD", "BTC");

    assertThat(ticker.getLast(), is(equalTo(MoneyUtils.parse("USD 13.30"))));
    assertThat(ticker.getBid(), is(equalTo(MoneyUtils.parse("USD 13.30"))));
    assertThat(ticker.getAsk(), is(equalTo(MoneyUtils.parse("USD 13.52"))));

  }
}
