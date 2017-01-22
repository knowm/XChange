package org.knowm.xchange.service.account;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.BaseService;

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
public interface AccountService extends BaseService {

  /**
   * Get account info
   * 
   * @return the AccountInfo object, null if some sort of error occurred. Implementers should log the error.
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been
   *         implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  public AccountInfo getAccountInfo() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException;

  /**
   * Withdraw funds from this account. Allows to withdraw digital currency funds from the exchange account to an external address
   * 
   * @param currency The currency to withdraw
   * @param amount The amount to withdraw
   * @param address The destination address
   * @return The result of the withdrawal (usually a transaction ID)
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been
   *         implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException;

  /**
   * Request a digital currency address to fund this account. Allows to fund the exchange account with digital currency from an external address
   * 
   * @param currency The digital currency that corresponds to the desired deposit address.
   * @param args
   * @return the internal deposit address to send funds to
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been
   *         implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  public String requestDepositAddress(Currency currency, String... args)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException;

  // TODO: Transaction history (deposits, withrawals, etc.)
}
