package org.knowm.xchange.lykke;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.lykke.dto.LykkeError;
import si.mazi.rescu.HttpStatusExceptionSupport;

public class LykkeException extends HttpStatusExceptionSupport {

  private LykkeError error;

  public LykkeException(@JsonProperty("Error") LykkeError error) {
    this.error = error;
  }

  public LykkeError getError() {
    return error;
  }

  @Override
  public String getMessage() {
    return String.format(
        "%s - %s (HTTP status code: %d)", error.getCode(), error.getMessage(), getHttpStatusCode());
  }

  @Override
  public String toString() {
    return "LykkeException{" + "error=" + error + '}';
  }
}
