/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
public abstract class PollingAccountService {

  private final static ExecutorService executorService = Executors.newCachedThreadPool();

  /**
   * Get account info
   * 
   * @return the AccountInfo object, null if some sort of error occurred. Implementers should log the error.
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  public abstract AccountInfo getAccountInfo() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException;

  /**
   * Withdraw funds from this account. Allows to withdraw digital currency funds from the exchange account to an external address
   * 
   * @param currency The currency to withdraw
   * @param amount The amount to withdraw
   * @param address The destination address
   * @return The result of the withdrawal (usually a transaction ID)
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  public abstract String withdrawFunds(String currency, BigDecimal amount, String address) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException,
      IOException;

  /**
   * Request a digital currency address to fund this account. Allows to fund the exchange account with digital currency from an external address
   * 
   * @param currency The digital currency that corresponds to the desired deposit address.
   * @param arguments
   * @return the internal deposit address to send funds to
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  public abstract String requestDepositAddress(String currency, String... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException;

  // TODO: Transaction history (deposits, withrawals, etc.)

  /*
   * The following methods are Asynchronous versions of the ones above. They create a new thread to retrieve data (thus allowing multiple requests
   * at the same time), and return the Future<?> object. The get() method may be called upon that Future<?> Object to retrieve the result, or block
   * until the result has been retrieved.
   */

  /**
   * Get account info
   * Asynchronous method: will return a Future object that get() can be called upon to retrieve result (or block until result retrieved)
   * 
   * @return The Future<AccountInfo> object. Call get() to retrieve result (or block until result retrieved)
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  public Future<AccountInfo> getAccountInfoAsync() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    class CallableAccountInfoRequest implements Callable<AccountInfo> {

      @Override
      public AccountInfo call() throws Exception {

        return getAccountInfo();
      }

    }
    return executorService.submit(new CallableAccountInfoRequest());
  }

  /**
   * Withdraw funds from this account. Allows to withdraw digital currency funds from the exchange account to an external address
   * Asynchronous method: will return a Future object that get() can be called upon to retrieve result (or block until result retrieved)
   * 
   * @param currency The currency to withdraw
   * @param amount The amount to withdraw
   * @param address The destination address
   * @return The Future<String> object. Call get() to retrieve result (or block until result retrieved)
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  public Future<String> withdrawFundsAsync(final String currency, final BigDecimal amount, final String address) throws ExchangeException, NotAvailableFromExchangeException,
      NotYetImplementedForExchangeException, IOException {

    class CallableWithdrawFundsRequest implements Callable<String> {

      @Override
      public String call() throws Exception {

        return withdrawFunds(currency, amount, address);
      }
    }
    return executorService.submit(new CallableWithdrawFundsRequest());
  }

  /**
   * Request a digital currency address to fund this account. Allows to fund the exchange account with digital currency from an external address
   * Asynchronous method: will return a Future object that get() can be called upon to retrieve result (or block until result retrieved)
   * 
   * @param currency The digital currency that corresponds to the desired deposit address.
   * @param arguments
   * @return The Future<String> object. Call get() to retrieve result (or block until result retrieved)
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  public Future<String> requestDepositAddressAsync(final String currency, final String... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException,
      IOException {

    class CallableDepositAddressRequest implements Callable<String> {

      @Override
      public String call() throws Exception {

        return requestDepositAddress(currency, args);
      }
    }
    return executorService.submit(new CallableDepositAddressRequest());
  }

  /**
   * Retrieves the raw layer for calling of raw methods.
   * 
   * @return
   */
  public abstract Object getRaw();
}
