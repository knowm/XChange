package com.xeiam.xchange.bitcurex.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcurex.BitcurexAuthenticated;
import com.xeiam.xchange.bitcurex.BitcurexUtils;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexFunds;
import com.xeiam.xchange.bitcurex.service.BitcurexDigest;
import com.xeiam.xchange.exceptions.ExchangeException;

public class BitcurexAccountServiceRaw extends BitcurexBasePollingService {

  private final BitcurexDigest signatureCreator;
  private final BitcurexAuthenticated bitcurexAuthenticated;

  public BitcurexAccountServiceRaw(ExchangeSpecification exchangeSpecification) throws IOException {

    super(exchangeSpecification);

    this.bitcurexAuthenticated = RestProxyFactory.createProxy(BitcurexAuthenticated.class, exchangeSpecification.getSslUri());
    this.signatureCreator = BitcurexDigest.createInstance(exchangeSpecification.getSecretKey(), exchangeSpecification.getApiKey());
  }

  public BitcurexFunds getFunds() throws IOException, ExchangeException {

    BitcurexFunds bitcurexFunds = bitcurexAuthenticated.getFunds(exchangeSpecification.getApiKey(), signatureCreator, BitcurexUtils.getNonce());
    if (bitcurexFunds.getError() != null) {
      throw new ExchangeException("Error getting balance. " + bitcurexFunds.getError());
    }
    return bitcurexFunds;
  }

}
