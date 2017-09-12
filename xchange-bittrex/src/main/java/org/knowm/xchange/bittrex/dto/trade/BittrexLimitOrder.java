package org.knowm.xchange.bittrex.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;

public class BittrexLimitOrder extends LimitOrder {

  private final BigDecimal pricePerUnit;

  public BittrexLimitOrder(OrderType type, BigDecimal tradableAmount, CurrencyPair currencyPair, String id, Date timestamp, BigDecimal limitPrice,
      BigDecimal quantityRemaining, BigDecimal pricePerUnit) {
    super(type, tradableAmount, quantityRemaining, currencyPair, id, timestamp, limitPrice);

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
