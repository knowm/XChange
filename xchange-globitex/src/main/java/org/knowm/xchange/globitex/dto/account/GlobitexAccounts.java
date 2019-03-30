package org.knowm.xchange.globitex.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

public class GlobitexAccounts implements Serializable {

  @JsonProperty("accounts")
  private final List<GlobitexAccount> accounts;

  public GlobitexAccounts(@JsonProperty("accounts") List<GlobitexAccount> accounts) {
    this.accounts = accounts;
  }

  public List<GlobitexAccount> getAccounts() {
    return accounts;
  }

  @Override
  public String toString() {
    return "GlobitexAccounts{" + "accounts=" + accounts + '}';
  }
}
