package org.knowm.xchange.btce.v3.dto.trade;

import java.util.Map;

import org.knowm.xchange.btce.v3.dto.BTCEReturn;

import com.fasterxml.jackson.annotation.JsonProperty;

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
  public BTCETradeHistoryReturn(@JsonProperty("success") boolean success, @JsonProperty("return") Map<Long, BTCETradeHistoryResult> value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
