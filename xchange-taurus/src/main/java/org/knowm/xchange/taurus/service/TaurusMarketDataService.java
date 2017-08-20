package org.knowm.xchange.taurus.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.taurus.Taurus;
import org.knowm.xchange.taurus.TaurusAdapters;

/**
 * @author Matija Mazi
 */
public class TaurusMarketDataService extends TaurusMarketDataServiceRaw implements MarketDataService {

  public TaurusMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return TaurusAdapters.adaptTicker(getTaurusTicker(), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return TaurusAdapters.adaptOrderBook(getTaurusOrderBook(), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    final Taurus.Time time = args == null || args.length == 0 ? null : (Taurus.Time) args[0];
    return TaurusAdapters.adaptTrades(getTaurusTransactions(time), currencyPair);
  }
}
