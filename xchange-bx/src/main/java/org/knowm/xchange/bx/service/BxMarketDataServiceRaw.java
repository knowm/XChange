package org.knowm.xchange.bx.service;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bx.BxUtils;
import org.knowm.xchange.bx.dto.marketdata.BxAssetPair;
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
    String pairKey = BxUtils.createBxCurrencyPair(currencyPair);
    Map<String, BxTicker> tickerMap = checkResult(bx.getTicker());
    return tickerMap.get(pairKey);
  }
}
