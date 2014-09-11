package com.xeiam.xchange.cryptotrade.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptotrade.CryptoTrade;
import com.xeiam.xchange.cryptotrade.CryptoTradeAdapters;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeBaseResponse;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeException;
import com.xeiam.xchange.cryptotrade.service.CryptoTradeHmacPostBodyDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class CryptoTradeBasePollingService<T extends CryptoTrade> extends BaseExchangeService implements BasePollingService {

  private static final long START_MILLIS = 1356998400000L; // Jan 1st, 2013 in milliseconds from epoch

  public final List<CurrencyPair> CURRENCY_PAIRS = new ArrayList<CurrencyPair>();

  protected final String apiKey;
  protected final T cryptoTradeProxy;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public CryptoTradeBasePollingService(Class<T> type, ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    this.cryptoTradeProxy = RestProxyFactory.createProxy(type, exchangeSpecification.getSslUri());
    this.apiKey = exchangeSpecification.getApiKey();
    this.signatureCreator = CryptoTradeHmacPostBodyDigest.createInstance(exchangeSpecification.getSecretKey());
  }

  protected int nextNonce() {

    return (int) ((System.currentTimeMillis() - START_MILLIS) / 250L);
  }

  protected <R extends CryptoTradeBaseResponse> R handleResponse(final R response) {

    final String status = response.getStatus();
    final String error = response.getError();
    if ((status != null && status.equalsIgnoreCase("error")) || (error != null && !error.isEmpty())) {
      throw new ExchangeException(error);
    }

    return response;
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws CryptoTradeException, IOException {

    if (CURRENCY_PAIRS.isEmpty()) {
      CURRENCY_PAIRS.addAll(cryptoTradeProxy.getPairs().getPairs().keySet());
    }
    return CURRENCY_PAIRS;
  }

}
