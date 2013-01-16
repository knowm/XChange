/**
 * Copyright 2012 Xeiam LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xeiam.xchange.service.account.polling;

import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.account.AccountInfo;

/**
 * <p>
 * Interface to provide the following to {@link Exchange}:
 * </p>
 * <ul>
 * <li>Standard methods available to explore send/receive account-related data</li>
 * </ul>
 * <p>
 * The implementation of this service is expected to be based on a client polling mechanism of some kind
 * </p>
 */
public interface PollingAccountService {

  /**
   * Get account info
   * 
   * @return the account info
   */
  public AccountInfo getAccountInfo();

  /**
   * Withdraw funds
   * 
   * @param amount
   * @param address
   * @return
   */
  public String withdrawFunds(BigDecimal amount, String address);

  /**
   * Request a bitcoin address to fund this account
   * 
   * @param arguments
   * @return the bitcoin address
   */
  public String requestBitcoinDepositAddress(final String... arguments);

  // TODO: Transaction history, trade history
}
