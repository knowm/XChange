package com.xeiam.xchange.bitcurex.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcurex.Bitcurex;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexDepth;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexTicker;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexTrade;

/**
 * <p>
 * Implementation of the raw market data service for Bitcurex
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BitcurexMarketDataServiceRaw extends BitcurexBasePollingService {

  private final Bitcurex bitcurex;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BitcurexMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.bitcurex = RestProxyFactory.createProxy(Bitcurex.class, exchangeSpecification.getSslUri());
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
