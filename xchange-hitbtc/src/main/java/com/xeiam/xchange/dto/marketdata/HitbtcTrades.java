package com.xeiam.xchange.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * @author kpysniak
 */
public class HitbtcTrades {

  private final HitbtcTrade[] hitbtcTrades;

  /**
   * Constructor
   * @param hitbtcTrades
   */
  public HitbtcTrades(@JsonProperty("trades") HitbtcTrade[] hitbtcTrades) {
    this.hitbtcTrades = hitbtcTrades;
  }

  public HitbtcTrade[] getHitbtcTrades() {
    return hitbtcTrades;
  }

  @Override
  public String toString() {

    return "HitbtcTrades{" +
        "trades=" + Arrays.toString(hitbtcTrades) +
        '}';
  }

  public static enum HitbtcTradesSortOrder {

    SORT_BY_TRADE_ID("trade_id"),
    SORT_BY_TIMESTAMP("ts");

    private final String hitbtcTradesSortOrder;

    HitbtcTradesSortOrder(String hitbtcTradesSortOrder) {
      this.hitbtcTradesSortOrder = hitbtcTradesSortOrder;
    }

    @Override
    public String toString() {
      return hitbtcTradesSortOrder;
    }
  }
}