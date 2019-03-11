package org.knowm.xchange.wex.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.wex.v3.dto.WexReturn;

/** @author Matija Mazi */
public class WexPlaceOrderReturn extends WexReturn<WexPlaceOrderResult> {

  /**
   * Constructor
   *
   * @param success
   * @param value
   * @param error
   */
  public WexPlaceOrderReturn(
      @JsonProperty("success") boolean success,
      @JsonProperty("return") WexPlaceOrderResult value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
