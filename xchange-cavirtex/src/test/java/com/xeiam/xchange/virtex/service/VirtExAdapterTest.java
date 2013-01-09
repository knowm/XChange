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
package com.xeiam.xchange.virtex.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.virtex.VirtExAdapters;
import com.xeiam.xchange.virtex.dto.marketdata.VirtExDepth;
import com.xeiam.xchange.virtex.dto.marketdata.VirtExTicker;
import com.xeiam.xchange.virtex.dto.marketdata.VirtExTrade;
import com.xeiam.xchange.virtex.service.marketdata.VirtExDepthJSONTest;
import com.xeiam.xchange.virtex.service.marketdata.VirtExTickerJSONTest;
import com.xeiam.xchange.virtex.service.marketdata.VirtExTradesJSONTest;

/**
 * Tests the VirtExAdapter class
 */
public class VirtExAdapterTest {

  @Test
  public void testOrderAdapterWithDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = VirtExDepthJSONTest.class.getResourceAsStream("/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    VirtExDepth VirtExDepth = mapper.readValue(is, VirtExDepth.class);

    List<LimitOrder> asks = VirtExAdapters.adaptOrders(VirtExDepth.getAsks(), "CAD", "ask", "");
    // System.out.println(openorders.size());
    // assertTrue("ASKS size should be 1582", asks.size() == 1582);

    // verify all fields filled
    // assertTrue("limit price should be 16.90536", asks.get(0).getLimitPrice().getAmount().floatValue() == 16.90536);
    assertTrue("order type should be ASK", asks.get(0).getType() == OrderType.ASK);
    // assertTrue("tradableAmount should be 6.51", asks.get(0).getTradableAmount().doubleValue() == 6.51);
    assertTrue("tradableIdentifier should be BTC", asks.get(0).getTradableIdentifier().equals("BTC"));
    assertTrue("transactionCurrency should be CAD", asks.get(0).getTransactionCurrency().equals("CAD"));

  }

  @Test
  public void testTradeAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = VirtExTradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    VirtExTrade[] VirtExTrades = mapper.readValue(is, VirtExTrade[].class);

    Trades trades = VirtExAdapters.adaptTrades(VirtExTrades, "CAD", "BTC");
    // System.out.println(trades.getTrades().size());
    assertTrue("Trades size should be 558", trades.getTrades().size() == 558);

    // verify all fields filled
    // System.out.println(trades.getTrades().get(0).toString());
    assertTrue("price should be 11.500000000", trades.getTrades().get(0).getPrice().getAmount().doubleValue() == 11.500000000);
    assertTrue("order type should be ASK", trades.getTrades().get(0).getType() == OrderType.ASK);
    assertTrue("tradableAmount should be 13.000000000", trades.getTrades().get(0).getTradableAmount().doubleValue() == 13.000000000);
    // assertTrue("tradableIdentifier should be BTC", trades.getTrades().get(0).getTradableIdentifier().equals("BTC"));
    // assertTrue("transactionCurrency should be PLN", trades.getTrades().get(0).getTransactionCurrency().equals("PLN"));
    assertEquals("timestamp incorrect", "2012-09-26T15:23:19.000Z", trades.getTrades().get(0).getTimestamp().toString());
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = VirtExTickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    VirtExTicker VirtExTicker = mapper.readValue(is, VirtExTicker.class);

    Ticker ticker = VirtExAdapters.adaptTicker(VirtExTicker, "CAD", "BTC");
    System.out.println(ticker.toString());

    assertEquals("last should be CAD 12.32900", ticker.getLast().toString(), "CAD 12.32900");
    assertEquals("bid should be CAD 11.64001", ticker.getLow().toString(), "CAD 11.64001");
    assertEquals("ask should be CAD 12.37989", ticker.getHigh().toString(), "CAD 12.37989");
    assertEquals("volume should be 1866.56", ticker.getVolume(), new BigDecimal("1866.56"));

  }
}
