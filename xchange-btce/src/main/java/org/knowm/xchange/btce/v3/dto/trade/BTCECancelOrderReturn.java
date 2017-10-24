package org.knowm.xchange.btce.v3.dto.trade;

import org.knowm.xchange.btce.v3.dto.BTCEReturn;

import com.fasterxml.jackson.annotation.JsonProperty;

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
  public BTCECancelOrderReturn(@JsonProperty("success") boolean success, @JsonProperty("return") BTCECancelOrderResult value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
