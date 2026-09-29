package org.knowm.xchange.hitbtc.v2.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.hitbtc.v2.BaseAuthenticatedServiceTest;
import org.knowm.xchange.hitbtc.v2.HitbtcAdapters;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcException;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test ignored in default build because it requires production authentication credentials. See
 * {@link BaseAuthenticatedServiceTest}.
 */
@Disabled
class HitbtcTradeServiceRawIntegration extends BaseAuthenticatedServiceTest {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(HitbtcTradeServiceRawIntegration.class);
  private HitbtcTradeServiceRaw service = (HitbtcTradeServiceRaw) exchange.getTradeService();
  private SecureRandom secureRandom = new SecureRandom();

  @Test
  void listOrders() throws Exception {

    List<HitbtcOrder> orderList = service.getOpenOrdersRaw();

    assertThat(orderList.isEmpty()).isTrue();
  }

  @Test
  void placeLimitOrderRaw() {

    Date date = new Date();
    String id = date.toString().replace(" ", "");
    LOGGER.info("Placing order id : " + id);

    BigDecimal limitPrice = new BigDecimal("1.00");

    LimitOrder limitOrder =
        new LimitOrder(
            Order.OrderType.BID,
            new BigDecimal("0.01"),
            CurrencyPair.BTC_USD,
            id,
            new Date(),
            limitPrice);

    Throwable exception =
        assertThatExceptionOfType(HitbtcException.class)
            .isThrownBy(() -> service.placeLimitOrderRaw(limitOrder))
            .actual();
    assertThat(exception.getMessage()).contains("Insufficient funds");
  }

  @Test
  void placeMarketOrderRaw() {

    Date date = new Date();
    String id = date.toString().replace(" ", "");
    LOGGER.info("Placing order id : " + id);

    Throwable exception =
        assertThatExceptionOfType(HitbtcException.class)
            .isThrownBy(
                () -> {
                  MarketOrder limitOrder =
                      new MarketOrder(
                          Order.OrderType.BID,
                          new BigDecimal("0.01"),
                          CurrencyPair.BTC_USD,
                          id,
                          new Date());

                  service.placeMarketOrderRaw(limitOrder);
                })
            .actual();
    assertThat(exception.getMessage()).contains("Insufficient funds");
  }

  @Test
  void updateOrderNoPrice() throws Exception {

    String orderId = String.valueOf(secureRandom.nextInt());
    BigDecimal askingPrice = new BigDecimal("0.05");

    LimitOrder limitOrder =
        new LimitOrder(
            Order.OrderType.ASK,
            new BigDecimal("0.01"),
            CurrencyPair.ETH_BTC,
            orderId,
            null,
            askingPrice);

    HitbtcOrder hitbtcOrder = null;

    try {
      hitbtcOrder = service.placeLimitOrderRaw(limitOrder);
      assertThat(hitbtcOrder).isNotNull();

      hitbtcOrder =
          service.updateMarketOrderRaw(
              hitbtcOrder.clientOrderId, new BigDecimal("0.02"), "", Optional.empty());
    } finally {
      if (hitbtcOrder != null) {
        service.cancelOrderRaw(hitbtcOrder.clientOrderId);
      }
    }
  }

  @Test
  void updateOrderWithPrice() throws Exception {

    String orderId = String.valueOf(secureRandom.nextInt());
    BigDecimal askingPrice = new BigDecimal("0.05");

    LimitOrder limitOrder =
        new LimitOrder(
            Order.OrderType.ASK,
            new BigDecimal("0.01"),
            CurrencyPair.ETH_BTC,
            orderId,
            null,
            askingPrice);

    HitbtcOrder hitbtcOrder = null;

    try {
      hitbtcOrder = service.placeLimitOrderRaw(limitOrder);
      assertThat(hitbtcOrder).isNotNull();

      Optional<BigDecimal> newPrice = Optional.of(new BigDecimal("0.051"));

      hitbtcOrder =
          service.updateMarketOrderRaw(
              hitbtcOrder.clientOrderId, new BigDecimal("0.02"), "", newPrice);
    } finally {
      if (hitbtcOrder != null) {
        service.cancelOrderRaw(hitbtcOrder.clientOrderId);
      }
    }
  }

  @Test
  void cancelOrderWrongOrder() {

    Throwable exception =
        assertThatExceptionOfType(HitbtcException.class)
            .isThrownBy(() -> service.cancelOrderRaw("WRONG"))
            .actual();
    assertThat(exception.getMessage()).contains("Order not found");
  }

  @Test
  void cancelAllOrders() throws Exception {

    service.cancelAllOrdersRaw(HitbtcAdapters.adaptCurrencyPair(CurrencyPair.BTC_USD));
  }
}
