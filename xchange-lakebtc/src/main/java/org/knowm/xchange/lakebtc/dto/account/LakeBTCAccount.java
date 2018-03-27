package org.knowm.xchange.lakebtc.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/** User: cristian.lucaci Date: 10/3/2014 Time: 4:53 PM */
public class LakeBTCAccount {

  private final LakeBTCBalance balance;
  private final LakeBTCProfile profile;

  /**
   * Constructor
   *
   * @param balance
   * @param profile
   */
  public LakeBTCAccount(
      @JsonProperty("balance") LakeBTCBalance balance,
      @JsonProperty("profile") LakeBTCProfile profile) {
    this.balance = balance;
    this.profile = profile;
  }

  public LakeBTCBalance getBalance() {
    return balance;
  }

  public LakeBTCProfile getProfile() {
    return profile;
  }
}
