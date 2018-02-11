package org.knowm.xchange.bibox.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BiboxDepositAddressCommandBody {
  @JsonProperty("coin_symbol")
  String coinSymbol;
  
  public BiboxDepositAddressCommandBody(String coinSymbol) {
    super();
    this.coinSymbol = coinSymbol;
  }

  public String getCoinSymbol() {
    return coinSymbol;
  }

  public void setCoinSymbol(String coinSymbol) {
    this.coinSymbol = coinSymbol;
  }
}
