package com.xeiam.xchange.exceptions;

public class FrequencyLimitExceededException extends ExchangeException {

  private static final long serialVersionUID = 2015041101L;

  public FrequencyLimitExceededException(String message) {
    super(message);
  }

  public FrequencyLimitExceededException() {
    super("Too many attempts made in a given time window.");
  }

}
