package org.knowm.xchange.bittrex.dto.trade;

import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;

public class BittrexLimitOrder extends LimitOrder {

  public BittrexLimitOrder(
      OrderType type,
      BigDecimal originalAmount,
      CurrencyPair currencyPair,
      String id,
      Date timestamp,
      BigDecimal limitPrice,
      BigDecimal quantityRemaining,
      BigDecimal pricePerUnit,
      BigDecimal fee) {
    super(
        type,
        originalAmount,
        quantityRemaining == null ? null : originalAmount.subtract(quantityRemaining),
        currencyPair,
        id,
        timestamp,
        limitPrice);
    this.setAveragePrice(pricePerUnit);
  }

  public BittrexLimitOrder(
      OrderType type,
      BigDecimal originalAmount,
      CurrencyPair currencyPair,
      String id,
      Date timestamp,
      BigDecimal limitPrice,
      BigDecimal quantityRemaining,
      BigDecimal pricePerUnit,
      BigDecimal fee,
      OrderStatus status) {
    super(
        type,
        originalAmount,
        currencyPair,
        id,
        timestamp,
        limitPrice,
        pricePerUnit,
        quantityRemaining == null ? null : originalAmount.subtract(quantityRemaining),
        fee,
        status);
  }
}
