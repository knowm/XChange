package org.knowm.xchange.anx.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

/** @author timmolter */
public class ANXTickerWrapper {

  private final String result;
  private final ANXTicker anxTicker;
  private final String error;

  /**
   * Constructor
   *
   * @param result
   * @param anxTicker
   * @param error
   */
  public ANXTickerWrapper(
      @JsonProperty("result") String result,
      @JsonProperty("data") ANXTicker anxTicker,
      @JsonProperty("error") String error) {

    this.result = result;
    this.anxTicker = anxTicker;
    this.error = error;
  }

  public String getResult() {

    return result;
  }

  public ANXTicker getAnxTicker() {

    return anxTicker;
  }

  public String getError() {

    return error;
  }
}
