package org.knowm.xchange.hitbtc.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HitbtcTime {

  private final long timestamp;

  /**
   * Constructor
   *
   * @param timestamp
   */
  public HitbtcTime(@JsonProperty("timestamp") long timestamp) {

    this.timestamp = timestamp;
  }

  public long getTimestamp() {

    return timestamp;
  }

  @Override
  public String toString() {
    return "HitbtcTime{" + "timestamp=" + timestamp + "}";
  }
}
