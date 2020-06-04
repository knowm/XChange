package org.knowm.xchange.bittrex.service.batch.order.neworder;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Type {
  LIMIT("LIMIT"),
  MARKET("MARKET"),
  CEILING_LIMIT("CEILING_LIMIT"),
  CEILING_MARKET("CEILING_MARKET");

  private final String type;
}
