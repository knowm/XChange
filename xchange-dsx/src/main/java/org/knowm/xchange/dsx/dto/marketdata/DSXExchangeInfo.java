package org.knowm.xchange.dsx.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/** @author Mikhail Wall */
public class DSXExchangeInfo {

  private final long serverTime;
  private final Map<String, DSXPairInfo> pairs;

  public DSXExchangeInfo(
      @JsonProperty("serverTime") long serverTime,
      @JsonProperty("pairs") Map<String, DSXPairInfo> pairs) {

    this.serverTime = serverTime;
    this.pairs = pairs;
  }

  public long getServerTime() {

    return serverTime;
  }

  public Map<String, DSXPairInfo> getPairs() {

    return pairs;
  }

  @Override
  public String toString() {

    return "DSXInfo [serverTime=" + serverTime + ", pairs=" + pairs.toString() + "]";
  }
}
