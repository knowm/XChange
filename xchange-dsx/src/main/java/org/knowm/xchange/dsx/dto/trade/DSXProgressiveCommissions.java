package org.knowm.xchange.dsx.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

/** @author Mikhail Wall */
public class DSXProgressiveCommissions {

  private DSXCommission[] commissions;
  private int indexOfCurrentCommission;
  private String currency;

  public DSXProgressiveCommissions(
      @JsonProperty("commissions") DSXCommission[] commissions,
      @JsonProperty("indexOfCurrentCommission") int indexOfCurrentCommission,
      @JsonProperty("currency") String currency) {
    this.commissions = commissions;
    this.indexOfCurrentCommission = indexOfCurrentCommission;
    this.currency = currency;
  }

  public DSXCommission[] getCommissions() {
    return commissions;
  }

  public int getIndexOfCurrentCommission() {
    return indexOfCurrentCommission;
  }

  public String getCurrency() {
    return currency;
  }
}
