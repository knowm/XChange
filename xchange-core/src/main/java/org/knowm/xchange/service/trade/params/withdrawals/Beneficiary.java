package org.knowm.xchange.service.trade.params.withdrawals;

public interface Beneficiary {
  String getId();

  String getName();

  String getAccountNumber();

  Address getAddress();

  String getReference();

  Bank getBank();
}
