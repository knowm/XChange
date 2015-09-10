package com.xeiam.xchange.bter.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bter.BTERAuthenticated;
import com.xeiam.xchange.bter.dto.BTERBaseResponse;
import com.xeiam.xchange.bter.service.BTERHmacPostBodyDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class BTERBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final String apiKey;
  protected final BTERAuthenticated bter;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTERBasePollingService(Exchange exchange) {

    super(exchange);

    this.bter = RestProxyFactory.createProxy(BTERAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = BTERHmacPostBodyDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    List<CurrencyPair> currencyPairs = new ArrayList<CurrencyPair>(bter.getPairs().getPairs());
    return currencyPairs;
  }

  protected <R extends BTERBaseResponse> R handleResponse(R response) {

    if (!response.isResult()) {
      throw new ExchangeException(response.getMessage());
    }

    return response;
  }

}
