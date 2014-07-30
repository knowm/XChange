package com.xeiam.xchange.btce.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.btce.v3.dto.BTCEReturn;

/**
 * @author Matija Mazi
 */
public class BTCEPlaceOrderReturn extends BTCEReturn<BTCEPlaceOrderResult> {

  /**
   * Constructor
   * 
   * @param success
   * @param value
   * @param error
   */
  public BTCEPlaceOrderReturn(@JsonProperty("success") boolean success, @JsonProperty("return") BTCEPlaceOrderResult value, @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
