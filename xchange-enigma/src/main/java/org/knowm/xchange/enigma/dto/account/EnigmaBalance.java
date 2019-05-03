package org.knowm.xchange.enigma.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnigmaBalance {

  private String productNameList;
  private String currencyName;

  public EnigmaBalance(
      @JsonProperty("product name list") String productNameList,
      @JsonProperty("currency name") String currencyName) {
    this.productNameList = productNameList;
    this.currencyName = currencyName;
  }

  public String getCurrencyName() {
    return currencyName;
  }

  public String getProductNameList() {
    return productNameList;
  }
}
