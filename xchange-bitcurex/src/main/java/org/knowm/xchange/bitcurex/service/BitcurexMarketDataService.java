package org.knowm.xchange.bitcurex.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcurex.BitcurexAdapters;
import org.knowm.xchange.bitcurex.dto.marketdata.BitcurexDepth;
import org.knowm.xchange.bitcurex.dto.marketdata.BitcurexTicker;
import org.knowm.xchange.bitcurex.dto.marketdata.BitcurexTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * <p>
 * Implementation of the generic market data service for Bitcurex
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BitcurexMarketDataService extends BitcurexMarketDataServiceRaw implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitcurexMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    // get data
    BitcurexTicker bitcurexTicker = getBitcurexTicker(currencyPair.counter.getCurrencyCode());

    // Adapt to XChange DTOs
    return BitcurexAdapters.adaptTicker(bitcurexTicker, currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    // get data
    BitcurexDepth bitcurexDepth = getBitcurexOrderBook(currencyPair.counter.getCurrencyCode());

    // Adapt to XChange DTOs
    List<LimitOrder> asks = BitcurexAdapters.adaptOrders(bitcurexDepth.getAsks(), currencyPair, OrderType.ASK, "");
    List<LimitOrder> bids = BitcurexAdapters.adaptOrders(bitcurexDepth.getBids(), currencyPair, OrderType.BID, "");

    return new OrderBook(null, asks, bids);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    // get data
    BitcurexTrade[] bitcurexTrades = getBitcurexTrades(currencyPair.counter.getCurrencyCode());

    // Adapt to XChange DTOs
    return BitcurexAdapters.adaptTrades(bitcurexTrades, currencyPair);
  }

}
