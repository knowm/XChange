package com.xeiam.xchange.cryptsy.service.polling;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptsy.Cryptsy;
import com.xeiam.xchange.cryptsy.CryptsyAdapters;
import com.xeiam.xchange.cryptsy.CryptsyCurrencyUtils;
import com.xeiam.xchange.cryptsy.dto.CryptsyGenericReturn;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyPublicMarketData;
import com.xeiam.xchange.cryptsy.service.CryptsyHmacPostBodyDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author ObsessiveOrange
 */
public class CryptsyBasePollingService<T extends Cryptsy> extends BaseExchangeService implements BasePollingService {

  private final Logger logger = LoggerFactory.getLogger(CryptsyBasePollingService.class);

  public final Set<CurrencyPair> currencyPairs = new HashSet<CurrencyPair>();

  private static final long START_MILLIS = 1356998400000L; // Jan 1st, 2013 in milliseconds from epoch
  // counter for the nonce
  private static final AtomicInteger lastNonce = new AtomicInteger((int) ((System.currentTimeMillis() - START_MILLIS) / 250L));

  protected final String apiKey;
  protected final T cryptsy;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public CryptsyBasePollingService(Class<T> cryptsyType, ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    this.cryptsy = RestProxyFactory.createProxy(cryptsyType, exchangeSpecification.getSslUri());
    this.apiKey = exchangeSpecification.getApiKey();
    this.signatureCreator = CryptsyHmacPostBodyDigest.createInstance(exchangeSpecification.getSecretKey());
  }

  @Override
  public synchronized Collection<CurrencyPair> getExchangeSymbols() throws IOException {

    if (currencyPairs.isEmpty()) {
      updateExchangeSymbols();
    }
    return currencyPairs;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public void updateExchangeSymbols() throws ExchangeException, IOException {

    Map<Integer, CryptsyPublicMarketData> overallMarketData = new CryptsyPublicMarketDataServiceRaw().getAllCryptsyMarketData();

    currencyPairs.addAll(CryptsyAdapters.adaptCurrencyPairs(overallMarketData));

    // Map of market currencyPairs and marketIds also have to be updated.
    HashMap[] marketSets = CryptsyAdapters.adaptMarketSets(overallMarketData);
    CryptsyCurrencyUtils.marketIds_CurrencyPairs = marketSets[0];
    CryptsyCurrencyUtils.currencyPairs_MarketIds = marketSets[1];
  }

  protected int nextNonce() {

    int nextNonce = lastNonce.incrementAndGet();
    logger.debug("nextNonce in CryptsyBaseService: " + nextNonce);

    return nextNonce;
  }

  @SuppressWarnings("rawtypes")
  public static <T extends CryptsyGenericReturn> T checkResult(T info) {

    if (!info.isSuccess()) {
      throw new ExchangeException("Cryptsy returned an error: " + info.getError());
    }
    else if (info.getError() != null) {
      throw new ExchangeException("Got error message: " + info.getError());
    }
    else if (info.getReturnValue() == null) {
      throw new ExchangeException("Null data returned");
    }
    return info;
  }

}
