package com.xeiam.xchange.bitcurex.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexDepth;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexTicker;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexTrade;
import com.xeiam.xchange.exceptions.ExchangeException;

/**
 * <p>
 * Implementation of the raw market data service for Bitcurex
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BitcurexMarketDataServiceRaw extends BitcurexBasePollingService {

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
