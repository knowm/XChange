package com.xeiam.xchange.vircurex.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
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
   * @param exchange
   */
  public VircurexMarketDataServiceRaw(Exchange exchange) {

    super(exchange);

    vircurex = RestProxyFactory.createProxy(Vircurex.class, exchange.getExchangeSpecification().getSslUri());
  }

  // TODO: implement Ticker by combining get_last_trade, get_volume, get_highest_bid and get_lowest_ask APIs: https://vircurex.com/welcome/api?locale=en

  // TODO: implement Trades using trades API: https://vircurex.com/welcome/api?locale=en

  public VircurexDepth getVircurexOrderBook(CurrencyPair currencyPair) throws IOException {

    VircurexDepth vircurexDepth = vircurex.getFullDepth(currencyPair.baseSymbol.toLowerCase(), currencyPair.counterSymbol.toLowerCase());

    return vircurexDepth;
  }

}
