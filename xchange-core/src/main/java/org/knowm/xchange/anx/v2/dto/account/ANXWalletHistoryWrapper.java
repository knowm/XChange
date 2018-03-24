package org.knowm.xchange.anx.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author deveth0
 */
public class ANXWalletHistoryWrapper {

  private final String result;
  private final ANXWalletHistory anxWalletHistory;
  private final String error;

  /**
   * Constructor
   *
   * @param result
   * @param anxWalletHistory
   * @param error
   */
  public ANXWalletHistoryWrapper(@JsonProperty("result") String result, @JsonProperty("data") ANXWalletHistory anxWalletHistory,
      @JsonProperty("error") String error) {

    this.result = result;
    this.anxWalletHistory = anxWalletHistory;
    this.error = error;
  }

  public String getResult() {

    return result;
  }

  public ANXWalletHistory getANXWalletHistory() {

    return anxWalletHistory;
  }

  public String getError() {

    return error;
  }
}
