package com.xeiam.xchange.ripple.service.polling.params;

/**
 * Address of the account for which the trade history is requested.
 */
public interface RippleTradeHistoryAccount {

  public void setAccount(final String value);
  public String getAccount();
}
