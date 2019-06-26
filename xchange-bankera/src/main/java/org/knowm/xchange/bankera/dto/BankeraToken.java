package org.knowm.xchange.bankera.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BankeraToken {
  private String accessToken;
  private String tokenType;
  private int expiresIn;
  private Long expirationTime;
  private String scope;
  private String userId;
  private String userName;
  private String environmentCode;
  private String loginIp;
  private String jti;

  public BankeraToken(
      @JsonProperty("access_token") final String accessToken,
      @JsonProperty("token_type") final String tokenType,
      @JsonProperty("expires_in") final int expiresIn,
      @JsonProperty("scope") final String scope,
      @JsonProperty("user_id") final String userId,
      @JsonProperty("user_name") final String userName,
      @JsonProperty("environment_code") final String environmentCode,
      @JsonProperty("login_ip") final String loginIp,
      @JsonProperty("jti") final String jti) {
    this.accessToken = accessToken;
    this.tokenType = tokenType;
    this.expiresIn = expiresIn;
    this.scope = scope;
    this.userId = userId;
    this.userName = userName;
    this.environmentCode = environmentCode;
    this.loginIp = loginIp;
    this.jti = jti;
    this.expirationTime = System.currentTimeMillis() + (this.expiresIn * 1000L);
  }

  public String getAccessToken() {
    return accessToken;
  }

  public String getTokenType() {
    return tokenType;
  }

  public int getExpiresIn() {
    return expiresIn;
  }

  public Long getExpirationTime() {
    return expirationTime;
  }

  public String getScope() {
    return scope;
  }

  public String getUserId() {
    return userId;
  }

  public String getUserName() {
    return userName;
  }

  public String getEnvironmentCode() {
    return environmentCode;
  }

  public String getLoginIp() {
    return loginIp;
  }

  public String getJti() {
    return jti;
  }

  @Override
  public String toString() {
    return "BankeraToken{"
        + "accessToken='"
        + accessToken
        + '\''
        + ", tokenType='"
        + tokenType
        + '\''
        + ", expiresIn="
        + expiresIn
        + ", scope='"
        + scope
        + '\''
        + ", userId='"
        + userId
        + '\''
        + ", userName='"
        + userName
        + '\''
        + ", environmentCode='"
        + environmentCode
        + '\''
        + ", loginIp='"
        + loginIp
        + '\''
        + ", jti='"
        + jti
        + '\''
        + '}';
  }
}
