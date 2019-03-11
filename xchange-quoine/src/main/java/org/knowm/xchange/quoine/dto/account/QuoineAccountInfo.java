package org.knowm.xchange.quoine.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;

/** @author timmolter */
public final class QuoineAccountInfo {

  private final BitcoinAccount bitcoinAccount;
  private final FiatAccount[] fiatAccounts;

  /**
   * Constructor
   *
   * @param bitcoinAccount
   * @param fiatAccounts
   */
  public QuoineAccountInfo(
      @JsonProperty("bitcoin_account") BitcoinAccount bitcoinAccount,
      @JsonProperty("fiat_accounts") FiatAccount[] fiatAccounts) {
    this.bitcoinAccount = bitcoinAccount;
    this.fiatAccounts = fiatAccounts;
  }

  public BitcoinAccount getBitcoinAccount() {
    return bitcoinAccount;
  }

  public FiatAccount[] getFiatAccounts() {
    return fiatAccounts;
  }

  @Override
  public String toString() {
    return "QuoineAccountInfo [bitcoinAccount="
        + bitcoinAccount
        + ", fiatAccounts="
        + Arrays.toString(fiatAccounts)
        + "]";
  }
}
