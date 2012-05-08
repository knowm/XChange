package com.xeiam.xchange.mtgox.v1.service;

import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.mtgox.v1.service.marketdata.dto.MtGoxDepth;
import com.xeiam.xchange.mtgox.v1.service.marketdata.dto.MtGoxTicker;
import com.xeiam.xchange.mtgox.v1.service.marketdata.dto.MtGoxTrade;

/**
 * 
 */
public interface MtGoxMarketDataService {

  /**
   * <p>
   * Get a ticker representing the current exchange rate
   * </p>
   * 
   * @param tradableIdentifier The identifier to use (e.g. BTC or GOOG)
   * @param currency The currency of interest, null if irrelevant
   * @return The Ticker
   * @throws NotAvailableFromExchangeException If the exchange does not support this request
   */
  MtGoxTicker getTicker(String tradableIdentifier, String currency) throws NotAvailableFromExchangeException;

  /**
   * <p>
   * Get an order book representing the current offered exchange rates
   * </p>
   * 
   * @param tradableIdentifier The identifier to use (e.g. BTC or GOOG)
   * @param currency The currency of interest, null if irrelevant
   * @return MtGoxDepth
   * @throws NotAvailableFromExchangeException If the exchange does not support this request
   */
  MtGoxDepth getDepth(String tradableIdentifier, String currency) throws NotAvailableFromExchangeException;

  /**
   * <p>
   * Get an order book representing the current offered exchange rates
   * </p>
   * 
   * @param tradableIdentifier The identifier to use (e.g. BTC or GOOG)
   * @param currency The currency of interest, null if irrelevant
   * @return MtGoxDepth
   * @throws NotAvailableFromExchangeException If the exchange does not support this request
   */
  MtGoxDepth getFullDepth(String tradableIdentifier, String currency) throws NotAvailableFromExchangeException;

  /**
   * <p>
   * Get the trades recently performed by the exchange
   * </p>
   * 
   * @param tradableIdentifier The identifier to use (e.g. BTC or GOOG)
   * @param currency The currency of interest, null if irrelevant
   * @return MtGoxTrade[]
   * @throws NotAvailableFromExchangeException If the exchange does not support this request
   */
  MtGoxTrade[] getTrades(String tradableIdentifier, String currency) throws NotAvailableFromExchangeException;

}
