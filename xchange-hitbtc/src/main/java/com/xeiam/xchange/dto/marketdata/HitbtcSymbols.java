package com.xeiam.xchange.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

/**
 * @author kpysniak
 */
public class HitbtcSymbols {

  private final HitbtcSymbol[] hitbtcSymbols;

  /**
   * Constructor
   *
   * @param hitbtcSymbols
   */
  public HitbtcSymbols(@JsonProperty("symbols") HitbtcSymbol[] hitbtcSymbols) {
    this.hitbtcSymbols = hitbtcSymbols;
  }

  public HitbtcSymbol[] getHitbtcSymbols() {
    return hitbtcSymbols;
  }

  @Override
  public String toString() {
    return "HitbtcSymbols{" +
        "hitbtcSymbols=" + Arrays.toString(hitbtcSymbols) +
        '}';
  }
}
