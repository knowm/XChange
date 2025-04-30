package org.knowm.xchange.bitget.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import si.mazi.rescu.ExceptionalReturnContentException;

@Data
@Builder
@Jacksonized
public class BitgetResponse<T> {

  @JsonProperty("code")
  private Integer code;

  @JsonProperty("msg")
  private String message;

  @JsonProperty("requestTime")
  private Instant requestTime;

  @JsonProperty("data")
  private T data;

  public void setCode(Integer code) {
    if (code != 0) {
      throw new ExceptionalReturnContentException(String.valueOf(code));
    }
    this.code = code;
  }
}
