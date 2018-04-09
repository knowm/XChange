package org.knowm.xchange.wex.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.wex.v3.dto.WexReturn;

/** @author Matija Mazi */
public class WexCancelOrderReturn extends WexReturn<WexCancelOrderResult> {

  /**
   * Constructor
   *
   * @param success
   * @param value
   * @param error
   */
  public WexCancelOrderReturn(
      @JsonProperty("success") boolean success,
      @JsonProperty("return") WexCancelOrderResult value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
