package org.knowm.xchange.coindirect.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoindirectMarket {
  public final long id;
  public final BigDecimal maximumPrice;
  public final BigDecimal minimumPrice;
  public final BigDecimal maximumQuantity;
  public final BigDecimal minimumQuantity;
  public final String status;
  public final BigDecimal stepSize;
  public final BigDecimal tickSize;
  public final String symbol;
  public final CoindirectMarketSummary summary;

  public CoindirectMarket(
      @JsonProperty("id") long id,
      @JsonProperty("maximumPrice") BigDecimal maximumPrice,
      @JsonProperty("minimumPrice") BigDecimal minimumPrice,
      @JsonProperty("maximumQuantity") BigDecimal maximumQuantity,
      @JsonProperty("minimumQuantity") BigDecimal minimumQuantity,
      @JsonProperty("status") String status,
      @JsonProperty("stepSize") BigDecimal stepSize,
      @JsonProperty("tickSize") BigDecimal tickSize,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("summary") CoindirectMarketSummary summary) {
    this.id = id;
    this.maximumPrice = maximumPrice;
    this.minimumPrice = minimumPrice;
    this.maximumQuantity = maximumQuantity;
    this.minimumQuantity = minimumQuantity;
    this.status = status;
    this.stepSize = stepSize;
    this.tickSize = tickSize;
    this.symbol = symbol;
    this.summary = summary;
  }

  @Override
  public String toString() {
    return "CoindirectMarket{"
        + "id="
        + id
        + ", maximumPrice="
        + maximumPrice
        + ", minimumPrice="
        + minimumPrice
        + ", maximumQuantity="
        + maximumQuantity
        + ", minimumQuantity="
        + minimumQuantity
        + ", status='"
        + status
        + '\''
        + ", stepSize="
        + stepSize
        + ", tickSize="
        + tickSize
        + ", symbol='"
        + symbol
        + '\''
        + ", summary="
        + summary
        + '}';
  }
}
