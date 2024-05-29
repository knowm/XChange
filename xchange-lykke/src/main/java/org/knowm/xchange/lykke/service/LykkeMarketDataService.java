package org.knowm.xchange.lykke.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.lykke.LykkeAdapter;
import org.knowm.xchange.lykke.dto.marketdata.LykkeAssetPair;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class LykkeMarketDataService extends LykkeMarketDataServiceRaw implements MarketDataService {

  public LykkeMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return LykkeAdapter.adaptOrderBook(getLykkeOrderBook(currencyPair), currencyPair);
  }

  public List<LykkeAssetPair> getAssetPairs() throws IOException {
    return lykke.getAssetPairs();
  }
}
