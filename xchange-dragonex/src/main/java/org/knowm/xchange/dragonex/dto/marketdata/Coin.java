package org.knowm.xchange.dragonex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coin {

  public final String code;
  public final long coinId;

  public Coin(@JsonProperty("code") String code, @JsonProperty("coin_id") long coinId) {
    this.code = code;
    this.coinId = coinId;
  }

  @Override
  public String toString() {
    return "Coin [" + (code != null ? "code=" + code + ", " : "") + "coinId=" + coinId + "]";
  }
}
