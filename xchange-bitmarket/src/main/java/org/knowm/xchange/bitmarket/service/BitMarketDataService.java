package org.knowm.xchange.bitmarket.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmarket.BitMarketAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import si.mazi.rescu.IRestProxyFactory;

/** @author kpysniak */
public class BitMarketDataService extends BitMarketDataServiceRaw implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitMarketDataService(Exchange exchange, IRestProxyFactory restProxyFactory) {

    super(exchange, restProxyFactory);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return BitMarketAdapters.adaptTicker(getBitMarketTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    return BitMarketAdapters.adaptOrderBook(getBitMarketOrderBook(currencyPair), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    return BitMarketAdapters.adaptTrades(getBitMarketTrades(currencyPair), currencyPair);
  }
}
