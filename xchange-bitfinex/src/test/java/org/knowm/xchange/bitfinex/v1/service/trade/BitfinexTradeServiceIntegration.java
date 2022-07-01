package org.knowm.xchange.bitfinex.v1.service.trade;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitfinex.BitfinexExchange;
import org.knowm.xchange.bitfinex.v1.service.BitfinexProperties;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.trade.params.CancelAllOrders;

public class BitfinexTradeServiceIntegration {

  private BitfinexProperties properties = new BitfinexProperties();
  private Exchange exchange;

  public BitfinexTradeServiceIntegration() throws IOException {}

  @Before
  public void setup() throws IOException {
    properties = new BitfinexProperties();
    Assume.assumeTrue("Ignore tests because credentials are missing", properties.isValid());

    exchange =
        ExchangeFactory.INSTANCE.createExchange(
            BitfinexExchange.class, properties.getApiKey(), properties.getSecretKey());
  }

  @After
  public void teardown() throws IOException {
    if (exchange != null) {
      exchange.getTradeService().cancelOrder(new CancelAllOrders() {});
    }
  }

  @Test
  public void placeLimitOrderAndChangeIt() throws Exception {

    LimitOrder limitOrder1 =
        new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.XRP_USD)
            .originalAmount(new BigDecimal("38.0"))
            .limitPrice(new BigDecimal("0.21"))
            .build();

    String orderId1 = exchange.getTradeService().placeLimitOrder(limitOrder1);
    assertThat(orderId1).isNotBlank();

    LimitOrder limitOrder2 =
        new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.XRP_USD)
            .limitPrice(new BigDecimal("0.22"))
            .id(orderId1)
            .build();

    String orderId2 = exchange.getTradeService().changeOrder(limitOrder2);
    assertThat(orderId2).isNotBlank();

    LimitOrder expectedOrder1 =
        new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.XRP_USD)
            .id(orderId1)
            .limitPrice(new BigDecimal("0.21"))
            .originalAmount(new BigDecimal("38.0"))
            .orderType(Order.OrderType.BID)
            .orderStatus(Order.OrderStatus.CANCELED)
            .build();

    Collection<Order> orders1 = exchange.getTradeService().getOrder(orderId1);
    assertThat(orders1).hasSize(1).element(0).isEqualToIgnoringNullFields(expectedOrder1);

    LimitOrder expectedOrder2 =
        new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.XRP_USD)
            .id(orderId2)
            .limitPrice(new BigDecimal("0.22"))
            .originalAmount(new BigDecimal("38.0"))
            .orderType(Order.OrderType.BID)
            .orderStatus(Order.OrderStatus.NEW)
            .build();

    Collection<Order> orders2 = exchange.getTradeService().getOrder(orderId2);
    assertThat(orders2).hasSize(1).element(0).isEqualToIgnoringNullFields(expectedOrder2);
  }
}
