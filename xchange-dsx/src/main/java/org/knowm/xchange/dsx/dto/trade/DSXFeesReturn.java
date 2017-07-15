package org.knowm.xchange.dsx.dto.trade;

import org.knowm.xchange.dsx.dto.DSXReturn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXFeesReturn extends DSXReturn<DSXFeesResult> {

  public DSXFeesReturn(@JsonProperty("success") boolean success, @JsonProperty("return") DSXFeesResult value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
