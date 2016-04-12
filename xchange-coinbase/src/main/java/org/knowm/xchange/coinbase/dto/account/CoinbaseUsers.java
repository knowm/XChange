package org.knowm.xchange.coinbase.dto.account;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author jamespedwards42
 */
public class CoinbaseUsers {

  private final List<CoinbaseUser> users;

  private CoinbaseUsers(@JsonProperty("users") final List<CoinbaseUser> users) {

    this.users = users;
  }

  public List<CoinbaseUser> getUsers() {

    return users;
  }

  @Override
  public String toString() {

    return "CoinbaseUsers [users=" + users + "]";
  }

}
