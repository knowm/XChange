package org.knowm.xchange.coinmate.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinmate.ExchangeUtils;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

/**
 * Integration tests for TradeService. For these tests to function, a file
 * 'exchangeConfiguration.json' must be on the classpath and contain valid api and secret keys.
 */
class TradeServiceIntegration {

  @Test
  void tradeHistoryTest() throws Exception {
    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange == null) {
      return; // forces pass if not configuration is available
    }
    assertThat(exchange).isNotNull();
    TradeService service = exchange.getTradeService();
    assertThat(service).isNotNull();
    UserTrades trades = service.getTradeHistory(service.createTradeHistoryParams());
    assertThat(trades).isNotNull();
    System.out.println("Got " + trades.getUserTrades().size() + " trades.");
    for (Trade trade : trades.getTrades()) {
      System.out.println(trade.toString());
    }
  }

  @Test
  void openOrdersTestNoPair() throws Exception {
    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange == null) {
      return; // forces pass if not configuration is available
    }
    assertThat(exchange).isNotNull();
    TradeService service = exchange.getTradeService();
    assertThat(service).isNotNull();
    OpenOrdersParams params = service.createOpenOrdersParams();
    assertThat(params).isNotNull();
    OpenOrders orders = service.getOpenOrders(params);
    assertThat(orders).isNotNull();
    System.out.println("Got " + orders.getOpenOrders().size() + " orders.");
    for (LimitOrder order : orders.getOpenOrders()) {
      System.out.println(order.toString());
    }
  }

  @Test
  void openOrdersTestBTC_CZK() throws Exception {
    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange == null) {
      return; // forces pass if not configuration is available
    }
    assertThat(exchange).isNotNull();
    TradeService service = exchange.getTradeService();
    assertThat(service).isNotNull();
    OpenOrdersParamCurrencyPair params =
        (OpenOrdersParamCurrencyPair) service.createOpenOrdersParams();
    assertThat(params).isNotNull();
    params.setCurrencyPair(CurrencyPair.BTC_CZK);
    OpenOrders orders = service.getOpenOrders(params);
    assertThat(orders).isNotNull();
    System.out.println("Got " + orders.getOpenOrders().size() + " orders.");
    for (LimitOrder order : orders.getOpenOrders()) {
      System.out.println(order.toString());
    }
  }

  /*
   * @Test public void marketBuyTest() throws Exception { Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration(); if (exchange ==
   * null) { return; // forces pass if not configuration is available } assertNotNull(exchange); TradeService service = exchange.getTradeService();
   * assertNotNull(service); String id = service.placeMarketOrder(new MarketOrder(Order.OrderType.BID, new BigDecimal("0.1"), CurrencyPair.BTC_EUR));
   * System.out.println("Market buy order id = " + id); }
   */

  @Test
  void marketSellTest() throws Exception {
    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange == null) {
      return; // forces pass if not configuration is available
    }
    assertThat(exchange).isNotNull();
    TradeService service = exchange.getTradeService();
    assertThat(service).isNotNull();
    service.placeMarketOrder(
        new MarketOrder(Order.OrderType.ASK, new BigDecimal("0.001"), CurrencyPair.BTC_EUR));
  }

  /*
   * @Test public void limitBuyTest() throws Exception { Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration(); if (exchange ==
   * null) { return; // forces pass if not configuration is available } assertNotNull(exchange); TradeService service = exchange.getTradeService();
   * assertNotNull(service); String id = service.placeLimitOrder(new LimitOrder(Order.OrderType.BID, new BigDecimal("1.0"), CurrencyPair.BTC_EUR,
   * null, null, new BigDecimal("1.0"))); System.out.println("Limit buy order id = " + id); }
   */

  /*
   * @Test public void limitSellTest() throws Exception { Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration(); if (exchange ==
   * null) { return; // forces pass if not configuration is available } assertNotNull(exchange); TradeService service = exchange.getTradeService();
   * assertNotNull(service); String id = service.placeLimitOrder(new LimitOrder(Order.OrderType.ASK, new BigDecimal("0.0"), CurrencyPair.BTC_EUR,
   * null, null, new BigDecimal("10000.0"))); System.out.println("Limit sell order id = " + id); }
   */

  /*
   * @Test public void cancelOrderTest() throws Exception { Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration(); if (exchange ==
   * null) { return; // forces pass if not configuration is available } assertNotNull(exchange); TradeService service = exchange.getTradeService();
   * assertNotNull(service); service.cancelOrder("784360"); }
   */

}
