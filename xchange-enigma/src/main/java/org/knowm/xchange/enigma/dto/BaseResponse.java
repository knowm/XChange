package org.knowm.xchange.enigma.dto;

import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.Map;
import lombok.NoArgsConstructor;
import org.knowm.xchange.enigma.model.ResponseException;

@NoArgsConstructor
public class BaseResponse {
  private static Map<Integer, ResponseException> errorCodes;
  private Integer code;
  private String message;
  private Boolean result;

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
