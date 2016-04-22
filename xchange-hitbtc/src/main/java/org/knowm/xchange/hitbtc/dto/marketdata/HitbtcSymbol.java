package org.knowm.xchange.hitbtc.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties("symbol")
public class HitbtcSymbol {

  private final String commodity;
  private final String currency;
  private final BigDecimal step;
  private final BigDecimal lot;
  private final BigDecimal takeLiquidityRate;
  private final BigDecimal provideLiquidityRate;

  /**
   * Constructor
   *
   * @param commodity base currency
   * @param currency counter currency
   * @param step granularity of price
   * @param lot lot
   */
  public HitbtcSymbol(@JsonProperty("commodity") String commodity, @JsonProperty("currency") String currency, @JsonProperty("step") BigDecimal step,
      @JsonProperty("lot") BigDecimal lot, @JsonProperty("takeLiquidityRate") BigDecimal takeLiquidityRate,
      @JsonProperty("provideLiquidityRate") BigDecimal provideLiquidityRate) {

    this.commodity = commodity;
    this.currency = currency;
    this.step = step;
    this.lot = lot;
    this.takeLiquidityRate = takeLiquidityRate;
    this.provideLiquidityRate = provideLiquidityRate;
  }

  public String getCommodity() {

    return commodity;
  }

  public String getCurrency() {

    return currency;
  }

  public BigDecimal getStep() {

    return step;
  }

  public BigDecimal getLot() {

    return lot;
  }

  public BigDecimal getTakeLiquidityRate() {

    return takeLiquidityRate;
  }

  public BigDecimal getProvideLiquidityRate() {

    return provideLiquidityRate;
  }

  @Override
  public String toString() {

    return "HitbtcSymbol{" + "symbol='" + commodity + '/' + currency + '\'' + ", step=" + step + ", lot=" + lot + ", takeRate=" + takeLiquidityRate
        + ", lot=" + lot

        + '}';
  }
}
