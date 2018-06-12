package org.knowm.xchange.coingi.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WithdrawalResponse {
  private boolean result;

  public WithdrawalResponse(@JsonProperty("result") boolean result) {
    this.result = result;
  }
}
