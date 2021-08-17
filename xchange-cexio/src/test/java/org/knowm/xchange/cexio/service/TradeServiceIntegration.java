package org.knowm.xchange.cexio.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cexio.CexIOExchange;
import org.knowm.xchange.cexio.CexioProperties;
import org.knowm.xchange.cexio.dto.trade.CexIOOrderWithTransactions;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;

public class TradeServiceIntegration {
  private CexIOTradeService tradeService;
  private LimitOrder order;

  @Before
  public void setup() throws IOException {
    CexioProperties properties = new CexioProperties();

    if (!properties.isValid()) {
      Assume.assumeTrue("Ignore tests because credentials are missing", properties.isValid());
      return;
    }

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CexIOExchange.class);

    ExchangeSpecification specification = exchange.getDefaultExchangeSpecification();
    specification.setApiKey(properties.getApiKey());
    specification.setSecretKey(properties.getSecretKey());
    specification.setUserName(properties.getUserName());

    exchange.applySpecification(specification);

    tradeService = (CexIOTradeService) exchange.getTradeService();

    order =
        buildOrder(
            Order.OrderType.BID,
            CurrencyPair.BCH_USD,
            BigDecimal.valueOf(300),
            BigDecimal.valueOf(0.02));
  }

  @Test
  public void getOrderTransactionsTest() throws IOException, InterruptedException {

    String orderId = tradeService.placeLimitOrder(order);

    tradeService.cancelOrder(orderId);

    Thread.sleep(2000);

    CexIOOrderWithTransactions orderWithTransactions = tradeService.getOrderTransactions(orderId);

    Assert.assertEquals(
        "Order id from transaction, must equals requested order id",
        orderId,
        orderWithTransactions.getId());

    Assert.assertEquals(
        "Order amount from transaction, must equal sent order amount",
        0,
        order.getOriginalAmount().compareTo(orderWithTransactions.getAmount()));

    Assert.assertTrue(
        "Transaction list must not be empty", orderWithTransactions.getVtx().size() > 0);
  }

  @Test
  public void orderPlaceGetCancelTest() throws IOException, InterruptedException {
    String orderId = tradeService.placeLimitOrder(order);

    tradeService.cancelOrder(orderId);

    List<Order> orders = (List<Order>) tradeService.getOrder(orderId);

    Assert.assertEquals("Order response must contain 1 order", 1, orders.size());
    Assert.assertEquals(
        "Returned order id must be the same as placed", orderId, orders.get(0).getId());
    Assert.assertSame(
        "Returned order must be canceled", orders.get(0).getStatus(), Order.OrderStatus.CANCELED);
  }

  @Test
  public void CancelOrderByCurrencyPair() throws IOException, InterruptedException {
    String orderId = tradeService.placeLimitOrder(order);
    String orderId2 = tradeService.placeLimitOrder(order);

    tradeService.cancelOrder((CancelOrderByCurrencyPair) () -> new CurrencyPair("BCH/USD"));

    List<Order> orders = (List<Order>) tradeService.getOrder(orderId, orderId2);

    Assert.assertEquals("Order response must contain 2 orders", 2, orders.size());
    Assert.assertEquals(
        "Returned order 1 id must be the same as placed", orderId, orders.get(0).getId());
    Assert.assertEquals(
        "Returned order 2 id must be the same as placed", orderId2, orders.get(1).getId());
    Assert.assertSame(
        "Order 1 must be canceled", orders.get(0).getStatus(), Order.OrderStatus.CANCELED);
    Assert.assertSame(
        "Order 2 must be canceled", orders.get(1).getStatus(), Order.OrderStatus.CANCELED);
  }

  @Test
  public void changeOrder() throws IOException {
    BigDecimal modifyPrice = new BigDecimal(302);
    BigDecimal endPrice = new BigDecimal(304);

    String orderId = tradeService.placeLimitOrder(order);

    LimitOrder order2 =
        new LimitOrder(
            order.getType(),
            order.getOriginalAmount(),
            order.getCurrencyPair(),
            orderId,
            order.getTimestamp(),
            modifyPrice);
    String orderId2 = tradeService.changeOrder(order2);

    LimitOrder order3 =
        new LimitOrder(
            order.getType(),
            order.getOriginalAmount(),
            order.getCurrencyPair(),
            orderId2,
            order.getTimestamp(),
            endPrice);
    String orderId3 = tradeService.changeOrder(order3);

    List<Order> orders = (List<Order>) tradeService.getOrder(orderId, orderId2, orderId3);

    Assert.assertEquals("Order response must contain 1 order", 3, orders.size());
    Assert.assertSame(
        "Order 1 must be canceled", orders.get(0).getStatus(), Order.OrderStatus.CANCELED);
    Assert.assertSame(
        "Order 2 must be canceled", orders.get(1).getStatus(), Order.OrderStatus.CANCELED);
    Assert.assertSame(
        "Order 3 must be placed", orders.get(2).getStatus(), Order.OrderStatus.PENDING_NEW);
    Assert.assertEquals(
        "Order 3 must have `endPrice` price",
        0,
        ((LimitOrder) orders.get(2)).getLimitPrice().compareTo(endPrice));

    tradeService.cancelOrder((CancelOrderByCurrencyPair) () -> new CurrencyPair("BCH/USD"));
  }

  private LimitOrder buildOrder(
      Order.OrderType orderType, CurrencyPair pair, BigDecimal price, BigDecimal amount) {
    return new LimitOrder.Builder(orderType, pair).limitPrice(price).originalAmount(amount).build();
  }
}
