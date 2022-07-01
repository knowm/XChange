package org.knowm.xchange.coindeal.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindeal.CoindealAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class CoindealMarketDataService extends CoindealMarketDataServiceRaw
    implements MarketDataService {

  public CoindealMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return CoindealAdapters.adaptOrderBook(getCoindealOrderbook(currencyPair), currencyPair);
  }
}
