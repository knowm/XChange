package com.xeiam.xchange.hitbtc.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kpysniak
 */
public class HitbtcSymbol {

  private final String symbol;
  private final BigDecimal step;
  private final BigDecimal lot;
  private final String currency;
  private final String commodity;

  /**
   * Constructor
   * 
   * @param symbol
   * @param step
   * @param lot
   */
  public HitbtcSymbol(@JsonProperty("symbol") String symbol, @JsonProperty("step") BigDecimal step, @JsonProperty("lot") BigDecimal lot, @JsonProperty("currency") String currency, @JsonProperty("commodity") String commodity) {

    this.symbol = symbol;
    this.step = step;
    this.lot = lot;
    this.currency = currency;
    this.commodity = commodity;
  }

  public String getSymbol() {

    return symbol;
  }

  public BigDecimal getStep() {

    return step;
  }

  public BigDecimal getLot() {

    return lot;
  }

  public String getCommodity() {
    return commodity;
  }

  public String getCurrency() {
    return currency;
  }

  @Override
  public String toString() {

    return "HitbtcSymbol{" + "symbol='" + symbol + '\'' + ", step=" + step + ", lot=" + lot + '}';
  }
}
