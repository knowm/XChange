package org.knowm.xchange.binance.dto;

import lombok.Getter;

@Getter
public enum ExchangeType {
  SPOT,
  FUTURES,
  INVERSE,
  PORTFOLIO_MARGIN;
}
