package org.knowm.xchange.anx.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author timmolter
 */
public class ANXBitcoinDepositAddressWrapper {

  private final String result;
  private final ANXBitcoinDepositAddress anxBitcoinDepositAddress;
  private final String error;

  /**
   * Constructor
   *
   * @param result
   * @param anxBitcoinDepositAddress
   * @param error
   */
  public ANXBitcoinDepositAddressWrapper(@JsonProperty("result") String result,
      @JsonProperty("data") ANXBitcoinDepositAddress anxBitcoinDepositAddress, @JsonProperty("error") String error) {

    this.result = result;
    this.anxBitcoinDepositAddress = anxBitcoinDepositAddress;
    this.error = error;
  }

  public String getResult() {

    return result;
  }

  public ANXBitcoinDepositAddress getAnxBitcoinDepositAddress() {

    return anxBitcoinDepositAddress;
  }

  public String getError() {

    return error;
  }

}
