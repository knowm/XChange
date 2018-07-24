package org.knowm.xchange.bity.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bity.BityAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

public class BityMarketDataService extends BityMarketDataServiceRaw implements MarketDataService {

  private Map<CurrencyPair, Ticker> mapTickers = new HashMap<>();

  /**
   * Constructor
   *
   * @param exchange
   */
  public BityMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public List<Ticker> getTickers(Params params) {

    mapTickers = new HashMap<>();

    List<Ticker> tickers = BityAdapters.adaptTickers(bity.getRates().getObjects());
    for (Ticker ticker : tickers) {
      mapTickers.put(ticker.getCurrencyPair(), ticker);
    }

    return tickers;
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    if (mapTickers.isEmpty()) {
      getTickers(null);
    }

    return mapTickers.get(currencyPair);
  }
}
