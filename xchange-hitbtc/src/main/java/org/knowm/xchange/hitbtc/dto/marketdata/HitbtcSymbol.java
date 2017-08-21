package org.knowm.xchange.hitbtc.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HitbtcSymbol {


//  {
//    "id": "BTCUSD",
//      "baseCurrency": "BTC",
//      "quoteCurrency": "USD",
//      "quantityIncrement": "0.01",
//      "tickSize": "0.01",
//      "takeLiquidityRate": "0.001",
//      "provideLiquidityRate": "-0.0001",
//      "feeCurrency": "USD"
//  },

  private final String id;
  private final String baseCurrency;
  private final String quoteCurrency;
  private final BigDecimal quantityIncrement;
  private final BigDecimal tickSize;
  private final BigDecimal takeLiquidityRate;
  private final BigDecimal provideLiquidityRate;
  private final String feeCurrency;

  public HitbtcSymbol(
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

    return "HitbtcSymbol{" + "symbol='" + id + '\'' + ", baseCurrency=" + baseCurrency + ", quoteCurrency=" + quoteCurrency + ", takeRate=" + takeLiquidityRate
        + ", feeCurrency=" + feeCurrency + '}';
  }


}
