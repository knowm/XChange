package org.knowm.xchange.btctrade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.btctrade.dto.account.BTCTradeBalance;
import org.knowm.xchange.btctrade.dto.account.BTCTradeWallet;
import org.knowm.xchange.btctrade.dto.marketdata.BTCTradeDepth;
import org.knowm.xchange.btctrade.dto.marketdata.BTCTradeTicker;
import org.knowm.xchange.btctrade.dto.marketdata.BTCTradeTrade;
import org.knowm.xchange.btctrade.dto.trade.BTCTradeOrder;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.exceptions.ExchangeException;

public class BTCTradeAdaptersTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testAdaptTicker() throws IOException {

    BTCTradeTicker btcTradeTicker =
        mapper.readValue(
            getClass().getResource("dto/marketdata/ticker.json"), BTCTradeTicker.class);
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

    BTCTradeDepth btcTradeDepth =
        mapper.readValue(getClass().getResource("dto/marketdata/depth.json"), BTCTradeDepth.class);
    OrderBook orderBook = BTCTradeAdapters.adaptOrderBook(btcTradeDepth, CurrencyPair.BTC_CNY);
    assertEquals(50, orderBook.getAsks().size());
    assertEquals(50, orderBook.getBids().size());

    assertEquals(OrderType.ASK, orderBook.getAsks().get(0).getType());
    assertEquals(CurrencyPair.BTC_CNY, orderBook.getAsks().get(0).getCurrencyPair());
    assertEquals(new BigDecimal("3756.00000"), orderBook.getAsks().get(0).getLimitPrice());
    assertEquals(new BigDecimal("0.685"), orderBook.getAsks().get(0).getOriginalAmount());

    assertEquals(new BigDecimal("3758.00000"), orderBook.getAsks().get(1).getLimitPrice());
    assertEquals(new BigDecimal("0.083"), orderBook.getAsks().get(1).getOriginalAmount());

    assertEquals(new BigDecimal("4045.00000"), orderBook.getAsks().get(49).getLimitPrice());
    assertEquals(new BigDecimal("1.402"), orderBook.getAsks().get(49).getOriginalAmount());

    assertEquals(OrderType.BID, orderBook.getBids().get(0).getType());
    assertEquals(CurrencyPair.BTC_CNY, orderBook.getBids().get(0).getCurrencyPair());
    assertEquals(new BigDecimal("3730.00000"), orderBook.getBids().get(0).getLimitPrice());
    assertEquals(new BigDecimal("1.066"), orderBook.getBids().get(0).getOriginalAmount());

    assertEquals(new BigDecimal("3728.01000"), orderBook.getBids().get(1).getLimitPrice());
    assertEquals(new BigDecimal("3.000"), orderBook.getBids().get(1).getOriginalAmount());

    assertEquals(new BigDecimal("2951.00000"), orderBook.getBids().get(49).getLimitPrice());
    assertEquals(new BigDecimal("4.000"), orderBook.getBids().get(49).getOriginalAmount());
  }

  @Test
  public void testAdaptTradesBTCTradeTradeArrayCurrencyPair() throws IOException {

    BTCTradeTrade[] btcTradeTrades =
        mapper.readValue(
            getClass().getResource("dto/marketdata/trades.json"), BTCTradeTrade[].class);

    Trades trades = BTCTradeAdapters.adaptTrades(btcTradeTrades, CurrencyPair.BTC_CNY);
    List<Trade> tradeList = trades.getTrades();

    assertEquals(new Date(1403397140L * 1000L), tradeList.get(0).getTimestamp());
    assertEquals(new BigDecimal("3703"), tradeList.get(0).getPrice());
    assertEquals(new BigDecimal("3.50000000000"), tradeList.get(0).getOriginalAmount());
    assertEquals("2895575", tradeList.get(0).getId());
    assertEquals(OrderType.BID, tradeList.get(0).getType());

    assertEquals(new Date(1403397191L * 1000L), tradeList.get(1).getTimestamp());
    assertEquals(new BigDecimal("3704"), tradeList.get(1).getPrice());
    assertEquals(new BigDecimal("0.00200000000"), tradeList.get(1).getOriginalAmount());
    assertEquals("2895576", tradeList.get(1).getId());
    assertEquals(OrderType.BID, tradeList.get(1).getType());

    assertEquals(new Date(1403428819L * 1000L), tradeList.get(tradeList.size() - 2).getTimestamp());
    assertEquals(new BigDecimal("3740.01"), tradeList.get(tradeList.size() - 2).getPrice());
    assertEquals(
        new BigDecimal("0.01000000000"), tradeList.get(tradeList.size() - 2).getOriginalAmount());
    assertEquals("2896235", tradeList.get(tradeList.size() - 2).getId());
    assertEquals(OrderType.ASK, tradeList.get(tradeList.size() - 2).getType());

    assertEquals(new Date(1403428797L * 1000L), tradeList.get(tradeList.size() - 1).getTimestamp());
    assertEquals(new BigDecimal("3752"), tradeList.get(tradeList.size() - 1).getPrice());
    assertEquals(
        new BigDecimal("16.70000000000"), tradeList.get(tradeList.size() - 1).getOriginalAmount());
    assertEquals("2896239", tradeList.get(tradeList.size() - 1).getId());
    assertEquals(OrderType.BID, tradeList.get(tradeList.size() - 1).getType());
  }

  @Test
  public void testAdaptAccountInfo() throws IOException {

    BTCTradeBalance balance =
        mapper.readValue(getClass().getResource("dto/account/balance.json"), BTCTradeBalance.class);

    Wallet wallet = BTCTradeAdapters.adaptWallet(balance);
    assertNull(wallet.getId());
    assertEquals(new BigDecimal(3), wallet.getBalance(Currency.BTC).getTotal());
    assertEquals(new BigDecimal(2), wallet.getBalance(Currency.BTC).getFrozen());
    assertEquals(new BigDecimal("7"), wallet.getBalance(Currency.LTC).getTotal());
    assertEquals(new BigDecimal(4), wallet.getBalance(Currency.LTC).getFrozen());
    assertEquals(new BigDecimal("11"), wallet.getBalance(Currency.DOGE).getTotal());
    assertEquals(new BigDecimal(6), wallet.getBalance(Currency.DOGE).getFrozen());
    assertEquals(new BigDecimal("15"), wallet.getBalance(Currency.YBC).getTotal());
    assertEquals(new BigDecimal(8), wallet.getBalance(Currency.YBC).getFrozen());
    assertEquals(new BigDecimal("19"), wallet.getBalance(Currency.CNY).getTotal());
    assertEquals(new BigDecimal(10), wallet.getBalance(Currency.CNY).getFrozen());
  }

  @Test
  public void testAdaptAccountInfoError() throws IOException {

    BTCTradeBalance balance =
        mapper.readValue(
            getClass().getResource("dto/account/balance-signature-error.json"),
            BTCTradeBalance.class);

    try {
      BTCTradeAdapters.adaptWallet(balance);
      fail("ExchangeException is expected.");
    } catch (ExchangeException e) {
      assertEquals("signature error", e.getMessage());
    }
  }

  @Test
  public void testAdaptDepositAddress() throws IOException {

    BTCTradeWallet wallet =
        mapper.readValue(getClass().getResource("dto/account/wallet.json"), BTCTradeWallet.class);

    String depositAddress = BTCTradeAdapters.adaptDepositAddress(wallet);
    assertEquals("MASKED ADDRESS", depositAddress);
  }

  @Test
  public void testAdaptOpenOrders() throws IOException {

    BTCTradeOrder[] btcTradeOrders =
        mapper.readValue(
            getClass().getResource("dto/trade/orders-open.json"), BTCTradeOrder[].class);

    OpenOrders openOrders = BTCTradeAdapters.adaptOpenOrders(btcTradeOrders);
    List<LimitOrder> openOrderList = openOrders.getOpenOrders();
    assertEquals(2, openOrderList.size());

    LimitOrder order = openOrderList.get(0);
    assertEquals(CurrencyPair.LTC_CNY, order.getCurrencyPair());
    assertEquals("16636810", order.getId());
    assertEquals(new BigDecimal("1.01"), order.getLimitPrice());
    // 2014-09-14 12:48:53 Asia/Shanghai
    assertEquals(1410670133000L, order.getTimestamp().getTime());
    assertEquals(new BigDecimal("0.1"), order.getOriginalAmount());
    assertEquals(OrderType.BID, order.getType());

    order = openOrderList.get(1);
    assertEquals(CurrencyPair.BTC_CNY, order.getCurrencyPair());
    assertEquals("16634460", order.getId());
    assertEquals(new BigDecimal("10.01"), order.getLimitPrice());
    // 2014-09-14 12:31:46 Asia/Shanghai
    assertEquals(1410669106000L, order.getTimestamp().getTime());
    assertEquals(new BigDecimal("0.01"), order.getOriginalAmount());
    assertEquals(OrderType.BID, order.getType());
  }

  @Test
  public void testNullSafeSum() throws Exception {
    assertEquals(BTCTradeAdapters.zeroIfNull(new BigDecimal(3)), new BigDecimal(3));
    assertEquals(BTCTradeAdapters.zeroIfNull(new BigDecimal(0)), BigDecimal.ZERO);
    assertEquals(BTCTradeAdapters.zeroIfNull(null), BigDecimal.ZERO);

    assertEquals(
        BTCTradeAdapters.nullSafeSum(BigDecimal.ONE, new BigDecimal(2)), new BigDecimal(3));
    assertEquals(BTCTradeAdapters.nullSafeSum(BigDecimal.ONE, null), BigDecimal.ONE);
    assertEquals(BTCTradeAdapters.nullSafeSum(null, new BigDecimal(2)), new BigDecimal(2));
    assertEquals(BTCTradeAdapters.nullSafeSum(null, null), BigDecimal.ZERO);
  }
}
