package org.knowm.xchange.anx.v2.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author timmolter
 */
public class ANXLagWrapper {

  private final String result;
  private final ANXLag anxLag;
  private final String error;

  /**
   * Constructor
   *
   * @param result
   * @param anxLag
   * @param error
   */
  public ANXLagWrapper(@JsonProperty("result") String result, @JsonProperty("data") ANXLag anxLag, @JsonProperty("error") String error) {

    this.result = result;
    this.anxLag = anxLag;
    this.error = error;
  }

  public String getResult() {

    return result;
  }

  public ANXLag getANXLag() {

    return anxLag;
  }

  public String getError() {

    return error;
  }

}
