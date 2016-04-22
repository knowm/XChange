package org.knowm.xchange.bleutrade.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bleutrade.BleutradeAdapters;
import org.knowm.xchange.bleutrade.BleutradeAuthenticated;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeMarketsReturn;
import org.knowm.xchange.bleutrade.service.BleutradeDigest;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class BleutradeBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final String apiKey;
  protected final BleutradeAuthenticated bleutrade;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BleutradeBasePollingService(Exchange exchange) {

    super(exchange);

    this.bleutrade = RestProxyFactory.createProxy(BleutradeAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = BleutradeDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    BleutradeMarketsReturn response = bleutrade.getBleutradeMarkets();
    return new ArrayList<CurrencyPair>(BleutradeAdapters.adaptBleutradeCurrencyPairs(response));
  }

}
