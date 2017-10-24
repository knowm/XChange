package org.knowm.xchange.dsx.dto.trade;

import org.knowm.xchange.dsx.dto.DSXReturn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXVolumeReturn extends DSXReturn<DSXVolumeResult> {

  public DSXVolumeReturn(@JsonProperty("success") boolean success, @JsonProperty("return") DSXVolumeResult value,
      @JsonProperty("error") String error) {
    super(success, value, error);
  }
}
