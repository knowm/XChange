package com.xeiam.xchange.btcchina.dto.account;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.btcchina.dto.BTCChinaValue;

/**
 * @author David Yam
 */
public class BTCChinaAccountInfo {

  private final BTCChinaProfile profile;
  private final Map<String, BTCChinaValue> balances;
  private final Map<String, BTCChinaValue> frozens;

  /**
   * Constructor
   * 
   * @param profile account profile
   * @param balances balances for the various currencies
   * @param frozens balances for the various frozen currencies
   */
  public BTCChinaAccountInfo(@JsonProperty("profile") BTCChinaProfile profile, @JsonProperty("balance") Map<String, BTCChinaValue> balances, @JsonProperty("frozen") Map<String, BTCChinaValue> frozens) {

    this.profile = profile;
    this.balances = balances;
    this.frozens = frozens;
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

  @Override
  public String toString() {

    return String.format("BTCChinaAccountInfo{profile=%s, balances=%s, frozens=%s}", profile, balances, frozens);
  }

}
