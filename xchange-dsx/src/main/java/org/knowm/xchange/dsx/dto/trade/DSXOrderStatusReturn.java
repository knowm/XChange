package org.knowm.xchange.dsx.dto.trade;

import org.knowm.xchange.dsx.dto.DSXReturn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXOrderStatusReturn extends DSXReturn<DSXOrderStatusResult> {

  public DSXOrderStatusReturn(@JsonProperty("success") boolean success, @JsonProperty("return") DSXOrderStatusResult value,
      @JsonProperty("error") String error) {
    super(success, value, error);
  }
}
