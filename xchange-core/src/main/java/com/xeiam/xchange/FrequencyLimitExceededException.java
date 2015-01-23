package com.xeiam.xchange;

public class FrequencyLimitExceededException extends ExchangeException {
  private static final long serialVersionUID = 2058332277163798808L;

  public FrequencyLimitExceededException(String s) {
    super(s);
  }

  public FrequencyLimitExceededException(String message, Throwable cause) {
    super(message, cause);
  }

}
