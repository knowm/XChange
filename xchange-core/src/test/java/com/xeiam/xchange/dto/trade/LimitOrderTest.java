package com.xeiam.xchange.dto.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.IOrderFlags;
import com.xeiam.xchange.dto.Order.OrderType;

public class LimitOrderTest {
  private enum TestFlags implements IOrderFlags {
    TEST1, TEST2, TEST3;
  }

  @Test
  public void testBuilder() {
    final OrderType type = OrderType.BID;
    final BigDecimal tradableAmount = new BigDecimal("99.401");
    final CurrencyPair currencyPair = CurrencyPair.LTC_BTC;
    final BigDecimal limitPrice = new BigDecimal("251.64");
    final Date timestamp = new Date();
    final String id = "id";

    final LimitOrder.Builder builder = (LimitOrder.Builder) new LimitOrder.Builder(type, currencyPair).tradableAmount(tradableAmount)
        .limitPrice(limitPrice).timestamp(timestamp).id(id).flag(TestFlags.TEST1);
    final LimitOrder copy = builder.build();

    assertThat(copy.getType()).isEqualTo(type);
    assertThat(copy.getTradableAmount()).isEqualTo(tradableAmount);
    assertThat(copy.getCurrencyPair()).isEqualTo(currencyPair);
    assertThat(copy.getLimitPrice()).isEqualTo(limitPrice);
    assertThat(copy.getTimestamp()).isEqualTo(timestamp);
    assertThat(copy.getId()).isEqualTo(id);
    assertThat(copy.getOrderFlags()).hasSize(1);
    assertThat(copy.getOrderFlags()).contains(TestFlags.TEST1);
  }

  @Test
  public void testBuilderFrom() {
    final OrderType type = OrderType.ASK;
    final BigDecimal tradableAmount = new BigDecimal("100.501");
    final CurrencyPair currencyPair = CurrencyPair.BTC_USD;
    final BigDecimal limitPrice = new BigDecimal("250.34");
    final Date timestamp = new Date();
    final String id = "id";

    final LimitOrder original = new LimitOrder(type, tradableAmount, currencyPair, id, timestamp, limitPrice);
    original.addOrderFlag(TestFlags.TEST1);
    original.addOrderFlag(TestFlags.TEST3);
    final LimitOrder copy = LimitOrder.Builder.from(original).build();

    assertThat(copy.getType()).isEqualTo(original.getType());
    assertThat(copy.getTradableAmount()).isEqualTo(original.getTradableAmount());
    assertThat(copy.getCurrencyPair()).isEqualTo(original.getCurrencyPair());
    assertThat(copy.getLimitPrice()).isEqualTo(original.getLimitPrice());
    assertThat(copy.getTimestamp()).isEqualTo(original.getTimestamp());
    assertThat(copy.getId()).isEqualTo(original.getId());
    assertThat(copy.getOrderFlags()).hasSize(2);
    assertThat(copy.getOrderFlags()).contains(TestFlags.TEST1);
    assertThat(copy.getOrderFlags()).contains(TestFlags.TEST3);
  }
}
