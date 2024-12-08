package org.knowm.xchange.bitmex.service;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.bitmex.BitmexPrompt;
import org.knowm.xchange.bitmex.HttpResponseAwareList;
import org.knowm.xchange.bitmex.dto.account.BitmexTicker;
import org.knowm.xchange.bitmex.dto.account.BitmexTickerList;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexAsset;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexFundingList;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexKline;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPublicOrder;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPublicTrade;
import org.knowm.xchange.bitmex.dto.marketdata.results.BitmexSymbolsAndPromptsResult;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

/**
 * Implementation of the market data service for Bitmex
 *
 * <ul>
 *   <li>Provides access to various market data values
 * </ul>
 */
@SuppressWarnings({"WeakerAccess", "JavaDoc"})
public class BitmexMarketDataServiceRaw extends BitmexBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitmexMarketDataServiceRaw(BitmexExchange exchange) {

    super(exchange);
  }

  public List<BitmexPublicOrder> getBitmexDepth(String bitmexSymbol, Integer depth) {
    return updateRateLimit(() -> bitmex.getDepth(bitmexSymbol, depth));
  }

  public List<BitmexPublicTrade> getBitmexTrades(String bitmexSymbol, Integer limit, Long start)
      throws ExchangeException {
    return updateRateLimit(() -> bitmex.getTrades(bitmexSymbol, true, limit, start));
  }

  public BitmexTickerList getTicker(String symbol) throws ExchangeException {
    return updateRateLimit(() -> bitmex.getTicker(symbol));
  }

  public BitmexTickerList getActiveTickers() throws ExchangeException {
    return updateRateLimit(() -> bitmex.getActiveTickers());
  }

  public BitmexSymbolsAndPromptsResult getActiveIntervals() {
    return updateRateLimit(() -> bitmex.getPromptsAndSymbols());
  }

  public BiMap<BitmexPrompt, String> getActivePrompts(List<BitmexTicker> tickers)
      throws ExchangeException {
    Map<String, BitmexPrompt> bitmexSymbolsToIntervalsMap = new HashMap<String, BitmexPrompt>();
    BiMap<BitmexPrompt, String> bitmexPromptsToSymbolsMap = HashBiMap.create();

    BitmexSymbolsAndPromptsResult promptsAndSymbolsResults = getActiveIntervals();
    for (int i = 0; i < promptsAndSymbolsResults.getIntervals().size(); i++) {
      String interval = promptsAndSymbolsResults.getIntervals().get(i);
      BitmexPrompt prompt = BitmexPrompt.valueOf(interval.split("\\:")[1].toUpperCase());
      bitmexSymbolsToIntervalsMap.put(promptsAndSymbolsResults.getSymbols().get(i), prompt);
    }
    // QUOTECCY.BASECCY.BITMEXPROMPT

    for (BitmexTicker ticker : tickers) {
      String promptSymbol = ticker.getSymbol().replaceFirst(ticker.getRootSymbol(), "");
      BitmexPrompt prompt = bitmexSymbolsToIntervalsMap.get(ticker.getSymbol());
      if (promptSymbol != null
          && prompt != null
          && prompt != BitmexPrompt.PERPETUAL
          && !bitmexPromptsToSymbolsMap.containsKey(prompt)) {
        try {
          bitmexPromptsToSymbolsMap.put(prompt, promptSymbol);
        } catch (Exception e) {
        }
      }
    }
    return bitmexPromptsToSymbolsMap;
  }

  public List<BitmexKline> getBucketedTrades(
      String binSize, Boolean partial, CurrencyPair pair, long count, Boolean reverse)
      throws ExchangeException {

    String bitmexSymbol = BitmexAdapters.toString(pair);

    return updateRateLimit(
        () -> bitmex.getBucketedTrades(binSize, partial, bitmexSymbol, count, reverse));
  }

  public BitmexFundingList getFundingHistory(
      String symbol,
      String filter,
      String columns,
      Integer count,
      Long start,
      Boolean reverse,
      Date startTime,
      Date endTime) {
    return updateRateLimit(
        () ->
            bitmex.getFundingHistory(
                symbol, filter, columns, count, start, reverse, startTime, endTime));
  }

  public List<BitmexAsset> getAssets() throws ExchangeException {
    return updateRateLimit(() -> {
      HttpResponseAwareList<BitmexAsset> assets = bitmex.getAssets();
      // set scale information for each network
      assets.forEach(asset -> {
        asset.getNetworks().forEach(network -> network.setAssetScale(asset.getScale()));
      });
      return assets;
    });
  }


}
