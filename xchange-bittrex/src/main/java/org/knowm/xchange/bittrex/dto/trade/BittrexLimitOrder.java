package org.knowm.xchange.bittrex.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;

public class BittrexLimitOrder extends LimitOrder {

  private final BigDecimal pricePerUnit;

  public BittrexLimitOrder(OrderType type, BigDecimal originalAmount, CurrencyPair currencyPair, String id, Date timestamp, BigDecimal limitPrice,
      BigDecimal quantityRemaining, BigDecimal pricePerUnit) {
    super(type, originalAmount, quantityRemaining == null ? null : originalAmount.subtract(quantityRemaining), currencyPair, id, timestamp, limitPrice);

    this.pricePerUnit = pricePerUnit;
  }

  public BittrexLimitOrder(OrderType type, BigDecimal originalAmount, CurrencyPair currencyPair, String id, Date timestamp, BigDecimal limitPrice,
      BigDecimal quantityRemaining, BigDecimal pricePerUnit, OrderStatus status) {
    super(type,
        originalAmount,
        currencyPair,
        id,
        timestamp,
        limitPrice,
        BigDecimal.ZERO,
        quantityRemaining == null ? null : originalAmount.subtract(quantityRemaining),
        status);

    this.pricePerUnit = pricePerUnit;
  }

  /**
   * The average price obtained for any trades that have filled the order.
   *
   * @return
   */
  public BigDecimal getPricePerUnit() {
    return pricePerUnit;
  }
}
