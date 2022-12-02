package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class WithdrawResponse {
  public final String id;

  public WithdrawResponse(@JsonProperty("id") String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }
}
