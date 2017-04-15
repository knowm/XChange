package org.knowm.xchange.btce.v3.dto.account;

import org.knowm.xchange.btce.v3.dto.BTCEReturn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Ondřej Novotný
 */
public class BTCEWithDrawInfoReturn extends BTCEReturn<BTCEWithdrawInfo> {

  /**
   * Constructor
   *
   * @param success True if successful
   * @param value The BTC-e account info
   * @param error Any error
   */
  public BTCEWithDrawInfoReturn(@JsonProperty("success") boolean success, @JsonProperty("return") BTCEWithdrawInfo value,
      @JsonProperty("error") String error) {
    super(success, value, error);
  }
}
