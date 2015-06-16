package com.xeiam.xchange.bitcurex.dto.marketdata.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitcurexFunds {

  private final BigDecimal eur;
  private final BigDecimal pln;
  private final BigDecimal btc;
  private final BigDecimal usd;
  private final BigDecimal fee;

  public BitcurexFunds(@JsonProperty("eur") BigDecimal eur,
                       @JsonProperty("pln") BigDecimal pln,
                       @JsonProperty("btc") BigDecimal btc,
                       @JsonProperty("usd") BigDecimal usd,
                       @JsonProperty("fee") BigDecimal fee) {

    this.eur = eur;
    this.pln = pln;
    this.btc = btc;
    this.usd = usd;
    this.fee = fee;
  }

  public BigDecimal getEur() {

    return eur;
  }

  public BigDecimal getPln() {

    return pln;
  }

  public BigDecimal getBtc() {

    return btc;
  }

  public BigDecimal getUsd() {
    return usd;
  }

  public BigDecimal getFee() {
    return fee;
  }

  @Override
  public String toString() {
    return "BitcurexFunds{" +
        "eur=" + eur +
        ", pln=" + pln +
        ", btc=" + btc +
        ", usd=" + usd +
        ", fee=" + fee +
        '}';
  }
}
