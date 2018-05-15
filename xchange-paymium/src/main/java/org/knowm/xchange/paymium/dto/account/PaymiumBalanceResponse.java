package org.knowm.xchange.paymium.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymiumBalanceResponse {

  @JsonProperty("result")
  private PaymiumBalance result;

  @JsonProperty("result")
  public PaymiumBalance getResult() {
    return result;
  }

  @JsonProperty("result")
  public void setResult(PaymiumBalance result) {
    this.result = result;
  }

  @Override
  public String toString() {

    return "PaymiumBalancesResponse [result=" + result + "]";
  }
}
