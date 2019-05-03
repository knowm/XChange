package org.knowm.xchange.enigma.dto;

import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.Map;
import org.knowm.xchange.enigma.model.ResponseException;

public class BaseResponse {
  private static Map<Integer, ResponseException> errorCodes;
  protected Integer code;
  protected String message;
  protected Boolean result;

  static {
    errorCodes =
        Arrays.stream(ResponseException.values())
            .collect(toMap(ResponseException::getCode, c -> c));
  }

  public BaseResponse(Integer code, String message, boolean result) {
    this.code = code;
    this.message = message;
    this.result = result;
  }

  public int getCode() {
    return this.code;
  }

  public ResponseException getException() {
    if (this.code == null) {
      return null;
    }
    return errorCodes.getOrDefault(this.code, ResponseException.GENERIC);
  }

  public String getMessage() {
    return this.message;
  }

  public boolean isResult() {
    return this.result;
  }
}
