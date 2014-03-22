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
package com.xeiam.xchange.vaultofsatoshi.service;

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
import com.xeiam.xchange.vaultofsatoshi.VaultOfSatoshiAdapters;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.DepthWrapper;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.TickerWrapper;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.TradesWrapper;
import com.xeiam.xchange.vaultofsatoshi.service.marketdata.VaultOfSatoshiDepthJSONTest;
import com.xeiam.xchange.vaultofsatoshi.service.marketdata.VaultOfSatoshiTickerJSONTest;
import com.xeiam.xchange.vaultofsatoshi.service.marketdata.VaultOfSatoshiTradesJSONTest;

/**
 * Tests the vosAdapter class
 */
public class VaultOfSatoshiAdapterTest {

  @Test
  public void testOrderAdapterWithDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = VaultOfSatoshiDepthJSONTest.class.getResourceAsStream("/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    DepthWrapper VaultOfSatoshiDepth = mapper.readValue(is, DepthWrapper.class);

    List<LimitOrder> asks = VaultOfSatoshiAdapters.adaptOrders(VaultOfSatoshiDepth.getDepth().getAsks(), new CurrencyPair("BTC","USD"), "ask", "");

    // Verify all fields filled
    assertThat(asks.get(0).getLimitPrice().doubleValue()).isEqualTo(682.00);
    assertThat(asks.get(0).getType()).isEqualTo(OrderType.ASK);
    assertThat(asks.get(0).getTradableAmount().doubleValue()).isEqualTo(0.22000000);
    assertThat(asks.get(0).getCurrencyPair().baseSymbol).isEqualTo("BTC");
    assertThat(asks.get(0).getCurrencyPair().counterSymbol).isEqualTo("USD");

  }

  @Test
  public void testTradeAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = VaultOfSatoshiTradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    TradesWrapper vosTrades = mapper.readValue(is, TradesWrapper.class);

    Trades trades = VaultOfSatoshiAdapters.adaptTrades(vosTrades.getTrades(), CurrencyPair.BTC_USD);
    assertThat(trades.getTrades().size()).isEqualTo(20);

    // Verify all fields filled
    assertThat(trades.getTrades().get(0).getPrice().doubleValue() == 592.60797000);
    assertThat(trades.getTrades().get(0).getTradableAmount().doubleValue() == 0.01786170);
    assertThat(trades.getTrades().get(0).getCurrencyPair().baseSymbol == "BTC");
    assertThat(DateUtils.toUTCString(trades.getTrades().get(0).getTimestamp())).isEqualTo("2014-03-20 20:16:33 GMT");
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = VaultOfSatoshiTickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    TickerWrapper VaultOfSatoshiTicker = mapper.readValue(is, TickerWrapper.class);

    Ticker ticker = VaultOfSatoshiAdapters.adaptTicker(VaultOfSatoshiTicker.getTicker(), CurrencyPair.BTC_USD);
    System.out.println(ticker.toString());

    assertThat(ticker.getLast().toString()).isEqualTo("684.00000000");
    assertThat(ticker.getLow().toString()).isEqualTo("601.00000000");
    assertThat(ticker.getHigh().toString()).isEqualTo("686.50000000");
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("29.32450256"));
    assertThat(ticker.getCurrencyPair().baseSymbol).isEqualTo("BTC");

  }
}
