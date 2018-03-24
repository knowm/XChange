package org.knowm.xchange.ripple.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RippleOrderCancelRequest {

  @JsonProperty("secret")
  private String secret;

  public String getSecret() {
    return secret;
  }

  public void setSecret(final String value) {
    secret = value;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName(); // do not log secret
  }
}
