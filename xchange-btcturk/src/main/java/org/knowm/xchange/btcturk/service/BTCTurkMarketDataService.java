package org.knowm.xchange.btcturk.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcturk.BTCTurkAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author semihunaldi */
public class BTCTurkMarketDataService extends BTCTurkMarketDataServiceRaw
    implements MarketDataService {

  public BTCTurkMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return BTCTurkAdapters.adaptTicker(getBTCTurkTicker(currencyPair));
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return BTCTurkAdapters.adaptOrderBook(getBTCTurkOrderBook(currencyPair), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    Integer last = args.length > 0 ? (Integer) args[0] : null;
    if (last != null && last > 500) { // Max. value for last parameter is 500)
      last = 500;
    }
    return BTCTurkAdapters.adaptTrades(getBTCTurkTrades(currencyPair, last), currencyPair);
  }
}
