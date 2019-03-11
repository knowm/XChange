package org.knowm.xchange.coinbase.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** @author jamespedwards42 */
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
