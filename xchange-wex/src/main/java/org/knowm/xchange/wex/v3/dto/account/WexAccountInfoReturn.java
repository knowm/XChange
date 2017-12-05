package org.knowm.xchange.wex.v3.dto.account;

import org.knowm.xchange.wex.v3.dto.WexReturn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Matija Mazi
 */
public class WexAccountInfoReturn extends WexReturn<WexAccountInfo> {

  /**
   * Constructor
   *
   * @param success True if successful
   * @param value The BTC-e account info
   * @param error Any error
   */
  public WexAccountInfoReturn(@JsonProperty("success") boolean success, @JsonProperty("return") WexAccountInfo value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
