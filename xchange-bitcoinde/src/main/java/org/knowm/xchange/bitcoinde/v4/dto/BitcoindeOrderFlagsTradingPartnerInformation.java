package org.knowm.xchange.bitcoinde.v4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.knowm.xchange.dto.Order.IOrderFlags;
import org.knowm.xchange.dto.trade.LimitOrder;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public final class BitcoindeOrderFlagsTradingPartnerInformation implements IOrderFlags {

  private final String userName;
  private final boolean kycFull;
  private final BitcoindeTrustLevel trustLevel;
  private String bankName;
  private String bic;
  private String seatOfBank;
  private Integer rating;
  private Integer numberOfTrades;

  /**
   * Returns a hash code value for the class itself. This ensure that {@link
   * LimitOrder#getOrderFlags()} can only contain a single instance of {@code
   * BitcoindeOrderFlagsTradingPartnerInformation}.
   *
   * @return a hash code value for this object.
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public int hashCode() {
    return BitcoindeOrderFlagsTradingPartnerInformation.class.hashCode();
  }

  /**
   * Indicates whether some other object is "equal to" this one by checking whether this is an
   * instance of {@code BitcoindeOrderFlagsTradingPartnerInformation} to comply with #hashCode()
   *
   * @param obj the reference object with which to compare.
   * @return {@code true} if the obj argument is {@code instanceof
   *     BitcoindeOrderFlagsTradingPartnerInformation}; {@code false} otherwise.
   * @see #hashCode()
   */
  @Override
  public boolean equals(Object obj) {
    return obj instanceof BitcoindeOrderFlagsTradingPartnerInformation;
  }
}
