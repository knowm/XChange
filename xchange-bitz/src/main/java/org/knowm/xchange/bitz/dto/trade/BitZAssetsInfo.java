package org.knowm.xchange.bitz.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BitZAssetsInfo {
  private final long uid;
  private final BigDecimal bzOver;
  private final BigDecimal bzLock;
  private final long created;
  private final long updated;

  public BitZAssetsInfo(
      @JsonProperty("uid") long uid,
      @JsonProperty("bz_over") BigDecimal bzOver,
      @JsonProperty("bz_lock") BigDecimal bzLock,
      @JsonProperty("created") long created,
      @JsonProperty("updated") long updated) {
    this.uid = uid;
    this.bzOver = bzOver;
    this.bzLock = bzLock;
    this.created = created;
    this.updated = updated;
  }

  public long getUid() {
    return uid;
  }

  public BigDecimal getBzOver() {
    return bzOver;
  }

  public BigDecimal getBzLock() {
    return bzLock;
  }

  public long getCreated() {
    return created;
  }

  public long getUpdated() {
    return updated;
  }

  @Override
  public String toString() {
    return "BitZAssetsInfo{"
        + "uid="
        + uid
        + ", bzOver="
        + bzOver
        + ", bzLock="
        + bzLock
        + ", created="
        + created
        + ", updated="
        + updated
        + '}';
  }
}
