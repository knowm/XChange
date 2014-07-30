package com.xeiam.xchange.btce.v3.dto.trade;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.btce.v3.dto.BTCEReturn;

/**
 * @author Raphael Voellmy
 */
public class BTCETradeHistoryReturn extends BTCEReturn<Map<Long, BTCETradeHistoryResult>> {

  /**
   * Constructor
   * 
   * @param success
   * @param value
   * @param error
   */
  public BTCETradeHistoryReturn(@JsonProperty("success") boolean success, @JsonProperty("return") Map<Long, BTCETradeHistoryResult> value, @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
