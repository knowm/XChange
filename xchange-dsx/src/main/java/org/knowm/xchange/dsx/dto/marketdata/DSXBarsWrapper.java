package org.knowm.xchange.dsx.dto.marketdata;

import java.util.Map;

/**
 * @author Mikhail Wall
 */

public class DSXBarsWrapper {

  private final Map<String, DSXBar[]> barsMap;

  public DSXBarsWrapper(Map<String, DSXBar[]> barsMap) {

    this.barsMap = barsMap;
  }

  public Map<String, DSXBar[]> getBarsMap() {

    return barsMap;
  }

  public DSXBar[] getBars(String pair) {

    DSXBar[] result = null;
    if (barsMap.containsKey(pair)) {
      result = barsMap.get(pair);
    }
    return result;
  }

  @Override
  public String toString() {

    return "DSXBarsWrapper{" +
        "barsMap=" + barsMap +
        '}';
  }

}
