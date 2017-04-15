package org.knowm.xchange.btce.v3.dto.trade;

import org.knowm.xchange.btce.v3.dto.BTCEReturn;

import com.fasterxml.jackson.annotation.JsonProperty;

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
  public BTCEPlaceOrderReturn(@JsonProperty("success") boolean success, @JsonProperty("return") BTCEPlaceOrderResult value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
