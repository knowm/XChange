package org.knowm.xchange.bity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

public class BityTickersHolder {

  private Map<CurrencyPair, Ticker> mapTickers = new HashMap<>();

  public BityTickersHolder(List<Ticker> tickers) {
    for (Ticker ticker : tickers) {
      mapTickers.put(ticker.getCurrencyPair(), ticker);
    }
  }

  public Ticker getTicker(CurrencyPair currencyPair) {
    return mapTickers.get(currencyPair);
  }
}
