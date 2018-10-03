package org.knowm.xchange.bitmarket.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmarket.dto.marketdata.BitMarketOrderBook;
import org.knowm.xchange.bitmarket.dto.marketdata.BitMarketTicker;
import org.knowm.xchange.bitmarket.dto.marketdata.BitMarketTrade;
import org.knowm.xchange.currency.CurrencyPair;
import si.mazi.rescu.IRestProxyFactory;

/** @author kpysniak */
public class BitMarketDataServiceRaw extends BitMarketBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BitMarketDataServiceRaw(Exchange exchange, IRestProxyFactory restProxyFactory) {
    super(exchange, restProxyFactory);
  }

  public BitMarketTicker getBitMarketTicker(CurrencyPair currencyPair) throws IOException {

    return bitMarket.getTicker(
        currencyPair.base.getCurrencyCode().toUpperCase()
            + currencyPair.counter.getCurrencyCode().toUpperCase());
  }

  public BitMarketOrderBook getBitMarketOrderBook(CurrencyPair currencyPair) throws IOException {

    return bitMarket.getOrderBook(
        currencyPair.base.getCurrencyCode().toUpperCase()
            + currencyPair.counter.getCurrencyCode().toUpperCase());
  }

  public BitMarketTrade[] getBitMarketTrades(CurrencyPair currencyPair) throws IOException {

    return bitMarket.getTrades(
        currencyPair.base.getCurrencyCode().toUpperCase()
            + currencyPair.counter.getCurrencyCode().toUpperCase());
  }
}
