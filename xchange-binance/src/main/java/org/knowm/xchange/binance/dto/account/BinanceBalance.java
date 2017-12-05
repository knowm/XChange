package org.knowm.xchange.binance.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class BinanceBalance {

  public final String asset;
  public final BigDecimal free;
  public final BigDecimal locked;

  public BinanceBalance(@JsonProperty("asset") String asset
      , @JsonProperty("free") BigDecimal free
      , @JsonProperty("locked") BigDecimal locked) {
    this.asset = asset;
    this.free = free;
    this.locked = locked;
  }
}
