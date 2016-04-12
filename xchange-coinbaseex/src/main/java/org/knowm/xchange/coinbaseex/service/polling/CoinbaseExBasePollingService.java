package org.knowm.xchange.coinbaseex.service.polling;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbaseex.CoinbaseEx;
import org.knowm.xchange.coinbaseex.CoinbaseExAdapters;
import org.knowm.xchange.coinbaseex.service.CoinbaseExDigest;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

/**
 * Created by Yingzhe on 4/6/2015.
 */
public class CoinbaseExBasePollingService<T extends CoinbaseEx> extends BaseExchangeService implements BasePollingService {

  protected final T coinbaseEx;
  protected final ParamsDigest digest;

  protected final String apiKey;
  protected final String passphrase;

  protected CoinbaseExBasePollingService(Class<T> type, Exchange exchange) {

    super(exchange);
    this.coinbaseEx = RestProxyFactory.createProxy(type, exchange.getExchangeSpecification().getSslUri());
    this.digest = CoinbaseExDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());

    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.passphrase = (String) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("passphrase");
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {
    return CoinbaseExAdapters.adaptProductsToSupportedExchangeSymbols(coinbaseEx.getProducts());
  }

  protected String getTimestamp() {
    return String.valueOf(System.currentTimeMillis() / 1000);
  }
}
