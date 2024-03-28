package org.knowm.xchange.exceptions;

import java.util.List;
import lombok.Getter;

/** Exception indicating a requested deposit address has multiple networks and network required */
@Getter
public class DepositAddressAmbiguousException extends ExchangeException {
  private final List<String> networks;

  public DepositAddressAmbiguousException(List<String> networks) {
    super("Deposit Address Not Found");
    this.networks = networks;
  }

  public DepositAddressAmbiguousException(List<String> networks, String message) {
    super(message);
    this.networks = networks;
  }

  public DepositAddressAmbiguousException(List<String> networks, String message, Throwable cause) {
    super(message, cause);
    this.networks = networks;
  }
}
