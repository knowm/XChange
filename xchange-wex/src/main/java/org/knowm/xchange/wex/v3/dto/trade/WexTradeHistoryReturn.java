package org.knowm.xchange.wex.v3.dto.trade;

import java.util.Map;

import org.knowm.xchange.wex.v3.dto.WexReturn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Raphael Voellmy
 */
public class WexTradeHistoryReturn extends WexReturn<Map<Long, WexTradeHistoryResult>> {

  /**
   * Constructor
   *
   * @param success
   * @param value
   * @param error
   */
  public WexTradeHistoryReturn(@JsonProperty("success") boolean success, @JsonProperty("return") Map<Long, WexTradeHistoryResult> value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
