package org.knowm.xchange.bybit.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import si.mazi.rescu.HttpStatusExceptionSupport;

@ToString
@Getter
public class BybitException extends HttpStatusExceptionSupport {

  private final int retCode;
  private final String retMsg;
  private final Object retExtInfo;

  public BybitException(
      @JsonProperty("retCode") int retCode,
      @JsonProperty("retMsg") String retMsg,
      @JsonProperty("retExtInfo") Object retExtInfo) {
    this.retCode = retCode;
    this.retMsg = retMsg;
    this.retExtInfo = retExtInfo;
  }

  @Override
  public String getMessage() {
    return toString();
  }
}
