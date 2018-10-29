package org.knowm.xchange.bitz.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class BitZCurrencyCoinRate {

  private final BigDecimal usdt;
  private final BigDecimal dkkt;
  private final BigDecimal eth;
  private final BigDecimal cny;
  private final BigDecimal jpy;
  private final BigDecimal usd;
  private final BigDecimal dkk;
  private final BigDecimal btc;

  public BitZCurrencyCoinRate(
      @JsonProperty("usdt") BigDecimal usdt,
      @JsonProperty("dkkt") BigDecimal dkkt,
      @JsonProperty("eth") BigDecimal eth,
      @JsonProperty("cny") BigDecimal cny,
      @JsonProperty("jpy") BigDecimal jpy,
      @JsonProperty("usd") BigDecimal usd,
      @JsonProperty("dkk") BigDecimal dkk,
      @JsonProperty("btc") BigDecimal btc) {
    this.usdt = usdt;
    this.dkkt = dkkt;
    this.eth = eth;
    this.cny = cny;
    this.jpy = jpy;
    this.usd = usd;
    this.dkk = dkk;
    this.btc = btc;
  }

  public BigDecimal getUsdt() {
    return usdt;
  }

  public BigDecimal getDkkt() {
    return dkkt;
  }

  public BigDecimal getEth() {
    return eth;
  }

  public BigDecimal getCny() {
    return cny;
  }

  public BigDecimal getJpy() {
    return jpy;
  }

  public BigDecimal getUsd() {
    return usd;
  }

  public BigDecimal getDkk() {
    return dkk;
  }

  public BigDecimal getBtc() {
    return btc;
  }

  @Override
  public String toString() {
    return "BitZCurrencyCoinRate{"
        + "usdt="
        + usdt
        + ", dkkt="
        + dkkt
        + ", eth="
        + eth
        + ", cny="
        + cny
        + ", jpy="
        + jpy
        + ", usd="
        + usd
        + ", dkk="
        + dkk
        + ", btc="
        + btc
        + '}';
  }
}
