package org.knowm.xchange.bitz.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BitZUserAssets {
  private final BigDecimal cny;
  private final BigDecimal usd;
  private final BigDecimal btcTotal;
  private final BitZUserAssetsInfo[] info;

  public BitZUserAssets(
      @JsonProperty("cny") BigDecimal cny,
      @JsonProperty("usd") BigDecimal usd,
      @JsonProperty("btc_total") BigDecimal btcTotal,
      @JsonProperty("info") BitZUserAssetsInfo[] info) {
    this.cny = cny;
    this.usd = usd;
    this.btcTotal = btcTotal;
    this.info = info;
  }

  public BigDecimal getCny() {
    return cny;
  }

  public BigDecimal getUsd() {
    return usd;
  }

  public BigDecimal getBtcTotal() {
    return btcTotal;
  }

  public BitZUserAssetsInfo[] getInfo() {
    return info;
  }
}
