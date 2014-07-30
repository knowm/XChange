package com.xeiam.xchange.anx.v2.service.polling;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.anx.v2.ANXAdapters;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXDepthWrapper;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * <p>
 * Implementation of the market data service for ANX V2
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class ANXMarketDataService extends ANXMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public ANXMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return ANXAdapters.adaptTicker(getANXTicker(currencyPair));
  }

  /**
   * Get market depth from exchange
   * 
   * @param args Optional arguments. Exchange-specific. This implementation assumes:
   *          absent or "full" -> get full OrderBook
   *          "partial" -> get partial OrderBook
   * @return The OrderBook
   * @throws java.io.IOException
   */
  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    // Request data
    ANXDepthWrapper anxDepthWrapper = null;
    if (args.length > 0) {
      if (args[0] instanceof String) {
        if ("full" == args[0]) {
          anxDepthWrapper = getANXFullOrderBook(currencyPair);
        }
        else {
          anxDepthWrapper = getANXPartialOrderBook(currencyPair);
        }
      }
      else {
        throw new ExchangeException("Orderbook type argument must be a String!");
      }
    }
    else { // default to full orderbook
      anxDepthWrapper = getANXFullOrderBook(currencyPair);
    }

    // Adapt to XChange DTOs
    List<LimitOrder> asks = ANXAdapters.adaptOrders(anxDepthWrapper.getAnxDepth().getAsks(), currencyPair.baseSymbol, currencyPair.counterSymbol, "ask", "");
    List<LimitOrder> bids = ANXAdapters.adaptOrders(anxDepthWrapper.getAnxDepth().getBids(), currencyPair.baseSymbol, currencyPair.counterSymbol, "bid", "");
    Date date = new Date(anxDepthWrapper.getAnxDepth().getMicroTime() / 1000);
    return new OrderBook(date, asks, bids);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    long sinceTimeStamp = 0;
    if (args != null && args.length == 1) {
      // parameter 1, if present, is the last trade timestamp in milliseconds
      if (args[0] instanceof Number) {
        Number arg = (Number) args[0];
        sinceTimeStamp = arg.longValue();
      }
      else if (args[0] instanceof Date) {
        Date arg = (Date) args[0];
        sinceTimeStamp = arg.getTime();
      }
      else {
        throw new IllegalArgumentException("Extra argument #1, the last trade time, must be a Date or Long (millisecond timestamp) (was " + args[0].getClass() + ")");
      }
    }

    List<ANXTrade> anxTrades = super.getANXTrades(currencyPair, sinceTimeStamp);
    return ANXAdapters.adaptTrades(anxTrades);

  }

}
