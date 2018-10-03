package org.knowm.xchange.cryptonit2.order.dto;

import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

public class CryptonitGenericOrder extends Order {

  public CryptonitGenericOrder(
      OrderType type,
      BigDecimal originalAmount,
      CurrencyPair currencyPair,
      String id,
      Date timestamp,
      BigDecimal averagePrice,
      BigDecimal cumulativeAmount,
      BigDecimal fee,
      OrderStatus status) {

    super(
        type,
        originalAmount,
        currencyPair,
        id,
        timestamp,
        averagePrice,
        cumulativeAmount,
        fee,
        status);
  }
}
