package org.knowm.xchange.enigma.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnigmaLoginResponse {
  private boolean result;
  private String key;

  public EnigmaLoginResponse(@JsonProperty boolean result, @JsonProperty String key) {
    this.result = result;
    this.key = key;
  }

  public boolean getResult() {
    return this.result;
  }

  public String getKey() {
    return this.key;
  }
}
