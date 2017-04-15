package org.knowm.xchange.bittrex.v1.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;

public class BittrexLimitOrder extends LimitOrder {

  private final BigDecimal quantityRemaining;
  private final BigDecimal pricePerUnit;

  public BittrexLimitOrder(OrderType type, BigDecimal tradableAmount, CurrencyPair currencyPair, String id, Date timestamp, BigDecimal limitPrice,
      BigDecimal quantityRemaining, BigDecimal pricePerUnit) {
    super(type, tradableAmount, currencyPair, id, timestamp, limitPrice);

    this.quantityRemaining = quantityRemaining;
    this.pricePerUnit = pricePerUnit;
  }

  /**
   * If an order has not yet received any fills then the quantity remaining will be the same as the total tradable amount. The quantity remaining
   * reduces to zero as fills occur.
   *
   * @return the volume of the order that has not yet executed
   */
  public BigDecimal getQuantityRemaining() {
    return quantityRemaining;
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
