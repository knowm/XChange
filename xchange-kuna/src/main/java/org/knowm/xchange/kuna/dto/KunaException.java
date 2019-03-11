package org.knowm.xchange.kuna.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.HttpStatusExceptionSupport;

/**
 * Kuna default exception.
 *
 * @author Dat Bui
 */
public class KunaException extends HttpStatusExceptionSupport {

  private String errorCode;

  public KunaException(@JsonProperty("code") String code, @JsonProperty("message") String message) {
    super(message);
    this.errorCode = code;
  }

  public String getErrorCode() {
    return errorCode;
  }
}
