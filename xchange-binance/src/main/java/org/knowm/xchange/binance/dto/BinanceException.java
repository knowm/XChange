package org.knowm.xchange.binance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BinanceException extends RuntimeException {

  public final int code;
  public final String msg;

  public BinanceException(@JsonProperty("code") int code, @JsonProperty("msg") String msg) {
    super(code + ": " + msg);
    this.code = code;
    this.msg = msg;
  }
}
