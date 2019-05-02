package org.knowm.xchange.enigma.exception;

public class EnigmaRequestForQuoteException extends RuntimeException {

  public EnigmaRequestForQuoteException() {
    super("Requesting a quote failed");
  }
}
