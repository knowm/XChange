package com.xeiam.xchange.bter.service.polling;

import java.io.IOException;
import java.util.Collection;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bter.BTER;
import com.xeiam.xchange.bter.dto.BTERBaseResponse;
import com.xeiam.xchange.bter.service.BTERHmacPostBodyDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class BTERBasePollingService<T extends BTER> extends BaseExchangeService implements BasePollingService {

  private static final long START_MILLIS = 1356998400000L; // Jan 1st, 2013 in milliseconds from epoch

  protected final String apiKey;
  protected final T bter;
  protected final ParamsDigest signatureCreator;
  private Collection<CurrencyPair> pairs;

  public BTERBasePollingService(Class<T> type, ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    this.bter = RestProxyFactory.createProxy(type, exchangeSpecification.getSslUri());
    this.apiKey = exchangeSpecification.getApiKey();
    this.signatureCreator = BTERHmacPostBodyDigest.createInstance(exchangeSpecification.getSecretKey());
  }

  protected int nextNonce() {

    // NOTE: this nonce creation formula is not bullet-proof:
    // - It allows for only one request per .25 seconds,
    // - It will cycle over MAX_INTEGER and start producing illegal negative nonces on January 5, 2030
    return (int) ((System.currentTimeMillis() - START_MILLIS) / 250L);
  }

  @Override
  public synchronized Collection<CurrencyPair> getExchangeSymbols() throws IOException {

    if (pairs == null) {
      pairs = bter.getPairs().getPairs();
    }

    return pairs;
  }

  protected <R extends BTERBaseResponse> R handleResponse(final R response) {

    if (!response.isResult()) {
      throw new ExchangeException(response.getMessage());
    }

    return response;
  }

}
