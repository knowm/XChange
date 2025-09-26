package org.knowm.xchange.bitso.dto.funding;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Withdrawal fee type enum */
public enum BitsoWithdrawalFeeType {
  @JsonProperty("fixed")
  FIXED,

  @JsonProperty("percentage")
  PERCENTAGE;

  @JsonCreator
  public static BitsoWithdrawalFeeType fromValue(String value) {
    switch (value) {
      case "fixed":
        return FIXED;
      case "percentage":
        return PERCENTAGE;
      default:
        throw new IllegalArgumentException("Unknown fee type: " + value);
    }
  }
}
