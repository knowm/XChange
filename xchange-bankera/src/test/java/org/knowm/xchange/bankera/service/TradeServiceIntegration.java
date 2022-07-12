package org.knowm.xchange.bankera.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bankera.ExchangeUtils;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.DefaultCancelOrderParamId;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TradeServiceIntegration {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private static String orderId;
  private static TradeService tradeService;

  @BeforeClass
  public static void init() {
    Exchange exchange = ExchangeUtils.createExchangeFromProperties();
    tradeService = exchange.getTradeService();
  }

  @Test
  public void testAcreateLimitOrderTest() throws Exception {

    LimitOrder limitOrder =
        new LimitOrder(
            Order.OrderType.BID,
            BigDecimal.valueOf(0.01),
            CurrencyPair.ETH_BTC,
            "",
            new Date(),
            BigDecimal.valueOf(0.000001));
    String createdOrderId = tradeService.placeLimitOrder(limitOrder);
    Assert.assertNotNull(createdOrderId);
    orderId = createdOrderId;
    logger.info("Response: {}", orderId);
  }

  @Test
  public void testBcreateMarketOrderTest() throws Exception {

    MarketOrder marketOrder =
        new MarketOrder(
            Order.OrderType.ASK, BigDecimal.valueOf(0.01), CurrencyPair.ETH_BTC, "", new Date());

    Assert.assertNotNull(tradeService.placeMarketOrder(marketOrder));
  }

  @Test
  public void testCgetOpenOrdersByMarketTest() throws Exception {

    DefaultOpenOrdersParamCurrencyPair currencyPair = new DefaultOpenOrdersParamCurrencyPair();
    currencyPair.setCurrencyPair(CurrencyPair.ETH_BTC);
    OpenOrders openOrders = tradeService.getOpenOrders(currencyPair);
    logger.info("Response: {}", openOrders);
  }

  @Test
  public void testDgetAllOpenOrdersTest() throws Exception {

    OpenOrders openOrders = tradeService.getOpenOrders();
    logger.info("Response: {}", openOrders);
  }

  @Test
  public void testFcancelOrderTest() throws Exception {

    Assert.assertTrue(tradeService.cancelOrder(this.orderId));
  }

  @Test(expected = NotYetImplementedForExchangeException.class)
  public void testGcancelAllOrdersTest() throws Exception {

    Assert.assertTrue(tradeService.cancelOrder(new DefaultCancelOrderParamId()));
  }

  @Test
  public void testEgetUserOrder() throws Exception {

    Collection<Order> orders = tradeService.getOrder(this.orderId);
    logger.info("Response: {}", orders);
  }

  @Test
  public void testHgetUserTrades() throws Exception {

    DefaultTradeHistoryParamCurrencyPair currencyPair = new DefaultTradeHistoryParamCurrencyPair();
    currencyPair.setCurrencyPair(CurrencyPair.ETH_BTC);
    UserTrades trades = tradeService.getTradeHistory(currencyPair);
    logger.info("Response: {}", trades.userTrades());
  }
}
