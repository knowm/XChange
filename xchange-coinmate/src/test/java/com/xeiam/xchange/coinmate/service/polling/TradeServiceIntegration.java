package com.xeiam.xchange.coinmate.service.polling;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinmate.ExchangeUtils;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

/**
 * Integration tests for TradeService. For these tests to function, a file 'exchangeConfiguration.json' must be on the classpath and contain valid api
 * and secret keys.
 */
public class TradeServiceIntegration {

  @Test
  public void transactionHistoryTest() throws Exception {
    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange == null) {
      return; // forces pass if not configuration is available
    }
    assertNotNull(exchange);
    PollingTradeService service = exchange.getPollingTradeService();
    assertNotNull(service);
    UserTrades trades = service.getTradeHistory();
    assertNotNull(trades);
    System.out.println("Got " + trades.getUserTrades().size() + " trades.");
    for (Trade trade : trades.getTrades()) {
      System.out.println(trade.toString());
    }
  }

  @Test
  public void openOrdersTest() throws Exception {
    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange == null) {
      return; // forces pass if not configuration is available
    }
    assertNotNull(exchange);
    PollingTradeService service = exchange.getPollingTradeService();
    assertNotNull(service);
    OpenOrders orders = service.getOpenOrders();
    assertNotNull(orders);
    System.out.println("Got " + orders.getOpenOrders().size() + " orders.");
    for (LimitOrder order : orders.getOpenOrders()) {
      System.out.println(order.toString());
    }
  }

  /*
   * @Test public void marketBuyTest() throws Exception { Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration(); if (exchange ==
   * null) { return; // forces pass if not configuration is available } assertNotNull(exchange); PollingTradeService service =
   * exchange.getPollingTradeService(); assertNotNull(service); String id = service.placeMarketOrder(new MarketOrder(Order.OrderType.BID, new
   * BigDecimal("0.1"), CurrencyPair.BTC_EUR)); System.out.println("Market buy order id = " + id); }
   */

  @Test
  public void marketSellTest() throws Exception {
    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange == null) {
      return; // forces pass if not configuration is available
    }
    assertNotNull(exchange);
    PollingTradeService service = exchange.getPollingTradeService();
    assertNotNull(service);
    service.placeMarketOrder(new MarketOrder(Order.OrderType.ASK, new BigDecimal("0.001"), CurrencyPair.BTC_EUR));
  }

  /*
   * @Test public void limitBuyTest() throws Exception { Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration(); if (exchange ==
   * null) { return; // forces pass if not configuration is available } assertNotNull(exchange); PollingTradeService service =
   * exchange.getPollingTradeService(); assertNotNull(service); String id = service.placeLimitOrder(new LimitOrder(Order.OrderType.BID, new
   * BigDecimal("1.0"), CurrencyPair.BTC_EUR, null, null, new BigDecimal("1.0"))); System.out.println("Limit buy order id = " + id); }
   */

  /*
   * @Test public void limitSellTest() throws Exception { Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration(); if (exchange ==
   * null) { return; // forces pass if not configuration is available } assertNotNull(exchange); PollingTradeService service =
   * exchange.getPollingTradeService(); assertNotNull(service); String id = service.placeLimitOrder(new LimitOrder(Order.OrderType.ASK, new
   * BigDecimal("0.0"), CurrencyPair.BTC_EUR, null, null, new BigDecimal("10000.0"))); System.out.println("Limit sell order id = " + id); }
   */

  /*
   * @Test public void cancelOrderTest() throws Exception { Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration(); if (exchange ==
   * null) { return; // forces pass if not configuration is available } assertNotNull(exchange); PollingTradeService service =
   * exchange.getPollingTradeService(); assertNotNull(service); service.cancelOrder("784360"); }
   */

}
