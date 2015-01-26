package com.xeiam.xchange.cryptotrade.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cryptotrade.CryptoTrade;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeBaseResponse;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeException;
import com.xeiam.xchange.cryptotrade.service.CryptoTradeHmacPostBodyDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class CryptoTradeBasePollingService<T extends CryptoTrade> extends BaseExchangeService implements BasePollingService {

  private static final long START_MILLIS = 1356998400000L; // Jan 1st, 2013 in milliseconds from epoch

  protected final String apiKey;
  protected final T cryptoTradeProxy;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param type
   * @param exchange
   */
  public CryptoTradeBasePollingService(Class<T> type, Exchange exchange) {

    super(exchange);

    this.cryptoTradeProxy = RestProxyFactory.createProxy(type, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = CryptoTradeHmacPostBodyDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  protected int nextNonce() {

    return (int) ((System.currentTimeMillis() - START_MILLIS) / 250L);
  }

  protected <R extends CryptoTradeBaseResponse> R handleResponse(R response) {

    final String status = response.getStatus();
    final String error = response.getError();
    if ((status != null && status.equalsIgnoreCase("error")) || (error != null && !error.isEmpty())) {
      throw new ExchangeException(error);
    }

    return response;
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws CryptoTradeException, IOException {

    List<CurrencyPair> currencyPairs = new ArrayList<CurrencyPair>();
    currencyPairs.addAll(cryptoTradeProxy.getPairs().getPairs().keySet());

    return currencyPairs;
  }

}
