package com.xeiam.xchange.bitvc.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitVcCancelOrderResult extends BitVcError {

  private final String result;

  public BitVcCancelOrderResult(@JsonProperty("code") final int code, @JsonProperty("msg") final String msg, @JsonProperty("time") final long time,
      @JsonProperty("result") final String result) {

    super(code, msg, time);
    this.result = result;
  }

  public String getResult() {

    return result;
  }

}
