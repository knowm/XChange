package org.knowm.xchange.anx.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author timmolter
 */
public class ANXDepthWrapper {

  private final String result;
  private final ANXDepth anxDepth;
  private final String error;

  /**
   * Constructor
   *
   * @param result
   * @param anxDepth
   * @param error
   */
  public ANXDepthWrapper(@JsonProperty("result") String result, @JsonProperty("data") ANXDepth anxDepth, @JsonProperty("error") String error) {

    this.result = result;
    this.anxDepth = anxDepth;
    this.error = error;
  }

  public String getResult() {

    return result;
  }

  public ANXDepth getAnxDepth() {

    return anxDepth;
  }

  public String getError() {

    return error;
  }

}
