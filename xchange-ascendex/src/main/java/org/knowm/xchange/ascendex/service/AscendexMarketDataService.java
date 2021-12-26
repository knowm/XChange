package org.knowm.xchange.ascendex.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ascendex.AscendexAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class AscendexMarketDataService extends AscendexMarketDataServiceRaw
    implements MarketDataService {

  public AscendexMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return AscendexAdapters.adaptOrderBook(getAscendexOrderbook(currencyPair.toString()));
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    return AscendexAdapters.adaptTrades(getAscendexTrades(currencyPair.toString()));
  }
}
