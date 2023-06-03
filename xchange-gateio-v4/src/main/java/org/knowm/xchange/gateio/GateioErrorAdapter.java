package org.knowm.xchange.gateio;

import lombok.experimental.UtilityClass;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.exceptions.InstrumentNotValidException;
import org.knowm.xchange.gateio.dto.GateioException;

@UtilityClass
public class GateioErrorAdapter {

  public final String INVALID_SIGNATURE = "INVALID_SIGNATURE";
  public final String INVALID_KEY = "INVALID_KEY";
  public final String FORBIDDEN = "FORBIDDEN";
  public final String INVALID_CURRENCY = "INVALID_CURRENCY";
  public final String BALANCE_NOT_ENOUGH = "BALANCE_NOT_ENOUGH";


  public ExchangeException adapt(GateioException e) {

    switch (e.getLabel()) {
      case INVALID_SIGNATURE:
      case INVALID_KEY:
      case FORBIDDEN:
        return new ExchangeSecurityException(e.getMessage(), e);

      case INVALID_CURRENCY:
        return new InstrumentNotValidException(e.getMessage(), e);

      case BALANCE_NOT_ENOUGH:
        return new FundsExceededException(e.getMessage(), e);
      default:
        return new ExchangeException(e.getMessage(), e);
    }

  }

}
