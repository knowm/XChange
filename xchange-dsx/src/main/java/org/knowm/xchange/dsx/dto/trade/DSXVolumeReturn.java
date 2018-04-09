package org.knowm.xchange.dsx.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.dsx.dto.DSXReturn;

/** @author Mikhail Wall */
public class DSXVolumeReturn extends DSXReturn<DSXVolumeResult> {

  public DSXVolumeReturn(
      @JsonProperty("success") boolean success,
      @JsonProperty("return") DSXVolumeResult value,
      @JsonProperty("error") String error) {
    super(success, value, error);
  }
}
