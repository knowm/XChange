package org.knowm.xchange.bittrex.service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.knowm.xchange.bittrex.BittrexAdapters;
import org.knowm.xchange.bittrex.BittrexAuthenticated;
import org.knowm.xchange.bittrex.BittrexErrorAdapter;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.bittrex.BittrexUtils;
import org.knowm.xchange.bittrex.dto.BittrexException;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexMarketSummary;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexTicker;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexTrade;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
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
  public BittrexMarketDataService(
      BittrexExchange exchange,
      BittrexAuthenticated bittrex,
      ResilienceRegistries resilienceRegistries) {
    super(exchange, bittrex, resilienceRegistries);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    try {
      String marketSymbol = BittrexUtils.toPairString(currencyPair);
      // The only way is to make two API calls since the essential information is split between
      // market
      // summary and ticker calls...
      BittrexMarketSummary bittrexMarketSummary =
          bittrexAuthenticated.getMarketSummary(marketSymbol);
      BittrexTicker bittrexTicker = bittrexAuthenticated.getTicker(marketSymbol);
      return BittrexAdapters.adaptTicker(bittrexMarketSummary, bittrexTicker);
    } catch (BittrexException e) {
      throw BittrexErrorAdapter.adapt(e);
    }
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    try {
      Set<CurrencyPair> currencyPairs = Collections.EMPTY_SET;
      if (params instanceof CurrencyPairsParam) {
        currencyPairs = new HashSet<>(((CurrencyPairsParam) params).getCurrencyPairs());
      }

      // The only way is to make two API calls since the essential information is split between
      // market
      // summary and ticker calls...
      List<BittrexMarketSummary> bittrexMarketSummaries = getBittrexMarketSummaries();
      List<BittrexTicker> bittrexTickers = getBittrexTickers();
      return BittrexAdapters.adaptTickers(currencyPairs, bittrexMarketSummaries, bittrexTickers);
    } catch (BittrexException e) {
      throw BittrexErrorAdapter.adapt(e);
    }
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    try {
      int depth = 500;

      if (args != null && args.length > 0) {
        if (args[0] instanceof Integer && (Integer) args[0] > 0 && (Integer) args[0] < 500) {
          depth = (Integer) args[0];
        }
      }
      return getBittrexSequencedOrderBook(BittrexUtils.toPairString(currencyPair), depth)
          .getOrderBook();
    } catch (BittrexException e) {
      throw BittrexErrorAdapter.adapt(e);
    }
  }

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
