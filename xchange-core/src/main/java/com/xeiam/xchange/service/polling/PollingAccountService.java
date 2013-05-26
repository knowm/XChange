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
package com.xeiam.xchange.service.polling;

import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
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
   * @return the AccountInfo object, null if some sort of error occurred. Implementers should log the error.
   * @throws ExchangeException - if some error occurs causing a failure in fetching the data
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been implemented
   */
  public AccountInfo getAccountInfo() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException;

  /**
   * Withdraw funds
   * 
   * @param amount The amount
   * @param address The Bitcoin address
   * @return The result of the withdrawal (usually a transaction ID)
   * @throws ExchangeException - if some error occurs causing a failure in fetching the data
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been implemented
   */
  public String withdrawFunds(BigDecimal amount, String address) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException;

  /**
   * Request a bitcoin address to fund this account
   * 
   * @param arguments A Bitcoin deposit address
   * @return the bitcoin address, null if some sort of error occurred. Implementers should log the error.
   * @throws ExchangeException - if some error occurs causing a failure in fetching the data
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been implemented
   */
  public String requestBitcoinDepositAddress(final String... arguments) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException;

  // TODO: Transaction history, trade history
}
