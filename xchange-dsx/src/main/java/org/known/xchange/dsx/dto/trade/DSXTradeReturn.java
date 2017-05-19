package org.known.xchange.dsx.dto.trade;

import org.known.xchange.dsx.dto.DSXReturn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXTradeReturn extends DSXReturn<DSXTradeResult> {

  public DSXTradeReturn(@JsonProperty("success") boolean success, @JsonProperty("return")DSXTradeResult value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
