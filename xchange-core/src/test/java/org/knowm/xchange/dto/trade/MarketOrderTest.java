package org.knowm.xchange.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.utils.ObjectMapperHelper;

public class MarketOrderTest {
  @Test
  public void testBuilder() {
    final Order.OrderType type = Order.OrderType.BID;
    final BigDecimal originalAmount = new BigDecimal("99.401");
    final BigDecimal cumulativeAmount = new BigDecimal("44.401");
    final BigDecimal averagePrice = new BigDecimal("255.00");
    final CurrencyPair currencyPair = CurrencyPair.LTC_BTC;
    final BigDecimal fee = new BigDecimal("22.2");
    final String userReference = "123";
    final Date timestamp = new Date();
    final String id = "id";
    final Order.OrderStatus status = Order.OrderStatus.FILLED;

    final MarketOrder copy =
        new MarketOrder.Builder(type, currencyPair)
            .originalAmount(originalAmount)
            .averagePrice(averagePrice)
            .cumulativeAmount(cumulativeAmount)
            .orderStatus(status)
            .timestamp(timestamp)
            .id(id)
            .flag(TestFlags.TEST1)
            .fee(fee)
            .userReference(userReference)
            .build();

    assertThat(copy.getType()).isEqualTo(type);
    assertThat(copy.getOriginalAmount()).isEqualTo(originalAmount);
    assertThat(copy.getCumulativeAmount()).isEqualTo(cumulativeAmount);
    assertThat(copy.getAveragePrice()).isEqualTo(averagePrice);
    assertThat(copy.getCurrencyPair()).isEqualTo(currencyPair);
    assertThat(copy.getTimestamp()).isEqualTo(timestamp);
    assertThat(copy.getId()).isEqualTo(id);
    assertThat(copy.getOrderFlags()).hasSize(1);
    assertThat(copy.getOrderFlags()).containsExactly(TestFlags.TEST1);
    assertThat(copy.hasFlag(TestFlags.TEST1));
    assertThat(copy.getStatus()).isEqualTo(status);
    assertThat(copy.getFee()).isEqualTo(fee);
    assertThat(copy.getUserReference()).isEqualTo(userReference);
  }

  @Test
  public void testBuilderFrom() throws IOException {
    final Order.OrderType type = Order.OrderType.ASK;
    final BigDecimal originalAmount = new BigDecimal("100.501");
    final BigDecimal cumulativeAmount = new BigDecimal("44.401");
    final BigDecimal averagePrice = new BigDecimal("255.00");
    final CurrencyPair currencyPair = CurrencyPair.BTC_USD;
    final BigDecimal fee = new BigDecimal("22.2");
    final Date timestamp = new Date();
    final String id = "id";
    final Order.OrderStatus status = Order.OrderStatus.FILLED;

    final MarketOrder original =
        new MarketOrder(
            type,
            originalAmount,
            currencyPair,
            id,
            timestamp,
            averagePrice,
            cumulativeAmount,
            fee,
            status);
    original.addOrderFlag(TestFlags.TEST1);
    original.addOrderFlag(TestFlags.TEST3);
    final MarketOrder copy = MarketOrder.Builder.from(original).build();

    assertThat(copy).isEqualToComparingFieldByField(original);
  }

  @Test
  public void testSerializeDeserialize() throws IOException {
    final Order.OrderType type = Order.OrderType.ASK;
    final BigDecimal originalAmount = new BigDecimal("100.501");
    final BigDecimal cumulativeAmount = new BigDecimal("44.401");
    final BigDecimal averagePrice = new BigDecimal("255.00");
    final CurrencyPair currencyPair = CurrencyPair.BTC_USD;
    final BigDecimal fee = new BigDecimal("22.2");
    final Date timestamp = new Date();
    final String id = "id";
    final Order.OrderStatus status = Order.OrderStatus.FILLED;

    final MarketOrder original =
        new MarketOrder(
            type,
            originalAmount,
            currencyPair,
            id,
            timestamp,
            averagePrice,
            cumulativeAmount,
            fee,
            status);
    original.addOrderFlag(TestFlags.TEST1);
    original.addOrderFlag(TestFlags.TEST3);

    MarketOrder jsonCopy = ObjectMapperHelper.viaJSON(original);
    assertThat(jsonCopy).isEqualToComparingFieldByField(original);
  }

  private enum TestFlags implements Order.IOrderFlags {
    TEST1,
    TEST2,
    TEST3
  }
}
