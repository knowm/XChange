package com.knowm.xchange.vertex.signing;

public class EIP712Type {

  private final String name;

  private final String type;

  public EIP712Type(String name, String type) {
    this.name = name;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }
}
