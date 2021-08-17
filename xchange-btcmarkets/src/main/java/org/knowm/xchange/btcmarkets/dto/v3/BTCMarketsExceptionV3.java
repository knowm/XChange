package org.knowm.xchange.btcmarkets.dto.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import si.mazi.rescu.HttpStatusExceptionSupport;

public class BTCMarketsExceptionV3 extends HttpStatusExceptionSupport {

  private final String message;
  private final String code;

  protected BTCMarketsExceptionV3(
      @JsonProperty("message") String errorMessage, @JsonProperty("code") String code) {
    this.message = errorMessage;
    this.code = code;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("message", message).append("code", code).toString();
  }
}
