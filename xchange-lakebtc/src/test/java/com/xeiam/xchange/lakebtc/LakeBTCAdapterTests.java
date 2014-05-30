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
package com.xeiam.xchange.lakebtc;

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
import com.xeiam.xchange.lakebtc.dto.marketdata.LakeBTCOrderBook;
import com.xeiam.xchange.lakebtc.dto.marketdata.LakeBTCTicker;
import com.xeiam.xchange.lakebtc.dto.marketdata.LakeBTCTickers;
import com.xeiam.xchange.lakebtc.marketdata.LakeBTCMarketDataJsonTests;

public class LakeBTCAdapterTests {

  @Test
  public void testAdaptTicker() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = LakeBTCMarketDataJsonTests.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    LakeBTCTickers tickers = mapper.readValue(is, LakeBTCTickers.class);

    LakeBTCTicker cnyTicker = tickers.getCny();
    Ticker adaptedTicker = LakeBTCAdapters.adaptTicker(cnyTicker, CurrencyPair.BTC_CNY);

    assertThat(adaptedTicker.getAsk()).isEqualTo("3524.07");
    assertThat(adaptedTicker.getBid()).isEqualTo("3517.13");
    assertThat(adaptedTicker.getLow()).isEqualTo("3480.07");
    assertThat(adaptedTicker.getHigh()).isEqualTo("3584.97");
    assertThat(adaptedTicker.getLast()).isEqualTo("3524.07");
    assertThat(adaptedTicker.getVolume()).isEqualTo("5964.7677");
    assertThat(adaptedTicker.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_CNY);
  }

  @Test
  public void testAdaptOrderbook() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = LakeBTCMarketDataJsonTests.class.getResourceAsStream("/marketdata/example-orderbook-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    LakeBTCOrderBook orderBook = mapper.readValue(is, LakeBTCOrderBook.class);

    OrderBook adaptedOrderBook = LakeBTCAdapters.adaptOrderBook(orderBook, CurrencyPair.BTC_USD);

    List<LimitOrder> asks = adaptedOrderBook.getAsks();
    assertThat(asks.size()).isEqualTo(3);
    LimitOrder order = asks.get(0);
    assertThat(order.getLimitPrice()).isEqualTo("564.87");
    assertThat(order.getTradableAmount()).isEqualTo("22.371");
    assertThat(order.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
  }
}
