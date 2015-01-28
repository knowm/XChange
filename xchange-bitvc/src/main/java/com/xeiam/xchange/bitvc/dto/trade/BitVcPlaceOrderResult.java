package com.xeiam.xchange.bitvc.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitVcPlaceOrderResult extends BitVcError {

  private final String result;
  private final long id;

  public BitVcPlaceOrderResult(@JsonProperty("code") final int code, @JsonProperty("msg") final String msg, @JsonProperty("time") final long time,
      @JsonProperty("result") final String result, @JsonProperty("id") final long id) {

    super(code, msg, time);
    this.result = result;
    this.id = id;
  }

  public String getResult() {

    return result;
  }

  public long getId() {

    return id;
  }

}
