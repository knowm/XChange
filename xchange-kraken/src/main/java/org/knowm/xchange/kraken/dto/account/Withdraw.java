package org.knowm.xchange.kraken.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Withdraw {
  /*
   * refid = reference id *
   */

  private final String refid;

  public Withdraw(@JsonProperty("refid") String refid) {
    super();
    this.refid = refid;
  }

  public String getRefid() {
    return refid;
  }

  @Override
  public String toString() {
    return "Withdraw [refid=" + refid + "]";
  }
}
