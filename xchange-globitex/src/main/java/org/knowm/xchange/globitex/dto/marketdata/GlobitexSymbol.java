package org.knowm.xchange.globitex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "symbol",
  "priceIncrement",
  "sizeIncrement",
  "sizeMin",
  "currency",
  "commodity"
})
public class GlobitexSymbol implements Serializable {

  @JsonProperty("symbol")
  private final String symbol;

  @JsonProperty("priceIncrement")
  private final BigDecimal priceIncrement;

  @JsonProperty("sizeIncrement")
  private final BigDecimal sizeIncrement;

  @JsonProperty("sizeMin")
  private final BigDecimal sizeMin;

  @JsonProperty("currency")
  private final String currency;

  @JsonProperty("commodity")
  private final String commodity;

  /**
   * @param priceIncrement
   * @param commodity
   * @param sizeMin
   * @param symbol
   * @param sizeIncrement
   * @param currency
   */
  public GlobitexSymbol(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("priceIncrement") BigDecimal priceIncrement,
      @JsonProperty("sizeIncrement") BigDecimal sizeIncrement,
      @JsonProperty("sizeMin") BigDecimal sizeMin,
      @JsonProperty("currency") String currency,
      @JsonProperty("commodity") String commodity) {
    super();
    this.symbol = symbol;
    this.priceIncrement = priceIncrement;
    this.sizeIncrement = sizeIncrement;
    this.sizeMin = sizeMin;
    this.currency = currency;
    this.commodity = commodity;
  }

  public String getSymbol() {
    return symbol;
  }

  public BigDecimal getPriceIncrement() {
    return priceIncrement;
  }

  public BigDecimal getSizeIncrement() {
    return sizeIncrement;
  }

  public BigDecimal getSizeMin() {
    return sizeMin;
  }

  public String getCurrency() {
    return currency;
  }

  public String getCommodity() {
    return commodity;
  }

  @Override
  public String toString() {
    return "GlobitexSymbol{"
        + "symbol='"
        + symbol
        + '\''
        + ", priceIncrement='"
        + priceIncrement
        + '\''
        + ", sizeIncrement='"
        + sizeIncrement
        + '\''
        + ", sizeMin='"
        + sizeMin
        + '\''
        + ", currency='"
        + currency
        + '\''
        + ", commodity='"
        + commodity
        + '\''
        + '}';
  }
}
