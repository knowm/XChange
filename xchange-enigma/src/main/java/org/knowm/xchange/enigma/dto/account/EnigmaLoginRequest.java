package org.knowm.xchange.enigma.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnigmaLoginRequest {
  @JsonProperty("username")
  private String username;

  @JsonProperty("password")
  private String password;

  public EnigmaLoginRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return this.username;
  }

  public String getPassword() {
    return this.password;
  }
}
