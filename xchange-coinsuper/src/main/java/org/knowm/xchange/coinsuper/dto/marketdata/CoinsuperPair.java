package org.knowm.xchange.coinsuper.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
  "symbol",
  "quantityMin",
  "quantityMax",
  "priceMin",
  "priceMax",
  "deviationRatio"
})
public class CoinsuperPair {

  @JsonProperty("symbol")
  private String symbol;

  @JsonProperty("quantityMin")
  private String quantityMin;

  @JsonProperty("quantityMax")
  private String quantityMax;

  @JsonProperty("priceMin")
  private String priceMin;

  @JsonProperty("priceMax")
  private String priceMax;

  @JsonProperty("deviationRatio")
  private String deviationRatio;

  /** No args constructor for use in serialization */
  public CoinsuperPair() {}

  /**
   * @param quantityMin
   * @param priceMin
   * @param symbol
   * @param priceMax
   * @param deviationRatio
   * @param quantityMax
   */
  public CoinsuperPair(
      String symbol,
      String quantityMin,
      String quantityMax,
      String priceMin,
      String priceMax,
      String deviationRatio) {
    super();
    this.symbol = symbol;
    this.quantityMin = quantityMin;
    this.quantityMax = quantityMax;
    this.priceMin = priceMin;
    this.priceMax = priceMax;
    this.deviationRatio = deviationRatio;
  }

  @JsonProperty("symbol")
  public String getSymbol() {
    return symbol;
  }

  @JsonProperty("symbol")
  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  @JsonProperty("quantityMin")
  public String getQuantityMin() {
    return quantityMin;
  }

  @JsonProperty("quantityMin")
  public void setQuantityMin(String quantityMin) {
    this.quantityMin = quantityMin;
  }

  @JsonProperty("quantityMax")
  public String getQuantityMax() {
    return quantityMax;
  }

  @JsonProperty("quantityMax")
  public void setQuantityMax(String quantityMax) {
    this.quantityMax = quantityMax;
  }

  @JsonProperty("priceMin")
  public String getPriceMin() {
    return priceMin;
  }

  @JsonProperty("priceMin")
  public void setPriceMin(String priceMin) {
    this.priceMin = priceMin;
  }

  @JsonProperty("priceMax")
  public String getPriceMax() {
    return priceMax;
  }

  @JsonProperty("priceMax")
  public void setPriceMax(String priceMax) {
    this.priceMax = priceMax;
  }

  @JsonProperty("deviationRatio")
  public String getDeviationRatio() {
    return deviationRatio;
  }

  @JsonProperty("deviationRatio")
  public void setDeviationRatio(String deviationRatio) {
    this.deviationRatio = deviationRatio;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("symbol", symbol)
        .append("quantityMin", quantityMin)
        .append("quantityMax", quantityMax)
        .append("priceMin", priceMin)
        .append("priceMax", priceMax)
        .append("deviationRatio", deviationRatio)
        .toString();
  }
}
