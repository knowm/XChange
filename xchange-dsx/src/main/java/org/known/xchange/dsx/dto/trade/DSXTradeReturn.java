package org.known.xchange.dsx.dto.trade;

import java.util.Map;

import org.known.xchange.dsx.dto.DSXReturn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXTradeReturn extends DSXReturn<Map<Long, DSXTradeResult>> {

  public DSXTradeReturn(@JsonProperty("success") boolean success, @JsonProperty("return") Map<Long, DSXTradeResult> value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
