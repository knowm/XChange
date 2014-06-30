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
package com.xeiam.xchange.bter.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bter.dto.BTEROrderType;
import com.xeiam.xchange.bter.dto.marketdata.BTERTradeHistory.BTERPublicTrade;
import com.xeiam.xchange.currency.CurrencyPair;

public class BTERMarketDataJsonTest {

  @Test
  public void testDeserializeCurrencyPairs() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTERMarketDataJsonTest.class.getResourceAsStream("/marketdata/example-pairs-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTERCurrencyPairs currencyPairs = mapper.readValue(is, BTERCurrencyPairs.class);

    Collection<CurrencyPair> pairs = currencyPairs.getPairs();
    assertThat(pairs).hasSize(83);

    assertThat(pairs.contains(new CurrencyPair("TIPS", "CNY"))).isTrue();
  }

  @Test
  public void testDeserializeTickers() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTERMarketDataJsonTest.class.getResourceAsStream("/marketdata/example-tickers-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTERTickers tickers = mapper.readValue(is, BTERTickers.class);

    Map<CurrencyPair, BTERTicker> tickerMap = tickers.getTickerMap();

    assertThat(tickerMap).hasSize(3);

    BTERTicker ticker = tickerMap.get(CurrencyPair.BTC_CNY);
    assertThat(ticker.getLast()).isEqualTo("3400.01");
    assertThat(ticker.getHigh()).isEqualTo("3497.41");
    assertThat(ticker.getLow()).isEqualTo("3400.01");
    assertThat(ticker.getAvg()).isEqualTo("3456.54");
    assertThat(ticker.getSell()).isEqualTo("3400.17");
    assertThat(ticker.getBuy()).isEqualTo("3400.01");
    assertThat(ticker.getTradeCurrencyVolume()).isEqualTo("347.2045");
    assertThat(ticker.getPriceCurrencyVolume()).isEqualTo("1200127.03");

    assertThat(ticker.isResult()).isTrue();
  }

  @Test
  public void testDeserializeDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTERMarketDataJsonTest.class.getResourceAsStream("/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTERDepth depth = mapper.readValue(is, BTERDepth.class);

    assertThat(depth.isResult()).isTrue();

    List<BTERPublicOrder> asks = depth.getAsks();
    assertThat(asks).hasSize(3);

    BTERPublicOrder ask = asks.get(0);
    assertThat(ask.getPrice()).isEqualTo("0.17936");
    assertThat(ask.getAmount()).isEqualTo("687");
  }

  @Test
  public void testDeserializeTrades() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTERMarketDataJsonTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTERTradeHistory tradeHistory = mapper.readValue(is, BTERTradeHistory.class);

    assertThat(tradeHistory.isResult()).isTrue();
    assertThat(tradeHistory.getElapsed()).isEqualTo("0.634ms");

    List<BTERPublicTrade> trades = tradeHistory.getTrades();
    assertThat(trades).hasSize(2);

    BTERPublicTrade trade = trades.get(0);
    assertThat(trade.getDate()).isEqualTo(1393908191);
    assertThat(trade.getPrice()).isEqualTo(new BigDecimal("3942"));
    assertThat(trade.getAmount()).isEqualTo(new BigDecimal("0.0129"));
    assertThat(trade.getTradeId()).isEqualTo("5600118");
    assertThat(trade.getType()).isEqualTo(BTEROrderType.SELL);
  }
}
