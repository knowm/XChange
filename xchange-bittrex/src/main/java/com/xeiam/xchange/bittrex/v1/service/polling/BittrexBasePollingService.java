package com.xeiam.xchange.bittrex.v1.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bittrex.v1.Bittrex;
import com.xeiam.xchange.bittrex.v1.BittrexAdapters;
import com.xeiam.xchange.bittrex.v1.dto.marketdata.BittrexSymbol;
import com.xeiam.xchange.bittrex.v1.service.BittrexDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class BittrexBasePollingService<T extends Bittrex> extends BaseExchangeService implements BasePollingService {

  private static final long START_MILLIS = 1356998400000L; // Jan 1st, 2013 in milliseconds from epoch
  private static final AtomicInteger lastNonce = new AtomicInteger((int) ((System.currentTimeMillis() - START_MILLIS) / 250L));

  protected final String apiKey;
  protected final T bittrex;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param type
   * @param exchange
   */
  // TODO look at this
  public BittrexBasePollingService(Class<T> type, Exchange exchange) {

    super(exchange);
    this.bittrex = RestProxyFactory.createProxy(type, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = BittrexDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  protected int nextNonce() {

    return lastNonce.incrementAndGet();
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    List<CurrencyPair> currencyPairs = new ArrayList<CurrencyPair>();
    for (BittrexSymbol symbol : bittrex.getSymbols().getSymbols()) {
      currencyPairs.add(BittrexAdapters.adaptCurrencyPair(symbol));
    }
    return currencyPairs;
  }
}
