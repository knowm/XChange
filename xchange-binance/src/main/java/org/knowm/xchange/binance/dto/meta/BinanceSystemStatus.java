package org.knowm.xchange.binance.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BinanceSystemStatus {

  // 0: normal，1：system maintenance
  @JsonProperty private String status;
  // normal or system maintenance
  @JsonProperty private String msg;

  public String getStatus() {
    return status;
  }

  public String getMsg() {
    return msg;
  }
}
