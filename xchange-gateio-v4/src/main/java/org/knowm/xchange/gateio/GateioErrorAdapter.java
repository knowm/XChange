package org.knowm.xchange.gateio;

import lombok.experimental.UtilityClass;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.exceptions.InstrumentNotValidException;
import org.knowm.xchange.gateio.dto.GateioException;

@UtilityClass
public class GateioErrorAdapter {

  public final String INVALID_SIGNATURE = "INVALID_SIGNATURE";
  public final String INVALID_KEY = "INVALID_KEY";
  public final String INVALID_CURRENCY = "INVALID_CURRENCY";


  public ExchangeException adapt(GateioException e) {

    switch (e.getLabel()) {
      case INVALID_SIGNATURE:
      case INVALID_KEY:
        return new ExchangeSecurityException(e.getMessage(), e);

      case INVALID_CURRENCY:
        return new InstrumentNotValidException(e.getMessage(), e);

      default:
        return new ExchangeException(e.getMessage(), e);
    }

  }

}
