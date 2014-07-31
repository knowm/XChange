package com.xeiam.xchange.hitbtc.service.polling;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.hitbtc.Hitbtc;
import com.xeiam.xchange.hitbtc.HitbtcAdapters;
import com.xeiam.xchange.hitbtc.service.HitbtcHmacDigest;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author kpysniak
 */
public abstract class HitbtcBasePollingService<T extends Hitbtc> extends BaseExchangeService implements BasePollingService {

  private static final long START_MILLIS = 1356998400000L; // Jan 1st, 2013 in milliseconds from epoch
  private static final AtomicInteger lastNonce = new AtomicInteger((int) ((System.currentTimeMillis() - START_MILLIS) / 250L));

  protected final T hitbtc;
  protected final String apiKey;
  protected final ParamsDigest signatureCreator;
  private final Set<CurrencyPair> currencyPairs;

  /**
   * Constructor Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  protected HitbtcBasePollingService(Class<T> hiitbtcType, ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    this.hitbtc = RestProxyFactory.createProxy(hiitbtcType, exchangeSpecification.getSslUri());
    this.apiKey = exchangeSpecification.getApiKey();
    String apiKey = exchangeSpecification.getSecretKey();
    this.signatureCreator = apiKey != null && !apiKey.isEmpty() ? HitbtcHmacDigest.createInstance(apiKey) : null;
    this.currencyPairs = new HashSet<CurrencyPair>();
  }

  protected long nextNonce() {

    return lastNonce.incrementAndGet();
  }

  @Override
  public synchronized Collection<CurrencyPair> getExchangeSymbols() throws IOException {

    if (currencyPairs.isEmpty()) {
      currencyPairs.addAll(HitbtcAdapters.adaptCurrencyPairs(hitbtc.getSymbols()));
    }

    return currencyPairs;
  }
}
