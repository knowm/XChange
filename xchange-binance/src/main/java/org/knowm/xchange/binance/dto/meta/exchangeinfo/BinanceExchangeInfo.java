package org.knowm.xchange.binance.dto.meta.exchangeinfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BinanceExchangeInfo {
  private String timezone;

  private Symbol[] symbols;

  private String serverTime;

  private RateLimit[] rateLimits;

  private String[] exchangeFilters;

  private String[] permissions;
}
