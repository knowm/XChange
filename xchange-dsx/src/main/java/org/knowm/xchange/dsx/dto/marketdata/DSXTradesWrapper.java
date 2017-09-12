package org.knowm.xchange.dsx.dto.marketdata;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @author Mikhail Wall
 */

public class DSXTradesWrapper {

  private final Map<String, DSXTrade[]> tradesMap;

  @JsonCreator
  public DSXTradesWrapper(Map<String, DSXTrade[]> tradesMap) {

    this.tradesMap = tradesMap;
  }

  public Map<String, DSXTrade[]> getTradesMap() {

    return tradesMap;
  }

  public DSXTrade[] getTrades(String pair) {

    DSXTrade[] result = null;
    if (tradesMap.containsKey(pair)) {
      result = tradesMap.get(pair);
    }
    return result;
  }

  @Override
  public String toString() {

    return "DSXTradesWrapper{" +
        "tradesMap=" + tradesMap +
        '}';
  }
}
