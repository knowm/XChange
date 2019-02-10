package org.knowm.xchange.kraken.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class KrakenDepositMethods {
  /*
   * method = name of deposit method limit = maximum net amount that can be deposited right now fee
   * = amount of fees that will be paid address-setup-fee = whether or not method has an address
   * setup fee (optional)
   */

  private final String method;
  private final String limit;
  private final BigDecimal fee;
  private final BigDecimal addressSetupFee;

  public KrakenDepositMethods(
      @JsonProperty("method") String method,
      @JsonProperty("limit") String limit,
      @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("address-setup-fee") BigDecimal addressSetupFee) {
    super();
    this.method = method;
    this.limit = limit;
    this.fee = fee;
    this.addressSetupFee = addressSetupFee;
  }

  public String getMethod() {
    return method;
  }

  public BigDecimal getLimit() {
    if (limit.equals("false")) {
      return BigDecimal.valueOf(Double.MAX_VALUE);
    } else {
      return new BigDecimal(limit);
    }
  }

  public BigDecimal getFee() {
    return fee;
  }

  public BigDecimal getAddressSetupFee() {
    return addressSetupFee;
  }

  @Override
  public String toString() {
    return "KrakenDepositMethods [method="
        + method
        + ", limit="
        + limit
        + ", fee="
        + fee
        + ", addressSetupFee="
        + addressSetupFee
        + "]";
  }
}
