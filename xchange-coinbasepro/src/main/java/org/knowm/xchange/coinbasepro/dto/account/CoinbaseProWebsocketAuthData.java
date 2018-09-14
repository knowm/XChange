package org.knowm.xchange.coinbasepro.dto.account;

public class CoinbaseProWebsocketAuthData {
  private final String userId;
  private final String key;
  private final String passphrase;
  private final String signature;
  private final long timestamp;

  public CoinbaseProWebsocketAuthData(
      String userId, String key, String passphrase, String signature, long timestamp) {
    this.userId = userId;
    this.key = key;
    this.passphrase = passphrase;
    this.signature = signature;
    this.timestamp = timestamp;
  }

  public String getUserId() {
    return userId;
  }

  public String getKey() {
    return key;
  }

  public String getPassphrase() {
    return passphrase;
  }

  public String getSignature() {
    return signature;
  }

  public long getTimestamp() {
    return timestamp;
  }
}
