package org.knowm.xchange.livecoin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/** @author walec51 */
public class LivecoinResponseWithDataMap extends LivecoinBaseResponse {

  private final Map data;

  public LivecoinResponseWithDataMap(
      @JsonProperty("success") Boolean success, @JsonProperty("data") Map data) {
    super(success);
    this.data = data;
  }

  public Map getData() {
    return data;
  }
}
