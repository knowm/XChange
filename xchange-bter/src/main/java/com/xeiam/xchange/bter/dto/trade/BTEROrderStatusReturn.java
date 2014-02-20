package com.xeiam.xchange.bter.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by David Henry on 2/19/14.
 */
public class BTEROrderStatusReturn {

  private final boolean result;
  private final BTEROrderStatus bterOrderStatus;
  private final String msg;

  public BTEROrderStatusReturn(@JsonProperty("result") boolean result,@JsonProperty("order") BTEROrderStatus bterOrderStatus,
                               @JsonProperty("msg") String msg) {
    this.result = result;
    this.bterOrderStatus = bterOrderStatus;
    this.msg = msg;
  }

  public boolean isResult() {
    return result;
  }

  public BTEROrderStatus getBterOrderStatus() {
    return bterOrderStatus;
  }

  public String getMsg() {
    return msg;
  }
}
