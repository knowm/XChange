package org.knowm.xchange.bybit;

import java.util.HashMap;
import java.util.Map;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.knowm.xchange.bybit.service.BybitException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.InstrumentNotValidException;

@UtilityClass
public class BybitErrorAdapter {

  private final Map<String, Class<? extends ExchangeException>> EXCEPTION_BY_MESSAGE = new HashMap<>();

  static {
    EXCEPTION_BY_MESSAGE.put("Not supported symbols", InstrumentNotValidException.class);
  }

  @SneakyThrows
  public ExchangeException adapt(BybitException e) {

    if (EXCEPTION_BY_MESSAGE.containsKey(e.getRetMsg())) {
      Class<? extends ExchangeException> a = EXCEPTION_BY_MESSAGE.get(e.getRetMsg());
      return a.getConstructor(String.class, Throwable.class).newInstance(e.getMessage(), e);
    }

    return new ExchangeException(e.getMessage(), e);

  }

}
