package org.knowm.xchange.btctrade.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCTradeSecretData {

  private final String secret;
  private final String id;
  private final String uid;
  private final String level;
  private final String expires;

  public BTCTradeSecretData(
      @JsonProperty("secret") String secret,
      @JsonProperty("id") String id,
      @JsonProperty("uid") String uid,
      @JsonProperty("level") String level,
      @JsonProperty("expires") String expires) {

    this.secret = secret;
    this.id = id;
    this.uid = uid;
    this.level = level;
    this.expires = expires;
  }

  public String getSecret() {

    return secret;
  }

  public String getId() {

    return id;
  }

  public String getUid() {

    return uid;
  }

  public String getLevel() {

    return level;
  }

  public String getExpires() {

    return expires;
  }
}
