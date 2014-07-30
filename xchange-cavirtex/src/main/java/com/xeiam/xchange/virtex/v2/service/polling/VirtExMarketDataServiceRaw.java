package com.xeiam.xchange.virtex.v2.service.polling;

import java.io.IOException;
import java.util.List;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.virtex.v2.VirtEx;
import com.xeiam.xchange.virtex.v2.dto.marketdata.VirtExDepth;
import com.xeiam.xchange.virtex.v2.dto.marketdata.VirtExTicker;
import com.xeiam.xchange.virtex.v2.dto.marketdata.VirtExTrade;

/**
 * <p>
 * Implementation of the market data service for VirtEx
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class VirtExMarketDataServiceRaw extends VirtexBasePollingService {

  private final VirtEx virtEx;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public VirtExMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.virtEx = RestProxyFactory.createProxy(VirtEx.class, exchangeSpecification.getSslUri());
  }

  public VirtExTicker getVirtExTicker(CurrencyPair currencyPair) throws IOException {

    return virtEx.getTicker(currencyPair.baseSymbol, currencyPair.counterSymbol).getTicker();
  }

  public VirtExDepth getVirtExOrderBook(CurrencyPair currencyPair) throws IOException {

    return virtEx.getFullDepth(currencyPair.baseSymbol, currencyPair.counterSymbol).getDepth();
  }

  public List<VirtExTrade> getVirtExTrades(CurrencyPair currencyPair) throws IOException {

    return virtEx.getTrades(currencyPair.baseSymbol, currencyPair.counterSymbol).getTrades();
  }

}
