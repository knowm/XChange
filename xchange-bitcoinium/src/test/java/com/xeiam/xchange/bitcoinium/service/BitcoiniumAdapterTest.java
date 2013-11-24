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
package com.xeiam.xchange.bitcoinium.service;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitcoinium.BitcoiniumAdapters;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumOrderbook;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumTicker;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumTickerHistory;
import com.xeiam.xchange.bitcoinium.service.marketdata.BitcoiniumDepthJSONTest;
import com.xeiam.xchange.bitcoinium.service.marketdata.BitcoiniumTickerJSONTest;
import com.xeiam.xchange.bitcoinium.service.marketdata.BitcoiniumTradesJSONTest;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Tests the BitcoiniumAdapter class
 */
public class BitcoiniumAdapterTest {

  @Test
  public void testOrderAdapterWithDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcoiniumDepthJSONTest.class.getResourceAsStream("/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoiniumOrderbook BitcoiniumDepth = mapper.readValue(is, BitcoiniumOrderbook.class);

    List<LimitOrder> asks = BitcoiniumAdapters.adaptOrders(BitcoiniumDepth, "USD", "ask", "");

    // Verify all fields filled
    assertThat(asks.get(0).getLimitPrice().getAmount().doubleValue()).isEqualTo(132.79);
    assertThat(asks.get(0).getType()).isEqualTo(OrderType.ASK);
    assertThat(asks.get(0).getTradableAmount().doubleValue()).isEqualTo(45.98);
    assertThat(asks.get(0).getTradableIdentifier()).isEqualTo("BTC");
    assertThat(asks.get(0).getTransactionCurrency()).isEqualTo("USD");

  }

  @Test
  public void testTradeAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcoiniumTradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoiniumTickerHistory BitcoiniumTrades = mapper.readValue(is, BitcoiniumTickerHistory.class);

    Trades trades = BitcoiniumAdapters.adaptTrades(BitcoiniumTrades, "USD", "BTC");
    assertThat(trades.getTrades().size()).isEqualTo(116);

    // Verify all fields filled
    assertThat(trades.getTrades().get(0).getPrice().getAmount().doubleValue() == 138.98);
    //assertThat(trades.getTrades().get(0).getTradableAmount().doubleValue() == 13.000000000);
    assertThat(DateUtils.toUTCString(trades.getTrades().get(0).getTimestamp())).isEqualTo("2013-09-15 17:00:24 GMT");
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcoiniumTickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoiniumTicker BitcoiniumTicker = mapper.readValue(is, BitcoiniumTicker.class);

    Ticker ticker = BitcoiniumAdapters.adaptTicker(BitcoiniumTicker, "USD", "BTC");
    System.out.println(ticker.toString());

    assertThat(ticker.getLast().toString()).isEqualTo("USD 132.22874");
    assertThat(ticker.getLow().toString()).isEqualTo("USD 131.0");
    assertThat(ticker.getHigh().toString()).isEqualTo("USD 135.99");
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("6582"));

  }
}
