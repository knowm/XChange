package org.knowm.xchange.bitcurex.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitcurexFunds {

  private final BigDecimal eurs;
  private final BigDecimal plns;
  private final BigDecimal btcs;
  private final String address;
  private final String error;

  public BitcurexFunds(@JsonProperty("eurs") BigDecimal eurs, @JsonProperty("plns") BigDecimal plns, @JsonProperty("btcs") BigDecimal btcs,
      @JsonProperty("address") String address, @JsonProperty("error") String error) {

    this.eurs = eurs;
    this.plns = plns;
    this.btcs = btcs;
    this.address = address;
    this.error = error;
  }

  public BigDecimal getEurs() {

    return eurs;
  }

  public BigDecimal getPlns() {

    return plns;
  }

  public BigDecimal getBtcs() {

    return btcs;
  }

  public String getAddress() {

    return address;
  }

  public String getError() {

    return error;
  }
}
