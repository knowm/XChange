package org.knowm.xchange.bity.service;

import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bity.BityAdapters;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

public class BityMarketDataService extends BityMarketDataServiceRaw implements MarketDataService {

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
    return BityAdapters.adaptTickers(bity.getRates().getObjects());
  }
}
