package com.xeiam.xchange.coinbase.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinbaseUser {

  private final String id;
  private final String email;
  private final String name;

  public CoinbaseUser(@JsonProperty("id") final String id, @JsonProperty("email") final String email, @JsonProperty("name") final String name) {

    this.id = id;
    this.email = email;
    this.name = name;
  }

  public String getId() {

    return id;
  }

  public String getEmail() {

    return email;
  }

  public String getName() {

    return name;
  }

  @Override
  public String toString() {

    return "CoinbaseUser [id=" + id + ", email=" + email + ", name=" + name + "]";
  }

}
