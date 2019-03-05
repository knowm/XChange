package org.knowm.xchange.simulated;

import static java.math.BigDecimal.ZERO;
import static java.util.UUID.randomUUID;
import static org.knowm.xchange.dto.Order.OrderType.ASK;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Builder;
import lombok.Data;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;

/**
 * An order placed on the {@link SimulatedExchange} order book.
 *
 * @author Graham Crockford
 */
@Data
@Builder
final class BookOrder {

  private static final BigDecimal INF = BigDecimal.valueOf(Long.MAX_VALUE);

  static BookOrder fromOrder(Order original, String apiKey) {
    return BookOrder.builder()
        .apiKey(apiKey)
        .id(randomUUID().toString())
        .limitPrice(
            original instanceof LimitOrder
                ? ((LimitOrder) original).getLimitPrice()
                : original.getType() == ASK ? ZERO : INF)
        .originalAmount(original.getOriginalAmount())
        .timestamp(new Date())
        .type(original.getType())
        .build();
  }

  private final String apiKey;
  private final BigDecimal originalAmount;
  private final String id;
  private final Date timestamp;
  private final BigDecimal limitPrice;
  private final OrderType type;
  @Builder.Default private volatile BigDecimal cumulativeAmount = ZERO;
  private volatile BigDecimal averagePrice;
  @Builder.Default private volatile BigDecimal fee = ZERO;

  BigDecimal getRemainingAmount() {
    return originalAmount.subtract(cumulativeAmount);
  }

  boolean isDone() {
    return originalAmount.compareTo(cumulativeAmount) == 0;
  }

  boolean matches(BookOrder takerOrder) {
    return type == ASK
        ? limitPrice.compareTo(takerOrder.getLimitPrice()) <= 0
        : limitPrice.compareTo(takerOrder.getLimitPrice()) >= 0;
  }

  LimitOrder toOrder(CurrencyPair currencyPair) {
    return new LimitOrder.Builder(type, currencyPair)
        .id(id)
        .averagePrice(averagePrice)
        .cumulativeAmount(cumulativeAmount)
        .fee(fee)
        .limitPrice(limitPrice)
        .orderStatus(
            cumulativeAmount.compareTo(ZERO) == 0
                ? OrderStatus.NEW
                : cumulativeAmount.compareTo(originalAmount) == 0
                    ? OrderStatus.FILLED
                    : OrderStatus.PARTIALLY_FILLED)
        .originalAmount(originalAmount)
        .timestamp(timestamp)
        .build();
  }
}
