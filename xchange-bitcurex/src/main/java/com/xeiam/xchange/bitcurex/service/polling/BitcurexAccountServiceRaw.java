package com.xeiam.xchange.bitcurex.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitcurex.BitcurexAuthenticated;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexFunds;
import com.xeiam.xchange.bitcurex.service.BitcurexDigest;
import com.xeiam.xchange.exceptions.ExchangeException;

public class BitcurexAccountServiceRaw extends BitcurexBasePollingService {

  private final BitcurexDigest signatureCreator;
  private final BitcurexAuthenticated bitcurexAuthenticated;

  /**
   * Constructor
   *
   * @param exchange
   * @throws IOException
   */
  public BitcurexAccountServiceRaw(Exchange exchange) {

    super(exchange);

    this.bitcurexAuthenticated = RestProxyFactory.createProxy(BitcurexAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = BitcurexDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(), exchange.getExchangeSpecification()
        .getApiKey());
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
