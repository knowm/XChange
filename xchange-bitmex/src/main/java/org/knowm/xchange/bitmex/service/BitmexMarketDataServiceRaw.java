package org.knowm.xchange.bitmex.service;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.bitmex.BitmexContract;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.bitmex.BitmexPrompt;
import org.knowm.xchange.bitmex.BitmexUtils;
import org.knowm.xchange.bitmex.dto.account.BitmexTicker;
import org.knowm.xchange.bitmex.dto.account.BitmexTickerList;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexDepth;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexKline;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPublicOrderList;
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

  public BitmexDepth getBitmexDepth(CurrencyPair pair, BitmexPrompt prompt, Object... args)
      throws ExchangeException {

    BitmexContract contract = new BitmexContract(pair, prompt);
    String bitmexSymbol = BitmexUtils.translateBitmexContract(contract);

    BitmexPublicOrderList result = updateRateLimit(() -> bitmex.getDepth(bitmexSymbol, 1000d));

    if (pair != null && prompt != null) return BitmexAdapters.adaptDepth(result, pair);
    return null;
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
      String binSize,
      Boolean partial,
      CurrencyPair pair,
      BitmexPrompt prompt,
      long count,
      Boolean reverse)
      throws ExchangeException {

    BitmexContract contract = new BitmexContract(pair, prompt);
    String bitmexSymbol = BitmexUtils.translateBitmexContract(contract);

    return updateRateLimit(
        () -> bitmex.getBucketedTrades(binSize, partial, bitmexSymbol, count, reverse));
  }
}
