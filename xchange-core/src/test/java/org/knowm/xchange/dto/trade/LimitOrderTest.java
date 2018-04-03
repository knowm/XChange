package org.knowm.xchange.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Date;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.IOrderFlags;
import org.knowm.xchange.dto.Order.OrderType;

public class LimitOrderTest {
  @Test
  public void testBuilder() {
    final OrderType type = OrderType.BID;
    final BigDecimal originalAmount = new BigDecimal("99.401");
    final CurrencyPair currencyPair = CurrencyPair.LTC_BTC;
    final BigDecimal limitPrice = new BigDecimal("251.64");
    final Date timestamp = new Date();
    final String id = "id";
    final Order.OrderStatus status = Order.OrderStatus.FILLED;

    final LimitOrder copy =
        new LimitOrder.Builder(type, currencyPair)
            .originalAmount(originalAmount)
            .limitPrice(limitPrice)
            .orderStatus(status)
            .timestamp(timestamp)
            .id(id)
            .flag(TestFlags.TEST1)
            .build();

    assertThat(copy.getType()).isEqualTo(type);
    assertThat(copy.getOriginalAmount()).isEqualTo(originalAmount);
    assertThat(copy.getCurrencyPair()).isEqualTo(currencyPair);
    assertThat(copy.getLimitPrice()).isEqualTo(limitPrice);
    assertThat(copy.getTimestamp()).isEqualTo(timestamp);
    assertThat(copy.getId()).isEqualTo(id);
    assertThat(copy.getOrderFlags()).hasSize(1);
    assertThat(copy.getOrderFlags()).contains(TestFlags.TEST1);
    assertThat(copy.hasFlag(TestFlags.TEST1));
    assertThat(copy.getStatus()).isEqualTo(status);
  }

  @Test
  public void testBuilderFrom() {
    final OrderType type = OrderType.ASK;
    final BigDecimal originalAmount = new BigDecimal("100.501");
    final CurrencyPair currencyPair = CurrencyPair.BTC_USD;
    final BigDecimal limitPrice = new BigDecimal("250.34");
    final Date timestamp = new Date();
    final String id = "id";
    final Order.OrderStatus status = Order.OrderStatus.FILLED;

    final LimitOrder original =
        new LimitOrder(type, originalAmount, currencyPair, id, timestamp, limitPrice);
    original.addOrderFlag(TestFlags.TEST1);
    original.addOrderFlag(TestFlags.TEST3);
    original.setOrderStatus(status);
    final LimitOrder copy = LimitOrder.Builder.from(original).build();

    assertThat(copy.getType()).isEqualTo(original.getType());
    assertThat(copy.getOriginalAmount()).isEqualTo(original.getOriginalAmount());
    assertThat(copy.getCurrencyPair()).isEqualTo(original.getCurrencyPair());
    assertThat(copy.getLimitPrice()).isEqualTo(original.getLimitPrice());
    assertThat(copy.getTimestamp()).isEqualTo(original.getTimestamp());
    assertThat(copy.getId()).isEqualTo(original.getId());
    assertThat(copy.getOrderFlags()).hasSize(2);
    assertThat(copy.getOrderFlags()).contains(TestFlags.TEST1);
    assertThat(copy.hasFlag(TestFlags.TEST1));
    assertThat(copy.getOrderFlags()).contains(TestFlags.TEST3);
    assertThat(copy.hasFlag(TestFlags.TEST3));
    assertThat(copy.getStatus()).isEqualTo(status);
  }

  @Test
  public void testCompareTo() {
    // bid@1
    LimitOrder bid1 =
        new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD)
            .limitPrice(new BigDecimal("1"))
            .build();
    LimitOrder anotherBid1 =
        new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD)
            .limitPrice(new BigDecimal("1"))
            .build();
    assertEquals(0, bid1.compareTo(anotherBid1));
    assertEquals(0, anotherBid1.compareTo(bid1));

    // bid@2
    LimitOrder bid2 =
        new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD)
            .limitPrice(new BigDecimal("2"))
            .build();

    // Sorted: bid@2, bid@1
    assertEquals(-1, bid2.compareTo(bid1));
    assertEquals(1, bid1.compareTo(bid2));

    // ask@3
    LimitOrder ask3 =
        new LimitOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USD)
            .limitPrice(new BigDecimal("3"))
            .build();
    LimitOrder anotherAsk3 =
        new LimitOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USD)
            .limitPrice(new BigDecimal("3"))
            .build();
    assertEquals(0, ask3.compareTo(anotherAsk3));
    assertEquals(0, anotherAsk3.compareTo(ask3));

    // ask@4
    LimitOrder ask4 =
        new LimitOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USD)
            .limitPrice(new BigDecimal("4"))
            .build();

    // Sorted: ask@3, ask@4
    assertEquals(-1, ask3.compareTo(ask4));
    assertEquals(1, ask4.compareTo(ask3));

    // Sorted: bid@2, bid@1, ask@3, ask@4
    assertEquals(-1, bid1.compareTo(ask3));
    assertEquals(1, ask3.compareTo(bid1));

    // ask@1
    LimitOrder ask1 =
        new LimitOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USD)
            .limitPrice(new BigDecimal("1"))
            .build();

    // Sorted: bid@1, ask@1
    assertEquals(-1, bid1.compareTo(ask1));
    assertEquals(1, ask1.compareTo(bid1));

    // Sorted: bid@2, ask@1
    assertEquals(-1, bid2.compareTo(ask1));
    assertEquals(1, ask1.compareTo(bid2));
  }

  private enum TestFlags implements IOrderFlags {
    TEST1,
    TEST2,
    TEST3
  }
}
