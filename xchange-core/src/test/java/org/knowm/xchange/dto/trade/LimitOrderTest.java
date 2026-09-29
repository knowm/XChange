package org.knowm.xchange.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.IOrderFlags;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.utils.ObjectMapperHelper;

class LimitOrderTest {
  @Test
  void builder() {
    final OrderType type = OrderType.BID;
    final BigDecimal originalAmount = new BigDecimal("99.401");
    final BigDecimal averagePrice = new BigDecimal("255.00");
    final BigDecimal cumulativeAmount = new BigDecimal("0.00");
    final CurrencyPair currencyPair = CurrencyPair.LTC_BTC;
    final BigDecimal limitPrice = new BigDecimal("251.64");
    final BigDecimal fee = new BigDecimal("22.2");
    final String userReference = "123";
    final Date timestamp = new Date();
    final String id = "id";
    final Order.OrderStatus status = Order.OrderStatus.FILLED;

    final LimitOrder copy =
        new LimitOrder.Builder(type, currencyPair)
            .originalAmount(originalAmount)
            .averagePrice(averagePrice)
            .cumulativeAmount(cumulativeAmount)
            .limitPrice(limitPrice)
            .orderStatus(status)
            .timestamp(timestamp)
            .id(id)
            .flag(TestFlags.TEST1)
            .fee(fee)
            .userReference(userReference)
            .build();
    assertThat(copy.getType()).isEqualTo(type);
    assertThat(copy.getOriginalAmount()).isEqualTo(originalAmount);
    assertThat(copy.getAveragePrice()).isEqualTo(averagePrice);
    assertThat(copy.getCumulativeAmount()).isEqualTo(cumulativeAmount);
    assertThat(copy.getCurrencyPair()).isEqualTo(currencyPair);
    assertThat(copy.getLimitPrice()).isEqualTo(limitPrice);
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
  void builderFrom() throws Exception {
    final OrderType type = OrderType.ASK;
    final BigDecimal originalAmount = new BigDecimal("100.501");
    final BigDecimal averagePrice = new BigDecimal("255.00");
    final BigDecimal cumulativeAmount = new BigDecimal("0.00");
    final CurrencyPair currencyPair = CurrencyPair.BTC_USD;
    final BigDecimal limitPrice = new BigDecimal("250.34");
    final BigDecimal fee = new BigDecimal("22.2");
    final Date timestamp = new Date();
    final String id = "id";
    final Order.OrderStatus status = Order.OrderStatus.FILLED;

    final LimitOrder original =
        new LimitOrder(
            type,
            originalAmount,
            currencyPair,
            id,
            timestamp,
            limitPrice,
            averagePrice,
            cumulativeAmount,
            fee,
            status);
    original.addOrderFlag(TestFlags.TEST1);
    original.addOrderFlag(TestFlags.TEST3);
    final LimitOrder copy = LimitOrder.Builder.from(original).build();

    assertThat(copy).isEqualToComparingFieldByField(original);
  }

  @Test
  void serializeDeserialize() throws Exception {
    final OrderType type = OrderType.ASK;
    final BigDecimal originalAmount = new BigDecimal("100.501");
    final BigDecimal averagePrice = new BigDecimal("255.00");
    final BigDecimal cumulativeAmount = new BigDecimal("0.00");
    final CurrencyPair currencyPair = CurrencyPair.BTC_USD;
    final BigDecimal limitPrice = new BigDecimal("250.34");
    final BigDecimal fee = new BigDecimal("22.2");
    final Date timestamp = new Date();
    final String id = "id";
    final Order.OrderStatus status = Order.OrderStatus.FILLED;

    final LimitOrder original =
        new LimitOrder(
            type,
            originalAmount,
            currencyPair,
            id,
            timestamp,
            limitPrice,
            averagePrice,
            cumulativeAmount,
            fee,
            status);
    original.addOrderFlag(TestFlags.TEST1);
    original.addOrderFlag(TestFlags.TEST3);

    LimitOrder jsonCopy = ObjectMapperHelper.viaJSON(original);
    assertThat(jsonCopy).isEqualToIgnoringGivenFields(original, "cumulativeAmount");
    assertThat(jsonCopy.getCumulativeAmount().compareTo(original.getCumulativeAmount()))
        .isEqualTo(0);
  }

  @Test
  void compareTo() {
    // bid@1
    LimitOrder bid1 =
        new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD)
            .limitPrice(new BigDecimal("1"))
            .build();
    LimitOrder anotherBid1 =
        new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD)
            .limitPrice(new BigDecimal("1"))
            .build();
    assertThat(bid1.compareTo(anotherBid1)).isEqualTo(0);
    assertThat(anotherBid1.compareTo(bid1)).isEqualTo(0);

    // bid@2
    LimitOrder bid2 =
        new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD)
            .limitPrice(new BigDecimal("2"))
            .build();

    // Sorted: bid@2, bid@1
    assertThat(bid2.compareTo(bid1)).isEqualTo(-1);
    assertThat(bid1.compareTo(bid2)).isEqualTo(1);

    // ask@3
    LimitOrder ask3 =
        new LimitOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USD)
            .limitPrice(new BigDecimal("3"))
            .build();
    LimitOrder anotherAsk3 =
        new LimitOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USD)
            .limitPrice(new BigDecimal("3"))
            .build();
    assertThat(ask3.compareTo(anotherAsk3)).isEqualTo(0);
    assertThat(anotherAsk3.compareTo(ask3)).isEqualTo(0);

    // ask@4
    LimitOrder ask4 =
        new LimitOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USD)
            .limitPrice(new BigDecimal("4"))
            .build();

    // Sorted: ask@3, ask@4
    assertThat(ask3.compareTo(ask4)).isEqualTo(-1);
    assertThat(ask4.compareTo(ask3)).isEqualTo(1);

    // Sorted: bid@2, bid@1, ask@3, ask@4
    assertThat(bid1.compareTo(ask3)).isEqualTo(-1);
    assertThat(ask3.compareTo(bid1)).isEqualTo(1);

    // ask@1
    LimitOrder ask1 =
        new LimitOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USD)
            .limitPrice(new BigDecimal("1"))
            .build();

    // Sorted: bid@1, ask@1
    assertThat(bid1.compareTo(ask1)).isEqualTo(-1);
    assertThat(ask1.compareTo(bid1)).isEqualTo(1);

    // Sorted: bid@2, ask@1
    assertThat(bid2.compareTo(ask1)).isEqualTo(-1);
    assertThat(ask1.compareTo(bid2)).isEqualTo(1);
  }

  private enum TestFlags implements IOrderFlags {
    TEST1,
    TEST2,
    TEST3
  }
}
