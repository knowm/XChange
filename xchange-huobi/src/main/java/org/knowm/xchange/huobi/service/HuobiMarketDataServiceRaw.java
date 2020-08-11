package org.knowm.xchange.huobi.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.huobi.HuobiUtils;
import org.knowm.xchange.huobi.dto.marketdata.*;
import org.knowm.xchange.huobi.dto.marketdata.results.*;

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
}
