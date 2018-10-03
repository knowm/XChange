package org.knowm.xchange.wex.v3.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Map;

/** Author: brox Data object representing multi-currency trades from Wex API v.3 */
public class WexTradesWrapper {

  private final Map<String, WexTrade[]> tradesMap;

  /**
   * Constructor
   *
   * @param tradesMap
   */
  @JsonCreator
  public WexTradesWrapper(Map<String, WexTrade[]> tradesMap) {

    this.tradesMap = tradesMap;
  }

  public Map<String, WexTrade[]> getTradesMap() {

    return tradesMap;
  }

  public WexTrade[] getTrades(String pair) {

    WexTrade[] result = null;
    if (tradesMap.containsKey(pair)) {
      result = tradesMap.get(pair);
    }
    return result;
  }

  @Override
  public String toString() {

    return "BTCETradesV3 [map=" + tradesMap.toString() + "]";
  }
}
