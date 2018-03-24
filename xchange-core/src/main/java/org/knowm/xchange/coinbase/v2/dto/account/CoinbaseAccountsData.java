package org.knowm.xchange.coinbase.v2.dto.account;

import java.util.Collections;
import java.util.List;

import org.knowm.xchange.coinbase.v2.dto.account.CoinbaseAccountData.CoinbaseAccount;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinbaseAccountsData {

  private final List<CoinbaseAccount> data;

  private CoinbaseAccountsData(@JsonProperty("data") final List<CoinbaseAccount> accounts) {
    this.data = accounts;
  }

  public List<CoinbaseAccount> getData() {
    return Collections.unmodifiableList(data);
  }

  @Override
  public String toString() {
    return "" + data;
  }

}
