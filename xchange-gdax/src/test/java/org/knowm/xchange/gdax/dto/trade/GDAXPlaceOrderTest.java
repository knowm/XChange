package org.knowm.xchange.gdax.dto.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.knowm.xchange.dto.Order.IOrderFlags;

public class GDAXPlaceOrderTest {

  @Test
  public void flagTest() {
    GDAXPlaceOrder orderFlagsNull = new GDAXPlaceOrder(BigDecimal.ZERO, BigDecimal.ZERO, "side", "productId", "type", null);
    assertThat(orderFlagsNull.isPostOnly()).isEqualTo(null);
    assertThat(orderFlagsNull.getTimeInForce()).isEqualTo(null);

    Set<IOrderFlags> flags = new HashSet<>();

    GDAXPlaceOrder order = new GDAXPlaceOrder(BigDecimal.ZERO, BigDecimal.ZERO, "side", "productId", "type", flags);
    assertThat(order.isPostOnly()).isEqualTo(null);
    assertThat(order.getTimeInForce()).isEqualTo(null);

    flags.add(GDAXOrderFlags.FILL_OR_KILL);
    assertThat(order.isPostOnly()).isEqualTo(null);
    assertThat(order.getTimeInForce()).isEqualTo("FOK");

    flags.add(GDAXOrderFlags.POST_ONLY);
    assertThat(order.isPostOnly()).isEqualTo(Boolean.TRUE);
    assertThat(order.getTimeInForce()).isEqualTo("FOK");

    flags.clear();
    flags.add(GDAXOrderFlags.IMMEDIATE_OR_CANCEL);
    assertThat(order.isPostOnly()).isEqualTo(null);
    assertThat(order.getTimeInForce()).isEqualTo("IOC");
  }
}
