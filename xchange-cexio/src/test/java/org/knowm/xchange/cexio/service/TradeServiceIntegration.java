package org.knowm.xchange.cexio.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

class TradeServiceIntegration {
  private CexIOTradeService tradeService;
  private LimitOrder order;

  @BeforeEach
  void setup() throws IOException {
    CexioProperties properties = new CexioProperties();

    if (!properties.isValid()) {
      Assumptions.assumeTrue(properties.isValid(), "Ignore tests because credentials are missing");
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
  void getOrderTransactionsTest() throws Exception {

    String orderId = tradeService.placeLimitOrder(order);

    tradeService.cancelOrder(orderId);

    Thread.sleep(2000);

    CexIOOrderWithTransactions orderWithTransactions = tradeService.getOrderTransactions(orderId);

    assertThat(orderWithTransactions.getId())
        .as("Order id from transaction, must equals requested order id")
        .isEqualTo(orderId);

    assertThat(order.getOriginalAmount().compareTo(orderWithTransactions.getAmount()))
        .as("Order amount from transaction, must equal sent order amount")
        .isEqualTo(0);

    assertThat(orderWithTransactions.getVtx().size() > 0)
        .withFailMessage("Transaction list must not be empty")
        .isTrue();
  }

  @Test
  void orderPlaceGetCancelTest() throws Exception {
    String orderId = tradeService.placeLimitOrder(order);

    tradeService.cancelOrder(orderId);

    List<Order> orders = (List<Order>) tradeService.getOrder(orderId);

    assertThat(orders.size()).as("Order response must contain 1 order").isEqualTo(1);
    assertThat(orders.get(0).getId())
        .as("Returned order id must be the same as placed")
        .isEqualTo(orderId);
    assertThat(orders.get(0).getStatus())
        .as("Returned order must be canceled")
        .isSameAs(Order.OrderStatus.CANCELED);
  }

  @Test
  void CancelOrderByCurrencyPair() throws Exception {
    String orderId = tradeService.placeLimitOrder(order);
    String orderId2 = tradeService.placeLimitOrder(order);

    tradeService.cancelOrder((CancelOrderByCurrencyPair) () -> new CurrencyPair("BCH/USD"));

    List<Order> orders = (List<Order>) tradeService.getOrder(orderId, orderId2);

    assertThat(orders.size()).as("Order response must contain 2 orders").isEqualTo(2);
    assertThat(orders.get(0).getId())
        .as("Returned order 1 id must be the same as placed")
        .isEqualTo(orderId);
    assertThat(orders.get(1).getId())
        .as("Returned order 2 id must be the same as placed")
        .isEqualTo(orderId2);
    assertThat(orders.get(0).getStatus())
        .as("Order 1 must be canceled")
        .isSameAs(Order.OrderStatus.CANCELED);
    assertThat(orders.get(1).getStatus())
        .as("Order 2 must be canceled")
        .isSameAs(Order.OrderStatus.CANCELED);
  }

  @Test
  void changeOrder() throws Exception {
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

    assertThat(orders.size()).as("Order response must contain 1 order").isEqualTo(3);
    assertThat(orders.get(0).getStatus())
        .as("Order 1 must be canceled")
        .isSameAs(Order.OrderStatus.CANCELED);
    assertThat(orders.get(1).getStatus())
        .as("Order 2 must be canceled")
        .isSameAs(Order.OrderStatus.CANCELED);
    assertThat(orders.get(2).getStatus())
        .as("Order 3 must be placed")
        .isSameAs(Order.OrderStatus.PENDING_NEW);
    assertThat(((LimitOrder) orders.get(2)).getLimitPrice().compareTo(endPrice))
        .as("Order 3 must have `endPrice` price")
        .isEqualTo(0);

    tradeService.cancelOrder((CancelOrderByCurrencyPair) () -> new CurrencyPair("BCH/USD"));
  }

  private LimitOrder buildOrder(
      Order.OrderType orderType, CurrencyPair pair, BigDecimal price, BigDecimal amount) {
    return new LimitOrder.Builder(orderType, pair).limitPrice(price).originalAmount(amount).build();
  }
}
