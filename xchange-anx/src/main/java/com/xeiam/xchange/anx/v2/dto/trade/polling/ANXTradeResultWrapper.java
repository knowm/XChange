package com.xeiam.xchange.anx.v2.dto.trade.polling;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kpysniak
 */
public class ANXTradeResultWrapper {

  private final String result;
  private final ANXTradeResult anxTradeResult;
  private final String error;

  /**
   * Constructor
   *
   * @param result
   * @param anxTradeResult
   * @param error
   */
  public ANXTradeResultWrapper(@JsonProperty("result") String result, @JsonProperty("data") ANXTradeResult anxTradeResult, @JsonProperty("error") String error) {

    this.result = result;
    this.anxTradeResult = anxTradeResult;
    this.error = error;
  }

  public String getResult() {

    return result;
  }

  public ANXTradeResult getAnxTradeResult() {

    return anxTradeResult;
  }

  public String getError() {

    return error;
  }
}
