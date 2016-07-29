package org.knowm.xchange.mexbt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.mexbt.dto.account.MeXBTBalanceResponse;
import org.knowm.xchange.mexbt.dto.account.MeXBTBalanceResponseTest;
import org.knowm.xchange.mexbt.dto.account.MeXBTOpenOrdersResponse;
import org.knowm.xchange.mexbt.dto.account.MeXBTOpenOrdersResponseTest;
import org.knowm.xchange.mexbt.dto.account.MeXBTTradeResponse;
import org.knowm.xchange.mexbt.dto.account.MeXBTUserTradeResponseTest;
import org.knowm.xchange.mexbt.dto.marketdata.MeXBTOrderBook;
import org.knowm.xchange.mexbt.dto.marketdata.MeXBTOrderBookTest;
import org.knowm.xchange.mexbt.dto.marketdata.MeXBTTicker;
import org.knowm.xchange.mexbt.dto.marketdata.MeXBTTickerTest;
import org.knowm.xchange.mexbt.dto.marketdata.MeXBTTrade;
import org.knowm.xchange.mexbt.dto.marketdata.MeXBTTradeTest;

public class MeXBTAdaptersTest {

  @Test
  public void testAdaptCurrencyPair() {
    assertEquals(CurrencyPair.BTC_MXN, MeXBTAdapters.adaptCurrencyPair("BTCMXN"));
  }

  @Test
  public void testAdaptOrderBook() throws JsonParseException, JsonMappingException, IOException {
    MeXBTOrderBook meXBTOrderBook = new ObjectMapper().readValue(MeXBTOrderBookTest.class.getResource("order-book.json"), MeXBTOrderBook.class);
    OrderBook orderBook = MeXBTAdapters.adaptOrderBook(CurrencyPair.BTC_USD, meXBTOrderBook);

    assertNull(orderBook.getTimeStamp());

    assertEquals(8, orderBook.getAsks().size());
    assertEquals(CurrencyPair.BTC_USD, orderBook.getAsks().get(0).getCurrencyPair());
    assertEquals(new BigDecimal("230.62"), orderBook.getAsks().get(0).getLimitPrice());
    assertEquals(new BigDecimal("30"), orderBook.getAsks().get(0).getTradableAmount());

    assertEquals(CurrencyPair.BTC_USD, orderBook.getBids().get(0).getCurrencyPair());
    assertEquals(22, orderBook.getBids().size());
    assertEquals(new BigDecimal("238.68"), orderBook.getBids().get(0).getLimitPrice());
    assertEquals(new BigDecimal("2.5"), orderBook.getBids().get(0).getTradableAmount());
  }

  @Test
  public void testAdaptTrades() throws JsonParseException, JsonMappingException, IOException {
    MeXBTTrade[] meXBTTrades = new ObjectMapper().readValue(MeXBTTradeTest.class.getResource("trades.json"), MeXBTTrade[].class);
    List<Trade> trades = MeXBTAdapters.adaptTrades(CurrencyPair.BTC_MXN, meXBTTrades).getTrades();

    assertEquals("0", trades.get(0).getId());
    assertEquals(1399951989L * 1000L, trades.get(0).getTimestamp().getTime());
    assertEquals(new BigDecimal("0.085913"), trades.get(0).getTradableAmount());
    assertEquals(new BigDecimal("5819.8379"), trades.get(0).getPrice());

    assertEquals("1", trades.get(1).getId());
    assertEquals(1403143708L * 1000L, trades.get(1).getTimestamp().getTime());
    assertEquals(new BigDecimal("0.25"), trades.get(1).getTradableAmount());
    assertEquals(new BigDecimal("7895.1487"), trades.get(1).getPrice());
  }

  @Test
  public void testAdaptTicker() throws JsonParseException, JsonMappingException, IOException {
    MeXBTTicker meXBTTicker = new ObjectMapper().readValue(MeXBTTickerTest.class.getResource("ticker.json"), MeXBTTicker.class);
    Ticker ticker = MeXBTAdapters.adaptTicker(CurrencyPair.BTC_MXN, meXBTTicker);
    assertEquals(new BigDecimal("3939"), ticker.getHigh());
    assertEquals(new BigDecimal("3805.65"), ticker.getLow());
    assertEquals(new BigDecimal("3874.34"), ticker.getLast());
    assertEquals(new BigDecimal("3859.74"), ticker.getBid());
    assertEquals(new BigDecimal("3873.45"), ticker.getAsk());
    assertEquals(new BigDecimal("41.58967896"), ticker.getVolume());
  }

  @Test
  public void testAdaptWallet() throws JsonParseException, JsonMappingException, IOException {
    MeXBTBalanceResponse balanceResponse = new ObjectMapper().readValue(MeXBTBalanceResponseTest.class.getResource("balance.json"),
        MeXBTBalanceResponse.class);
    Wallet wallet = MeXBTAdapters.adaptWallet(balanceResponse);
    assertEquals(new BigDecimal("482198.87"), wallet.getBalance(Currency.BTC).getAvailable());
    assertEquals(new BigDecimal("482056"), wallet.getBalance(Currency.BTC).getFrozen());
    assertEquals(new BigDecimal("990119"), wallet.getBalance(Currency.LTC).getAvailable());
    assertEquals(new BigDecimal("11108"), wallet.getBalance(Currency.LTC).getFrozen());
  }

  @Test
  public void testAdaptOpenOrders() throws JsonParseException, JsonMappingException, IOException {
    MeXBTOpenOrdersResponse openOrdersResponse = new ObjectMapper().readValue(MeXBTOpenOrdersResponseTest.class.getResource("orders.json"),
        MeXBTOpenOrdersResponse.class);
    OpenOrders openOrders = MeXBTAdapters.adaptOpenOrders(openOrdersResponse);
    LimitOrder limitOrder = openOrders.getOpenOrders().get(0);
    assertEquals("63710062", limitOrder.getId());
    assertEquals(new BigDecimal("100"), limitOrder.getLimitPrice());
    assertEquals(new BigDecimal("1"), limitOrder.getTradableAmount());
    assertEquals(OrderType.BID, limitOrder.getType());
    assertEquals(1415353750631L, limitOrder.getTimestamp().getTime());
  }

  @Test
  public void testAdaptUserTrades() throws JsonParseException, JsonMappingException, IOException {
    MeXBTTradeResponse tradeResponse = new ObjectMapper().readValue(MeXBTUserTradeResponseTest.class.getResource("trades.json"),
        MeXBTTradeResponse.class);
    UserTrades userTrades = MeXBTAdapters.adaptUserTrades(tradeResponse);
    UserTrade t = userTrades.getUserTrades().get(0);
    assertEquals("0", t.getId());
    assertEquals(new BigDecimal("123"), t.getPrice());
    assertEquals(new BigDecimal("2"), t.getTradableAmount());
    assertEquals(1373142974499L, t.getTimestamp().getTime());
  }

}
