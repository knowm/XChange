package com.xeiam.xchange;

public class FundsExceededException extends IllegalArgumentException {
  private static final long serialVersionUID = 2406896439418334853L;

  public FundsExceededException() {
  }

  public FundsExceededException(String s) {
    super(s);
  }

  public FundsExceededException(String message, Throwable cause) {
    super(message, cause);
  }

  public FundsExceededException(Throwable cause) {
    super(cause);
  }
}
