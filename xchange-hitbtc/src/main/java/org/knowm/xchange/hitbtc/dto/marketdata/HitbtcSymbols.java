package org.knowm.xchange.hitbtc.dto.marketdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kpysniak
 */
public class HitbtcSymbols {

  private final List<HitbtcSymbol> hitbtcSymbols;

  /**
   * Constructor
   *
   * @param hitbtcSymbols
   */
  public HitbtcSymbols(@JsonProperty("symbols") List<HitbtcSymbol> hitbtcSymbols) {

    this.hitbtcSymbols = hitbtcSymbols;
  }

  public List<HitbtcSymbol> getHitbtcSymbols() {

    return hitbtcSymbols;
  }

  @Override
  public String toString() {

    return "HitbtcSymbols{" + "hitbtcSymbols=" + hitbtcSymbols + '}';
  }
}
