package org.knowm.xchange.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.utils.ObjectMapperHelper;

public class StopOrderTest {
  @Test
  public void testBuilder() {
    final Order.OrderType type = Order.OrderType.BID;
    final BigDecimal originalAmount = new BigDecimal("99.401");
    final BigDecimal averagePrice = new BigDecimal("255.00");
    final BigDecimal cumulativeAmount = new BigDecimal("0.00");
    final CurrencyPair currencyPair = CurrencyPair.LTC_BTC;
    final BigDecimal limitPrice = new BigDecimal("251.64");
    final BigDecimal stopPrice = new BigDecimal("266.21");
    final BigDecimal fee = new BigDecimal("22.2");
    final Date timestamp = new Date();
    final String id = "id";
    final Order.OrderStatus status = Order.OrderStatus.FILLED;
    final String reference = "reference";
    final StopOrder.Intention intention = StopOrder.Intention.TAKE_PROFIT;

    final StopOrder copy =
        new StopOrder.Builder(type, currencyPair)
            .originalAmount(originalAmount)
            .averagePrice(averagePrice)
            .cumulativeAmount(cumulativeAmount)
            .limitPrice(limitPrice)
            .stopPrice(stopPrice)
            .orderStatus(status)
            .timestamp(timestamp)
            .id(id)
            .flag(TestFlags.TEST1)
            .fee(fee)
            .userReference(reference)
            .intention(intention)
            .build();

    assertThat(copy.getType()).isEqualTo(type);
    assertThat(copy.getOriginalAmount()).isEqualTo(originalAmount);
    assertThat(copy.getAveragePrice()).isEqualTo(averagePrice);
    assertThat(copy.getCumulativeAmount()).isEqualTo(cumulativeAmount);
    assertThat(copy.getCurrencyPair()).isEqualTo(currencyPair);
    assertThat(copy.getStopPrice()).isEqualTo(stopPrice);
    assertThat(copy.getLimitPrice()).isEqualTo(limitPrice);
    assertThat(copy.getTimestamp()).isEqualTo(timestamp);
    assertThat(copy.getId()).isEqualTo(id);
    assertThat(copy.getOrderFlags()).hasSize(1);
    assertThat(copy.getOrderFlags()).containsExactly(TestFlags.TEST1);
    assertThat(copy.hasFlag(TestFlags.TEST1));
    assertThat(copy.getStatus()).isEqualTo(status);
    assertThat(copy.getFee()).isEqualTo(fee);
    assertThat(copy.getUserReference()).isEqualTo(reference);
    assertThat(copy.getIntention()).isEqualTo(intention);
  }

  @Test
  public void testBuilderFrom() throws IOException {
    final Order.OrderType type = Order.OrderType.ASK;
    final BigDecimal originalAmount = new BigDecimal("100.501");
    final CurrencyPair currencyPair = CurrencyPair.BTC_USD;
    final BigDecimal limitPrice = new BigDecimal("250.34");
    final BigDecimal averagePrice = new BigDecimal("255.00");
    final BigDecimal cumulativeAmount = new BigDecimal("0.00");
    final BigDecimal stopPrice = new BigDecimal("266.21");
    final BigDecimal fee = new BigDecimal("22.2");
    final Date timestamp = new Date();
    final String id = "id";
    final Order.OrderStatus status = Order.OrderStatus.FILLED;
    final String reference = "reference";
    final StopOrder.Intention intention = StopOrder.Intention.TAKE_PROFIT;

    final StopOrder original =
        new StopOrder(
            type,
            originalAmount,
            currencyPair,
            id,
            timestamp,
            stopPrice,
            limitPrice,
            averagePrice,
            cumulativeAmount,
            fee,
            status,
            reference,
            intention);
    original.addOrderFlag(TestFlags.TEST1);
    original.addOrderFlag(TestFlags.TEST3);
    final StopOrder copy = StopOrder.Builder.from(original).build();

    assertThat(copy).isEqualToComparingFieldByField(original);
  }

  @Test
  public void testSerializeDeserialize() throws IOException {
    final Order.OrderType type = Order.OrderType.ASK;
    final BigDecimal originalAmount = new BigDecimal("100.501");
    final CurrencyPair currencyPair = CurrencyPair.BTC_USD;
    final BigDecimal limitPrice = new BigDecimal("250.34");
    final BigDecimal averagePrice = new BigDecimal("255.00");
    final BigDecimal cumulativeAmount = new BigDecimal("0.00");
    final BigDecimal stopPrice = new BigDecimal("266.21");
    final BigDecimal fee = new BigDecimal("22.2");
    final Date timestamp = new Date();
    final String id = "id";
    final Order.OrderStatus status = Order.OrderStatus.FILLED;
    final String reference = "reference";
    final StopOrder.Intention intention = StopOrder.Intention.TAKE_PROFIT;

    final StopOrder original =
        new StopOrder(
            type,
            originalAmount,
            currencyPair,
            id,
            timestamp,
            stopPrice,
            limitPrice,
            averagePrice,
            cumulativeAmount,
            fee,
            status,
            reference,
            intention);
    original.addOrderFlag(TestFlags.TEST1);
    original.addOrderFlag(TestFlags.TEST3);

    StopOrder jsonCopy = ObjectMapperHelper.viaJSON(original);
    assertThat(jsonCopy).isEqualToIgnoringGivenFields(original, "cumulativeAmount");
    assertTrue(jsonCopy.getCumulativeAmount().compareTo(original.getCumulativeAmount()) == 0);
  }

  private enum TestFlags implements Order.IOrderFlags {
    TEST1,
    TEST2,
    TEST3
  }
}
