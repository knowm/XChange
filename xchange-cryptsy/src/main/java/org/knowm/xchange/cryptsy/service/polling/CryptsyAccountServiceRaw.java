package org.knowm.xchange.cryptsy.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import javax.annotation.Nullable;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptsy.dto.CryptsyGenericReturn;
import org.knowm.xchange.cryptsy.dto.account.CryptsyAccountInfoReturn;
import org.knowm.xchange.cryptsy.dto.account.CryptsyDepositAddressReturn;
import org.knowm.xchange.cryptsy.dto.account.CryptsyNewAddressReturn;
import org.knowm.xchange.cryptsy.dto.account.CryptsyTransfersReturn;
import org.knowm.xchange.cryptsy.dto.account.CryptsyTxnHistoryReturn;
import org.knowm.xchange.cryptsy.dto.account.CryptsyWithdrawalReturn;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;

/**
 * @author ObsessiveOrange
 */
public class CryptsyAccountServiceRaw extends CryptsyBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptsyAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  /**
   * Retrieves account information, including wallet balances
   *
   * @return CryptsyAccountInfo DTO representing account information
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this
   *         error.
   * @throws IOException
   */
  public CryptsyAccountInfoReturn getCryptsyAccountInfo() throws IOException, ExchangeException {

    return checkResult(cryptsyAuthenticated.getinfo(apiKey, signatureCreator, exchange.getNonceFactory()));
  }

  /**
   * Retrieves transaction history
   *
   * @return CryptsyTxnHistoryReturn DTO representing past transactions made
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this
   *         error.
   * @throws IOException
   */
  public CryptsyTxnHistoryReturn getCryptsyTransactions() throws IOException, ExchangeException {

    return checkResult(cryptsyAuthenticated.mytransactions(apiKey, signatureCreator, exchange.getNonceFactory()));
  }

  /**
   * Generates a new deposit address for the specified currency. Only one of the two (currencyID or currencyCode) is needed
   *
   * @param currencyID Nullable - Integer representation of the currencyID (not normally used)
   * @param currencyCode Nullable - String representation of the currencyCode (Eg: "BTC")
   * @return CryptsyNewAddressReturn representing new deposit address for specified currency
   * @throws ExchangeException if both currencyID and currencyCode are null
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this
   *         error.
   * @throws IOException
   */
  public CryptsyNewAddressReturn generateNewCryptsyDepositAddress(@Nullable Integer currencyID, @Nullable String currencyCode)
      throws IOException, ExchangeException {

    if (currencyID == null && currencyCode == null) {
      throw new ExchangeException("Either currencyID or currencyCode must be supplied. Both cannot be null");
    }

    return checkResult(cryptsyAuthenticated.generatenewaddress(apiKey, signatureCreator, exchange.getNonceFactory(), currencyID, currencyCode));
  }

  /**
   * Gets an map of current deposit addresses
   *
   * @return CryptsyDepositAddressReturn DTO containing map of deposit addresses
   */
  public CryptsyDepositAddressReturn getCurrentCryptsyDepositAddresses() throws IOException, ExchangeException {

    return checkResult(cryptsyAuthenticated.getmydepositaddresses(apiKey, signatureCreator, exchange.getNonceFactory()));
  }

  /**
   * Makes a withdrawal of given amount from Cryptsy to the pre-approved address given. Currency is determined by address of withdrawal
   *
   * @param address Address to withdraw to
   * @param amount Amount to withdraw to address
   * @return CryptsyWithdrawalReturn DTO representing result of request
   * @throws IOException
   */
  public CryptsyWithdrawalReturn makeCryptsyWithdrawal(String address, BigDecimal amount) throws IOException, ExchangeException {

    return checkResult(cryptsyAuthenticated.makewithdrawal(apiKey, signatureCreator, exchange.getNonceFactory(), address, amount));
  }

  /**
   * Get history of transfers (within Cryptsy, send to another user)
   *
   * @return CryptsyTransfersReturn DTO representing past transfers
   */
  public CryptsyTransfersReturn getTransferHistory() throws IOException, ExchangeException {

    return checkResult(cryptsyAuthenticated.mytransfers(apiKey, signatureCreator, exchange.getNonceFactory()));
  }

  /**
   * Stub method for future development
   *
   * @return CryptsyAccountInfo DTO
   */
  public CryptsyGenericReturn<String> getWalletStatus() throws IOException, ExchangeException {

    throw new NotAvailableFromExchangeException();
    // return cryptsy.getwalletstatus(apiKey, signatureCreator,exchange.getNonceFactory());
  }
}
