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
package com.xeiam.xchange.bter.dto;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bter.BTERAdapters;
import com.xeiam.xchange.bter.dto.account.BTERFunds;
import com.xeiam.xchange.bter.dto.marketdata.BTERCurrencyPairs;
import com.xeiam.xchange.bter.dto.marketdata.BTERDepth;
import com.xeiam.xchange.bter.dto.marketdata.BTERTicker;
import com.xeiam.xchange.bter.dto.marketdata.BTERTickers;
import com.xeiam.xchange.bter.dto.marketdata.BTERTradeHistory;
import com.xeiam.xchange.bter.dto.trade.BTEROpenOrders;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;

public class BTERAdapterTests {

  Collection<CurrencyPair> currencyPairs;

  @Before
  public void before() throws JsonParseException, JsonMappingException, IOException {

    // Read in the JSON from the example resources
    InputStream is = BTERAdapterTests.class.getResourceAsStream("/marketdata/example-pairs-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTERCurrencyPairs currencyPairs = mapper.readValue(is, BTERCurrencyPairs.class);

    this.currencyPairs = currencyPairs.getPairs();
  }

  @Test
  public void testAdaptOpenOrders() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTERAdapterTests.class.getResourceAsStream("/trade/example-order-list-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTEROpenOrders openOrders = mapper.readValue(is, BTEROpenOrders.class);

    OpenOrders adaptedOpenOrders = BTERAdapters.adaptOpenOrders(openOrders, currencyPairs);

    List<LimitOrder> adaptedOrderList = adaptedOpenOrders.getOpenOrders();
    assertThat(adaptedOrderList).hasSize(1);

    LimitOrder adaptedOrder = adaptedOrderList.get(0);
    assertThat(adaptedOrder.getType()).isEqualTo(OrderType.ASK);
    assertThat(adaptedOrder.getTradableAmount()).isEqualTo("0.384");
    assertThat(adaptedOrder.getCurrencyPair()).isEqualTo(CurrencyPair.LTC_BTC);
    assertThat(adaptedOrder.getId()).isEqualTo("12941907");
    assertThat(adaptedOrder.getTimestamp()).isNull();
    assertThat(adaptedOrder.getLimitPrice()).isEqualTo(new BigDecimal("0.010176").divide(new BigDecimal("0.384"), RoundingMode.HALF_EVEN));
  }

  @Test
  public void testAdaptTrades() throws IOException {

    InputStream is = BTERAdapterTests.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTERTradeHistory tradeHistory = mapper.readValue(is, BTERTradeHistory.class);

    Trades adaptedTrades = BTERAdapters.adaptTrades(tradeHistory, CurrencyPair.BTC_CNY);

    List<Trade> tradeList = adaptedTrades.getTrades();
    assertThat(tradeList).hasSize(2);

    Trade trade = tradeList.get(0);
    assertThat(trade.getType()).isEqualTo(OrderType.ASK);
    assertThat(trade.getTradableAmount()).isEqualTo("0.0129");
    assertThat(trade.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_CNY);
    assertThat(trade.getPrice()).isEqualTo("3942");
    assertThat(trade.getTimestamp()).isEqualTo(new Date(1393908191000L));
    assertThat(trade.getId()).isEqualTo("5600118");
    assertThat(trade.getOrderId()).isNull();
  }

  @Test
  public void testAdaptAccountInfo() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTERAdapterTests.class.getResourceAsStream("/account/example-funds-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTERFunds funds = mapper.readValue(is, BTERFunds.class);

    AccountInfo accountInfo = BTERAdapters.adaptAccountInfo(funds);

    assertThat(accountInfo.getWallets()).hasSize(4);
    assertThat(accountInfo.getBalance(Currencies.BTC)).isEqualTo("0.00010165");
  }

  @Test
  public void testAdaptTicker() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTERAdapterTests.class.getResourceAsStream("/marketdata/example-tickers-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTERTickers tickers = mapper.readValue(is, BTERTickers.class);

    Map<CurrencyPair, BTERTicker> tickerMap = tickers.getTickerMap();

    Ticker ticker = BTERAdapters.adaptTicker(CurrencyPair.BTC_CNY, tickerMap.get(CurrencyPair.BTC_CNY));
    assertThat(ticker.getLast()).isEqualTo("3400.01");
    assertThat(ticker.getHigh()).isEqualTo("3497.41");
    assertThat(ticker.getLow()).isEqualTo("3400.01");
    assertThat(ticker.getAsk()).isEqualTo("3400.17");
    assertThat(ticker.getBid()).isEqualTo("3400.01");
    assertThat(ticker.getVolume()).isEqualTo("347.2045");
  }

  @Test
  public void testAdaptOrderBook() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTERAdapterTests.class.getResourceAsStream("/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTERDepth depth = mapper.readValue(is, BTERDepth.class);

    OrderBook orderBook = BTERAdapters.adaptOrderBook(depth, CurrencyPair.LTC_BTC);

    List<LimitOrder> asks = orderBook.getAsks();
    assertThat(asks).hasSize(3);

    LimitOrder ask = asks.get(0);
    assertThat(ask.getLimitPrice()).isEqualTo("0.1785");
    assertThat(ask.getTradableAmount()).isEqualTo("1324.111");
    assertThat(ask.getType()).isEqualTo(OrderType.ASK);
    assertThat(ask.getTimestamp()).isNull();
    assertThat(ask.getCurrencyPair()).isEqualTo(CurrencyPair.LTC_BTC);
  }
}
