package org.knowm.xchange.bity.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bity.BityAdapters;
import org.knowm.xchange.bity.BityTickersHolder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

public class BityMarketDataService extends BityMarketDataServiceRaw implements MarketDataService {

  private BityTickersHolder tickersHolder;

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

    List<Ticker> tickers = BityAdapters.adaptTickers(bity.getRates().getObjects());
    tickersHolder = new BityTickersHolder(tickers);
    return tickers;
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return tickersHolder.getTicker(currencyPair);
  }
}
