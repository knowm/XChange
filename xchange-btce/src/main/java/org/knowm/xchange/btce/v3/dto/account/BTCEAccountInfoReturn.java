package org.knowm.xchange.btce.v3.dto.account;

import org.knowm.xchange.btce.v3.dto.BTCEReturn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Matija Mazi
 */
public class BTCEAccountInfoReturn extends BTCEReturn<BTCEAccountInfo> {

  /**
   * Constructor
   *
   * @param success True if successful
   * @param value The BTC-e account info
   * @param error Any error
   */
  public BTCEAccountInfoReturn(@JsonProperty("success") boolean success, @JsonProperty("return") BTCEAccountInfo value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
