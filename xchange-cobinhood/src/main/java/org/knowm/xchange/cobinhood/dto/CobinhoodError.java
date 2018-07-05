package org.knowm.xchange.cobinhood.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CobinhoodError {
  private final String code;

  public CobinhoodError(@JsonProperty("error_code") String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  @Override
  public String toString() {
    return "CobinhoodError{" + "code='" + code + '\'' + '}';
  }
}
