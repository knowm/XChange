package org.knowm.xchange.cryptsy.dto.marketdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ObsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptsyOrderBook {

  private final int marketId;
  private final List<CryptsyBuyOrder> buyOrders;
  private final List<CryptsySellOrder> sellOrders;

  /**
   * Constructor
   */
  @JsonCreator
  public CryptsyOrderBook(@JsonProperty("marketid") int marketId, @JsonProperty("buyorders") List<CryptsyBuyOrder> buyOrders,
      @JsonProperty("sellorders") List<CryptsySellOrder> sellOrders) {

    this.marketId = marketId;
    this.buyOrders = buyOrders;
    this.sellOrders = sellOrders;
  }

  public int marketId() {

    return marketId;
  }

  public List<CryptsyBuyOrder> buyOrders() {

    return buyOrders;
  }

  public List<CryptsySellOrder> sellOrders() {

    return sellOrders;
  }

  @Override
  public String toString() {

    return "CryptsyOrderBook [Market ID='" + marketId + "',Buy Orders='" + buyOrders + "',Sell Orders=" + sellOrders + "]";
  }

}
