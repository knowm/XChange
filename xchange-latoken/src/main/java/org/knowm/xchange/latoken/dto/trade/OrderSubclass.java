package org.knowm.xchange.latoken.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.knowm.xchange.exceptions.ExchangeException;

public enum OrderSubclass {
  limit;

  @JsonCreator
  public static OrderSubclass parse(String s) {
    try {
      return OrderSubclass.valueOf(s);
    } catch (Exception e) {
      throw new ExchangeException("Unknown OrderSubclass " + s + ".");
    }
  }
}
