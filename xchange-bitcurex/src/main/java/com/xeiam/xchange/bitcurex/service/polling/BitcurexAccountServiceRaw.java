package com.xeiam.xchange.bitcurex.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexFunds;
import com.xeiam.xchange.exceptions.ExchangeException;

public class BitcurexAccountServiceRaw extends BitcurexBasePollingService {

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
