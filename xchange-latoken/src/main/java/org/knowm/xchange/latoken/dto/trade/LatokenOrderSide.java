package org.knowm.xchange.latoken.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.knowm.xchange.exceptions.ExchangeException;

public enum LatokenOrderSide {
  sell,
  buy;

  @JsonCreator
  public static LatokenOrderSide parse(String s) {
    try {
      return LatokenOrderSide.valueOf(s);
    } catch (Exception e) {
      throw new ExchangeException("Unknown LatokenOrderSide " + s + ".");
    }
  }
}
