package org.knowm.xchange.bitfinex.v2.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitfinex.v2.BitfinexAdapters;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

public class BitfinexMarketDataService extends BitfinexMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitfinexMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    return Arrays.stream(getBitfinexTickers(exchange.getExchangeSymbols()))
        .map(BitfinexAdapters::adaptTicker)
        .collect(Collectors.toList());
  }
}
