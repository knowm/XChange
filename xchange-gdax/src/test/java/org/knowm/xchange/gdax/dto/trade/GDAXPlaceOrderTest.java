package org.knowm.xchange.gdax.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.gdax.GDAXAdapters;

public class GDAXPlaceOrderTest {

  @Test
  public void nullflagsTest() {
    LimitOrder limitOrder =
        new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD)
            .limitPrice(BigDecimal.ZERO)
            .build();

    GDAXPlaceLimitOrder orderFlagsNull = GDAXAdapters.adaptGDAXPlaceLimitOrder(limitOrder);
    assertThat(orderFlagsNull.getPostOnly()).isEqualTo(null);
    assertThat(orderFlagsNull.getTimeInForce()).isEqualTo(null);
  }

  @Test
  public void fillOrKillflagTest() {
    LimitOrder order =
        new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD)
            .limitPrice(BigDecimal.ZERO)
            .flag(GDAXOrderFlags.FILL_OR_KILL)
            .build();

    GDAXPlaceLimitOrder orderFOK = GDAXAdapters.adaptGDAXPlaceLimitOrder(order);

    assertThat(orderFOK.getPostOnly()).isEqualTo(null);
    assertThat(orderFOK.getTimeInForce()).isEqualTo(GDAXPlaceLimitOrder.TimeInForce.FOK);
  }

  @Test
  public void postOnlyflagTest() {
    LimitOrder order =
        new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD)
            .limitPrice(BigDecimal.ZERO)
            .flag(GDAXOrderFlags.POST_ONLY)
            .build();

    GDAXPlaceLimitOrder orderPostOnly = GDAXAdapters.adaptGDAXPlaceLimitOrder(order);

    assertThat(orderPostOnly.getPostOnly()).isEqualTo(Boolean.TRUE);
    assertThat(orderPostOnly.getTimeInForce()).isEqualTo(null);
  }

  @Test
  public void immediateOrCancelflagTest() {
    LimitOrder order =
        new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD)
            .limitPrice(BigDecimal.ZERO)
            .flag(GDAXOrderFlags.IMMEDIATE_OR_CANCEL)
            .build();

    GDAXPlaceLimitOrder orderIOC = GDAXAdapters.adaptGDAXPlaceLimitOrder(order);

    assertThat(orderIOC.getPostOnly()).isEqualTo(null);
    assertThat(orderIOC.getTimeInForce()).isEqualTo(GDAXPlaceLimitOrder.TimeInForce.IOC);
  }
}
