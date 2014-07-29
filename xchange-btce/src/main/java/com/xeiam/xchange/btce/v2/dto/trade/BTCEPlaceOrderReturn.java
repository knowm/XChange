package com.xeiam.xchange.btce.v2.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.btce.v2.dto.marketdata.BTCEReturn;

/**
 * @author Matija Mazi
 */
@Deprecated
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
