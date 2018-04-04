package org.knowm.xchange.huobi.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.huobi.HuobiUtils;
import org.knowm.xchange.huobi.dto.marketdata.HuobiAssetPair;
import org.knowm.xchange.huobi.dto.marketdata.HuobiTicker;
import org.knowm.xchange.huobi.dto.marketdata.results.HuobiAssetPairsResult;
import org.knowm.xchange.huobi.dto.marketdata.results.HuobiTickerResult;

public class HuobiMarketDataServiceRaw extends HuobiBaseService {

  public HuobiMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public HuobiTicker getHuobiTicker(CurrencyPair currencyPair) throws IOException {
    String huobiCurrencyPair = HuobiUtils.createHuobiCurrencyPair(currencyPair);
    HuobiTickerResult tickerResult = huobi.getTicker(huobiCurrencyPair);
    return checkResult(tickerResult);
  }

  public HuobiAssetPair[] getHuobiAssetPairs() throws IOException {
    HuobiAssetPairsResult assetPairsResult = huobi.getAssetPairs();
    return checkResult(assetPairsResult);
  }
}
