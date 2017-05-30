package org.knowm.xchange.dsx.dto.marketdata;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @author Mikhail Wall
 */

public class DSXTickerWrapper {

  private final Map<String, DSXTicker> tickerMap;

  @JsonCreator
  public DSXTickerWrapper(Map<String, DSXTicker> result) {

      this.tickerMap = result;
  }

  public Map<String, DSXTicker> getTickerMap() {

      return tickerMap;
  }

  public DSXTicker getTicker(String pair) {

      DSXTicker result = null;
      if (tickerMap.containsKey(pair)) {
          result = tickerMap.get(pair);
      }
      return result;
  }

  @Override
  public String toString() {

      return "DSXTickerWrapper{" +
              "tickerMap=" + tickerMap.toString() +
              '}';
  }
}
