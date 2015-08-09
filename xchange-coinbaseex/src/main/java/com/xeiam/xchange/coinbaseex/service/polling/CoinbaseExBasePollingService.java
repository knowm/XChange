package com.xeiam.xchange.coinbaseex.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinbaseex.CoinbaseEx;
import com.xeiam.xchange.coinbaseex.CoinbaseExAdapters;
import com.xeiam.xchange.coinbaseex.service.CoinbaseExDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

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
