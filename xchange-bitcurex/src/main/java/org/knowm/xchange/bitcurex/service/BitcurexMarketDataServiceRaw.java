package org.knowm.xchange.bitcurex.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcurex.dto.marketdata.BitcurexDepth;
import org.knowm.xchange.bitcurex.dto.marketdata.BitcurexTicker;
import org.knowm.xchange.bitcurex.dto.marketdata.BitcurexTrade;
import org.knowm.xchange.exceptions.ExchangeException;

/**
 * <p>
 * Implementation of the raw market data service for Bitcurex
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BitcurexMarketDataServiceRaw extends BitcurexBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitcurexMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public BitcurexTicker getBitcurexTicker(String currency) throws IOException, ExchangeException {

    return bitcurex.getTicker(currency);
  }

  public BitcurexDepth getBitcurexOrderBook(String currency) throws IOException, ExchangeException {

    return bitcurex.getFullDepth(currency);
  }

  public BitcurexTrade[] getBitcurexTrades(String currency) throws IOException, ExchangeException {

    return bitcurex.getTrades(currency);
  }

}
