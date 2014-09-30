package com.xeiam.xchange.btcchina.service.streaming;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.xeiam.xchange.btcchina.dto.trade.BTCChinaOrderStatus;
import com.xeiam.xchange.btcchina.dto.trade.streaming.BTCChinaOrder;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;

public class BTCChinaJSONObjectAdaptersTest {

  @Test
  public void testAdaptTicker() throws JSONException, IOException {

    JSONObject jsonObject = new JSONObject(IOUtils.toString(getClass().getResource("ticker.json")));
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

    JSONObject jsonObject = new JSONObject(IOUtils.toString(getClass().getResource("trade.json")));
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

    JSONObject jsonObject = new JSONObject(IOUtils.toString(getClass().getResource("order.json")));
    BTCChinaOrder order = BTCChinaJSONObjectAdapters.adaptOrder(jsonObject);
    assertEquals(new BigDecimal("0.01"), order.getTradableAmount());
    assertEquals("26821399", order.getId());
    assertEquals(new BigDecimal("3605.23"), order.getLimitPrice());
    assertEquals(CurrencyPair.BTC_CNY, order.getCurrencyPair());
    assertEquals(BTCChinaOrderStatus.CANCELLED, order.getStatus());
    assertEquals(1411924393000L, order.getTimestamp().getTime());
    assertEquals(OrderType.ASK, order.getType());
    assertEquals(new BigDecimal("0.01"), order.getAmountOriginal());
  }

}
