package com.xeiam.xchange.cryptsy.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptsy.CryptsyAuthenticated;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyGetMarketsReturn;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyMarketTradesReturn;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyOrderBookReturn;

/**
 * @author ObsessiveOrange
 */
public class CryptsyMarketDataServiceRaw extends CryptsyBasePollingService<CryptsyAuthenticated> {

  protected static final int FULL_SIZE = 2000;

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public CryptsyMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(CryptsyAuthenticated.class, exchangeSpecification);
  }

  /**
   * Get the orderbook for this market
   * 
   * @param marketID the marketID to get the orderbook for
   * @return CryptsyOrderBookReturn DTO representing the overall orderbook
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this error.
   * @throws IOException
   */
  public CryptsyOrderBookReturn getCryptsyOrderBook(int marketID) throws IOException, ExchangeException {

    return checkResult(cryptsy.marketorders(apiKey, signatureCreator, nextNonce(), marketID));
  }

  /**
   * @param marketID the marketID to get the orderbook for
   * @return CryptsyMarketTradesReturn DTO representing the past trades in this market
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this error.
   * @throws IOException
   */
  public CryptsyMarketTradesReturn getCryptsyTrades(int marketID) throws IOException, ExchangeException {

    return checkResult(cryptsy.markettrades(apiKey, signatureCreator, nextNonce(), marketID));
  }

  /**
   * Get all active markets from exchange
   * 
   * @return CryptsyTradesWrapper DTO representing market summary information
   * @throws ExchangeException Indication that the exchange reported some kind of error with the request or response. Implementers should log this error.
   * @throws IOException
   */
  public CryptsyGetMarketsReturn getCryptsyMarkets() throws IOException, ExchangeException {

    return checkResult(cryptsy.getmarkets(apiKey, signatureCreator, nextNonce()));
  }
}
