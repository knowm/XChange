package org.knowm.xchange.anx.v2.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

/** @author kpysniak */
public class ANXTradeResultWrapper {

  private final String result;
  private final ANXTradeResult[] anxTradeResults;
  private final String error;

  /**
   * Constructor
   *
   * @param result
   * @param anxTradeResults
   * @param error
   */
  public ANXTradeResultWrapper(
      @JsonProperty("result") String result,
      @JsonProperty("data") ANXTradeResult[] anxTradeResults,
      @JsonProperty("error") String error) {

    this.result = result;
    this.anxTradeResults = anxTradeResults;
    this.error = error;
  }

  public String getResult() {

    return result;
  }

  public ANXTradeResult[] getAnxTradeResults() {

    return anxTradeResults;
  }

  public String getError() {

    return error;
  }
}
