package com.xeiam.xchange.btcchina;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.btcchina.dto.BTCChinaError;

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
