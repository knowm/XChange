package com.xeiam.xchange.coinsetter.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinsetterAccountList {

  private final CoinsetterAccount[] accountList;

  /**
   * @param accountList
   */
  public CoinsetterAccountList(@JsonProperty("accountList") CoinsetterAccount[] accountList) {

    this.accountList = accountList;
  }

  public CoinsetterAccount[] getAccountList() {

    return accountList;
  }

}
