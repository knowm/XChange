package com.xeiam.xchange.bitmarket.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Yaroslav
 * Date: 24/04/15
 * Time: 16:12
 */
public class BitMarketOrdersResponse {

  private final List<BitMarketOrder> buy;
  private final List<BitMarketOrder> sell;

  public BitMarketOrdersResponse(@JsonProperty("buy") List<BitMarketOrder> buy,
                                     @JsonProperty("sell") List<BitMarketOrder> sell) {
    this.buy = buy;
    this.sell = sell;
  }

  public List<BitMarketOrder> getBuy() {
    return buy;
  }

  public List<BitMarketOrder> getSell() {
    return sell;
  }
}
