package com.xeiam.xchange.bitcurex.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitcurex.dto.account.BitcurexFunds;
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
    if (bitcurexFunds.getStatus().equals("error")) {
      throw new ExchangeException("Error getting balance. " + bitcurexFunds.getStatus());
    }
    return bitcurexFunds;
  }

}
