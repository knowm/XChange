package org.knowm.xchange.gateio;

import lombok.experimental.UtilityClass;
import org.knowm.xchange.exceptions.*;
import org.knowm.xchange.gateio.dto.GateioException;

@UtilityClass
public class GateioErrorAdapter {

  public final String INVALID_SIGNATURE = "INVALID_SIGNATURE";
  public final String INVALID_KEY = "INVALID_KEY";
  public final String FORBIDDEN = "FORBIDDEN";
  public final String INVALID_CURRENCY = "INVALID_CURRENCY";
  public final String BALANCE_NOT_ENOUGH = "BALANCE_NOT_ENOUGH";
  public final String TOO_FAST = "TOO_FAST";
  public final String TOO_MANY_REQUESTS = "TOO_MANY_REQUESTS";
  public final String INVALID_PARAM_VALUE = "INVALID_PARAM_VALUE";
  public final String SERVER_ERROR = "SERVER_ERROR";


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

      case TOO_FAST:
      case TOO_MANY_REQUESTS:
        return new RateLimitExceededException(e.getMessage(), e);

      case SERVER_ERROR:
        return new InternalServerException(e.getMessage(), e);

      case INVALID_PARAM_VALUE:
        if (e.getMessage().contains("below minimum")) {
          return new OrderAmountUnderMinimumException(e.getMessage(), e);
        }
        else {
          return new OrderNotValidException(e.getMessage(), e);
        }

      default:
        return new ExchangeException(e.getMessage(), e);
    }

  }

}
