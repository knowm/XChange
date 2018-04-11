package org.knowm.xchange.wex.v3.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/** @author brox */
public class WexExchangeInfo {

  private final long serverTime;
  private final Map<String, WexPairInfo> pairs;

  public WexExchangeInfo(
      @JsonProperty("server_time") long serverTime,
      @JsonProperty("pairs") Map<String, WexPairInfo> pairs) {

    this.serverTime = serverTime;
    this.pairs = pairs;
  }

  public long getServerTime() {

    return serverTime;
  }

  public Map<String, WexPairInfo> getPairs() {

    return pairs;
  }

  @Override
  public String toString() {

    return "BTCEInfoV3 [serverTime=" + serverTime + ", pairs=" + pairs.toString() + "]";
  }
}
