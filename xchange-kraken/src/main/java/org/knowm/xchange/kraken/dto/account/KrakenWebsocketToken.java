package org.knowm.xchange.kraken.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenWebsocketToken {

  @JsonProperty("token")
  private final String token;

  @JsonProperty("expires")
  private final int expiresInSeconds;

  public KrakenWebsocketToken(
      @JsonProperty("token") String token, @JsonProperty("expires") int expiresInSeconds) {
    this.token = token;
    this.expiresInSeconds = expiresInSeconds;
  }

  public String getToken() {
    return token;
  }

  public int getExpiresInSeconds() {
    return expiresInSeconds;
  }

  @Override
  public String toString() {
    return "KrakenWebsocketToken{"
        + "token='"
        + token
        + '\''
        + ", expiresInSeconds="
        + expiresInSeconds
        + '}';
  }
}
