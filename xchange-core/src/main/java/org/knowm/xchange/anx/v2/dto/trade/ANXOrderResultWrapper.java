package org.knowm.xchange.anx.v2.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author timmolter
 */
public class ANXOrderResultWrapper {

  private final String result;
  private final ANXOrderResult anxOrderResult;
  private final String error;

  /**
   * Constructor
   *
   * @param result
   * @param anxOrderResult
   * @param error
   */
  public ANXOrderResultWrapper(@JsonProperty("result") String result, @JsonProperty("data") ANXOrderResult anxOrderResult,
      @JsonProperty("error") String error) {

    this.result = result;
    this.anxOrderResult = anxOrderResult;
    this.error = error;
  }

  public String getResult() {

    return result;
  }

  public ANXOrderResult getANXOrderResult() {

    return anxOrderResult;
  }

  public String getError() {

    return error;
  }

}
