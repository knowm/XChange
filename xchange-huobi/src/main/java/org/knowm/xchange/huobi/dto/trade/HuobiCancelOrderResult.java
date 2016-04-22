package org.knowm.xchange.huobi.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HuobiCancelOrderResult extends HuobiError {

  private final String result;

  public HuobiCancelOrderResult(@JsonProperty("code") final int code, @JsonProperty("msg") final String msg, @JsonProperty("time") final long time,
      @JsonProperty("result") final String result) {

    super(code, msg, time);
    this.result = result;
  }

  public String getResult() {

    return result;
  }

}
