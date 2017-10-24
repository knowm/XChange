package org.knowm.xchange.bitcurex.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcurex.dto.marketdata.BitcurexFunds;
import org.knowm.xchange.exceptions.ExchangeException;

public class BitcurexAccountServiceRaw extends BitcurexBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitcurexAccountServiceRaw(Exchange exchange) {

    super(exchange);

  }

  public BitcurexFunds getFunds() throws IOException, ExchangeException {

    BitcurexFunds bitcurexFunds = bitcurexAuthenticated.getFunds(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory());
    if (bitcurexFunds.getError() != null) {
      throw new ExchangeException("Error getting balance. " + bitcurexFunds.getError());
    }
    return bitcurexFunds;
  }

}
