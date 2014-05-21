package com.xeiam.xchange.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * @author kpysniak
 */
public class HitbtcSymbol {

  private final String symbol;
  private final BigDecimal step;
  private final BigDecimal lot;

  /**
   * Constructor
   *
   * @param symbol
   * @param step
   * @param lot
   */
  public HitbtcSymbol(@JsonProperty("symbol") String symbol, @JsonProperty("step") BigDecimal step,
                      @JsonProperty("lot") BigDecimal lot) {
    this.symbol = symbol;
    this.step = step;
    this.lot = lot;
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

  @Override
  public String toString() {
    return "HitbtcSymbol{" +
        "symbol='" + symbol + '\'' +
        ", step=" + step +
        ", lot=" + lot +
        '}';
  }
}
