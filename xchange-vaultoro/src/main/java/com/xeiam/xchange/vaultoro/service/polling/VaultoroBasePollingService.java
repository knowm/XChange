package com.xeiam.xchange.vaultoro.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;
import com.xeiam.xchange.vaultoro.VaultoroAuthenticated;
import com.xeiam.xchange.vaultoro.service.VaultoroDigest;

public class VaultoroBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final String apiKey;
  protected final VaultoroAuthenticated vaultoro;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public VaultoroBasePollingService(Exchange exchange) {

    super(exchange);

    this.vaultoro = RestProxyFactory.createProxy(VaultoroAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = VaultoroDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    List<CurrencyPair> pairs = new ArrayList<CurrencyPair>();
    pairs.add(new CurrencyPair("GLD", "BTC"));
    return pairs;
  }
}
