package com.knowm.xchange.serum.dto;

import java.util.Objects;

public class PublicKey {

  private final String publicKey;

  public PublicKey(final String publicKey) {
    this.publicKey = publicKey;
  }

  public String getKeyString() {
    return publicKey;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PublicKey publicKey1 = (PublicKey) o;
    return Objects.equals(publicKey, publicKey1.publicKey);
  }

  @Override
  public int hashCode() {
    return Objects.hash(publicKey);
  }
}
