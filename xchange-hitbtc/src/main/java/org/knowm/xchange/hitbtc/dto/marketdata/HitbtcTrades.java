package org.knowm.xchange.hitbtc.dto.marketdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kpysniak
 */
public class HitbtcTrades {

  private final List<HitbtcTrade> hitbtcTrades;

  /**
   * Constructor
   *
   * @param hitbtcTrades
   */
  public HitbtcTrades(@JsonProperty("trades") List<HitbtcTrade> hitbtcTrades) {

    this.hitbtcTrades = hitbtcTrades;
  }

  public List<HitbtcTrade> getHitbtcTrades() {

    return hitbtcTrades;
  }

  @Override
  public String toString() {

    return "HitbtcTrades{" + "trades=" + hitbtcTrades.toString() + '}';
  }

  public enum HitbtcTradesSortField {

    SORT_BY_TRADE_ID("trade_id"), SORT_BY_TIMESTAMP("ts");

    private final String hitbtcTradesSortField;

    HitbtcTradesSortField(String hitbtcTradesSortField) {

      this.hitbtcTradesSortField = hitbtcTradesSortField;
    }

    @Override
    public String toString() {

      return hitbtcTradesSortField;
    }
  }

  public enum HitbtcTradesSortDirection {

    SORT_ASCENDING("asc"), SORT_DESCENDING("desc");

    private final String hitbtcTradesSortDirection;

    HitbtcTradesSortDirection(String hitbtcTradesSortDirection) {

      this.hitbtcTradesSortDirection = hitbtcTradesSortDirection;
    }

    @Override
    public String toString() {

      return hitbtcTradesSortDirection;
    }
  }
}
