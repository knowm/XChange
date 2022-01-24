package org.knowm.xchange.gemini.v1.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.LoanOrderBook;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.FixedRateLoanOrder;
import org.knowm.xchange.dto.trade.FloatingRateLoanOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.gemini.v1.GeminiAdapters;
import org.knowm.xchange.gemini.v1.GeminiUtils;
import org.knowm.xchange.gemini.v1.dto.marketdata.GeminiDepth;
import org.knowm.xchange.gemini.v1.dto.marketdata.GeminiLendDepth;
import org.knowm.xchange.gemini.v1.dto.marketdata.GeminiTrade;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * Implementation of the market data service for Gemini
 *
 * <ul>
 *   <li>Provides access to various market data values
 * </ul>
 */
public class GeminiMarketDataService extends GeminiMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GeminiMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(Instrument instrument, Object... args) throws IOException {
    return getTicker((CurrencyPair) instrument, args);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return GeminiAdapters.adaptTicker(
        getGeminiTicker(GeminiUtils.toPairString(currencyPair)), currencyPair);
  }

  /** @param args If two integers are provided, then those count as limit bid and limit ask count */
  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    // null will cause fetching of full order book, the default behavior in XChange
    Integer limitBids = null;
    Integer limitAsks = null;

    if (args != null && args.length == 2) {
      Object arg0 = args[0];
      if (!(arg0 instanceof Integer)) {
        throw new ExchangeException("Argument 0 must be an Integer!");
      } else {
        limitBids = (Integer) arg0;
      }
      Object arg1 = args[1];
      if (!(arg1 instanceof Integer)) {
        throw new ExchangeException("Argument 1 must be an Integer!");
      } else {
        limitAsks = (Integer) arg1;
      }
    }

    GeminiDepth GeminiDepth =
        getGeminiOrderBook(GeminiUtils.toPairString(currencyPair), limitBids, limitAsks);

    OrderBook orderBook = GeminiAdapters.adaptOrderBook(GeminiDepth, currencyPair);

    return orderBook;
  }

  public LoanOrderBook getLendOrderBook(String currency, Object... args) throws IOException {

    // According to API docs, default is 50
    int limitBids = 50;
    int limitAsks = 50;

    if (args != null && args.length == 2) {
      Object arg0 = args[0];
      if (!(arg0 instanceof Integer)) {
        throw new ExchangeException("Argument 0 must be an Integer!");
      } else {
        limitBids = (Integer) arg0;
      }
      Object arg1 = args[1];
      if (!(arg1 instanceof Integer)) {
        throw new ExchangeException("Argument 1 must be an Integer!");
      } else {
        limitAsks = (Integer) arg1;
      }
    }

    GeminiLendDepth GeminiLendDepth = getGeminiLendBook(currency, limitBids, limitAsks);

    List<FixedRateLoanOrder> fixedRateAsks =
        GeminiAdapters.adaptFixedRateLoanOrders(GeminiLendDepth.getAsks(), currency, "ask", "");
    List<FixedRateLoanOrder> fixedRateBids =
        GeminiAdapters.adaptFixedRateLoanOrders(GeminiLendDepth.getBids(), currency, "bid", "");
    List<FloatingRateLoanOrder> floatingRateAsks =
        GeminiAdapters.adaptFloatingRateLoanOrders(GeminiLendDepth.getAsks(), currency, "ask", "");
    List<FloatingRateLoanOrder> floatingRateBids =
        GeminiAdapters.adaptFloatingRateLoanOrders(GeminiLendDepth.getBids(), currency, "bid", "");

    return new LoanOrderBook(
        null, fixedRateAsks, fixedRateBids, floatingRateAsks, floatingRateBids);
  }

  /**
   * @param currencyPair The CurrencyPair for which to query trades.
   * @param args One argument may be supplied which is the timestamp after which trades should be
   *     collected. Trades before this time are not reported. The argument may be of type
   *     java.util.Date or Number (milliseconds since Jan 1, 1970)
   */
  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    // According to API docs, default is 50
    int limitTrades = 50;
    if (args != null && args.length > 1) {
      if (args[1] instanceof Integer) {
        limitTrades = ((Integer) args[1]).intValue();
      } else {
        throw new ExchangeException("Argument 1 must be an Integer!");
      }
    }

    long lastTradeTime = 0;
    if (args != null && args.length > 0) {
      // parameter 0, if present, is the last trade timestamp
      if (args[0] instanceof Number) {
        Number arg = (Number) args[0];
        lastTradeTime =
            arg.longValue() / 1000; // divide by 1000 to convert to unix timestamp (seconds)
      } else if (args[0] instanceof Date) {
        Date arg = (Date) args[0];
        lastTradeTime =
            arg.getTime() / 1000; // divide by 1000 to convert to unix timestamp (seconds)
      } else {
        throw new IllegalArgumentException(
            "Argument 0, the last trade time, must be a Date or Long (millisecond timestamp) (was "
                + args[0].getClass()
                + ")");
      }
    }
    GeminiTrade[] trades =
        getGeminiTrades(GeminiUtils.toPairString(currencyPair), lastTradeTime, limitTrades);

    return GeminiAdapters.adaptTrades(trades, currencyPair);
  }
}
