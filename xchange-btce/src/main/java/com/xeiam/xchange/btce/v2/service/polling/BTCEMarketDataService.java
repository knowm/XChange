package com.xeiam.xchange.btce.v2.service.polling;

import java.io.IOException;
import java.util.List;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btce.v2.BTCE;
import com.xeiam.xchange.btce.v2.BTCEAdapters;
import com.xeiam.xchange.btce.v2.dto.marketdata.BTCEDepth;
import com.xeiam.xchange.btce.v2.dto.marketdata.BTCETickerWrapper;
import com.xeiam.xchange.btce.v2.dto.marketdata.BTCETrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * <p>
 * Implementation of the market data service for BTCE
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
@Deprecated
public class BTCEMarketDataService extends BTCEBasePollingService implements PollingMarketDataService {

  protected final BTCE btce;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTCEMarketDataService(Exchange exchange) {

    super(exchange);
    btce = RestProxyFactory.createProxy(BTCE.class, exchange.getExchangeSpecification().getSslUri());
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    BTCETickerWrapper btceTicker = btce.getTicker(currencyPair.baseSymbol.toLowerCase(), currencyPair.counterSymbol.toLowerCase());

    // Adapt to XChange DTOs
    return BTCEAdapters.adaptTicker(btceTicker, currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    BTCEDepth btceDepth = btce.getDepth(currencyPair.baseSymbol.toLowerCase(), currencyPair.counterSymbol.toLowerCase());
    // Adapt to XChange DTOs
    List<LimitOrder> asks = BTCEAdapters.adaptOrders(btceDepth.getAsks(), currencyPair, "ask", "");
    List<LimitOrder> bids = BTCEAdapters.adaptOrders(btceDepth.getBids(), currencyPair, "bid", "");

    return new OrderBook(null, asks, bids);
  }

  /**
   * Get recent trades from exchange
   *
   * @param tradableIdentifier The identifier to use (e.g. BTC or GOOG)
   * @param currency The currency of interest, null if irrelevant
   * @param args Optional arguments. This implementation assumes args[0] is integer value limiting number of trade items to get. -1 or missing -> use
   *        default output of 150 items from API v.2 int from 1 to 2000 -> use API v.3 to get corresponding number of trades
   * @return Trades object
   * @throws IOException
   */
  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    BTCETrade[] BTCETrades = btce.getTrades(currencyPair.baseSymbol.toLowerCase(), currencyPair.counterSymbol.toLowerCase());

    return BTCEAdapters.adaptTrades(BTCETrades);

  }

}
