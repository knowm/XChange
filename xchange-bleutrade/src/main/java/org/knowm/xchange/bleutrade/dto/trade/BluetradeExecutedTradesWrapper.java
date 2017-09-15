package org.knowm.xchange.bleutrade.dto.trade;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

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
