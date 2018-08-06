package org.knowm.xchange.bittrex.service;

import java.io.IOException;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bittrex.BittrexAdapters;
import org.knowm.xchange.bittrex.BittrexErrorAdapter;
import org.knowm.xchange.bittrex.BittrexUtils;
import org.knowm.xchange.bittrex.dto.BittrexException;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexDepth;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexMarketSummary;
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
    try {
      BittrexMarketSummary summary =
          getBittrexMarketSummary(BittrexUtils.toPairString(currencyPair));
      if (summary == null) {
        throw new ExportException("Bittrex didn't return any summary nor an error");
      }
      return BittrexAdapters.adaptTicker(summary, currencyPair);
    } catch (BittrexException e) {
      throw BittrexErrorAdapter.adapt(e);
    }
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    try {
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
              ticker ->
                  currencyPairs.size() == 0 || currencyPairs.contains(ticker.getCurrencyPair()))
          .collect(Collectors.toList());
    } catch (BittrexException e) {
      throw BittrexErrorAdapter.adapt(e);
    }
  }

  /** @param args If an integer is provided, then it used as depth of order book */
  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    try {
      int depth = 50;

      if (args != null && args.length > 0) {
        if (args[0] instanceof Integer && (Integer) args[0] > 0 && (Integer) args[0] <= 50) {
          depth = (Integer) args[0];
        }
      }

      BittrexDepth bittrexDepth =
          getBittrexOrderBook(BittrexUtils.toPairString(currencyPair), depth);

      List<LimitOrder> asks =
          BittrexAdapters.adaptOrders(bittrexDepth.getAsks(), currencyPair, "ask", "", depth);
      List<LimitOrder> bids =
          BittrexAdapters.adaptOrders(bittrexDepth.getBids(), currencyPair, "bid", "", depth);

      return new OrderBook(null, asks, bids);
    } catch (BittrexException e) {
      throw BittrexErrorAdapter.adapt(e);
    }
  }

  /**
   * @param currencyPair The CurrencyPair for which to query trades.
   * @param args no further args are supported by the API
   */
  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    try {
      List<BittrexTrade> trades = getBittrexTrades(BittrexUtils.toPairString(currencyPair));

      return BittrexAdapters.adaptTrades(trades, currencyPair);
    } catch (BittrexException e) {
      throw BittrexErrorAdapter.adapt(e);
    }
  }
}
