package org.knowm.xchange.cryptofacilities.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptofacilities.dto.account.CryptoFacilitiesAccount;
import org.knowm.xchange.cryptofacilities.dto.account.CryptoFacilitiesAccounts;
import org.knowm.xchange.exceptions.ExchangeException;

/**
 * @author Jean-Christophe Laruelle
 */

public class CryptoFacilitiesAccountServiceRaw extends CryptoFacilitiesBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptoFacilitiesAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public CryptoFacilitiesAccount getCryptoFacilitiesAccount() throws IOException {

    CryptoFacilitiesAccount cryptoFacilitiesAccount = cryptoFacilities.account(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory());

    if (cryptoFacilitiesAccount.isSuccess()) {
      return cryptoFacilitiesAccount;
    } else {
      throw new ExchangeException("Error getting CF account info: " + cryptoFacilitiesAccount.getError());
    }
  }

  public CryptoFacilitiesAccounts getCryptoFacilitiesAccounts() throws IOException {

    CryptoFacilitiesAccounts cryptoFacilitiesAccounts = cryptoFacilities.accounts(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory());

    if (cryptoFacilitiesAccounts.isSuccess()) {
      return cryptoFacilitiesAccounts;
    } else {
      throw new ExchangeException("Error getting CF accounts info: " + cryptoFacilitiesAccounts.getError());
    }
  }
}
