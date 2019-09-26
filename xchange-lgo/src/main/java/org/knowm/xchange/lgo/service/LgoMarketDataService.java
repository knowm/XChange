package org.knowm.xchange.lgo.service;

import java.io.IOException;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.lgo.LgoAdapters;
import org.knowm.xchange.lgo.LgoErrorAdapter;
import org.knowm.xchange.lgo.LgoExchange;
import org.knowm.xchange.lgo.dto.LgoException;
import org.knowm.xchange.lgo.dto.marketdata.LgoOrderbook;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class LgoMarketDataService extends LgoMarketDataServiceRaw implements MarketDataService {

  public LgoMarketDataService(LgoExchange exchange) {
    super(exchange);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    try {
      LgoOrderbook orderBook = super.getLgoOrderBook(currencyPair);
      return LgoAdapters.adaptOrderBook(orderBook, currencyPair);
    } catch (LgoException e) {
      throw LgoErrorAdapter.adapt(e);
    }
  }
}
