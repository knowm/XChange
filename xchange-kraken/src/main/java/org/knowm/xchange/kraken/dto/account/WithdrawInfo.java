package org.knowm.xchange.kraken.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class WithdrawInfo {
  /*
   * method = name of the withdrawal method that will be used limit = maximum net amount that can be withdrawn right now fee = amount of fees that
   * will be paid
   */

  private final String method;
  private final BigDecimal limit;
  private final String fee;

  public WithdrawInfo(
      @JsonProperty("method") String method,
      @JsonProperty("limit") BigDecimal limit,
      @JsonProperty("fee") String fee) {
    super();
    this.method = method;
    this.limit = limit;
    this.fee = fee;
  }

  public String getMethod() {
    return method;
  }

  public BigDecimal getLimit() {
    return limit;
  }

  public String getFee() {
    return fee;
  }

  @Override
  public String toString() {
    return "WithdrawInfo [method=" + method + ", limit=" + limit + ", fee=" + fee + "]";
  }
}
