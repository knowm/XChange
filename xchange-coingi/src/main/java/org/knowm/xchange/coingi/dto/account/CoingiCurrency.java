package org.knowm.xchange.coingi.dto.account;

public class CoingiCurrency {
  private boolean crypto;

  private String name;

  public boolean getCrypto() {
    return crypto;
  }

  public void setCrypto(boolean crypto) {
    this.crypto = crypto;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
