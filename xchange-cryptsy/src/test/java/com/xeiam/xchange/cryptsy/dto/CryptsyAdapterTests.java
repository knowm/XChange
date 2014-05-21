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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.cryptsy.CryptsyAdapters;
import com.xeiam.xchange.cryptsy.dto.account.CryptsyAccountInfoReturn;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyGetMarketsReturn;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyMarketTradesReturn;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyOrderBookReturn;
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
    InputStream is = CryptsyAdapterTests.class.getResourceAsStream("/marketdata/Sample_GetMarket_Data.json");
    
    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptsyGetMarketsReturn cryptsyTradeHistory = mapper.readValue(is, CryptsyGetMarketsReturn.class);
    
    List<CurrencyPair> adaptedCurrencyPairs = CryptsyAdapters.adaptCurrencyPairs(cryptsyTradeHistory);
    
    assertEquals(adaptedCurrencyPairs.size(), 180);
    assertEquals(adaptedCurrencyPairs.get(0), new CurrencyPair("42", "BTC"));
    assertEquals(adaptedCurrencyPairs.get(179), new CurrencyPair("LTC", "USD"));
  }
  
  @SuppressWarnings("rawtypes")
  @Test
  public void testAdaptMarketSets() throws IOException {
  
    // Read in the JSON from the example resources
    InputStream is = CryptsyAdapterTests.class.getResourceAsStream("/marketdata/Sample_GetMarket_Data.json");
    
    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptsyGetMarketsReturn cryptsyTradeHistory = mapper.readValue(is, CryptsyGetMarketsReturn.class);
    
    HashMap[] marketSets = CryptsyAdapters.adaptMarketSets(cryptsyTradeHistory);
    
    assertEquals(marketSets[0].get(14), new CurrencyPair("WDC", "BTC"));
    assertEquals(marketSets[0].get(21), new CurrencyPair("WDC", "LTC"));
    assertEquals(marketSets[0].get(3), new CurrencyPair("LTC", "BTC"));
    
    assertEquals(marketSets[1].get(new CurrencyPair("WDC", "BTC")), 14);
    assertEquals(marketSets[1].get(new CurrencyPair("WDC", "LTC")), 21);
    assertEquals(marketSets[1].get(new CurrencyPair("LTC", "BTC")), 3);
  }
}
