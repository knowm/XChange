package org.knowm.xchange.abucoins.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO representing the output JSON for the Abucoins <code>GET /time</code> endpoint. Example:
 * <code><pre>
 * {
 *   "iso":"2017-10-10T11:16:50Z",
 *   "epoch":1507634210
 * }
 * </pre></code>
 */
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
