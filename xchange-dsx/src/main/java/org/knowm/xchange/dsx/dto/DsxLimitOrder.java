package org.knowm.xchange.dsx.dto;

import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;

/**
 * This class was originally written in order to support an order ID provided by the client, that
 * functionality is now implemented as 'userReference' as part of LimitOrder class. Hence, this
 * class is no longer needed, it still exists here simply for backward compatibility.
 *
 * @deprecated Use {@link LimitOrder} with user reference instead.
 */
public class DsxLimitOrder extends LimitOrder {

  public DsxLimitOrder(
      OrderType type,
      BigDecimal originalAmount,
      CurrencyPair currencyPair,
      String id,
      Date timestamp,
      BigDecimal limitPrice,
      String clientOrderId) {
    super(
        type,
        originalAmount,
        currencyPair,
        id,
        timestamp,
        limitPrice,
        null,
        null,
        null,
        null,
        clientOrderId);
  }

  public DsxLimitOrder(
      OrderType type,
      BigDecimal originalAmount,
      BigDecimal cumulativeAmount,
      CurrencyPair currencyPair,
      String id,
      Date timestamp,
      BigDecimal limitPrice,
      String clientOrderId) {
    super(
        type,
        originalAmount,
        currencyPair,
        id,
        timestamp,
        limitPrice,
        null,
        cumulativeAmount,
        null,
        null,
        clientOrderId);
  }

  public DsxLimitOrder(
      OrderType type,
      BigDecimal originalAmount,
      CurrencyPair currencyPair,
      String id,
      Date timestamp,
      BigDecimal limitPrice,
      BigDecimal averagePrice,
      BigDecimal cumulativeAmount,
      BigDecimal fee,
      OrderStatus status,
      String clientOrderId) {
    super(
        type,
        originalAmount,
        currencyPair,
        id,
        timestamp,
        limitPrice,
        averagePrice,
        cumulativeAmount,
        fee,
        status,
        clientOrderId);
  }

  public String getClientOrderId() {
    return getUserReference();
  }
}
