package org.knowm.xchange.lakebtc.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/** User: cristian.lucaci Date: 10/3/2014 Time: 4:54 PM */
public class LakeBTCProfile {
  private final String email;
  private final String id;
  private final String btcDepositAddres;

  public LakeBTCProfile(
      @JsonProperty("email") String email,
      @JsonProperty("id") String id,
      @JsonProperty("btc_deposit_addres") String btc_deposit_addres) {
    this.email = email;
    this.id = id;
    this.btcDepositAddres = btc_deposit_addres;
  }

  public String getEmail() {
    return email;
  }

  public String getId() {
    return id;
  }

  public String getBtcDepositAddres() {
    return btcDepositAddres;
  }
}
