package org.knowm.xchange.bittrex.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bittrex.BittrexAdapters;
import org.knowm.xchange.bittrex.BittrexUtils;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexDepth;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * <p>
 * Implementation of the market data service for Bittrex
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BittrexMarketDataService extends BittrexMarketDataServiceRaw implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BittrexMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return BittrexAdapters.adaptTicker(getBittrexMarketSummary(BittrexUtils.toPairString(currencyPair)), currencyPair);
  }

  /**
   * @param args If an integer is provided, then it used as depth of order book
   */
  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    int depth = 50;

    if (args != null && args.length > 0) {
      if (args[0] instanceof Integer && (Integer) args[0] > 0 && (Integer) args[0] <= 50) {
        depth = (Integer) args[0];
      }
    }

    BittrexDepth bittrexDepth = getBittrexOrderBook(BittrexUtils.toPairString(currencyPair), depth);

    List<LimitOrder> asks = BittrexAdapters.adaptOrders(bittrexDepth.getAsks(), currencyPair, "ask", "");
    List<LimitOrder> bids = BittrexAdapters.adaptOrders(bittrexDepth.getBids(), currencyPair, "bid", "");

    return new OrderBook(null, asks, bids);
  }

  /**
   * @param currencyPair The CurrencyPair for which to query trades.
   * @param args One argument may be supplied which is the timestamp after which trades should be collected. Trades before this time are not reported.
   * The argument may be of type java.util.Date or Number (milliseconds since Jan 1, 1970)
   */
  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    int count = 50;

    if (args != null && args.length > 0) {
      if (args[0] instanceof Integer) {
        count = (Integer) args[0];
      }
    }

    BittrexTrade[] trades = getBittrexTrades(BittrexUtils.toPairString(currencyPair), count);

    return BittrexAdapters.adaptTrades(trades, currencyPair);
  }

}
