package org.knowm.xchange.service.trade.params.withdrawals;

public interface Bank {
  String getName();

  String getCode();

  Address getAddress();
}
