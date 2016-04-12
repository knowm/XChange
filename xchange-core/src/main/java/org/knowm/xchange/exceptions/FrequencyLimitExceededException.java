package org.knowm.xchange.exceptions;

public class FrequencyLimitExceededException extends ExchangeException {

  public FrequencyLimitExceededException(String message) {
    super(message);
  }

  public FrequencyLimitExceededException() {
    super("Too many attempts made in a given time window.");
  }

}
