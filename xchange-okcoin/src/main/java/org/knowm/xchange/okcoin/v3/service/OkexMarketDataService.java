package org.knowm.xchange.okcoin.v3.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.okcoin.OkexAdaptersV3;
import org.knowm.xchange.okcoin.OkexExchangeV3;
import org.knowm.xchange.okcoin.v3.dto.marketdata.OkexSpotTicker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

public class OkexMarketDataService extends OkexMarketDataServiceRaw implements MarketDataService {

  public OkexMarketDataService(OkexExchangeV3 exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    OkexSpotTicker tokenPairInformation =
        okex.getSpotTicker(OkexAdaptersV3.toSpotInstrument(currencyPair));
    return OkexAdaptersV3.convert(tokenPairInformation);
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    return okex.getAllSpotTickers().stream()
        .map(OkexAdaptersV3::convert)
        .collect(Collectors.toList());
  }
}
