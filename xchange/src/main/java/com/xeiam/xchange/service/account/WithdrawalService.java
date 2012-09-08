package com.xeiam.xchange.service.account;

import org.joda.money.BigMoney;

/**
 * <p>
 * Interface to provide the following to {@link com.xeiam.xchange.Exchange}:
 * </p>
 * <ul>
 * <li>Standard methods available to allow withdrawals from an exchange account</li>
 * </ul>
 * <p>All exchanges are unique in the way that they permit their customers to withdraw funds. To that end this
 * API forces the implementation of a {@link WithdrawalRequestBuilder} that allows an individual exchange
 * to construct the necessary values as required.
 * </p>
 */
public interface WithdrawalService {

  /**
   * @return A suitable withdrawal request builder for the given exchange
   */
  WithdrawalRequestBuilder newWithdrawalRequest();
}
