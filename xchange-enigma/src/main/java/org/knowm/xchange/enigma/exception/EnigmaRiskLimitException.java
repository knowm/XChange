package org.knowm.xchange.enigma.exception;

public class EnigmaRiskLimitException extends RuntimeException {

  public EnigmaRiskLimitException() {
    super("Requesting the account's risk limit failed");
  }
}
