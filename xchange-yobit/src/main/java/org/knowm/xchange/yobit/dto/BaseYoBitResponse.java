package org.knowm.xchange.yobit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class BaseYoBitResponse {
  public final boolean success;
  public final Map returnData;

  public BaseYoBitResponse(
      @JsonProperty("success") boolean success, @JsonProperty("return") Map returnData) {
    this.success = success;
    this.returnData = returnData;
  }
}
