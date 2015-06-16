package com.xeiam.xchange.bitcurex.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexResponse;
import com.xeiam.xchange.bitcurex.dto.marketdata.account.BitcurexFunds;
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
    BitcurexResponse<BitcurexFunds> response = bitcurexAuthenticated.getFunds(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());

    if ("ok".equals(response.getStatus())) {
      return response.getData();
    } else {
      throw new ExchangeException("Error getting balance. " + response.getError());
    }
  }

}
