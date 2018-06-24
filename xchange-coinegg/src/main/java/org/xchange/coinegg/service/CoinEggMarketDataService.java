package org.xchange.coinegg.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.xchange.coinegg.CoinEggAdapters;
import org.xchange.coinegg.CoinEggUtils;

public class CoinEggMarketDataService extends CoinEggMarketDataServiceRaw
    implements MarketDataService {

  public CoinEggMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return CoinEggAdapters.adaptTicker(
        getCoinEggTicker(CoinEggUtils.toBaseCoin(currencyPair)), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return CoinEggAdapters.adaptOrders(
        getCoinEggOrders(CoinEggUtils.toBaseCoin(currencyPair)), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    return CoinEggAdapters.adaptTrades(
        getCoinEggTrades(CoinEggUtils.toBaseCoin(currencyPair)), currencyPair);
  }
}
