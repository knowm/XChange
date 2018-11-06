package org.knowm.xchange.bitz.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BitZAssetsData {
  private final BigDecimal bzLock;
  private final BigDecimal bzOver;

  public BitZAssetsData(
      @JsonProperty("bz_lock") BigDecimal bzLock, @JsonProperty("bz_over") BigDecimal bzOver) {
    this.bzLock = bzLock;
    this.bzOver = bzOver;
  }

  public BigDecimal getBzLock() {
    return bzLock;
  }

  public BigDecimal getBzOver() {
    return bzOver;
  }
}
