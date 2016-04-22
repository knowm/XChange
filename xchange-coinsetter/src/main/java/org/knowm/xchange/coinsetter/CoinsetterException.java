package org.knowm.xchange.coinsetter;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.exceptions.ExchangeException;

/**
 * Coinsetter exception.
 */
public class CoinsetterException extends ExchangeException {

  private static final long serialVersionUID = 2014101801L;

  public CoinsetterException(@JsonProperty("error") String message) {

    super(message);
  }

}
