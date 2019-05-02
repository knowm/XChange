package org.knowm.xchange.enigma.exception;

public class EnigmaOrderIssueException extends RuntimeException {

  public EnigmaOrderIssueException() {
    super("Order creation failed");
  }
}
