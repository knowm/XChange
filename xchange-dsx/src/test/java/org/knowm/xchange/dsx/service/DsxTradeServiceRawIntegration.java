package org.knowm.xchange.dsx.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dsx.BaseAuthenticatedServiceTest;
import org.knowm.xchange.dsx.DsxAdapters;
import org.knowm.xchange.dsx.dto.DsxException;
import org.knowm.xchange.dsx.dto.DsxOrder;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test ignored in default build because it requires production authentication credentials. See
 * {@link BaseAuthenticatedServiceTest}.
 */
@Ignore
public class DsxTradeServiceRawIntegration extends BaseAuthenticatedServiceTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(DsxTradeServiceRawIntegration.class);
  @Rule public final ExpectedException exception = ExpectedException.none();
  private DsxTradeServiceRaw service = (DsxTradeServiceRaw) exchange.getTradeService();
  private SecureRandom secureRandom = new SecureRandom();

  @Test
  public void testListOrders() throws IOException {

    List<DsxOrder> orderList = service.getOpenOrdersRaw();

    Assert.assertTrue(orderList.isEmpty());
  }

  @Test
  public void testPlaceLimitOrderRaw() throws IOException {

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

    exception.expect(DsxException.class);
    exception.expectMessage("Insufficient funds");
    service.placeLimitOrderRaw(limitOrder);
  }

  @Test
  public void testPlaceMarketOrderRaw() throws IOException {

    Date date = new Date();
    String id = date.toString().replace(" ", "");
    LOGGER.info("Placing order id : " + id);

    exception.expect(DsxException.class);
    exception.expectMessage("Insufficient funds");
    MarketOrder limitOrder =
        new MarketOrder(
            Order.OrderType.BID, new BigDecimal("0.01"), CurrencyPair.BTC_USD, id, new Date());

    service.placeMarketOrderRaw(limitOrder);
  }

  @Test
  public void testUpdateOrder_noPrice() throws IOException {

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

    DsxOrder dsxOrder = null;

    try {
      dsxOrder = service.placeLimitOrderRaw(limitOrder);
      assertThat(dsxOrder).isNotNull();

      dsxOrder =
          service.updateMarketOrderRaw(
              dsxOrder.clientOrderId, new BigDecimal("0.02"), "", Optional.empty());
    } finally {
      if (dsxOrder != null) {
        service.cancelOrderRaw(dsxOrder.clientOrderId);
      }
    }
  }

  @Test
  public void testUpdateOrder_withPrice() throws IOException {

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

    DsxOrder dsxOrder = null;

    try {
      dsxOrder = service.placeLimitOrderRaw(limitOrder);
      assertThat(dsxOrder).isNotNull();

      Optional<BigDecimal> newPrice = Optional.of(new BigDecimal("0.051"));

      dsxOrder =
          service.updateMarketOrderRaw(
              dsxOrder.clientOrderId, new BigDecimal("0.02"), "", newPrice);
    } finally {
      if (dsxOrder != null) {
        service.cancelOrderRaw(dsxOrder.clientOrderId);
      }
    }
  }

  @Test
  public void testCancelOrder_wrongOrder() throws IOException {

    exception.expect(DsxException.class);
    exception.expectMessage("Order not found");

    service.cancelOrderRaw("WRONG");
  }

  @Test
  public void testCancelAllOrders() throws IOException {

    service.cancelAllOrdersRaw(DsxAdapters.adaptCurrencyPair(CurrencyPair.BTC_USD));
  }
}
