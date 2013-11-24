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

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

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

/**
 * @deprecated Use V2! This will be removed in 1.8.0+
 */
@Deprecated
@Ignore
public class MtGoxAdapterTest {

  @Test
  public void testOrderAdapterWithDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = FullDepthJSONTest.class.getResourceAsStream("/v0/marketdata/example-depth-data-v0.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    MtGoxDepth mtGoxDepth = mapper.readValue(is, MtGoxDepth.class);

    List<LimitOrder> asks = MtGoxAdapters.adaptOrders(mtGoxDepth.getAsks(), "USD", "ask", "");

    // Verify all fields filled
    assertThat(asks.get(0).getLimitPrice().getAmount().doubleValue()).isEqualTo(16.634);
    assertThat(asks.get(0).getType()).isEqualTo(OrderType.ASK);
    assertThat(asks.get(0).getTradableAmount().doubleValue()).isEqualTo(21.989000000000001);
    assertThat(asks.get(0).getTradableIdentifier()).isEqualTo("BTC");
    assertThat(asks.get(0).getTransactionCurrency()).isEqualTo(("USD"));

  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = MtGoxAdapterTest.class.getResourceAsStream("/v0/marketdata/example-ticker-data-v0.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    MtGoxTicker mtGoxTicker = mapper.readValue(is, MtGoxTicker.class);

    Ticker ticker = MtGoxAdapters.adaptTicker(mtGoxTicker, "BTC", "USD");

    assertThat(ticker.getLast()).isEqualTo(MoneyUtils.parse("USD 16.800000000000001"));
    assertThat(ticker.getBid()).isEqualTo(MoneyUtils.parse("USD 16.79036"));
    assertThat(ticker.getAsk()).isEqualTo(MoneyUtils.parse("USD 16.800000000000001"));
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("60418"));

  }

  @Test
  public void testTradeAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = MtGoxAdapterTest.class.getResourceAsStream("/v0/marketdata/example-trades-data-v0.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    MtGoxTrades[] mtGoxTrades = mapper.readValue(is, MtGoxTrades[].class);

    Trades trades = MtGoxAdapters.adaptTrades(mtGoxTrades);
    System.out.println(trades.getTrades().size());
    assertThat(trades.getTrades().size() == 609);

    // verify all fields filled
    assertThat(trades.getTrades().get(0).getPrice().getAmount().doubleValue()).isEqualTo(16.75);
    assertThat(trades.getTrades().get(0).getType()).isEqualTo(OrderType.BID);
    assertThat(trades.getTrades().get(0).getTradableAmount().doubleValue()).isEqualTo(0.09910328);
    assertThat(trades.getTrades().get(0).getTradableIdentifier().equals("BTC"));
    assertThat(trades.getTrades().get(0).getTransactionCurrency().equals("USD"));
    // Unix 1358803625 = Mon, 21 Jan 2013 21:27:05 GMT
    assertThat(DateUtils.toUTCString(trades.getTrades().get(0).getTimestamp())).isEqualTo("2013-01-21 21:27:05 GMT");

  }

}
