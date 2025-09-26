package org.knowm.xchange.bitso.dto.funding;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Funding transaction status enum */
public enum BitsoFundingStatus {
  @JsonProperty("pending")
  PENDING,

  @JsonProperty("complete")
  COMPLETE,

  @JsonProperty("cancelled")
  CANCELLED,

  @JsonProperty("failed")
  FAILED;

  @JsonCreator
  public static BitsoFundingStatus fromValue(String value) {
    switch (value) {
      case "pending":
        return PENDING;
      case "complete":
        return COMPLETE;
      case "cancelled":
        return CANCELLED;
      case "failed":
        return FAILED;
      default:
        throw new IllegalArgumentException("Unknown funding status: " + value);
    }
  }
}
