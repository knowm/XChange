package org.knowm.xchange.btce.v3.dto.marketdata;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Author: brox Since: 11/12/13 11:00 PM Data object representing multi-currency market data from BTCE API v.3
 */
public class BTCETickerWrapper {

  private final Map<String, BTCETicker> tickerMap;

  /**
   * Constructor
   *
   * @param resultV3
   */
  @JsonCreator
  public BTCETickerWrapper(Map<String, BTCETicker> resultV3) {

    this.tickerMap = resultV3;
  }

  public Map<String, BTCETicker> getTickerMap() {

    return tickerMap;
  }

  public BTCETicker getTicker(String pair) {

    BTCETicker result = null;
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
