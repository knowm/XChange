package org.knowm.xchange.anx.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author timmolter
 */
public class ANXAccountInfoWrapper {

  private final String result;
  private final ANXAccountInfo ANXAccountInfo;
  private final String error;

  /**
   * Constructor
   *
   * @param result
   * @param anxAccountInfo
   * @param error
   */
  public ANXAccountInfoWrapper(@JsonProperty("result") String result, @JsonProperty("data") ANXAccountInfo anxAccountInfo,
      @JsonProperty("error") String error) {

    this.result = result;
    this.ANXAccountInfo = anxAccountInfo;
    this.error = error;
  }

  public String getResult() {

    return result;
  }

  public ANXAccountInfo getANXAccountInfo() {

    return ANXAccountInfo;
  }

  public String getError() {

    return error;
  }

}
