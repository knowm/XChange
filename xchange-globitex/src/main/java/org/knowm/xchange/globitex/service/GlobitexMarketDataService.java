package org.knowm.xchange.globitex.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.globitex.GlobitexAdapters;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

public class GlobitexMarketDataService extends GlobitexMarketDataServiceRaw
    implements MarketDataService {

  public GlobitexMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return GlobitexAdapters.adaptToTicker(getGlobitexTickerBySymbol(currencyPair));
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    return GlobitexAdapters.adaptToListTicker(getGlobitexTickers());
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return GlobitexAdapters.adaptToOrderBook(
        getGlobitexOrderBookBySymbol(currencyPair), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    return GlobitexAdapters.adaptToTrades(getGlobitexTradesBySymbol(currencyPair), currencyPair);
  }
}
