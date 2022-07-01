package org.knowm.xchange.huobi.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.huobi.HuobiUtils;
import org.knowm.xchange.huobi.dto.marketdata.HuobiAllTicker;
import org.knowm.xchange.huobi.dto.marketdata.HuobiAssetPair;
import org.knowm.xchange.huobi.dto.marketdata.HuobiCurrencyWrapper;
import org.knowm.xchange.huobi.dto.marketdata.HuobiDepth;
import org.knowm.xchange.huobi.dto.marketdata.HuobiKline;
import org.knowm.xchange.huobi.dto.marketdata.HuobiTicker;
import org.knowm.xchange.huobi.dto.marketdata.HuobiTradeWrapper;
import org.knowm.xchange.huobi.dto.marketdata.KlineInterval;
import org.knowm.xchange.huobi.dto.marketdata.results.HuobiAllTickersResult;
import org.knowm.xchange.huobi.dto.marketdata.results.HuobiAssetPairsResult;
import org.knowm.xchange.huobi.dto.marketdata.results.HuobiCurrenciesResult;
import org.knowm.xchange.huobi.dto.marketdata.results.HuobiDepthResult;
import org.knowm.xchange.huobi.dto.marketdata.results.HuobiTickerResult;
import org.knowm.xchange.huobi.dto.marketdata.results.HuobiTradesResult;

public class HuobiMarketDataServiceRaw extends HuobiBaseService {

  public HuobiMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public HuobiTicker getHuobiTicker(CurrencyPair currencyPair) throws IOException {
    String huobiCurrencyPair = HuobiUtils.createHuobiCurrencyPair(currencyPair);
    HuobiTickerResult tickerResult = huobi.getTicker(huobiCurrencyPair);
    return checkResult(tickerResult);
  }

  public HuobiAllTicker[] getHuobiAllTickers() throws IOException {
    HuobiAllTickersResult allTickerResult = huobi.getAllTickers();
    return checkResult(allTickerResult);
  }

  public HuobiAssetPair[] getHuobiAssetPairs() throws IOException {
    HuobiAssetPairsResult assetPairsResult = huobi.getAssetPairs();
    return checkResult(assetPairsResult);
  }

  public HuobiDepth getHuobiDepth(CurrencyPair currencyPair, String depthType) throws IOException {
    String huobiCurrencyPair = HuobiUtils.createHuobiCurrencyPair(currencyPair);
    HuobiDepthResult depthResult = huobi.getDepth(huobiCurrencyPair, depthType);
    return checkResult(depthResult);
  }

  public HuobiTradeWrapper[] getHuobiTrades(CurrencyPair currencyPair, int size)
      throws IOException {
    String huobiCurrencyPair = HuobiUtils.createHuobiCurrencyPair(currencyPair);
    HuobiTradesResult tradesResult = huobi.getTrades(huobiCurrencyPair, size);
    return checkResult(tradesResult);
  }

  public HuobiKline[] getKlines(CurrencyPair pair, KlineInterval interval, Integer limit)
      throws IOException {
    return checkResult(
        huobi.getKlines(
            pair.base.getSymbol().toLowerCase() + pair.counter.getSymbol().toLowerCase(),
            interval.code(),
            limit));
  }

  public HuobiCurrencyWrapper[] getHuobiCurrencies(String currency) throws IOException {
    HuobiCurrenciesResult currenciesResult =
        huobi.getCurrencies(
            currency.toLowerCase(),
            false,
            exchange.getExchangeSpecification().getApiKey(),
            HuobiDigest.HMAC_SHA_256,
            2,
            HuobiUtils.createUTCDate(exchange.getNonceFactory()),
            signatureCreator);
    return checkResult(currenciesResult);
  }
}
