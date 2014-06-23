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
package com.xeiam.xchange.cryptonit.v2.service;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.cryptonit.v2.CryptonitAdapters;
import com.xeiam.xchange.cryptonit.v2.dto.marketdata.CryptonitOrders;
import com.xeiam.xchange.cryptonit.v2.dto.marketdata.CryptonitTicker;
import com.xeiam.xchange.cryptonit.v2.service.marketdata.CryptonitDepthJSONTest;
import com.xeiam.xchange.cryptonit.v2.service.marketdata.CryptonitTickerJSONTest;
import com.xeiam.xchange.cryptonit.v2.service.marketdata.CryptonitTradesJSONTest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Tests the cryptonitAdapter class
 */
public class CryptonitAdapterTest {

  @Test
  public void testOrderAdapterWithDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptonitDepthJSONTest.class.getResourceAsStream("/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptonitOrders cryptonitTrades = mapper.readValue(is, CryptonitOrders.class);

    List<LimitOrder> asks = CryptonitAdapters.adaptOrders(cryptonitTrades, CurrencyPair.BTC_USD, "ask", "");

    // Verify all fields filled
    assertThat(asks.get(0).getLimitPrice().doubleValue()).isEqualTo(700.0);
    assertThat(asks.get(0).getType()).isEqualTo(OrderType.ASK);
    assertThat(asks.get(0).getTradableAmount().doubleValue()).isEqualTo(0.00100);
    assertThat(asks.get(0).getCurrencyPair().baseSymbol).isEqualTo("BTC");
    assertThat(asks.get(0).getCurrencyPair().counterSymbol).isEqualTo("USD");

  }

  @Test
  public void testTradeAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptonitTradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptonitOrders cryptonitTrades = mapper.readValue(is, CryptonitOrders.class);

    Trades trades = CryptonitAdapters.adaptTrades(cryptonitTrades, CurrencyPair.BTC_USD);
    assertThat(trades.getTrades().size()).isEqualTo(100);

    // Verify all fields filled
    assertThat(trades.getTrades().get(0).getPrice().doubleValue() == 605.997);
    assertThat(trades.getTrades().get(0).getTradableAmount().doubleValue() == 1.189100000);
    assertThat(trades.getTrades().get(0).getCurrencyPair().baseSymbol == "BTC");
    assertThat(DateUtils.toUTCString(trades.getTrades().get(0).getTimestamp())).isEqualTo("2014-06-20 00:09:09 GMT");
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptonitTickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptonitTicker cryptonitTicker = mapper.readValue(is, CryptonitTicker.class);

    Ticker ticker = CryptonitAdapters.adaptTicker(cryptonitTicker, CurrencyPair.BTC_USD);
    System.out.println(ticker.toString());

    assertThat(ticker.getLast().toString()).isEqualTo("605.997");
    assertThat(ticker.getLow().toString()).isEqualTo("572.73768613");
    assertThat(ticker.getHigh().toString()).isEqualTo("610.00000000");
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("8.28600851"));

    assertThat(ticker.getCurrencyPair().baseSymbol).isEqualTo("BTC");

  }
}
