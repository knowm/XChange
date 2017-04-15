package org.knowm.xchange.btce.v3.dto.marketdata;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Author: brox Data object representing multi-currency trades from BTCE API v.3
 */
public class BTCETradesWrapper {

  private final Map<String, BTCETrade[]> tradesMap;

  /**
   * Constructor
   *
   * @param tradesMap
   */
  @JsonCreator
  public BTCETradesWrapper(Map<String, BTCETrade[]> tradesMap) {

    this.tradesMap = tradesMap;
  }

  public Map<String, BTCETrade[]> getTradesMap() {

    return tradesMap;
  }

  public BTCETrade[] getTrades(String pair) {

    BTCETrade[] result = null;
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
