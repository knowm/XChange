package org.knowm.xchange.bybit.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.HttpStatusExceptionSupport;

public class BybitException extends HttpStatusExceptionSupport {

  private final int retCode;
  private final String retMsg;
  private final String extCode;
  private final String extInfo;

  public BybitException(
      @JsonProperty("ret_code") int retCode,
      @JsonProperty("ret_msg") String retMsg,
      @JsonProperty("ext_code") String extCode,
      @JsonProperty("ext_info") String extInfo) {
    this.retCode = retCode;
    this.retMsg = retMsg;
    this.extCode = extCode;
    this.extInfo = extInfo;
  }

  @Override
  public String getMessage() {
    return "{"
        + "retCode="
        + retCode
        + ", retMsg='"
        + retMsg
        + '\''
        + ", extCode='"
        + extCode
        + '\''
        + ", extInfo='"
        + extInfo
        + '\''
        + '}';
  }

  @Override
  public String toString() {
    return "BybitException{"
        + "retCode="
        + retCode
        + ", retMsg='"
        + retMsg
        + '\''
        + ", extCode='"
        + extCode
        + '\''
        + ", extInfo='"
        + extInfo
        + '\''
        + '}';
  }
}
