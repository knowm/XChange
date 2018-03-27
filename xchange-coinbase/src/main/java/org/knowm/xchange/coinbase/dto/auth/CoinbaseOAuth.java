package org.knowm.xchange.coinbase.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

/** @author jamespedwards42 */
public class CoinbaseOAuth {

  private final String accessToken;
  private final String tokenType;
  private final long expiresIn;
  private final String refreshToken;
  private final CoinbaseScope scope;

  private CoinbaseOAuth(
      @JsonProperty("access_token") final String accessToken,
      @JsonProperty("token_type") final String tokenType,
      @JsonProperty("expires_in") final long expiresIn,
      @JsonProperty("refresh_token") final String refreshToken,
      @JsonProperty("scope") final CoinbaseScope scope) {

    this.accessToken = accessToken;
    this.tokenType = tokenType;
    this.expiresIn = expiresIn;
    this.refreshToken = refreshToken;
    this.scope = scope;
  }

  public String getAccessToken() {

    return accessToken;
  }

  public String getTokenType() {

    return tokenType;
  }

  public long getExpiresIn() {

    return expiresIn;
  }

  public String getRefreshToken() {

    return refreshToken;
  }

  public CoinbaseScope getScope() {

    return scope;
  }

  @Override
  public String toString() {

    return "CoinbaseOAuthInfo [accessToken="
        + accessToken
        + ", tokenType="
        + tokenType
        + ", expiresIn="
        + expiresIn
        + ", refreshToken="
        + refreshToken
        + ", scope="
        + scope
        + "]";
  }
}
