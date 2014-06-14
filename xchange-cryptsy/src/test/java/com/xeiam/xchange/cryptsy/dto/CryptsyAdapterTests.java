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
package com.xeiam.xchange.cryptsy.dto;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.cryptsy.CryptsyAdapters;
import com.xeiam.xchange.cryptsy.CryptsyUtils;
import com.xeiam.xchange.cryptsy.dto.account.CryptsyAccountInfoReturn;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyGetMarketsReturn;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyMarketDataJsonTests;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyMarketTradesReturn;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyOrderBookReturn;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyPublicMarketData;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyPublicMarketDataReturn;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyPublicOrderbook;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyPublicOrderbookReturn;
import com.xeiam.xchange.cryptsy.dto.trade.CryptsyOpenOrdersReturn;
import com.xeiam.xchange.cryptsy.dto.trade.CryptsyTradeHistoryReturn;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.Wallet;

public class CryptsyAdapterTests {

  @Test
  public void testAdaptOrderBook() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyAdapterTests.class.getResourceAsStream("/marketdata/Sample_MarketOrders_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptsyOrderBookReturn cryptsyOrderBook = mapper.readValue(is, CryptsyOrderBookReturn.class);

    OrderBook adaptedOrderBook = CryptsyAdapters.adaptOrderBook(cryptsyOrderBook, CurrencyPair.WDC_BTC);

    assertEquals(adaptedOrderBook.getAsks().size(), cryptsyOrderBook.getReturnValue().sellOrders().size());
    assertEquals(adaptedOrderBook.getBids().size(), cryptsyOrderBook.getReturnValue().buyOrders().size());
    assertNotNull(adaptedOrderBook.getTimeStamp());
  }

  @Test
  public void testAdaptOrderBookPublic() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyMarketDataJsonTests.class.getResourceAsStream("/marketdata/Sample_Orderbook_Public_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    Map<Integer, CryptsyPublicOrderbook> cryptsyOrderBookMap = CryptsyAdapters.adaptPublicOrderBookMap(mapper.readValue(is, CryptsyPublicOrderbookReturn.class).getReturnValue());

    List<OrderBook> adaptedOrderBookList = CryptsyAdapters.adaptPublicOrderBooks(cryptsyOrderBookMap);
    assertThat(adaptedOrderBookList).hasSize(1);

    OrderBook adaptedOrderBook = adaptedOrderBookList.get(0);
    assertEquals(adaptedOrderBook.getAsks().size(), 3);
    assertEquals(adaptedOrderBook.getBids().size(), 3);
    assertNotNull(adaptedOrderBook.getTimeStamp());

    LimitOrder asks = adaptedOrderBook.getAsks().get(0);
    assertThat(asks.getCurrencyPair()).isEqualTo(CurrencyPair.DOGE_LTC);
    assertThat(asks.getId()).isNull();
    assertThat(asks.getLimitPrice()).isEqualTo("0.00003495");
    assertThat(asks.getTradableAmount()).isEqualTo("334369.75217020");
    assertThat(asks.getType()).isEqualTo(OrderType.ASK);
  }

  @Test
  public void testAdaptTrades() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyAdapterTests.class.getResourceAsStream("/marketdata/Sample_MarketTrades_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptsyMarketTradesReturn cryptsyTrades = mapper.readValue(is, CryptsyMarketTradesReturn.class);

    Trades adaptedTrades = CryptsyAdapters.adaptTrades(cryptsyTrades, CurrencyPair.WDC_BTC);

    assertEquals(adaptedTrades.getTrades().size(), cryptsyTrades.getReturnValue().size());
  }

  @Test
  public void testAdaptTradesPublic() throws IOException, ParseException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyMarketDataJsonTests.class.getResourceAsStream("/marketdata/Sample_MarketData_Public_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    Map<Integer, CryptsyPublicMarketData> cryptsyMarketData = CryptsyAdapters.adaptPublicMarketDataMap(mapper.readValue(is, CryptsyPublicMarketDataReturn.class).getReturnValue());

    Map<CurrencyPair, Trades> adaptedTradesMap = CryptsyAdapters.adaptPublicTrades(cryptsyMarketData);

    Trades adaptedTrades = adaptedTradesMap.get(CurrencyPair.DOGE_LTC);
    List<Trade> adaptedTradesList = adaptedTrades.getTrades();
    assertThat(adaptedTradesList).hasSize(2);

    Trade adaptedTrade = adaptedTradesList.get(1);
    assertThat(adaptedTrade.getCurrencyPair()).isEqualTo(CurrencyPair.DOGE_LTC);
    assertThat(adaptedTrade.getId()).isEqualTo("47692497");
    assertThat(adaptedTrade.getOrderId()).isNull();
    assertThat(adaptedTrade.getPrice()).isEqualTo("0.00003495");
    assertThat(adaptedTrade.getTradableAmount()).isEqualTo("2961.55892792");
    assertThat(adaptedTrade.getTimestamp()).isEqualTo(CryptsyUtils.convertDateTime("2014-05-29 21:49:34"));
    assertThat(adaptedTrade.getType()).isNull();
  }

  @Test
  public void testAdaptTicker() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyAdapterTests.class.getResourceAsStream("/marketdata/Sample_GetMarket_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptsyGetMarketsReturn cryptsyTrades = mapper.readValue(is, CryptsyGetMarketsReturn.class);

    Ticker adaptedTicker = CryptsyAdapters.adaptTicker(cryptsyTrades, CurrencyPair.WDC_BTC);

    assertEquals(adaptedTicker.getCurrencyPair(), CurrencyPair.WDC_BTC);
    assertEquals(adaptedTicker.getLast(), new BigDecimal("0.00006187"));
    assertEquals(adaptedTicker.getLow(), new BigDecimal("0.00006040"));
    assertEquals(adaptedTicker.getHigh(), new BigDecimal("0.00006335"));
    assertEquals(adaptedTicker.getVolume(), new BigDecimal("61283.49173059"));
  }

  @Test
  public void testAdaptTickerPublic() throws IOException, ParseException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyMarketDataJsonTests.class.getResourceAsStream("/marketdata/Sample_MarketData_Public_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    Map<Integer, CryptsyPublicMarketData> cryptsyMarketData = CryptsyAdapters.adaptPublicMarketDataMap(mapper.readValue(is, CryptsyPublicMarketDataReturn.class).getReturnValue());

    List<Ticker> adaptedTickerList = CryptsyAdapters.adaptPublicTickers(cryptsyMarketData);
    assertThat(adaptedTickerList).hasSize(1);

    Ticker adaptedTicker = adaptedTickerList.get(0);
    assertEquals(adaptedTicker.getCurrencyPair(), CurrencyPair.DOGE_LTC);
    assertEquals(adaptedTicker.getLast(), new BigDecimal("0.00003485"));
    assertEquals(adaptedTicker.getBid(), new BigDecimal("00.00003485"));
    assertEquals(adaptedTicker.getAsk(), new BigDecimal("0.00003495"));
    assertThat(adaptedTicker.getLow()).isNull();
    assertThat(adaptedTicker.getHigh()).isNull();
    assertEquals(adaptedTicker.getVolume(), new BigDecimal("13154836.13271418"));
  }

  @Test
  public void testAdaptAccountInfo() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyAdapterTests.class.getResourceAsStream("/account/Sample_GetInfo_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptsyAccountInfoReturn accountInfo = mapper.readValue(is, CryptsyAccountInfoReturn.class);

    AccountInfo adaptedAccountInfo = CryptsyAdapters.adaptAccountInfo(accountInfo);

    List<Wallet> wallets = adaptedAccountInfo.getWallets();
    assertEquals(wallets.size(), 150);
    for (Wallet wallet : wallets) {
      if (wallet.getCurrency().equals("BTC")) {
        assertEquals(wallet.getBalance(), new BigDecimal("0.01527794"));
      }
      if (wallet.getCurrency().equals("ZET")) {
        assertEquals(wallet.getBalance(), new BigDecimal("1.35094992"));
      }
      if (wallet.getCurrency().equals("XPM")) {
        assertEquals(wallet.getBalance(), new BigDecimal("0.00000057"));
      }
    }
  }

  @Test
  public void testAdaptOpenOrders() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyAdapterTests.class.getResourceAsStream("/trade/Sample_AllMyOrders_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptsyOpenOrdersReturn cryptsyOpenOrders = mapper.readValue(is, CryptsyOpenOrdersReturn.class);

    OpenOrders adaptedOpenOrders = CryptsyAdapters.adaptOpenOrders(cryptsyOpenOrders);

    assertEquals(adaptedOpenOrders.getOpenOrders().size(), 9);

    LimitOrder order = adaptedOpenOrders.getOpenOrders().get(0);
    assertEquals(order.getId(), "90039904");
    assertEquals(order.getLimitPrice(), new BigDecimal("0.00000001"));
    assertEquals(order.getTradableAmount(), new BigDecimal("50000.10000000"));
    assertEquals(order.getCurrencyPair().baseSymbol, "WDC");
    assertEquals(order.getCurrencyPair().counterSymbol, "BTC");
    assertEquals(order.getType(), OrderType.BID);

    LimitOrder order2 = adaptedOpenOrders.getOpenOrders().get(8);
    assertEquals(order2.getId(), "90041288");
    assertEquals(order2.getLimitPrice(), new BigDecimal("0.00000009"));
    assertEquals(order2.getTradableAmount(), new BigDecimal("50001.00000000"));
    assertEquals(order2.getCurrencyPair().baseSymbol, "LTC");
    assertEquals(order2.getCurrencyPair().counterSymbol, "BTC");
    assertEquals(order2.getType(), OrderType.BID);
  }

  @Test
  public void testAdaptTradeHistory() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyAdapterTests.class.getResourceAsStream("/trade/Sample_AllMyTrades_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptsyTradeHistoryReturn cryptsyTradeHistory = mapper.readValue(is, CryptsyTradeHistoryReturn.class);

    Trades adaptedTrades = CryptsyAdapters.adaptTradeHistory(cryptsyTradeHistory);

    Trade trade = adaptedTrades.getTrades().get(0);
    assertEquals(trade.getCurrencyPair(), CurrencyPair.LTC_BTC);
    assertEquals(trade.getId(), "9982231");
    assertEquals(trade.getOrderId(), "23569349");
    assertEquals(trade.getType(), OrderType.BID);
    assertEquals(trade.getTradableAmount(), new BigDecimal("0.15949550"));
    assertEquals(trade.getPrice(), new BigDecimal("0.03128615"));
  }

  @Test
  public void testAdaptCurrencyPairs() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyAdapterTests.class.getResourceAsStream("/marketdata/Sample_AllMarketData_Public_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    Map<Integer, CryptsyPublicMarketData> cryptsyMarketData = CryptsyAdapters.adaptPublicMarketDataMap(mapper.readValue(is, CryptsyPublicMarketDataReturn.class).getReturnValue());

    Collection<CurrencyPair> adaptedCurrencyPairs = CryptsyAdapters.adaptCurrencyPairs(cryptsyMarketData);

    assertEquals(adaptedCurrencyPairs.size(), 185);
  }

  @SuppressWarnings("rawtypes")
  @Test
  public void testAdaptMarketSets() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyMarketDataJsonTests.class.getResourceAsStream("/marketdata/Sample_AllMarketData_Public_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    Map<Integer, CryptsyPublicMarketData> cryptsyMarketData = CryptsyAdapters.adaptPublicMarketDataMap(mapper.readValue(is, CryptsyPublicMarketDataReturn.class).getReturnValue());

    HashMap[] marketSets = CryptsyAdapters.adaptMarketSets(cryptsyMarketData);
    assertEquals(marketSets[0].get(135), CurrencyPair.DOGE_LTC);
    assertEquals(marketSets[1].get(CurrencyPair.DOGE_LTC), 135);
  }
}
