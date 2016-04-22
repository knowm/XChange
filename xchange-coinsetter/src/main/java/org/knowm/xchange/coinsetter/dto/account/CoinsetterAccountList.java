package org.knowm.xchange.coinsetter.dto.account;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A list of accounts for a specified customer.
 */
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

  @Override
  public String toString() {

    return "CoinsetterAccountList [accountList=" + Arrays.toString(accountList) + "]";
  }

}
