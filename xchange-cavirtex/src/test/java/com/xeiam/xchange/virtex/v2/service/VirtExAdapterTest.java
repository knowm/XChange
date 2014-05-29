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
package com.xeiam.xchange.virtex.v2.service;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.utils.DateUtils;
import com.xeiam.xchange.virtex.v2.VirtExAdapters;
import com.xeiam.xchange.virtex.v2.dto.marketdata.VirtExDepthWrapper;
import com.xeiam.xchange.virtex.v2.dto.marketdata.VirtExTickerWrapper;
import com.xeiam.xchange.virtex.v2.dto.marketdata.VirtExTradesWrapper;
import com.xeiam.xchange.virtex.v2.service.marketdata.VirtExDepthJSONTest;
import com.xeiam.xchange.virtex.v2.service.marketdata.VirtExTickerJSONTest;
import com.xeiam.xchange.virtex.v2.service.marketdata.VirtExTradesJSONTest;

/**
 * Tests the VirtExAdapter class
 */
public class VirtExAdapterTest {

  @Test
  public void testOrderAdapterWithDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = VirtExDepthJSONTest.class.getResourceAsStream("/marketdata/example-depth-data-v2.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    VirtExDepthWrapper VirtExDepth = mapper.readValue(is, VirtExDepthWrapper.class);
    
    List<LimitOrder> asks = VirtExAdapters.adaptOrders(VirtExDepth.getDepth().getAsks(), CurrencyPair.BTC_CAD, "ask", "");

    // Verify all fields filled
    assertThat(asks.get(0).getLimitPrice().doubleValue()).isEqualTo(500000.000000000);
    assertThat(asks.get(0).getType()).isEqualTo(OrderType.ASK);
    assertThat(asks.get(0).getTradableAmount().doubleValue()).isEqualTo(7.000000000);
    assertThat(asks.get(0).getCurrencyPair().baseSymbol).isEqualTo("BTC");
    assertThat(asks.get(0).getCurrencyPair().counterSymbol).isEqualTo("CAD");

  }

  @Test
  public void testTradeAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = VirtExTradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data-v2.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    VirtExTradesWrapper VirtExTrades = mapper.readValue(is, VirtExTradesWrapper.class);

    Trades trades = VirtExAdapters.adaptTrades(VirtExTrades.getTrades(), CurrencyPair.BTC_CAD);
    assertThat(trades.getTrades().size()).isEqualTo(632);

    // Verify all fields filled
    assertThat(trades.getTrades().get(0).getPrice().doubleValue() == 545.060000000);
    assertThat(trades.getTrades().get(0).getTradableAmount().doubleValue() == 1.189100000);
    assertThat(trades.getTrades().get(0).getCurrencyPair().baseSymbol == "BTC");
    assertThat(DateUtils.toUTCString(trades.getTrades().get(0).getTimestamp())).isEqualTo("2014-05-21 20:45:15 GMT");
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = VirtExTickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data-v2.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    VirtExTickerWrapper VirtExTicker = mapper.readValue(is, VirtExTickerWrapper.class);

    Ticker ticker = VirtExAdapters.adaptTicker(VirtExTicker.getTicker(), CurrencyPair.BTC_CAD);
    System.out.println(ticker.toString());

    assertThat(ticker.getLast().toString()).isEqualTo("545.060000000");
    assertThat(ticker.getLow().toString()).isEqualTo("538.000000000");
    assertThat(ticker.getHigh().toString()).isEqualTo("574.000000000");
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("284.231600000"));
    
    assertThat(ticker.getCurrencyPair().baseSymbol).isEqualTo("BTC");

  }
}
