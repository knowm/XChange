package com.xeiam.xchange.taurus.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;
import com.xeiam.xchange.taurus.Taurus;
import com.xeiam.xchange.taurus.TaurusAdapters;

/**
 * @author Matija Mazi
 */
public class TaurusMarketDataService extends TaurusMarketDataServiceRaw implements PollingMarketDataService {

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
    final Taurus.Time time = args.length == 0 ? null : (Taurus.Time) args[0];
    return TaurusAdapters.adaptTrades(getTaurusTransactions(time), currencyPair);
  }
}
