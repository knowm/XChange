package org.knowm.xchange.kucoin;

import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.exceptions.InstrumentNotValidException;
import org.knowm.xchange.kucoin.dto.KucoinException;

public final class KucoinErrorAdapter {
  /**
   * 200004	Balance insufficient
   */
  public static final int BALANCE_INSUFFICIENT = 200004;

  /**
   * 400001	Missing security header
   */
  public static final int MISSING_SECURITY_HEADER = 400001;

  /**
   * 900001	symbol not exists
   */
  public static final int SYMBOL_NOT_EXISTS = 900001;

  /**
   * 900003	Currency not exists
   */
  public static final int CURRENCY_NOT_EXISTS = 900003;

  private KucoinErrorAdapter() {
  }

  public static ExchangeException adapt(KucoinException e) {
    String message = e.getMessage();
    if (StringUtils.isEmpty(message)) {
      message = "Operation failed without any error message";
    }
    switch (e.getCode()) {
      case BALANCE_INSUFFICIENT:
        return new FundsExceededException(e.getMessage(), e);
      case SYMBOL_NOT_EXISTS:
        return new CurrencyPairNotValidException(e.getMessage(), e);
      case CURRENCY_NOT_EXISTS:
        return new InstrumentNotValidException(e.getMessage(), e);
      case MISSING_SECURITY_HEADER:
        return new ExchangeSecurityException(e.getMessage(), e);
      default:
        return new ExchangeException(message, e);
    }
  }

}
