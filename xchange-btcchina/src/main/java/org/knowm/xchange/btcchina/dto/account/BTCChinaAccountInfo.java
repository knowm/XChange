package org.knowm.xchange.btcchina.dto.account;

import java.util.Map;

import org.knowm.xchange.btcchina.dto.BTCChinaValue;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author David Yam
 */
public class BTCChinaAccountInfo {

  private final BTCChinaProfile profile;
  private final Map<String, BTCChinaValue> balances;
  private final Map<String, BTCChinaValue> frozens;
  private final Map<String, BTCChinaValue> loans;

  /**
   * Constructor
   *
   * @param profile account profile
   * @param balances balances for the various currencies
   * @param frozens balances for the various frozen currencies
   */
  public BTCChinaAccountInfo(@JsonProperty("profile") BTCChinaProfile profile, @JsonProperty("balance") Map<String, BTCChinaValue> balances,
      @JsonProperty("frozen") Map<String, BTCChinaValue> frozens, @JsonProperty("loan") Map<String, BTCChinaValue> loans) {

    this.profile = profile;
    this.balances = balances;
    this.frozens = frozens;
    this.loans = loans;
  }

  /**
   * Get the associated profile.
   *
   * @return the profile
   */
  public BTCChinaProfile getProfile() {

    return profile;
  }

  /**
   * Get the balances.
   *
   * @return the balances
   */
  public Map<String, BTCChinaValue> getBalances() {

    return balances;
  }

  /**
   * Get the frozen balances.
   *
   * @return the frozen balances
   */
  // todo: as above - document 'frozen'
  public Map<String, BTCChinaValue> getFrozens() {

    return frozens;
  }

  /**
   * Get the loaned balances.
   *
   * @return the frozen balances
   */
  public Map<String, BTCChinaValue> getLoans() {

    return loans;
  }

  @Override
  public String toString() {

    return String.format("BTCChinaAccountInfo{profile=%s, balances=%s, frozens=%s}", profile, balances, frozens);
  }

}
