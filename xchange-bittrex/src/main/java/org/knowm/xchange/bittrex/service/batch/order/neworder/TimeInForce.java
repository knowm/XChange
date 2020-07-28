package org.knowm.xchange.bittrex.service.batch.order.neworder;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TimeInForce {
  GOOD_TIL_CANCELLED("GOOD_TIL_CANCELLED"),
  IMMEDIATE_OR_CANCEL("IMMEDIATE_OR_CANCEL"),
  FILL_OR_KILL("FILL_OR_KILL"),
  POST_ONLY_GOOD_TIL_CANCELLED("POST_ONLY_GOOD_TIL_CANCELLED"),
  BUY_NOW("BUY_NOW");

  private final String timeInForce;
}
