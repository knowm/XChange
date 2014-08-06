package com.xeiam.xchange.hitbtc.service.polling;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import si.mazi.rescu.NonceFactory;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.hitbtc.Hitbtc;
import com.xeiam.xchange.hitbtc.HitbtcAdapters;
import com.xeiam.xchange.hitbtc.service.HitbtcHmacDigest;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;
import si.mazi.rescu.ValueFactory;

/**
 * @author kpysniak, piotr.ladyzynski
 */
public abstract class HitbtcBasePollingService<T extends Hitbtc> extends BaseExchangeService implements BasePollingService {

  /*
   * Workaround for bug reported here https://github.com/timmolter/XChange/pull/581.
   * Pending fix in ResCU at https://github.com/mmazi/rescu/pull/43.
   * TODO Replace with vanilla NonceFactory when fixed.
   */
  protected static final ValueFactory<Long> valueFactory = new NonceFactory() {
    @Override
    public String toString() {
      return Long.toString(createValue());
    }
  };

  protected final T hitbtc;
  protected final String apiKey;
  protected final ParamsDigest signatureCreator;
  private final Set<CurrencyPair> currencyPairs;

  /**
   * Constructor Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  protected HitbtcBasePollingService(Class<T> hitbtcType, ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    this.hitbtc = RestProxyFactory.createProxy(hitbtcType, exchangeSpecification.getSslUri());
    this.apiKey = exchangeSpecification.getApiKey();
    String apiKey = exchangeSpecification.getSecretKey();
    this.signatureCreator = apiKey != null && !apiKey.isEmpty() ? HitbtcHmacDigest.createInstance(apiKey) : null;
    this.currencyPairs = new HashSet<CurrencyPair>();
  }


  @Override
  public synchronized Collection<CurrencyPair> getExchangeSymbols() throws IOException {

    if (currencyPairs.isEmpty()) {
      currencyPairs.addAll(HitbtcAdapters.adaptCurrencyPairs(hitbtc.getSymbols()));
    }

    return currencyPairs;
  }
}
