package org.knowm.xchange.liqui.dto;

public class LiquiException extends RuntimeException {

  private final String message;

  public LiquiException(final String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
