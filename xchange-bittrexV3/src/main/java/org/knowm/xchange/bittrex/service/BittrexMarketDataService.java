package org.knowm.xchange.bittrex.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.knowm.xchange.bittrex.BittrexAdapters;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.bittrex.BittrexUtils;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexMarketSummary;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexTicker;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexTrade;
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
  public BittrexMarketDataService(BittrexExchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    String marketSymbol = BittrexUtils.toPairString(currencyPair);
    // The only way is to make two API calls since the essential information is split between market
    // summary
    // and ticker calls...
    BittrexMarketSummary bittrexMarketSummary = bittrexAuthenticated.getMarketSummary(marketSymbol);
    BittrexTicker bittrexTicker = bittrexAuthenticated.getTicker(marketSymbol);
    return BittrexAdapters.adaptTicker(bittrexMarketSummary, bittrexTicker);
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    List<CurrencyPair> currencyPairs =
        (params instanceof CurrencyPairsParam)
            ? new ArrayList<>(((CurrencyPairsParam) params).getCurrencyPairs())
            : new ArrayList<>();

    // The only way is to make two API calls since the essential information is split between market
    // summary
    // and ticker calls...
    List<BittrexMarketSummary> bittrexMarketSummaries = getBittrexMarketSummaries();
    List<BittrexTicker> bittrexTickers = getBittrexTickers();
    Map<CurrencyPair, SummaryTickerPair> tickerCombinationMap =
        new HashMap<>(Math.min(bittrexMarketSummaries.size(), bittrexTickers.size()));
    bittrexMarketSummaries.forEach(
        marketSummary ->
            tickerCombinationMap.put(
                BittrexUtils.toCurrencyPair(marketSummary.getSymbol()),
                new SummaryTickerPair(marketSummary, null)));
    bittrexTickers.forEach(
        ticker -> {
          CurrencyPair currencyPair = BittrexUtils.toCurrencyPair(ticker.getSymbol());
          if (tickerCombinationMap.containsKey(currencyPair)) {
            tickerCombinationMap.get(currencyPair).setTicker(ticker);
          }
        });

    return tickerCombinationMap.entrySet().stream()
        .filter(entry -> currencyPairs.isEmpty() || currencyPairs.contains(entry.getKey()))
        .map(Map.Entry::getValue)
        .filter(
            summaryTickerPair ->
                summaryTickerPair.getSummary() != null && summaryTickerPair.getTicker() != null)
        .map(
            summaryTickerPair ->
                BittrexAdapters.adaptTicker(
                    summaryTickerPair.getSummary(), summaryTickerPair.getTicker()))
        .collect(Collectors.toList());
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    int depth = 500;

    if (args != null && args.length > 0) {
      if (args[0] instanceof Integer && (Integer) args[0] > 0 && (Integer) args[0] < 500) {
        depth = (Integer) args[0];
      }
    }
    return getBittrexSequencedOrderBook(BittrexUtils.toPairString(currencyPair), depth)
        .getOrderBook();
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    List<BittrexTrade> trades = getBittrexTrades(BittrexUtils.toPairString(currencyPair));
    return BittrexAdapters.adaptTrades(trades, currencyPair);
  }

  @Data
  @AllArgsConstructor
  private static class SummaryTickerPair {
    private BittrexMarketSummary summary;
    private BittrexTicker ticker;
  }
}
