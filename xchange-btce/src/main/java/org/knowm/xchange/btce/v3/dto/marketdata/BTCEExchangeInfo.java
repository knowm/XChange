package org.knowm.xchange.btce.v3.dto.marketdata;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author brox
 */
public class BTCEExchangeInfo {

  private final long serverTime;
  private final Map<String, BTCEPairInfo> pairs;

  public BTCEExchangeInfo(@JsonProperty("server_time") long serverTime, @JsonProperty("pairs") Map<String, BTCEPairInfo> pairs) {

    this.serverTime = serverTime;
    this.pairs = pairs;
  }

  public long getServerTime() {

    return serverTime;
  }

  public Map<String, BTCEPairInfo> getPairs() {

    return pairs;
  }

  @Override
  public String toString() {

    return "BTCEInfoV3 [serverTime=" + serverTime + ", pairs=" + pairs.toString() + "]";
  }

}
