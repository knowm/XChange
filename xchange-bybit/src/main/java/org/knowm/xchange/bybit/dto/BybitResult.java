package org.knowm.xchange.bybit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.Map;
import lombok.Data;
import si.mazi.rescu.ExceptionalReturnContentException;

@Data
public class BybitResult<V> {

  private static final int SUCCESS_CODE = 0;


  @JsonProperty("retCode")
  int retCode;

  @JsonProperty("retMsg")
  String retMsg;

  @JsonProperty("retExtInfo")
  Map extInfo;

  @JsonProperty("result")
  V result;

  @JsonProperty("time")
  Instant timestamp;


  public void setRetCode(int code) {
    if (SUCCESS_CODE != code) {
      throw new ExceptionalReturnContentException(String.valueOf(code));
    }
    retCode = code;
  }



  //todo: remove
  public boolean isSuccess() {
    return retCode == 0;
  }
}
