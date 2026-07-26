package org.knowm.xchange.cryptocom.dto;

import lombok.Getter;
import si.mazi.rescu.HttpStatusExceptionSupport;

@Getter
public class CryptoComException extends HttpStatusExceptionSupport {

  private final int code;

  public CryptoComException(int code, String message) {
    super(message);
    this.code = code;
  }

  @Override
  public String getMessage() {
    return String.format("[%d] %s", code, super.getMessage());
  }
}
