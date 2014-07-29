package com.xeiam.xchange.btce.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.btce.v2.dto.marketdata.BTCEReturn;

/**
 * @author Matija Mazi
 */
@Deprecated
public class BTCEAccountInfoReturn extends BTCEReturn<BTCEAccountInfo> {

  /**
   * Constructor
   * 
   * @param success True if successful
   * @param value The BTC-e account info
   * @param error Any error
   */
  public BTCEAccountInfoReturn(@JsonProperty("success") boolean success, @JsonProperty("return") BTCEAccountInfo value, @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
