package org.knowm.xchange.enigma.model;

public enum Infrastructure {
  DEVELOPMENT("dev"),
  PRODUCTION("prod");

  private String value;

  private Infrastructure(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}
