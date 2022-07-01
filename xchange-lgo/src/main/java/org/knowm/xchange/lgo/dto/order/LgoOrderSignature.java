package org.knowm.xchange.lgo.dto.order;

public class LgoOrderSignature {

  private final String value;
  private final String source = "RSA";

  public LgoOrderSignature(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public String getSource() {
    return source;
  }
}
