package org.knowm.xchange.wex.v3.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Map;

/**
 * Author: brox Since: 11/12/13 11:00 PM Data object representing multi-currency market data from
 * Wex API v.3
 */
public class WexDepthWrapper {

  private final Map<String, WexDepth> depthMap;

  /**
   * Constructor
   *
   * @param resultV3
   */
  @JsonCreator
  public WexDepthWrapper(Map<String, WexDepth> resultV3) {

    this.depthMap = resultV3;
  }

  public Map<String, WexDepth> getDepthMap() {

    return depthMap;
  }

  public WexDepth getDepth(String pair) {

    WexDepth result = null;
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
