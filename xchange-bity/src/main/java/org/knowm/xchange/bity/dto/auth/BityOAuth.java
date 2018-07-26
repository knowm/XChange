package org.knowm.xchange.bity.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BityOAuth {

  private String clientId;

  private String grantType;

  private String username;

  private String password;

  private BityOAuth(
      @JsonProperty("client_id") final String clientId,
      @JsonProperty("grant_type") final String grantType,
      @JsonProperty("username") final String username,
      @JsonProperty("password") final String password) {

    this.clientId = clientId;
    this.grantType = grantType;
    this.username = username;
    this.password = password;
  }

  @Override
  public String toString() {
    return "BityOAuth [clientId="
        + clientId
        + ", grantType="
        + grantType
        + ", username="
        + username
        + ", password="
        + password
        + "]";
  }
}
