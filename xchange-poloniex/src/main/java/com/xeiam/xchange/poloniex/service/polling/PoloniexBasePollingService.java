package com.xeiam.xchange.poloniex.service.polling;

/**
 * @author Zach Holmes
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.poloniex.Poloniex;
import com.xeiam.xchange.poloniex.PoloniexAuthenticated;
import com.xeiam.xchange.poloniex.PoloniexUtils;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexMarketData;
import com.xeiam.xchange.poloniex.service.PoloniexDigest;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class PoloniexBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final String apiKey;
  protected final PoloniexAuthenticated poloniexAuthenticated;
  protected final ParamsDigest signatureCreator;

  protected final Poloniex poloniex;

  /**
   * Constructor
   *
   * @param exchange
   */
  public PoloniexBasePollingService(Exchange exchange) {

    super(exchange);
    this.poloniexAuthenticated = RestProxyFactory.createProxy(PoloniexAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = PoloniexDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());

    this.poloniex = RestProxyFactory.createProxy(Poloniex.class, exchange.getExchangeSpecification().getSslUri());

  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    List<CurrencyPair> currencyPairs = new ArrayList<CurrencyPair>();

    String command = "returnTicker";
    HashMap<String, PoloniexMarketData> marketData = poloniex.getTicker(command);
    Set<String> pairStrings = marketData.keySet();

    for (String pairString : pairStrings) {
      currencyPairs.add(PoloniexUtils.toCurrencyPair(pairString));
    }

    return currencyPairs;
  }
}
