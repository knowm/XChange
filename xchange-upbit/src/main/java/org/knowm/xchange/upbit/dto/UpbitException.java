package org.knowm.xchange.upbit.dto;

@SuppressWarnings("serial")
public class UpbitException extends RuntimeException {
  private String message;

  public UpbitException(String message) {
    super();
    this.message = message;
  }

  @Override
  public String toString() {
    return this.message;
  }
}
