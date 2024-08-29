package org.knowm.xchange.bitget;

import lombok.experimental.UtilityClass;
import org.knowm.xchange.bitget.dto.BitgetException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.InstrumentNotValidException;

@UtilityClass
public class BitgetErrorAdapter {

  public final int INVALID_PARAMETER_CODE = 40034;

  public ExchangeException adapt(BitgetException e) {

    switch (e.getCode()) {
      case INVALID_PARAMETER_CODE:
        return new InstrumentNotValidException(e.getMessage(), e);

      default:
        return new ExchangeException(e.getMessage(), e);
    }
  }
}
