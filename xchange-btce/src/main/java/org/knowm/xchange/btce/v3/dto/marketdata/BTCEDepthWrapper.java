package org.knowm.xchange.btce.v3.dto.marketdata;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Author: brox Since: 11/12/13 11:00 PM Data object representing multi-currency market data from BTCE API v.3
 */
public class BTCEDepthWrapper {

  private final Map<String, BTCEDepth> depthMap;

  /**
   * Constructor
   *
   * @param resultV3
   */
  @JsonCreator
  public BTCEDepthWrapper(Map<String, BTCEDepth> resultV3) {

    this.depthMap = resultV3;
  }

  public Map<String, BTCEDepth> getDepthMap() {

    return depthMap;
  }

  public BTCEDepth getDepth(String pair) {

    BTCEDepth result = null;
    if (depthMap.containsKey(pair)) {
      result = depthMap.get(pair);
    }
    return result;
  }

  @Override
  public String toString() {

    return "BTCEDepthV3 [map=" + depthMap.toString() + "]";
  }

}
