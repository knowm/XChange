package com.xeiam.xchange.vircurex.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.vircurex.Vircurex;
import com.xeiam.xchange.vircurex.dto.marketdata.VircurexDepth;

/**
 * <p>
 * Implementation of the market data service for Vircurex
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class VircurexMarketDataServiceRaw extends VircurexBasePollingService {

  private final Vircurex vircurex;

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public VircurexMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    vircurex = RestProxyFactory.createProxy(Vircurex.class, exchangeSpecification.getSslUri());
  }

  public VircurexDepth getVircurexOrderBook(CurrencyPair currencyPair) throws IOException {

    VircurexDepth vircurexDepth = vircurex.getFullDepth(currencyPair.counterSymbol.toLowerCase(), currencyPair.counterSymbol.toLowerCase());

    return vircurexDepth;
  }

}
