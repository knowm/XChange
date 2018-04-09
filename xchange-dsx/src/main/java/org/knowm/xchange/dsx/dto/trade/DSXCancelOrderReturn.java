package org.knowm.xchange.dsx.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.dsx.dto.DSXReturn;

/** @author Mikhail Wall */
public class DSXCancelOrderReturn extends DSXReturn<DSXCancelOrderResult> {

  public DSXCancelOrderReturn(
      @JsonProperty("success") boolean success,
      @JsonProperty("return") DSXCancelOrderResult value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
