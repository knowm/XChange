package com.xeiam.xchange.cryptotrade.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cryptotrade.CryptoTradeAuthenticated;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeBaseResponse;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeException;
import com.xeiam.xchange.cryptotrade.service.CryptoTradeHmacPostBodyDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class CryptoTradeBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final String apiKey;
  protected final CryptoTradeAuthenticated cryptoTradeAuthenticated;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptoTradeBasePollingService(Exchange exchange) {

    super(exchange);

    this.cryptoTradeAuthenticated = RestProxyFactory.createProxy(CryptoTradeAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = CryptoTradeHmacPostBodyDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
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
    currencyPairs.addAll(cryptoTradeAuthenticated.getPairs().getPairs().keySet());

    return currencyPairs;
  }

}
