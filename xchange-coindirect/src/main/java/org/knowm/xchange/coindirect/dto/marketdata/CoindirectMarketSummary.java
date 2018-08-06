package org.knowm.xchange.coindirect.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoindirectMarketSummary {
  public final BigDecimal change24h;
  public final long id;
  public final BigDecimal lastPrice;
  public final BigDecimal volume24h;

  public CoindirectMarketSummary(
      @JsonProperty("change24h") BigDecimal change24h,
      @JsonProperty("id") long id,
      @JsonProperty("lastPrice") BigDecimal lastPrice,
      @JsonProperty("volume24h") BigDecimal volume24h) {
    this.change24h = change24h;
    this.id = id;
    this.lastPrice = lastPrice;
    this.volume24h = volume24h;
  }

  @Override
  public String toString() {
    return "CoindirectMarketSummary{"
        + "change24h="
        + change24h
        + ", id="
        + id
        + ", lastPrice="
        + lastPrice
        + ", volume24h="
        + volume24h
        + '}';
  }
}
