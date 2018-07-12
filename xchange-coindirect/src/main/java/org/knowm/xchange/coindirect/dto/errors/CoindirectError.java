package org.knowm.xchange.coindirect.dto.errors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoindirectError {
  public final String parameter;
  public final String message;
  public final String code;

  public CoindirectError(String parameter, String message, String code) {
    this.parameter = parameter;
    this.message = message;
    this.code = code;
  }

  @Override
  public String toString() {
    return "CoindirectError{"
        + "parameter='"
        + parameter
        + '\''
        + ", message='"
        + message
        + '\''
        + ", code='"
        + code
        + '\''
        + '}';
  }
}
