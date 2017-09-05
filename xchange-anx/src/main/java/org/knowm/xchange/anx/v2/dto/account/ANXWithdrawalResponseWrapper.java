package org.knowm.xchange.anx.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author timmolter
 */
public class ANXWithdrawalResponseWrapper {

  private final String result;
  private final ANXWithdrawalResponse anxWithdrawalResponse;
  private final String error;

  /**
   * Constructor
   *
   * @param result
   * @param anxWithdrawalResponse
   * @param error
   */
  public ANXWithdrawalResponseWrapper(@JsonProperty("result") String result, @JsonProperty("data") ANXWithdrawalResponse anxWithdrawalResponse,
      @JsonProperty("error") String error) {

    this.result = result;
    this.anxWithdrawalResponse = anxWithdrawalResponse;
    this.error = error;
  }

  public String getResult() {

    return result;
  }

  public ANXWithdrawalResponse getAnxWithdrawalResponse() {

    return anxWithdrawalResponse;
  }

  public String getError() {

    return error;
  }

}
