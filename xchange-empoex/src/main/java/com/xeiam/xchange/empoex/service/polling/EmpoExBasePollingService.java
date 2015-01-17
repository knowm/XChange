package com.xeiam.xchange.empoex.service.polling;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.empoex.EmpoEx;
import com.xeiam.xchange.empoex.EmpoExUtils;
import com.xeiam.xchange.empoex.dto.marketdata.EmpoExTicker;
import com.xeiam.xchange.empoex.service.EmpoExBaseService;
import com.xeiam.xchange.empoex.service.EmpoExHmacPostBodyDigest;
import com.xeiam.xchange.empoex.service.EmpoExPayloadDigest;
import com.xeiam.xchange.service.polling.BasePollingService;

public class EmpoExBasePollingService<T extends EmpoEx> extends EmpoExBaseService implements BasePollingService {

  private static final long START_MILLIS = 1356998400000L;
  private static final AtomicInteger lastNonce = new AtomicInteger((int) ((System.currentTimeMillis() - START_MILLIS) / 250L));

  protected final String apiKey;
  protected final T empoex;
  protected final ParamsDigest signatureCreator;
  protected final ParamsDigest payloadCreator;
  private final Set<CurrencyPair> currencyPairs;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public EmpoExBasePollingService(Class<T> type, ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.empoex = RestProxyFactory.createProxy(type, exchangeSpecification.getSslUri());
    this.apiKey = exchangeSpecification.getApiKey();
    this.signatureCreator = EmpoExHmacPostBodyDigest.createInstance(exchangeSpecification.getSecretKey());
    this.payloadCreator = new EmpoExPayloadDigest();
    this.currencyPairs = new HashSet<CurrencyPair>();
  }

  protected int nextNonce() {

    return lastNonce.incrementAndGet();
  }

  @Override
  public synchronized Collection<CurrencyPair> getExchangeSymbols() throws IOException {

    currencyPairs.clear();
    List<EmpoExTicker> tickers = empoex.getEmpoExTickers();

    for (EmpoExTicker ticker : tickers) {

      currencyPairs.add(EmpoExUtils.toCurrencyPair(ticker.getPairname()));
    }

    return currencyPairs;
  }
}
