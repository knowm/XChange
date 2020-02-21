package org.knowm.xchange.gatehub.dto;

public class BearerToken {
  private String token;

  public BearerToken(String token) {
    this.token = token;
  }

  @Override
  public String toString() {
    return String.format("Bearer %s", token);
  }
}
