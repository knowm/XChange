
package org.knowm.xchange.hitbtc.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HitbtcStreamingTrade extends HitbtcTrade {

  /**
   * Constructor
   *
   * @param price
   * @param size
   * @param tradeId
   * @param timestamp
   * @param side
   */
  public HitbtcStreamingTrade(@JsonProperty("price") BigDecimal price, @JsonProperty("size") BigDecimal size, @JsonProperty("tradeId") String tradeId,
      @JsonProperty("timestamp") long timestamp, @JsonProperty("side") HitbtcTradeSide side) {
    super(timestamp, price, size, tradeId, side);
  }
}
