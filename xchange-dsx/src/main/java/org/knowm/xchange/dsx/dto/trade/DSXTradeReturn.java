package org.knowm.xchange.dsx.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.dsx.dto.DSXReturn;

/** @author Mikhail Wall */
public class DSXTradeReturn extends DSXReturn<DSXTradeResult> {

  public DSXTradeReturn(
      @JsonProperty("success") boolean success,
      @JsonProperty("return") DSXTradeResult value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
