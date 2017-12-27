package org.knowm.xchange.wex.v3.dto.trade;

import java.util.Map;

import org.knowm.xchange.wex.v3.dto.WexReturn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Matija Mazi
 */
public class WexOpenOrdersReturn extends WexReturn<Map<Long, WexOrder>> {

  /**
   * Constructor
   *
   * @param success
   * @param value
   * @param error
   */
  public WexOpenOrdersReturn(@JsonProperty("success") boolean success, @JsonProperty("return") Map<Long, WexOrder> value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
