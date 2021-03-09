package org.knowm.xchange.dsx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class DsxSymbol {

  private final String id;
  private final String baseCurrency;
  private final String quoteCurrency;
  private final BigDecimal quantityIncrement;
  private final BigDecimal tickSize;
  private final BigDecimal takeLiquidityRate;
  private final BigDecimal provideLiquidityRate;
  private final String feeCurrency;

  public DsxSymbol(
      @JsonProperty("id") String id,
      @JsonProperty("baseCurrency") String baseCurrency,
      @JsonProperty("quoteCurrency") String quoteCurrency,
      @JsonProperty("quantityIncrement") BigDecimal quantityIncrement,
      @JsonProperty("tickSize") BigDecimal tickSize,
      @JsonProperty("takeLiquidityRate") BigDecimal takeLiquidityRate,
      @JsonProperty("provideLiquidityRate") BigDecimal provideLiquidityRate,
      @JsonProperty("feeCurrency") String feeCurrency) {

    this.id = id;
    this.baseCurrency = baseCurrency;
    this.quoteCurrency = quoteCurrency;
    this.quantityIncrement = quantityIncrement;
    this.tickSize = tickSize;
    this.takeLiquidityRate = takeLiquidityRate;
    this.provideLiquidityRate = provideLiquidityRate;
    this.feeCurrency = feeCurrency;
  }

  public String getId() {
    return id;
  }

  public String getBaseCurrency() {
    return baseCurrency;
  }

  public String getQuoteCurrency() {
    return quoteCurrency;
  }

  public BigDecimal getQuantityIncrement() {
    return quantityIncrement;
  }

  public BigDecimal getTickSize() {
    return tickSize;
  }

  public BigDecimal getTakeLiquidityRate() {
    return takeLiquidityRate;
  }

  public BigDecimal getProvideLiquidityRate() {
    return provideLiquidityRate;
  }

  public String getFeeCurrency() {
    return feeCurrency;
  }

  @Override
  public String toString() {
    return "DsxSymbol{"
        + "id='"
        + id
        + '\''
        + ", baseCurrency='"
        + baseCurrency
        + '\''
        + ", quoteCurrency='"
        + quoteCurrency
        + '\''
        + ", quantityIncrement="
        + quantityIncrement
        + ", tickSize="
        + tickSize
        + ", takeLiquidityRate="
        + takeLiquidityRate
        + ", provideLiquidityRate="
        + provideLiquidityRate
        + ", feeCurrency='"
        + feeCurrency
        + '\''
        + '}';
  }
}
