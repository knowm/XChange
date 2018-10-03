package org.knowm.xchange.therock.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import si.mazi.rescu.HttpStatusExceptionSupport;

public class TheRockException extends HttpStatusExceptionSupport {

  private List<Error> errors;

  protected TheRockException() {}

  public TheRockException(@JsonProperty("errors") List<Error> errors) {
    super(getFirstMessage(errors));
    this.errors = errors;
  }

  private static String getFirstMessage(List<Error> errors) {
    return errors == null || errors.isEmpty() ? null : errors.get(0).getMessage();
  }

  public List<Error> getErrors() {
    return errors;
  }

  public static class Error {
    private String message;
    private Integer code;
    private Map<String, Object> meta;

    public String getMessage() {
      return message;
    }

    public Integer getCode() {
      return code;
    }

    public Map<String, Object> getMeta() {
      return meta;
    }

    @Override
    public String toString() {
      return String.format("Error{message='%s', code=%d, meta=%s}", message, code, meta);
    }
  }
}
