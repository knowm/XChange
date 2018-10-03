package org.knowm.xchange.bitcoinde.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;

public class BitcoindeException extends RuntimeException {

  private final BitcoindeError[] errors;
  private final int credits;

  public BitcoindeException(
      @JsonProperty("errors") BitcoindeError[] errors, @JsonProperty("credits") int credits) {
    this.errors = errors;
    this.credits = credits;
  }

  public BitcoindeError[] getErrors() {
    return errors;
  }

  public int getCredits() {
    return credits;
  }

  @Override
  public String toString() {
    return "BitcoindeException{"
        + "errors="
        + Arrays.toString(errors)
        + ", credits="
        + credits
        + "}";
  }

  @Override
  public String getMessage() {
    return errors[0].getMessage() + " (" + credits + " credits)"; // just the first error message
  }
}
