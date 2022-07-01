package org.knowm.xchange.coinegg.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinegg.CoinEggAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class CoinEggMarketDataService extends CoinEggMarketDataServiceRaw
    implements MarketDataService {

  public CoinEggMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return CoinEggAdapters.adaptTicker(
        getCoinEggTicker(currencyPair.base.getCurrencyCode().toLowerCase()), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return CoinEggAdapters.adaptOrders(
        getCoinEggOrders(
            currencyPair.counter.getCurrencyCode().toLowerCase(),
            currencyPair.base.getCurrencyCode().toLowerCase()),
        currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    return CoinEggAdapters.adaptTrades(
        getCoinEggTrades(currencyPair.base.getCurrencyCode().toLowerCase()), currencyPair);
  }
}
