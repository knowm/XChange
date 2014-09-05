package com.xeiam.xchange.bittrex.v1.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bittrex.v1.BittrexAdapters;
import com.xeiam.xchange.bittrex.v1.BittrexUtils;
import com.xeiam.xchange.bittrex.v1.dto.marketdata.BittrexDepth;
import com.xeiam.xchange.bittrex.v1.dto.marketdata.BittrexTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * <p>
 * Implementation of the market data service for Bittrex
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BittrexMarketDataService extends BittrexMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BittrexMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return BittrexAdapters.adaptTicker(getBittrexTicker(BittrexUtils.toPairString(currencyPair)), currencyPair);
  }

  /**
   * @param args If two integers are provided, then those count as limit bid and limit ask count
   */
  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    int depth = 50;

    if (args.length > 0) {
      if (args[0] instanceof Integer) {
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
   * @param args One argument may be supplied which is the timestamp after which trades should be collected.
   *          Trades before this time are not reported. The argument may be of type java.util.Date or
   *          Number (milliseconds since Jan 1, 1970)
   */
  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    int count = 50;

    if (args.length > 0) {
      if (args[0] instanceof Integer) {
        count = (Integer) args[0];
      }
    }

    BittrexTrade[] trades = getBittrexTrades(BittrexUtils.toPairString(currencyPair), count);

    return BittrexAdapters.adaptTrades(trades, currencyPair);
  }

}
