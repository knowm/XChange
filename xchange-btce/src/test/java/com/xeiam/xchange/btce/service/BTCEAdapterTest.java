/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.btce.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.xeiam.xchange.btce.BTCEAdapters;
import com.xeiam.xchange.btce.dto.marketdata.BTCEDepth;
import com.xeiam.xchange.btce.dto.marketdata.BTCETicker;
import com.xeiam.xchange.btce.dto.marketdata.BTCETrade;
import com.xeiam.xchange.btce.service.marketdata.BTCEDepthJSONTest;
import com.xeiam.xchange.btce.service.marketdata.BTCETickerJSONTest;
import com.xeiam.xchange.btce.service.marketdata.BTCETradesJSONTest;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;

/**
 * Tests the BTCEAdapter class
 */
public class BTCEAdapterTest {

  @Test
  public void testOrderAdapterWithDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTCEDepthJSONTest.class.getResourceAsStream("/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCEDepth BTCEDepth = mapper.readValue(is, BTCEDepth.class);

    List<LimitOrder> asks = BTCEAdapters.adaptOrders(BTCEDepth.getAsks(), "BTC", "USD", "ask", "");
    // System.out.println(openorders.size());
    // assertTrue("ASKS size should be 1582", asks.size() == 1582);

    // verify all fields filled
    // assertTrue("limit price should be 16.90536", asks.get(0).getLimitPrice().getAmount().floatValue() == 16.90536);
    assertTrue("order type should be ASK", asks.get(0).getType() == OrderType.ASK);
    // assertTrue("tradableAmount should be 6.51", asks.get(0).getTradableAmount().doubleValue() == 6.51);
    assertTrue("tradableIdentifier should be BTC", asks.get(0).getTradableIdentifier().equals("BTC"));
    assertTrue("transactionCurrency should be USD", asks.get(0).getTransactionCurrency().equals("USD"));

  }

  @Test
  public void testTradeAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTCETradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCETrade[] BTCETrades = mapper.readValue(is, BTCETrade[].class);

    Trades trades = BTCEAdapters.adaptTrades(BTCETrades);
    // System.out.println(trades.getTrades().size());
    assertTrue("Trades size should be 150", trades.getTrades().size() == 150);

    // verify all fields filled
    assertTrue("price should be 13.07", trades.getTrades().get(0).getPrice().getAmount().doubleValue() == 13.07);
    assertTrue("order type should be ASK", trades.getTrades().get(0).getType() == OrderType.ASK);
    assertTrue("tradableAmount should be 1.0", trades.getTrades().get(0).getTradableAmount().doubleValue() == 1.0);
    assertTrue("tradableIdentifier should be BTC", trades.getTrades().get(0).getTradableIdentifier().equals("BTC"));
    // assertTrue("transactionCurrency should be PLN", trades.getTrades().get(0).getTransactionCurrency().equals("PLN"));
    assertEquals("timestamp incorrect", "2012-12-22T08:06:14.000Z", trades.getTrades().get(0).getTimestamp().toString());
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTCETickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCETicker BTCETicker = mapper.readValue(is, BTCETicker.class);

    Ticker ticker = BTCEAdapters.adaptTicker(BTCETicker, "BTC", "USD");

    assertEquals("last should be USD 13.07", ticker.getLast().toString(), "USD 13.07");
    assertEquals("low should be USD 13", ticker.getLow().toString(), "USD 13.0");
    assertEquals("high should be USD 13.23", ticker.getHigh().toString(), "USD 13.23");
    assertEquals("volume should be 40418.44988", ticker.getVolume(), new BigDecimal(40418.44988));
    assertEquals("Tradable Identifier should be BTC", ticker.getTradableIdentifier(), "BTC");

  }
}
