package org.knowm.xchange.bittrex.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
import org.knowm.xchange.service.marketdata.params.CurrencyPairsParam;
import org.knowm.xchange.service.marketdata.params.Params;

/**
 * Implementation of the market data service for Bittrex
 *
 * <ul>
 *   <li>Provides access to various market data values
 * </ul>
 */
public class BittrexMarketDataService extends BittrexMarketDataServiceRaw
    implements MarketDataService {

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

    return BittrexAdapters.adaptTicker(
        getBittrexMarketSummary(BittrexUtils.toPairString(currencyPair)), currencyPair);
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    List<CurrencyPair> currencyPairs =
        (params instanceof CurrencyPairsParam)
            ? new ArrayList<>(((CurrencyPairsParam) params).getCurrencyPairs())
            : new ArrayList<>();
    return getBittrexMarketSummaries()
        .stream()
        .map(
            bittrexMarketSummary ->
                BittrexAdapters.adaptTicker(
                    bittrexMarketSummary,
                    BittrexUtils.toCurrencyPair(bittrexMarketSummary.getMarketName())))
        .filter(
            ticker -> currencyPairs.size() == 0 || currencyPairs.contains(ticker.getCurrencyPair()))
        .collect(Collectors.toList());
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

    List<LimitOrder> asks =
        BittrexAdapters.adaptOrders(bittrexDepth.getAsks(), currencyPair, "ask", "");
    List<LimitOrder> bids =
        BittrexAdapters.adaptOrders(bittrexDepth.getBids(), currencyPair, "bid", "");

    return new OrderBook(null, asks, bids);
  }

  /**
   * @param currencyPair The CurrencyPair for which to query trades.
   * @param args no further args are supported by the API
   */
  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    BittrexTrade[] trades = getBittrexTrades(BittrexUtils.toPairString(currencyPair));

    return BittrexAdapters.adaptTrades(trades, currencyPair);
  }
}
