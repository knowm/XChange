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
package com.xeiam.xchange.virtex.v1.service;

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
import com.xeiam.xchange.virtex.v1.VirtExAdapters;
import com.xeiam.xchange.virtex.v1.dto.marketdata.VirtExDepth;
import com.xeiam.xchange.virtex.v1.dto.marketdata.VirtExTicker;
import com.xeiam.xchange.virtex.v1.dto.marketdata.VirtExTrade;
import com.xeiam.xchange.virtex.v1.service.marketdata.VirtExDepthJSONTest;
import com.xeiam.xchange.virtex.v1.service.marketdata.VirtExTickerJSONTest;
import com.xeiam.xchange.virtex.v1.service.marketdata.VirtExTradesJSONTest;

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

    // Verify all fields filled
    assertThat(asks.get(0).getLimitPrice().doubleValue()).isEqualTo(16.90536);
    assertThat(asks.get(0).getType()).isEqualTo(OrderType.ASK);
    assertThat(asks.get(0).getTradableAmount().doubleValue()).isEqualTo(6.51);
    assertThat(asks.get(0).getCurrencyPair().baseSymbol).isEqualTo("BTC");
    assertThat(asks.get(0).getCurrencyPair().counterSymbol).isEqualTo("CAD");

  }

  @Test
  public void testTradeAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = VirtExTradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    VirtExTrade[] VirtExTrades = mapper.readValue(is, VirtExTrade[].class);

    Trades trades = VirtExAdapters.adaptTrades(VirtExTrades, CurrencyPair.BTC_CAD);
    assertThat(trades.getTrades().size()).isEqualTo(558);

    // Verify all fields filled
    assertThat(trades.getTrades().get(0).getPrice().doubleValue() == 11.500000000);
    assertThat(trades.getTrades().get(0).getTradableAmount().doubleValue() == 13.000000000);
    assertThat(trades.getTrades().get(0).getCurrencyPair().baseSymbol == "BTC");
    assertThat(DateUtils.toUTCString(trades.getTrades().get(0).getTimestamp())).isEqualTo("2012-09-26 15:23:19 GMT");
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = VirtExTickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    VirtExTicker VirtExTicker = mapper.readValue(is, VirtExTicker.class);

    Ticker ticker = VirtExAdapters.adaptTicker(VirtExTicker, CurrencyPair.BTC_CAD);
    System.out.println(ticker.toString());

    assertThat(ticker.getLast().toString()).isEqualTo("12.32900");
    assertThat(ticker.getLow().toString()).isEqualTo("11.64001");
    assertThat(ticker.getHigh().toString()).isEqualTo("12.37989");
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("1866.56"));
    assertThat(ticker.getCurrencyPair().baseSymbol).isEqualTo("BTC");

  }
}
