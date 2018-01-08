package org.knowm.xchange.abucoins.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AbucoinsServerTime {
  String iso;
  long epoch;
        
  public AbucoinsServerTime(@JsonProperty("iso") String iso, @JsonProperty("epoch") long epoch) {
    this.iso = iso;
    this.epoch = epoch;
  }

  public String getIso() {
    return iso;
  }

  public long getEpoch() {
    return epoch;
  }

  @Override
  public String toString() {
    return "AbucoinsServerTime [iso=" + iso + ", epoch=" + epoch + "]";
  }
}
