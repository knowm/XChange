package org.knowm.xchange.zaif.dto.marketdata;

import org.knowm.xchange.currency.CurrencyPair;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ZaifMarket {

  @JsonProperty("name")
  private CurrencyPair name;

  @JsonProperty("name")
  public CurrencyPair getName() {
    return name;
  }

  @JsonProperty("name")
  public void setName(CurrencyPair name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "ZaifMarket [name = " + this.getName() + "]";
  }
}
