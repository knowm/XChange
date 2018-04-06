package org.knowm.xchange.bx.service;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bx.BxUtils;
import org.knowm.xchange.bx.dto.marketdata.BxAssetPair;
import org.knowm.xchange.bx.dto.marketdata.BxHistoryTrade;
import org.knowm.xchange.bx.dto.marketdata.BxTicker;
import org.knowm.xchange.currency.CurrencyPair;

public class BxMarketDataServiceRaw extends BxBaseService {

  BxMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public Map<String, BxAssetPair> getBxAssetPairs() throws IOException {
    return checkResult(bx.getAssetPairs());
  }

  public BxTicker getBxTicker(CurrencyPair currencyPair) throws IOException {
    String keyRequest = BxUtils.createBxCurrencyPair(currencyPair);
    Map<String, BxTicker> tickerMap = checkResult(bx.getTicker());
    BxTicker result = null;
    for (String key : tickerMap.keySet()) {
      if (key.equals(keyRequest)) {
        result = tickerMap.get(key);
        break;
      }
    }
    if (result != null) {
      BxHistoryTrade historyTrade =
          checkResult(
              bx.getHistoryTrade(keyRequest, BxUtils.createUTCDate(exchange.getNonceFactory())));
      result.setOpen(historyTrade.getOpen());
      result.setHigh(historyTrade.getHigh());
      result.setLow(historyTrade.getLow());
      result.setAvg(historyTrade.getAvg());
      result.setVolume(historyTrade.getVolume());
    }
    return result;
  }
}
