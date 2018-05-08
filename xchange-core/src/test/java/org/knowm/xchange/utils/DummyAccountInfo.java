package org.knowm.xchange.utils;

/** A mutable AccountInfo class used for HttpTemplate testing. */
public class DummyAccountInfo {

  String username;
  String currency;
  int amount_int;

  public String getUsername() {

    return username;
  }

  public void setUsername(String username) {

    this.username = username;
  }

  public String getCurrency() {

    return currency;
  }

  public void setCurrency(String currency) {

    this.currency = currency;
  }

  public int getAmount_int() {

    return amount_int;
  }

  public void setAmount_int(int amount_int) {

    this.amount_int = amount_int;
  }
}
