package org.knowm.xchange.btcturk.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcturk.BTCTurkAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

/**
 * @author semihunaldi
 * @author mertguner
 */
public class BTCTurkMarketDataService extends BTCTurkMarketDataServiceRaw
    implements MarketDataService {

  public BTCTurkMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return BTCTurkAdapters.adaptTicker(super.getBTCTurkTicker(currencyPair));
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    return BTCTurkAdapters.adaptTicker(super.getBTCTurkTicker());
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {
    return super.getExchangeSymbols();
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return BTCTurkAdapters.adaptOrderBook(super.getBTCTurkOrderBook(currencyPair), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    Integer last = args.length > 0 ? (Integer) args[0] : null;
    if (last != null) {
      if (last > 500) // Max. value for last parameter is 500)
      last = 500;
      return BTCTurkAdapters.adaptTrades(super.getBTCTurkTrades(currencyPair, last), currencyPair);
    } else
      return BTCTurkAdapters.adaptTrades(super.getBTCTurkTrades(currencyPair, 50), currencyPair);
  }
}
