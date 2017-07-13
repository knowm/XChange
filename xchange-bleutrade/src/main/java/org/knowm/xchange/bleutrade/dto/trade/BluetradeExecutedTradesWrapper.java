package org.knowm.xchange.bleutrade.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BluetradeExecutedTradesWrapper {
  public final boolean success;
  public final String message;
  public final List<BluetradeExecutedTrade> result;

  public BluetradeExecutedTradesWrapper(@JsonProperty("success") boolean success, @JsonProperty("message") String message, @JsonProperty("result") List<BluetradeExecutedTrade> result) {
    this.success = success;
    this.message = message;
    this.result = result;
  }
}
