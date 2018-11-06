package org.knowm.xchange.bitz.dto.account.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bitz.dto.BitZResult;
import org.knowm.xchange.bitz.dto.account.BitZUserAssets;

public class BitZUserAssetsResult extends BitZResult<BitZUserAssets> {
  public BitZUserAssetsResult(
      @JsonProperty("status") int status,
      @JsonProperty("msg") String message,
      @JsonProperty("data") BitZUserAssets data,
      @JsonProperty("time") String time,
      @JsonProperty("microtime") String microtime,
      @JsonProperty("source") String source) {
    super(status, message, data);
  }
}
