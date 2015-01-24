package com.xeiam.xchange.oer.service.polling;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.CachedDataSession;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.oer.OER;
import com.xeiam.xchange.oer.OERUtils;
import com.xeiam.xchange.oer.dto.marketdata.OERRates;
import com.xeiam.xchange.oer.dto.marketdata.OERTickers;

/**
 * @author timmolter
 */
public class OERMarketDataServiceRaw extends OERBasePollingService implements CachedDataSession {

  private final Logger logger = LoggerFactory.getLogger(OERMarketDataServiceRaw.class);

  private final OER openExchangeRates;

  /**
   * Time stamps used to pace API calls
   */
  private long tickerRequestTimeStamp = 0L;

  OERTickers cachedOERTickers;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public OERMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.openExchangeRates = RestProxyFactory.createProxy(OER.class, exchangeSpecification.getPlainTextUri());
  }

  @Override
  public long getRefreshRate() {

    return OERUtils.REFRESH_RATE_MILLIS;
  }

  public OERRates getOERTicker() throws IOException {

    // check for pacing violation
    if (tickerRequestTimeStamp == 0L || System.currentTimeMillis() - tickerRequestTimeStamp >= getRefreshRate()) {

      logger.debug("requesting OER tickers");

      // Request data
      cachedOERTickers = openExchangeRates.getTickers(exchangeSpecification.getApiKey());
      if (cachedOERTickers == null) {
        throw new ExchangeException("Null response returned from Open Exchange Rates!");
      }

      // if we make it here, set the timestamp so we know a cached request has been successful
      tickerRequestTimeStamp = System.currentTimeMillis();
    }

    OERRates rates = cachedOERTickers.getRates();

    // Adapt to XChange DTOs
    return rates;
  }

}
