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
package com.xeiam.xchange.btctrade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.btctrade.dto.account.BTCTradeBalance;
import com.xeiam.xchange.btctrade.dto.account.BTCTradeWallet;
import com.xeiam.xchange.btctrade.dto.marketdata.BTCTradeDepth;
import com.xeiam.xchange.btctrade.dto.marketdata.BTCTradeTicker;
import com.xeiam.xchange.btctrade.dto.marketdata.BTCTradeTrade;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;

public class BTCTradeAdaptersTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testAdaptTicker() throws IOException {

    BTCTradeTicker btcTradeTicker = mapper.readValue(getClass().getResource("dto/marketdata/ticker.json"), BTCTradeTicker.class);
    Ticker ticker = BTCTradeAdapters.adaptTicker(btcTradeTicker, CurrencyPair.BTC_CNY);
    assertEquals(new BigDecimal("3760"), ticker.getHigh());
    assertEquals(new BigDecimal("3658"), ticker.getLow());
    assertEquals(new BigDecimal("3752"), ticker.getBid());
    assertEquals(new BigDecimal("3758"), ticker.getAsk());
    assertEquals(new BigDecimal("3757"), ticker.getLast());
    assertEquals(new BigDecimal("11009.76"), ticker.getVolume());
  }

  @Test
  public void testAdaptOrderBook() throws IOException {

    BTCTradeDepth btcTradeDepth = mapper.readValue(getClass().getResource("dto/marketdata/depth.json"), BTCTradeDepth.class);
    OrderBook orderBook = BTCTradeAdapters.adaptOrderBook(btcTradeDepth, CurrencyPair.BTC_CNY);
    assertEquals(50, orderBook.getAsks().size());
    assertEquals(50, orderBook.getBids().size());

    assertEquals(OrderType.ASK, orderBook.getAsks().get(0).getType());
    assertEquals(CurrencyPair.BTC_CNY, orderBook.getAsks().get(0).getCurrencyPair());
    assertEquals(new BigDecimal("3756.00000"), orderBook.getAsks().get(0).getLimitPrice());
    assertEquals(new BigDecimal("0.685"), orderBook.getAsks().get(0).getTradableAmount());

    assertEquals(new BigDecimal("3758.00000"), orderBook.getAsks().get(1).getLimitPrice());
    assertEquals(new BigDecimal("0.083"), orderBook.getAsks().get(1).getTradableAmount());

    assertEquals(new BigDecimal("4045.00000"), orderBook.getAsks().get(49).getLimitPrice());
    assertEquals(new BigDecimal("1.402"), orderBook.getAsks().get(49).getTradableAmount());

    assertEquals(OrderType.BID, orderBook.getBids().get(0).getType());
    assertEquals(CurrencyPair.BTC_CNY, orderBook.getBids().get(0).getCurrencyPair());
    assertEquals(new BigDecimal("3730.00000"), orderBook.getBids().get(0).getLimitPrice());
    assertEquals(new BigDecimal("1.066"), orderBook.getBids().get(0).getTradableAmount());

    assertEquals(new BigDecimal("3728.01000"), orderBook.getBids().get(1).getLimitPrice());
    assertEquals(new BigDecimal("3.000"), orderBook.getBids().get(1).getTradableAmount());

    assertEquals(new BigDecimal("2951.00000"), orderBook.getBids().get(49).getLimitPrice());
    assertEquals(new BigDecimal("4.000"), orderBook.getBids().get(49).getTradableAmount());
  }

  @Test
  public void testAdaptTradesBTCTradeTradeArrayCurrencyPair() throws IOException {

    BTCTradeTrade[] btcTradeTrades = mapper.readValue(getClass().getResource("dto/marketdata/trades.json"), BTCTradeTrade[].class);

    Trades trades = BTCTradeAdapters.adaptTrades(btcTradeTrades, CurrencyPair.BTC_CNY);
    List<Trade> tradeList = trades.getTrades();

    assertEquals(new Date(1403397140L * 1000L), tradeList.get(0).getTimestamp());
    assertEquals(new BigDecimal("3703"), tradeList.get(0).getPrice());
    assertEquals(new BigDecimal("3.50000000000"), tradeList.get(0).getTradableAmount());
    assertEquals("2895575", tradeList.get(0).getId());
    assertEquals(OrderType.BID, tradeList.get(0).getType());

    assertEquals(new Date(1403397191L * 1000L), tradeList.get(1).getTimestamp());
    assertEquals(new BigDecimal("3704"), tradeList.get(1).getPrice());
    assertEquals(new BigDecimal("0.00200000000"), tradeList.get(1).getTradableAmount());
    assertEquals("2895576", tradeList.get(1).getId());
    assertEquals(OrderType.BID, tradeList.get(1).getType());

    assertEquals(new Date(1403428819L * 1000L), tradeList.get(tradeList.size() - 2).getTimestamp());
    assertEquals(new BigDecimal("3740.01"), tradeList.get(tradeList.size() - 2).getPrice());
    assertEquals(new BigDecimal("0.01000000000"), tradeList.get(tradeList.size() - 2).getTradableAmount());
    assertEquals("2896235", tradeList.get(tradeList.size() - 2).getId());
    assertEquals(OrderType.ASK, tradeList.get(tradeList.size() - 2).getType());

    assertEquals(new Date(1403428797L * 1000L), tradeList.get(tradeList.size() - 1).getTimestamp());
    assertEquals(new BigDecimal("3752"), tradeList.get(tradeList.size() - 1).getPrice());
    assertEquals(new BigDecimal("16.70000000000"), tradeList.get(tradeList.size() - 1).getTradableAmount());
    assertEquals("2896239", tradeList.get(tradeList.size() - 1).getId());
    assertEquals(OrderType.BID, tradeList.get(tradeList.size() - 1).getType());

  }

  @Test
  public void testAdaptAccountInfo() throws IOException {

    BTCTradeBalance balance = mapper.readValue(getClass().getResource("dto/account/balance.json"), BTCTradeBalance.class);

    AccountInfo accountInfo = BTCTradeAdapters.adaptAccountInfo(balance);
    assertNull(accountInfo.getUsername());
    assertNull(accountInfo.getTradingFee());
    assertEquals(new BigDecimal(3), accountInfo.getBalance(Currencies.BTC));
    assertEquals(new BigDecimal("7"), accountInfo.getBalance(Currencies.LTC));
    assertEquals(new BigDecimal("11"), accountInfo.getBalance(Currencies.DOGE));
    assertEquals(new BigDecimal("15"), accountInfo.getBalance("YBC"));
    assertEquals(new BigDecimal("19"), accountInfo.getBalance(Currencies.CNY));
  }

  @Test
  public void testAdaptAccountInfoError() throws IOException {

    BTCTradeBalance balance = mapper.readValue(getClass().getResource("dto/account/balance-signature-error.json"), BTCTradeBalance.class);

    try {
      BTCTradeAdapters.adaptAccountInfo(balance);
      fail("ExchangeException is expected.");
    } catch (ExchangeException e) {
      assertEquals("signature error", e.getMessage());
    }
  }

  @Test
  public void testAdaptDepositAddress() throws IOException {

    BTCTradeWallet wallet = mapper.readValue(getClass().getResource("dto/account/wallet.json"), BTCTradeWallet.class);

    String depositAddress = BTCTradeAdapters.adaptDepositAddress(wallet);
    assertEquals("MASKED ADDRESS", depositAddress);
  }

}
