package org.knowm.xchange.bitcoinde.v4.dto;

import java.math.BigDecimal;
import lombok.Value;
import org.knowm.xchange.dto.Order.IOrderFlags;
import org.knowm.xchange.dto.trade.LimitOrder;

@Value
public class BitcoindeOrderFlagsOrderQuantities implements IOrderFlags {

  BigDecimal minAmountToTrade;
  BigDecimal maxAmountToTrade;
  BigDecimal minVolumeToPay;
  BigDecimal maxVolumeToPay;

  /**
   * Returns a hash code value for the class itself. This ensure that {@link
   * LimitOrder#getOrderFlags()} can only contain a single instance of {@code
   * BitcoindeOrderFlagsOrderQuantities}.
   *
   * @return a hash code value for this object.
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public int hashCode() {
    return BitcoindeOrderFlagsOrderQuantities.class.hashCode();
  }

  /**
   * Indicates whether some other object is "equal to" this one by checking whether this is an
   * instance of {@code BitcoindeOrderFlagsOrderQuantities} to comply with #hashCode()
   *
   * @param obj the reference object with which to compare.
   * @return {@code true} if the obj argument is {@code instanceof
   *     BitcoindeOrderFlagsOrderQuantities}; {@code false} otherwise.
   * @see #hashCode()
   */
  @Override
  public boolean equals(Object obj) {
    return obj instanceof BitcoindeOrderFlagsOrderQuantities;
  }
}
