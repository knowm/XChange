package com.xeiam.xchange.itbit.v1.service.polling;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.itbit.v1.ItBitAuthenticated;
import com.xeiam.xchange.itbit.v1.service.ItBitHmacPostBodyDigest;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;
import si.mazi.rescu.ValueFactory;

public class ItBitBasePollingService extends BaseExchangeService implements BasePollingService {

  protected static final ValueFactory<Long> valueFactory = new ValueFactory() {

      private final long START_MILLIS = 1356998400000L; // Jan 1st, 2013 in milliseconds from epoch
      private final AtomicLong lastNonce = new AtomicLong((System.currentTimeMillis() - START_MILLIS) / 250L);

      @Override
      public Long createValue() {
          return lastNonce.incrementAndGet();
      }
  };

  protected final String apiKey;
  protected final ItBitAuthenticated itBit;
  protected final ParamsDigest signatureCreator;

  public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(new CurrencyPair("XBT", "USD"), new CurrencyPair("XBT", "EUR"), new CurrencyPair("XBT", "SGD"));

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public ItBitBasePollingService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.itBit = RestProxyFactory.createProxy(ItBitAuthenticated.class, (String) exchangeSpecification.getExchangeSpecificParametersItem("authHost"));

    this.apiKey = exchangeSpecification.getApiKey();
    this.signatureCreator = ItBitHmacPostBodyDigest.createInstance(apiKey, exchangeSpecification.getSecretKey());
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return CURRENCY_PAIRS;
  }
}
