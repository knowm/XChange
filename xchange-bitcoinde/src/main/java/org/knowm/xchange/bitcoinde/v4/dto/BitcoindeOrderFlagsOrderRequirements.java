package org.knowm.xchange.bitcoinde.v4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.knowm.xchange.dto.Order.IOrderFlags;
import org.knowm.xchange.dto.trade.LimitOrder;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public final class BitcoindeOrderFlagsOrderRequirements implements IOrderFlags {

  private final BitcoindeTrustLevel minTrustLevel;
  private Boolean onlyKYCFull;
  private String[] seatsOfBank;
  private BitcoindePaymentOption paymentOption;

  /**
   * Returns a hash code value for the class itself. This ensure that {@link
   * LimitOrder#getOrderFlags()} can only contain a single instance of {@code
   * BitcoindeOrderRequirements}.
   *
   * @return a hash code value for this object.
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public int hashCode() {
    return BitcoindeOrderFlagsOrderRequirements.class.hashCode();
  }

  /**
   * Indicates whether some other object is "equal to" this one by checking whether this is an
   * instance of {@code BitcoindeOrderRequirements} to comply with #hashCode()
   *
   * @param obj the reference object with which to compare.
   * @return {@code true} if the obj argument is {@code instanceof BitcoindeOrderRequirements};
   *     {@code false} otherwise.
   * @see #hashCode()
   */
  @Override
  public boolean equals(Object obj) {
    return obj instanceof BitcoindeOrderFlagsOrderRequirements;
  }
}
