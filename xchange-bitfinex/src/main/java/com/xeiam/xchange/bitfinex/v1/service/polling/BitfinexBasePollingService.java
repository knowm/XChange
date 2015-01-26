package com.xeiam.xchange.bitfinex.v1.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitfinex.v1.Bitfinex;
import com.xeiam.xchange.bitfinex.v1.BitfinexAdapters;
import com.xeiam.xchange.bitfinex.v1.service.BitfinexBaseService;
import com.xeiam.xchange.bitfinex.v1.service.BitfinexHmacPostBodyDigest;
import com.xeiam.xchange.bitfinex.v1.service.BitfinexPayloadDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.polling.BasePollingService;

public class BitfinexBasePollingService<T extends Bitfinex> extends BitfinexBaseService implements BasePollingService {

  private static final long START_MILLIS = 1356998400000L; // Jan 1st, 2013 in milliseconds from epoch
  private static final AtomicInteger lastNonce = new AtomicInteger((int) ((System.currentTimeMillis() - START_MILLIS) / 250L));

  protected final String apiKey;
  protected final T bitfinex;
  protected final ParamsDigest signatureCreator;
  protected final ParamsDigest payloadCreator;
  private final List<CurrencyPair> currencyPairs = new ArrayList<CurrencyPair>();;

  /**
   * Constructor
   *
   * @param type
   * @param exchange
   */
  //TODO Look at this TYPE argument
  public BitfinexBasePollingService(Class<T> type, Exchange exchange) {

    super(exchange);
    this.bitfinex = RestProxyFactory.createProxy(type, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = BitfinexHmacPostBodyDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    this.payloadCreator = new BitfinexPayloadDigest();
  }

  protected int nextNonce() {

    return lastNonce.incrementAndGet();
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    List<CurrencyPair> currencyPairs = new ArrayList<CurrencyPair>();
    for (String symbol : bitfinex.getSymbols()) {
      currencyPairs.add(BitfinexAdapters.adaptCurrencyPair(symbol));
    }
    return currencyPairs;
  }
}
