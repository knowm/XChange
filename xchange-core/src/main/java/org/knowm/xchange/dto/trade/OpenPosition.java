package org.knowm.xchange.dto.trade;

import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

public class OpenPosition extends Order {

  public OpenPosition(
      OrderType type,
      BigDecimal originalAmount,
      CurrencyPair currencyPair,
      String id,
      Date timestamp,
      BigDecimal averagePrice,
      BigDecimal fee) {
    super(type, originalAmount, currencyPair, id, timestamp);
    setAveragePrice(averagePrice);
    setFee(fee);
  }
}
