package org.knowm.xchange.bankera.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bankera.BankeraAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BankeraMarketDataService extends BankeraMarketDataServiceRaw
    implements MarketDataService {

  public BankeraMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return BankeraAdapters.adaptTicker(getBankeraTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    return BankeraAdapters.adaptOrderBook(getOrderbook(currencyPair), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    return BankeraAdapters.adaptTrades(getRecentTrades(currencyPair), currencyPair);
  }
}
