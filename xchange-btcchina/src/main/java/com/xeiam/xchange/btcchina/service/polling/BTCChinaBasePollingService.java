package com.xeiam.xchange.btcchina.service.polling;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.ValueFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btcchina.BTCChina;
import com.xeiam.xchange.btcchina.BTCChinaAdapters;
import com.xeiam.xchange.btcchina.dto.BTCChinaResponse;
import com.xeiam.xchange.btcchina.service.BTCChinaDigest;
import com.xeiam.xchange.btcchina.service.BTCChinaTonceFactory;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;
import com.xeiam.xchange.utils.Assert;

/**
 * @author timmolter
 */
public class BTCChinaBasePollingService<T extends BTCChina> extends BaseExchangeService implements BasePollingService {

  protected final T btcChina;
  protected final ParamsDigest signatureCreator;
  protected final ValueFactory<Long> tonce;
  private final Set<CurrencyPair> currencyPairs;

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BTCChinaBasePollingService(Class<T> type, ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");

    this.btcChina = RestProxyFactory.createProxy(type, (String) exchangeSpecification.getSslUri());
    this.signatureCreator = BTCChinaDigest.createInstance(exchangeSpecification.getApiKey(), exchangeSpecification.getSecretKey());
    this.tonce = new BTCChinaTonceFactory();
    this.currencyPairs = new HashSet<CurrencyPair>();
  }

  @Override
  public synchronized Collection<CurrencyPair> getExchangeSymbols() throws IOException {

    if (currencyPairs.isEmpty()) {
      for (String tickerKey : btcChina.getTickers("all").keySet()) {
        currencyPairs.add(BTCChinaAdapters.adaptCurrencyPairFromTickerMarketKey(tickerKey));
      }
    }

    return currencyPairs;
  }

  @SuppressWarnings("rawtypes")
  public static <T extends BTCChinaResponse> T checkResult(T returnObject) {

    if (returnObject.getError() != null) {
      throw new ExchangeException("Got error message: " + returnObject.getError().toString());
    }
    else if (returnObject.getResult() == null) {
      throw new ExchangeException("Null data returned");
    }
    return returnObject;
  }
}
