package org.knowm.xchange.dto.meta;

public enum ExchangeHealth {
  ONLINE,
  OFFLINE,

  /** Can only cancel the order but not place order */
  CANCEL_ONLY;
}
