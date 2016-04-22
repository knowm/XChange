package org.knowm.xchange.btcchina.service.streaming;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import org.knowm.xchange.btcchina.dto.trade.streaming.BTCChinaBalance;
import org.knowm.xchange.btcchina.dto.trade.streaming.BTCChinaOrder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;

public class BTCChinaJSONObjectAdaptersTest {

  @Test
  public void testAdaptTicker() throws JSONException, IOException {

    JSONObject jsonObject = new JSONObject(IOUtils.toString(getClass().getResource("ticker.json"), Charsets.UTF_8));
    Ticker ticker = BTCChinaJSONObjectAdapters.adaptTicker(jsonObject);
    assertEquals(new BigDecimal("90604.96"), ticker.getVolume());
    assertEquals(new BigDecimal("25.96"), ticker.getLast());
    assertEquals(new BigDecimal("26.1"), ticker.getAsk());
    assertEquals(new BigDecimal("25.94"), ticker.getBid());
    assertEquals(new BigDecimal("27.41"), ticker.getHigh());
    assertEquals(1411924391000L, ticker.getTimestamp().getTime());
    assertEquals(new BigDecimal("25.8"), ticker.getLow());
    assertEquals(CurrencyPair.LTC_CNY, ticker.getCurrencyPair());
  }

  @Test
  public void testAdaptTrade() throws JSONException, IOException {

    JSONObject jsonObject = new JSONObject(IOUtils.toString(getClass().getResource("trade.json"), Charsets.UTF_8));
    Trade trade = BTCChinaJSONObjectAdapters.adaptTrade(jsonObject);
    assertEquals(new BigDecimal("0.79000000"), trade.getTradableAmount());
    assertEquals(CurrencyPair.BTC_CNY, trade.getCurrencyPair());
    assertEquals(new BigDecimal("2350.25"), trade.getPrice());
    assertEquals("8948160", trade.getId());
    assertEquals(1411924375000L, trade.getTimestamp().getTime());
    assertEquals(OrderType.ASK, trade.getType());
  }

  @Test
  public void testAdaptOrder() throws JSONException, IOException {

    JSONObject jsonObject = new JSONObject(IOUtils.toString(getClass().getResource("order.json"), Charsets.UTF_8));
    BTCChinaOrder order = BTCChinaJSONObjectAdapters.adaptOrder(jsonObject);
    assertEquals(new BigDecimal("0.01"), order.getTradableAmount());
    assertEquals("26821399", order.getId());
    assertEquals(new BigDecimal("3605.23"), order.getLimitPrice());
    assertEquals(CurrencyPair.BTC_CNY, order.getCurrencyPair());
    assertEquals(OrderStatus.CANCELED, order.getStatus());
    assertEquals(1411924393000L, order.getTimestamp().getTime());
    assertEquals(OrderType.ASK, order.getType());
    assertEquals(new BigDecimal("0.01"), order.getAmountOriginal());
  }

  @Test
  public void testAdaptBalanceBTC() throws JSONException, IOException {

    JSONObject jsonObject = new JSONObject(IOUtils.toString(getClass().getResource("account_info-balance-BTC.json"), Charsets.UTF_8));
    BTCChinaBalance balance = BTCChinaJSONObjectAdapters.adaptBalance(jsonObject);
    assertEquals(new BigDecimal("196441900"), balance.getAmountInteger());
    assertEquals(new BigDecimal("1.964419"), balance.getAmount());
    assertEquals("฿", balance.getSymbol());
    assertEquals(8, balance.getAmountDecimal());
    assertEquals("BTC", balance.getCurrency());
  }

  @Test
  public void testAdaptBalanceCNY() throws JSONException, IOException {

    JSONObject jsonObject = new JSONObject(IOUtils.toString(getClass().getResource("account_info-balance-CNY.json"), Charsets.UTF_8));
    BTCChinaBalance balance = BTCChinaJSONObjectAdapters.adaptBalance(jsonObject);
    assertEquals(new BigDecimal("4.2609492000000005E9"), balance.getAmountInteger());
    assertEquals(new BigDecimal("42.609492"), balance.getAmount());
    assertEquals("¥", balance.getSymbol());
    assertEquals(8, balance.getAmountDecimal());
    assertEquals("CNY", balance.getCurrency());
  }

  @Test
  public void testAdaptDepth() throws JSONException, IOException {

    JSONObject jsonObject = new JSONObject(IOUtils.toString(getClass().getResource("grouporder.json"), Charsets.UTF_8));
    OrderBook depth = BTCChinaJSONObjectAdapters.adaptDepth(jsonObject);

    // asks should be sorted ascending
    assertEquals(OrderType.ASK, depth.getAsks().get(0).getType());
    assertEquals(new BigDecimal("2403.37"), depth.getAsks().get(0).getLimitPrice());
    assertEquals(new BigDecimal("0.113"), depth.getAsks().get(0).getTradableAmount());

    assertEquals(OrderType.ASK, depth.getAsks().get(1).getType());
    assertEquals(new BigDecimal("2403.39"), depth.getAsks().get(1).getLimitPrice());
    assertEquals(new BigDecimal("4.9328"), depth.getAsks().get(1).getTradableAmount());

    // bids should be sorted descending
    assertEquals(OrderType.BID, depth.getBids().get(0).getType());
    assertEquals(new BigDecimal("2401.8"), depth.getBids().get(0).getLimitPrice());
    assertEquals(new BigDecimal("3.2666"), depth.getBids().get(0).getTradableAmount());

    assertEquals(OrderType.BID, depth.getBids().get(1).getType());
    assertEquals(new BigDecimal("2401.73"), depth.getBids().get(1).getLimitPrice());
    assertEquals(new BigDecimal("0.6024"), depth.getBids().get(1).getTradableAmount());
  }

}
