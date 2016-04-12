package org.knowm.xchange.btcchina;

import org.knowm.xchange.btcchina.dto.BTCChinaError;
import org.knowm.xchange.exceptions.ExchangeException;

public class BTCChinaExchangeException extends ExchangeException {

  private static final long serialVersionUID = 2014082501L;

  private final BTCChinaError error;

  public BTCChinaExchangeException(BTCChinaError error) {

    super(error.getMessage());
    this.error = error;
  }

  public int getErrorCode() {

    return error.getCode();
  }

}
