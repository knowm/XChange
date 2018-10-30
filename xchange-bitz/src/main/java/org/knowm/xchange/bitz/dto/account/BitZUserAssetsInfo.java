package org.knowm.xchange.bitz.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BitZUserAssetsInfo {
  private final String name;
  private final BigDecimal num;
  private final BigDecimal over;
  private final BigDecimal lock;
  private final BigDecimal btc;
  private final BigDecimal usd;
  private final BigDecimal cny;

  public BitZUserAssetsInfo(
      @JsonProperty("name") String name,
      @JsonProperty("num") BigDecimal num,
      @JsonProperty("over") BigDecimal over,
      @JsonProperty("lock") BigDecimal lock,
      @JsonProperty("btc") BigDecimal btc,
      @JsonProperty("usd") BigDecimal usd,
      @JsonProperty("cny") BigDecimal cny) {
    this.name = name;
    this.num = num;
    this.over = over;
    this.lock = lock;
    this.btc = btc;
    this.usd = usd;
    this.cny = cny;
  }

  public String getName() {
    return name;
  }

  public BigDecimal getNum() {
    return num;
  }

  public BigDecimal getOver() {
    return over;
  }

  public BigDecimal getLock() {
    return lock;
  }

  public BigDecimal getBtc() {
    return btc;
  }

  public BigDecimal getUsd() {
    return usd;
  }

  public BigDecimal getCny() {
    return cny;
  }

  @Override
  public String toString() {
    return "BitZUserAssetsInfo{"
        + "name='"
        + name
        + '\''
        + ", num="
        + num
        + ", over="
        + over
        + ", lock="
        + lock
        + ", btc="
        + btc
        + ", usd="
        + usd
        + ", cny="
        + cny
        + '}';
  }
}
