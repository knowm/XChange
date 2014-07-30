package com.xeiam.xchange.btce.v3.service.polling;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btce.v3.BTCE;
import com.xeiam.xchange.btce.v3.BTCEAdapters;
import com.xeiam.xchange.btce.v3.dto.BTCEReturn;
import com.xeiam.xchange.btce.v3.service.BTCEHmacPostBodyDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author Matija Mazi
 */
public class BTCEBasePollingService<T extends BTCE> extends BaseExchangeService implements BasePollingService {

  private final Logger logger = LoggerFactory.getLogger(BTCEBasePollingService.class);

  public final Set<CurrencyPair> currencyPairs = new HashSet<CurrencyPair>();

  private static final long START_MILLIS = 1356998400000L; // Jan 1st, 2013 in milliseconds from epoch
  // counter for the nonce
  private static final AtomicInteger lastNonce = new AtomicInteger((int) ((System.currentTimeMillis() - START_MILLIS) / 250L));

  protected final String apiKey;
  protected final T btce;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BTCEBasePollingService(Class<T> btceType, ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    this.btce = RestProxyFactory.createProxy(btceType, exchangeSpecification.getSslUri());
    this.apiKey = exchangeSpecification.getApiKey();
    this.signatureCreator = BTCEHmacPostBodyDigest.createInstance(exchangeSpecification.getSecretKey());
  }

  @Override
  public Collection<CurrencyPair> getExchangeSymbols() throws IOException {

    if (currencyPairs.isEmpty()) {
      currencyPairs.addAll(BTCEAdapters.adaptCurrencyPairs(btce.getInfo().getPairs().keySet()));
    }

    return currencyPairs;
  }

  protected int nextNonce() {

    int nextNonce = lastNonce.incrementAndGet();
    logger.debug("nextNonce in BTCEBaseService: " + nextNonce);

    return nextNonce;
  }

  protected void checkResult(BTCEReturn<?> info) {

    if (!info.isSuccess()) {
      throw new ExchangeException("BTCE returned an error: " + info.getError());
    }
    else if (info.getReturnValue() == null) {
      throw new ExchangeException("Didn't recieve any return value. Message: " + info.getError());
    }
    else if (info.getError() != null) {
      throw new ExchangeException("Got error message: " + info.getError());
    }
  }

}
