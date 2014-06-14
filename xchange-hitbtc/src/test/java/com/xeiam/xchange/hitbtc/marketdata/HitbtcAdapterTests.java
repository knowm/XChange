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
package com.xeiam.xchange.hitbtc.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.hitbtc.HitbtcAdapters;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcOrderBook;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcTicker;

public class HitbtcAdapterTests {

  @Test
  public void testAdaptTicker() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = HitbtcAdapterTests.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    HitbtcTicker ticker = mapper.readValue(is, HitbtcTicker.class);
    Ticker adaptedTicker = HitbtcAdapters.adaptTicker(ticker, CurrencyPair.BTC_USD);

    assertThat(adaptedTicker.getAsk()).isEqualTo("609.58");
    assertThat(adaptedTicker.getBid()).isEqualTo("608.63");
    assertThat(adaptedTicker.getLow()).isEqualTo("563.4");
    assertThat(adaptedTicker.getHigh()).isEqualTo("621.81");
    assertThat(adaptedTicker.getLast()).isEqualTo("609.24");
    assertThat(adaptedTicker.getVolume()).isEqualTo("1232.17");
    assertThat(adaptedTicker.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
  }

  @Test
  public void testAdaptOrderbook() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = HitbtcAdapterTests.class.getResourceAsStream("/marketdata/example-orderbook-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    HitbtcOrderBook orderBook = mapper.readValue(is, HitbtcOrderBook.class);

    OrderBook adaptedOrderBook = HitbtcAdapters.adaptOrderBook(orderBook, CurrencyPair.BTC_USD);

    List<LimitOrder> asks = adaptedOrderBook.getAsks();
    assertThat(asks.size()).isEqualTo(3);
    LimitOrder order = asks.get(0);
    assertThat(order.getLimitPrice()).isEqualTo("609.58");
    assertThat(order.getTradableAmount()).isEqualTo("1.23");
    assertThat(order.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
  }
}
