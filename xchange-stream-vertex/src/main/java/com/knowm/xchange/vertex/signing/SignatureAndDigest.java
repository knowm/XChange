package com.knowm.xchange.vertex.signing;

public class SignatureAndDigest {
  private final String signature;
  private final String digest;

  public SignatureAndDigest(String signature, String digest) {
    this.signature = signature;
    this.digest = digest;
  }

  public String getSignature() {
    return signature;
  }

  public String getDigest() {
    return digest;
  }

  @Override
  public String toString() {
    return "Sig: " + signature + ", Digest: " + digest;
  }
}
