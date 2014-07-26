/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.btce.v2.service;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.btce.v2.BTCEAdapters;
import com.xeiam.xchange.btce.v2.dto.marketdata.BTCEDepth;
import com.xeiam.xchange.btce.v2.dto.marketdata.BTCETickerWrapper;
import com.xeiam.xchange.btce.v2.dto.marketdata.BTCETrade;
import com.xeiam.xchange.btce.v2.dto.trade.BTCETradeHistoryReturn;
import com.xeiam.xchange.btce.v2.service.marketdata.BTCEDepthJSONTest;
import com.xeiam.xchange.btce.v2.service.marketdata.BTCETickerJSONTest;
import com.xeiam.xchange.btce.v2.service.marketdata.BTCETradesJSONTest;
import com.xeiam.xchange.btce.v2.service.trade.BTCETradeHistoryJSONTest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Tests the BTCEAdapter class
 */
@Deprecated
@Ignore
public class BTCEAdapterTest {

  @Test
  public void testOrderAdapterWithDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTCEDepthJSONTest.class.getResourceAsStream("/v2/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCEDepth BTCEDepth = mapper.readValue(is, BTCEDepth.class);

    List<LimitOrder> asks = BTCEAdapters.adaptOrders(BTCEDepth.getAsks(), CurrencyPair.BTC_USD, "ask", "");

    // verify all fields filled
    assertThat(asks.get(0).getType()).isEqualTo(OrderType.ASK);
    assertThat(asks.get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);

  }

  @Test
  public void testTradeAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTCETradesJSONTest.class.getResourceAsStream("/v2/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCETrade[] BTCETrades = mapper.readValue(is, BTCETrade[].class);

    Trades trades = BTCEAdapters.adaptTrades(BTCETrades);
    // System.out.println(trades.getTrades().size());
    assertThat(trades.getTrades().size() == 150);

    // verify all fields filled
    assertThat(trades.getTrades().get(0).getPrice().doubleValue()).isEqualTo(13.07);
    assertThat(trades.getTrades().get(0).getType()).isEqualTo(OrderType.ASK);
    assertThat(trades.getTrades().get(0).getTradableAmount().doubleValue()).isEqualTo(1.0);
    assertThat(trades.getTrades().get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    // assertThat("transactionCurrency should be PLN",
    // trades.getTrades().get(0).getTransactionCurrency().equals("PLN"));
    assertThat(DateUtils.toUTCString(trades.getTrades().get(0).getTimestamp())).isEqualTo("2012-12-22 08:06:14 GMT");
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTCETickerJSONTest.class.getResourceAsStream("/v2/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCETickerWrapper BTCETicker = mapper.readValue(is, BTCETickerWrapper.class);

    Ticker ticker = BTCEAdapters.adaptTicker(BTCETicker, CurrencyPair.BTC_USD);

    assertThat(ticker.getLast().toString()).isEqualTo("13.07");
    assertThat(ticker.getLow().toString()).isEqualTo("13");
    assertThat(ticker.getHigh().toString()).isEqualTo("13.23");
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("3078.62284"));
    assertThat(ticker.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);

  }

  @Test
  public void testUserTradeHistoryAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTCETradeHistoryJSONTest.class.getResourceAsStream("/v2/trade/example-trade-history-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCETradeHistoryReturn btceTradeHistory = mapper.readValue(is, BTCETradeHistoryReturn.class);

    Trades trades = BTCEAdapters.adaptTradeHistory(btceTradeHistory.getReturnValue());
    List<Trade> tradeList = trades.getTrades();
    Trade lastTrade = tradeList.get(tradeList.size() - 1);
    assertThat(lastTrade.getId()).isEqualTo("7258275");
    assertThat(lastTrade.getType()).isEqualTo(OrderType.ASK);
    assertThat(lastTrade.getPrice().toString()).isEqualTo("125.75");
    assertThat(lastTrade.getTimestamp().getTime()).isEqualTo(1378194574000L);
    assertThat(DateUtils.toUTCString(lastTrade.getTimestamp())).isEqualTo("2013-09-03 07:49:34 GMT");

  }
}
