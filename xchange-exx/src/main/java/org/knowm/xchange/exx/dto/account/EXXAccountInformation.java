package org.knowm.xchange.exx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class EXXAccountInformation {

  @JsonProperty("credits")
  private List<Object> credits = null;

  @JsonProperty("funds")
  private Map<String, EXXBalance> balances;

  /** No args constructor for use in serialization */
  public EXXAccountInformation() {}

  /**
   * @param funds
   * @param credits
   */
  public EXXAccountInformation(List<Object> credits, Map<String, EXXBalance> balances) {
    super();
    this.credits = credits;
    this.balances = balances;
  }

  @JsonProperty("credits")
  public List<Object> getCredits() {
    return credits;
  }

  @JsonProperty("credits")
  public void setCredits(List<Object> credits) {
    this.credits = credits;
  }

  @JsonProperty("funds")
  public Map<String, EXXBalance> getBalances() {
    return balances;
  }

  @JsonProperty("funds")
  public void setBalances(Map<String, EXXBalance> balances) {
    this.balances = balances;
  }
}
