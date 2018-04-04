package org.knowm.xchange.wex.v3.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.wex.v3.WexAdapters;
import org.knowm.xchange.wex.v3.dto.marketdata.WexDepthWrapper;
import org.knowm.xchange.wex.v3.dto.marketdata.WexTickerWrapper;
import org.knowm.xchange.wex.v3.dto.marketdata.WexTrade;

public class WexMarketDataService extends WexMarketDataServiceRaw implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public WexMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    String pairs = WexAdapters.getPair(currencyPair);
    WexTickerWrapper wexTickerWrapper = getBTCETicker(pairs);

    // Adapt to XChange DTOs
    return WexAdapters.adaptTicker(
        wexTickerWrapper.getTicker(WexAdapters.getPair(currencyPair)), currencyPair);
  }

  /**
   * Get market depth from exchange
   *
   * @param args Optional arguments. Exchange-specific. This implementation assumes: Integer value
   *     from 1 to 2000 -> get corresponding number of items
   * @return The OrderBook
   * @throws IOException
   */
  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    String pairs = WexAdapters.getPair(currencyPair);
    WexDepthWrapper wexDepthWrapper = null;

    if (args != null && args.length > 0) {
      Object arg0 = args[0];
      if (!(arg0 instanceof Integer) || ((Integer) arg0 < 1) || ((Integer) arg0 > FULL_SIZE)) {
        throw new ExchangeException(
            "Orderbook size argument must be an Integer in the range: (1, 2000)!");
      } else {
        wexDepthWrapper = getBTCEDepth(pairs, (Integer) arg0);
      }
    } else { // default to full orderbook
      wexDepthWrapper = getBTCEDepth(pairs, FULL_SIZE);
    }

    // Adapt to XChange DTOs
    List<LimitOrder> asks =
        WexAdapters.adaptOrders(
            wexDepthWrapper.getDepth(WexAdapters.getPair(currencyPair)).getAsks(),
            currencyPair,
            "ask",
            "");
    List<LimitOrder> bids =
        WexAdapters.adaptOrders(
            wexDepthWrapper.getDepth(WexAdapters.getPair(currencyPair)).getBids(),
            currencyPair,
            "bid",
            "");

    return new OrderBook(null, asks, bids);
  }

  /**
   * Get recent trades from exchange
   *
   * @param args Optional arguments. This implementation assumes args[0] is integer value limiting
   *     number of trade items to get. -1 or missing -> use default 2000 max fetch value int from 1
   *     to 2000 -> use API v.3 to get corresponding number of trades
   * @return Trades object
   * @throws IOException
   */
  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    String pairs = WexAdapters.getPair(currencyPair);
    int numberOfItems = FULL_SIZE;
    if (args != null && args.length > 0) {
      if (args[0] instanceof Number) {
        numberOfItems = (Integer) args[0];
      }
    }

    WexTrade[] bTCETrades =
        getBTCETrades(pairs, numberOfItems).getTrades(WexAdapters.getPair(currencyPair));
    return WexAdapters.adaptTrades(bTCETrades, currencyPair);
  }
}
