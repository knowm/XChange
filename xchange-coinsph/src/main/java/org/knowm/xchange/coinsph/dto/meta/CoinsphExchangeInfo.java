package org.knowm.xchange.coinsph.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CoinsphExchangeInfo {

  private final String timezone;
  private final long serverTime;
  private final List<CoinsphRateLimit> rateLimits;
  private final List<Object> exchangeFilters;
  private final List<CoinsphSymbol> symbols;

  public CoinsphExchangeInfo(
      @JsonProperty("timezone") String timezone,
      @JsonProperty("serverTime") long serverTime,
      @JsonProperty("rateLimits") List<CoinsphRateLimit> rateLimits,
      @JsonProperty("exchangeFilters") List<Object> exchangeFilters,
      @JsonProperty("symbols") List<CoinsphSymbol> symbols) {
    this.timezone = timezone;
    this.serverTime = serverTime;
    this.rateLimits = rateLimits;
    this.exchangeFilters = exchangeFilters;
    this.symbols = symbols;
  }
}
