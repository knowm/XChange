package org.knowm.xchange.bitget;

import lombok.experimental.UtilityClass;
import org.knowm.xchange.bitget.dto.BitgetException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.InstrumentNotValidException;
import org.knowm.xchange.exceptions.OrderAmountUnderMinimumException;

@UtilityClass
public class BitgetErrorAdapter {

  public final int INVALID_PARAMETER_CODE = 40034;
  public final int MIN_ORDER_SIZE = 13008;
  public final int MIN_ORDER_AMOUNT = 45110;
  public final int MIN_ORDER_QTY = 45111;

  public ExchangeException adapt(BitgetException e) {

    switch (e.getCode()) {
      case INVALID_PARAMETER_CODE:
        return new InstrumentNotValidException(e.getMessage(), e);

      case MIN_ORDER_SIZE:
      case MIN_ORDER_AMOUNT:
      case MIN_ORDER_QTY:
        return new OrderAmountUnderMinimumException(e.getMessage(), e);

      default:
        return new ExchangeException(e.getMessage(), e);
    }
  }
}
