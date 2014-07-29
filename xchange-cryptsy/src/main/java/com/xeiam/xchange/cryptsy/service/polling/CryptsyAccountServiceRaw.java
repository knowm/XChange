package com.xeiam.xchange.cryptsy.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import javax.annotation.Nullable;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.cryptsy.CryptsyAuthenticated;
import com.xeiam.xchange.cryptsy.dto.CryptsyGenericReturn;
import com.xeiam.xchange.cryptsy.dto.account.CryptsyAccountInfoReturn;
import com.xeiam.xchange.cryptsy.dto.account.CryptsyDepositAddressReturn;
import com.xeiam.xchange.cryptsy.dto.account.CryptsyNewAddressReturn;
import com.xeiam.xchange.cryptsy.dto.account.CryptsyTransfersReturn;
import com.xeiam.xchange.cryptsy.dto.account.CryptsyTxnHistoryReturn;
import com.xeiam.xchange.cryptsy.dto.account.CryptsyWithdrawalReturn;

/**
 * @author ObsessiveOrange
 */
public class CryptsyAccountServiceRaw extends CryptsyBasePollingService<CryptsyAuthenticated> {

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public CryptsyAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(CryptsyAuthenticated.class, exchangeSpecification);
  }

  /**
   * Retrieves account information, including wallet balances
   * 
   * @return CryptsyAccountInfo DTO representing account information
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this error.
   * @throws IOException
   */
  public CryptsyAccountInfoReturn getCryptsyAccountInfo() throws IOException, ExchangeException {

    return checkResult(cryptsy.getinfo(apiKey, signatureCreator, nextNonce()));
  }

  /**
   * Retrieves transaction history
   * 
   * @return CryptsyTxnHistoryReturn DTO representing past transactions made
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this error.
   * @throws IOException
   */
  public CryptsyTxnHistoryReturn getCryptsyTransactions() throws IOException, ExchangeException {

    return checkResult(cryptsy.mytransactions(apiKey, signatureCreator, nextNonce()));
  }

  /**
   * Generates a new deposit address for the specified currency.
   * Only one of the two (currencyID or currencyCode) is needed
   * 
   * @param currencyID Nullable - Integer representation of the currencyID (not normally used)
   * @param currencyCode Nullable - String representation of the currencyCode (Eg: "BTC")
   * @return CryptsyNewAddressReturn representing new deposit address for specified currency
   * @throws ExchangeException if both currencyID and currencyCode are null
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this error.
   * @throws IOException
   */
  public CryptsyNewAddressReturn generateNewCryptsyDepositAddress(@Nullable Integer currencyID, @Nullable String currencyCode) throws IOException, ExchangeException {

    if (currencyID == null && currencyCode == null) {
      throw new ExchangeException("Either currencyID or currencyCode must be supplied. Both cannot be null");
    }

    return checkResult(cryptsy.generatenewaddress(apiKey, signatureCreator, nextNonce(), currencyID, currencyCode));
  }

  /**
   * Gets an map of current deposit addresses
   * 
   * @return CryptsyDepositAddressReturn DTO containing map of deposit addresses
   */
  public CryptsyDepositAddressReturn getCurrentCryptsyDepositAddresses() throws IOException, ExchangeException {

    return checkResult(cryptsy.getmydepositaddresses(apiKey, signatureCreator, nextNonce()));
  }

  /**
   * Makes a withdrawal of given amount from Cryptsy to the pre-approved address given.
   * Currency is determined by address of withdrawal
   * 
   * @param address Address to withdraw to
   * @param amount Amount to withdraw to address
   * @return CryptsyWithdrawalReturn DTO representing result of request
   * @throws IOException
   */
  public CryptsyWithdrawalReturn makeCryptsyWithdrawal(String address, BigDecimal amount) throws IOException, ExchangeException {

    return checkResult(cryptsy.makewithdrawal(apiKey, signatureCreator, nextNonce(), address, amount));
  }

  /**
   * Get history of transfers (within Cryptsy, send to another user)
   * 
   * @return CryptsyTransfersReturn DTO representing past transfers
   */
  public CryptsyTransfersReturn getTransferHistory() throws IOException, ExchangeException {

    return checkResult(cryptsy.mytransfers(apiKey, signatureCreator, nextNonce()));
  }

  /**
   * Stub method for future development
   * 
   * @return CryptsyAccountInfo DTO
   */
  public CryptsyGenericReturn<String> getWalletStatus() throws IOException, ExchangeException {

    throw new NotAvailableFromExchangeException();
    // return cryptsy.getwalletstatus(apiKey, signatureCreator, nextNonce());
  }
}
