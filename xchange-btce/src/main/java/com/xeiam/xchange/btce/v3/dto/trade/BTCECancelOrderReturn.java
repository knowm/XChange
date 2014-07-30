package com.xeiam.xchange.btce.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.btce.v3.dto.BTCEReturn;

/**
 * @author Matija Mazi
 */
public class BTCECancelOrderReturn extends BTCEReturn<BTCECancelOrderResult> {

  /**
   * Constructor
   * 
   * @param success
   * @param value
   * @param error
   */
  public BTCECancelOrderReturn(@JsonProperty("success") boolean success, @JsonProperty("return") BTCECancelOrderResult value, @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
