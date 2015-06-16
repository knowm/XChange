package com.xeiam.xchange.cryptsy;

import java.util.HashMap;
import java.util.Map;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptsy.service.polling.CryptsyAccountService;
import com.xeiam.xchange.cryptsy.service.polling.CryptsyMarketDataService;
import com.xeiam.xchange.cryptsy.service.polling.CryptsyPublicMarketDataService;
import com.xeiam.xchange.cryptsy.service.polling.CryptsyTradeService;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;
import com.xeiam.xchange.utils.nonce.AtomicLongIncrementalTime2014NonceFactory;

/**
 * @author ObsessiveOrange
 */
public class CryptsyExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new AtomicLongIncrementalTime2014NonceFactory();

  public static final String KEY_PUBLIC_API_HOST = "KEY_PUBLIC_API_HOST";
  public static final String KEY_PUBLIC_API_URL = "KEY_PUBLIC_API_URL";

  /**
   * Crptsy has both a public and private market data API. Here we add the public maraketdata service
   */
  protected PollingMarketDataService pollingPublicMarketDataService;

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new CryptsyMarketDataService(this);
    this.pollingAccountService = new CryptsyAccountService(this);
    this.pollingTradeService = new CryptsyTradeService(this);

    // public
    this.pollingPublicMarketDataService = new CryptsyPublicMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    // Cryptsy has different endpoint URLs for public and private data

    // the common params
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Cryptsy");
    exchangeSpecification.setExchangeDescription("Cryptsy is an altcoin exchange");

    // the private params
    exchangeSpecification.setSslUri("https://api.cryptsy.com");
    exchangeSpecification.setHost("api.cryptsy.com");

    // the public params
    Map<String, Object> exchangeSpecificParameters = new HashMap<String, Object>();
    exchangeSpecificParameters.put(KEY_PUBLIC_API_HOST, "pubapi.cryptsy.com");
    exchangeSpecificParameters.put(KEY_PUBLIC_API_URL, "http://pubapi.cryptsy.com");
    exchangeSpecification.setExchangeSpecificParameters(exchangeSpecificParameters);

    return exchangeSpecification;
  }

  public static ExchangeSpecification getDefaultPublicExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(CryptsyExchange.class.getCanonicalName());
    exchangeSpecification.setSslUri("http://pubapi.cryptsy.com");
    exchangeSpecification.setHost("pubapi.cryptsy.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Cryptsy");
    exchangeSpecification.setExchangeDescription("Cryptsy is an altcoin exchange");

    return exchangeSpecification;
  }

  public PollingMarketDataService getPollingPublicMarketDataService() {
    return pollingPublicMarketDataService;
  }
  
  @Override
  public PollingMarketDataService getPollingMarketDataService() {
    if (exchangeSpecification != null && exchangeSpecification.getApiKey() != null) {
      return pollingMarketDataService;
    } else {
      return pollingPublicMarketDataService;
    }
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
