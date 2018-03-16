package org.knowm.xchange.bitmex.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.bitmex.BitmexContract;
import org.knowm.xchange.bitmex.BitmexException;
import org.knowm.xchange.bitmex.BitmexPrompt;
import org.knowm.xchange.bitmex.BitmexUtils;
import org.knowm.xchange.bitmex.dto.account.BitmexTicker;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexDepth;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexKline;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPublicOrder;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPublicTrade;
import org.knowm.xchange.bitmex.dto.marketdata.results.BitmexSymbolsAndPromptsResult;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * <p>
 * Implementation of the market data service for Bitmex
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BitmexMarketDataServiceRaw extends BitmexBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitmexMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public BitmexDepth getBitmexDepth(CurrencyPair pair, BitmexPrompt prompt, Object... args) throws IOException {

    BitmexContract contract = new BitmexContract(pair, prompt);
    String bitmexSymbol = BitmexUtils.translateBitmexContract(contract);
    BitmexPublicOrder[] result = bitmex.getDepth(bitmexSymbol, 1000d);

    if (pair != null && prompt != null)
      return BitmexAdapters.adaptDepth(result, pair);

    // return result;
    return null;

    // return checkResult(result);
  }

  public Trades getBitmexTrades(CurrencyPair pair, BitmexPrompt prompt, Object... args) throws IOException {

    List<BitmexPublicTrade> trades = new ArrayList<>();

    BitmexContract contract = new BitmexContract(pair, prompt);
    String bitmexSymbol = BitmexUtils.translateBitmexContract(contract);

    Integer limit = (Integer) args[0];

    for (int i = 0; trades.size() + 500 <= limit; i++) {
      BitmexPublicTrade[] result = bitmex.getTrades(bitmexSymbol, true, 500, i * 500);
      trades.addAll(Arrays.asList(result));
    }

    if (pair != null && prompt != null) {
      List<BitmexPublicTrade> trimmed = trades.subList(0, Math.min(limit, trades.size()));
      return BitmexAdapters.adaptTrades(trimmed, pair);
    }

    return null;
  }

  public List<BitmexTicker> getTicker(String symbol) throws IOException {

    try {
      return bitmex.getTicker(symbol);
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public List<BitmexTicker> getActiveTickers() throws IOException {

    try {
      return bitmex.getActiveTickers();
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public BiMap<BitmexPrompt, String> getActivePrompts(List<BitmexTicker> tickers) throws IOException {

//    BiMap<BitmexPrompt, String> bitmexPromptsBiMap = HashBiMap.create();
    Map<String, BitmexPrompt> bitmexSymbolsToIntervalsMap = new HashMap<String, BitmexPrompt>();
//    BiMap<BitmexTicker, BitmexPrompt> bitmexTickersToIntervalsMap = HashBiMap.create();
    BiMap<BitmexPrompt, String> bitmexPromptsToSymbolsMap = HashBiMap.create();

    try {
      BitmexSymbolsAndPromptsResult promptsAndSymbolsResults = bitmex.getPromptsAndSymbols();
      // promptsAndSymbolsResult
      int index = 0;
      for (Object interval : promptsAndSymbolsResults.getIntervals()) {
        BitmexPrompt prompt = BitmexPrompt.valueOf(interval.toString().split("\\:")[1].toUpperCase());

        bitmexSymbolsToIntervalsMap.put(promptsAndSymbolsResults.getSymbols().get(index).toString(), prompt);
        index++;
      }
      // QUOTECCY.BASECCY.BITMEXPROMPT

      for (BitmexTicker ticker : tickers) {
        String promptSymbol = ticker.getSymbol().replaceFirst(ticker.getRootSymbol(), "");
        BitmexPrompt prompt = bitmexSymbolsToIntervalsMap.get(ticker.getSymbol());
        if (promptSymbol != null && prompt != null && prompt != BitmexPrompt.PERPETUAL && !bitmexPromptsToSymbolsMap.containsKey(prompt)) {
        	try {
						bitmexPromptsToSymbolsMap.put(prompt, promptSymbol);
					} catch (Exception e) {}
        }
      }
      return bitmexPromptsToSymbolsMap;

    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public List<BitmexKline> getBucketedTrades(String binSize, Boolean partial, CurrencyPair pair, BitmexPrompt prompt, long count, Boolean reverse) throws IOException {

  	BitmexContract contract = new BitmexContract(pair, prompt);
    String bitmexSymbol = BitmexUtils.translateBitmexContract(contract);

    try {
      return bitmex.getBucketedTrades(binSize, partial, bitmexSymbol, count, reverse);
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  protected <R> List<R> checkResult(BitmexSymbolsAndPromptsResult<R> bitmexSymbolsAndPromptsResult) {

    if (!bitmexSymbolsAndPromptsResult.isSuccess()) {
      throw new ExchangeException("Unable to retieve prompts and symbols from bitmex");
    }

    return bitmexSymbolsAndPromptsResult.getIntervals();
  }
}
