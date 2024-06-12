package org.knowm.xchange.binance.dto.meta.exchangeinfo;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class BinanceExchangeInfo {
  private String timezone;

  private List<Symbol> symbols;

  private String serverTime;

  private List<RateLimit> rateLimits;

  private List<String> exchangeFilters;

  private List<String> permissions;
}
