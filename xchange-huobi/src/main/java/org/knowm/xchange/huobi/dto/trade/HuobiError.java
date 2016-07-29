package org.knowm.xchange.huobi.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HuobiError {

  public static final int ORDER_NOT_EXIST = 26;

  private final int code;
  private final String msg;
  private final long time;

  public HuobiError(@JsonProperty("code") final int code, @JsonProperty("msg") final String msg, @JsonProperty("time") final long time) {

    this.code = code;
    this.msg = msg;
    this.time = time;
  }

  public int getCode() {

    return code;
  }

  public String getMsg() {

    return msg;
  }

  public long getTime() {

    return time;
  }

}
