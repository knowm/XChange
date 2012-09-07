package com.xeiam.xchange.service.account;

import org.joda.money.BigMoney;

/**
 * <p>
 * Interface to provide the following to {@link com.xeiam.xchange.Exchange}:
 * </p>
 * <ul>
 * <li>Standard methods available to allow withdrawals from an exchange account</li>
 * </ul>
 * <p>
 * The implementation of this service is expected to be based on a client polling mechanism of some kind
 * </p>
 */
public interface WithdrawalService {

  /**
   *
   * @param amount
   * @return
   */
  WithdrawalResponse withdraw(BigMoney amount);

}
