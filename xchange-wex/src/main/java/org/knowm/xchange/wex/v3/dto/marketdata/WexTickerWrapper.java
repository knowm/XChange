package org.knowm.xchange.wex.v3.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Map;

/**
 * Author: brox Since: 11/12/13 11:00 PM Data object representing multi-currency market data from
 * Wex API v.3
 */
public class WexTickerWrapper {

  private final Map<String, WexTicker> tickerMap;

  /**
   * Constructor
   *
   * @param resultV3
   */
  @JsonCreator
  public WexTickerWrapper(Map<String, WexTicker> resultV3) {

    this.tickerMap = resultV3;
  }

  public Map<String, WexTicker> getTickerMap() {

    return tickerMap;
  }

  public WexTicker getTicker(String pair) {

    WexTicker result = null;
    if (tickerMap.containsKey(pair)) {
      result = tickerMap.get(pair);
    }
    return result;
  }

  @Override
  public String toString() {

    return "BTCETickerV3 [map=" + tickerMap.toString() + "]";
  }
}
