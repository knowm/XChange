package org.knowm.xchange.bitmex.service;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmex.*;
import org.knowm.xchange.bitmex.dto.BitmexInstrument;
import org.knowm.xchange.bitmex.dto.BitmexInstrumentInterval;
import org.knowm.xchange.bitmex.dto.BitmexOrderBookL2;
import org.knowm.xchange.bitmex.dto.BitmexTrade;
import org.knowm.xchange.bitmex.dto.account.BitmexTicker;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexDepth;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPublicOrder;
import org.knowm.xchange.bitmex.dto.marketdata.results.BitmexSymbolsAndPromptsResult;
import org.knowm.xchange.bitmex.util.ApiException;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Implementation of the market data service for Bitmex
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BitmexMarketDataServiceRaw extends BitmexBaseService {

    public static final TradeApi TRADE_API = new TradeApi();
    public static final InstrumentApi INSTRUMENT_API = new InstrumentApi();

    /**
     * Constructor
     *
     * @param exchange
     */
    public BitmexMarketDataServiceRaw(Exchange exchange) {

        super(exchange);
    }

    public BitmexDepth getBitmexDepth(CurrencyPair pair, BitmexPrompt prompt, Object... args) throws IOException, ApiException {

        BitmexContract contract = new BitmexContract(pair, prompt);
        String bitmexSymbol = BitmexUtils.translateBitmexContract(contract);
        List<BitmexOrderBookL2> result = new OrderBookApi ().orderBookGetL2( bitmexSymbol, BigDecimal.valueOf(25));

        if (pair != null && prompt != null)
            return BitmexAdapters.adaptDepth(result, pair);

        // return result;
        return null;

        // return checkResult(result);
    }

    public Trades getBitmexTrades(CurrencyPair pair, BitmexPrompt prompt, Object... args) throws IOException {

        BitmexContract contract = new BitmexContract(pair, prompt);
        String bitmexSymbol = BitmexUtils.translateBitmexContract(contract);
        List<BitmexTrade> result = new ArrayList<>();
        try {
            List<BitmexTrade> result1 = TRADE_API.tradeGet(bitmexSymbol, null, null, null, null, true, null, null);
            result = result1;
        } catch (ApiException e) {
            e.printStackTrace();
        }

        if (pair != null && prompt != null)
            return BitmexAdapters.adaptTrades(result, pair);

        // return result;
        return null;

        // return checkResult(result);
    }

    public List<BitmexInstrument> getTicker(String symbol) throws IOException {

        try {return INSTRUMENT_API.instrumentGet(symbol,null,null,null,null,null,null,null);
        } catch (Exception e) {
            throw handleError(e);
        }
    }

    public List<BitmexInstrument> getActiveTickers() throws IOException {

        try {
            return INSTRUMENT_API.instrumentGetActive() ;
        } catch (Exception e) {
            throw handleError(e);
        }
    }

    public BiMap<BitmexPrompt, String> getActivePrompts(List<BitmexInstrument> tickers) throws IOException {

        BiMap<BitmexPrompt, String> bitmexPromptsBiMap = HashBiMap.create();
        Map<String, BitmexPrompt> bitmexSymbolsToIntervalsMap = new HashMap<String, BitmexPrompt>();
        BiMap<BitmexTicker, BitmexPrompt> bitmexTickersToIntervalsMap = HashBiMap.create();
        BiMap<BitmexPrompt, String> bitmexPromptsToSymbolsMap = HashBiMap.create();

        try {
            BitmexInstrumentInterval promptsAndSymbolsResults = INSTRUMENT_API.instrumentGetActiveIntervals();
            // promptsAndSymbolsResult
            int index = 0;
            for (Object interval : promptsAndSymbolsResults.getIntervals()) {
                BitmexPrompt prompt = BitmexPrompt.valueOf(interval.toString().split("\\:")[1].toUpperCase());

                bitmexSymbolsToIntervalsMap.put(promptsAndSymbolsResults.getSymbols().get(index).toString(), prompt);
                index++;
            }
            // QUOTECCY.BASECCY.BITMEXPROMPT

            for (BitmexInstrument ticker : tickers) {
                String promptSymbol = ticker.getSymbol().replaceFirst(ticker.getRootSymbol(), "");
                if (promptSymbol != null && bitmexSymbolsToIntervalsMap.get(ticker.getSymbol()) != null && bitmexSymbolsToIntervalsMap.get(ticker.getSymbol()) != BitmexPrompt.PERPETUAL
                        && !bitmexPromptsToSymbolsMap.containsKey(ticker.getSymbol()))
                    bitmexPromptsToSymbolsMap.put(bitmexSymbolsToIntervalsMap.get(ticker.getSymbol()), promptSymbol);

                // bitmexTickersToIntervalsMap.put(ticker, bitmexSymbolsToIntervalsMap.get(ticker.getSymbol()));
            }

            return bitmexPromptsToSymbolsMap;

        } catch (Exception e) {
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
