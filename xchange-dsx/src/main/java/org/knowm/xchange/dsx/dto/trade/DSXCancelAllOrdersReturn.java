package org.knowm.xchange.dsx.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.dsx.dto.DSXReturn;

/** @author Mikhail Wall */
public class DSXCancelAllOrdersReturn extends DSXReturn<DSXCancelAllOrdersResult> {

  public DSXCancelAllOrdersReturn(
      @JsonProperty("success") boolean success,
      @JsonProperty("return") DSXCancelAllOrdersResult value,
      @JsonProperty("error") String error) {
    super(success, value, error);
  }
}
