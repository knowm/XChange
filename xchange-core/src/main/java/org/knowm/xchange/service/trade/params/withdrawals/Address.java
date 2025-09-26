package org.knowm.xchange.service.trade.params.withdrawals;

public interface Address {
  default String getLine1() {
    return null;
  }

  default String getLine2() {
    return null;
  }

  default String getCity() {
    return null;
  }

  default String getState() {
    return null;
  }

  default String getCountry() {
    return null;
  }

  default String getPostalCode() {
    return null;
  }
}
