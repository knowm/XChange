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
package com.xeiam.xchange.bitcoin24.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitcoin24.Bitcoin24Adapters;
import com.xeiam.xchange.bitcoin24.dto.marketdata.Bitcoin24Depth;
import com.xeiam.xchange.bitcoin24.dto.marketdata.Bitcoin24Ticker;
import com.xeiam.xchange.bitcoin24.dto.marketdata.Bitcoin24Trade;
import com.xeiam.xchange.bitcoin24.service.marketdata.Bitcoin24DepthJSONTest;
import com.xeiam.xchange.bitcoin24.service.marketdata.Bitcoin24TickerJSONTest;
import com.xeiam.xchange.bitcoin24.service.marketdata.Bitcoin24TradesJSONTest;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Tests the Bitcoin24Adapter class
 */
public class Bitcoin24AdapterTest {

  @Test
  public void testOrderAdapterWithDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = Bitcoin24DepthJSONTest.class.getResourceAsStream("/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    Bitcoin24Depth Bitcoin24Depth = mapper.readValue(is, Bitcoin24Depth.class);

    List<LimitOrder> asks = Bitcoin24Adapters.adaptOrders(Bitcoin24Depth.getAsks(), "BTC", "USD", "ask", "");

    // verify all fields filled
    assertTrue("order type should be ASK", asks.get(0).getType() == OrderType.ASK);
    assertTrue("tradableIdentifier should be BTC", asks.get(0).getTradableIdentifier().equals("BTC"));
    assertTrue("transactionCurrency should be USD", asks.get(0).getTransactionCurrency().equals("USD"));

  }

  @Test
  public void testTradeAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = Bitcoin24TradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    Bitcoin24Trade[] bitcoin24Trades = mapper.readValue(is, Bitcoin24Trade[].class);

    Trades trades = Bitcoin24Adapters.adaptTrades(bitcoin24Trades, Currencies.BTC, Currencies.USD);
    //System.out.println(trades.getTrades().size());
    assertTrue("Trades size should be 2007", trades.getTrades().size() == 2007);

    // verify all fields filled
    System.out.println("Price: " + trades.getTrades().get(0).getPrice().getAmount().doubleValue());
    assertTrue("price should be 4.88000", trades.getTrades().get(0).getPrice().getAmount().doubleValue() == 4.88000);
    assertTrue("order type should be ASK", trades.getTrades().get(0).getType() == OrderType.ASK);
    assertTrue("tradableAmount should be 3.00000000", trades.getTrades().get(0).getTradableAmount().doubleValue() == 3.00000000);
    assertTrue("tradableIdentifier should be BTC", trades.getTrades().get(0).getTradableIdentifier().equals("BTC"));
    // assertTrue("transactionCurrency should be PLN", trades.getTrades().get(0).getTransactionCurrency().equals("PLN"));
    assertEquals("timestamp incorrect", "2012-05-14 14:13:17 GMT", DateUtils.toUTCString(trades.getTrades().get(0).getTimestamp()));
    // Mon, 14 May 2012 14:13:17 GMT
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = Bitcoin24TickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    Bitcoin24Ticker Bitcoin24Ticker = mapper.readValue(is, Bitcoin24Ticker.class);

    Ticker ticker = Bitcoin24Adapters.adaptTicker(Bitcoin24Ticker, "BTC", "USD");

    assertEquals("last should be USD 30.50001", ticker.getLast().toString(), "USD 30.50001");
    assertEquals("low should be USD 30.00043", ticker.getLow().toString(), "USD 30.00043");
    assertEquals("high should be USD 34.39799", ticker.getHigh().toString(), "USD 34.39799");
    assertEquals("volume should be 12", ticker.getVolume(), new BigDecimal("12"));
    assertEquals("Tradable Identifier should be BTC", ticker.getTradableIdentifier(), "BTC");

  }
}
