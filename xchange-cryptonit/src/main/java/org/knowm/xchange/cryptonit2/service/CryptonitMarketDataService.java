package org.knowm.xchange.cryptonit2.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptonit2.CryptonitAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author Matija Mazi */
public class CryptonitMarketDataService
    extends org.knowm.xchange.cryptonit2.service.CryptonitMarketDataServiceRaw
    implements MarketDataService {

  public CryptonitMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return CryptonitAdapters.adaptTicker(getCryptonitTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return CryptonitAdapters.adaptOrderBook(getCryptonitOrderBook(currencyPair), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    CryptonitTime time = args.length > 0 ? (CryptonitTime) args[0] : null;
    return CryptonitAdapters.adaptTrades(getTransactions(currencyPair, time), currencyPair);
  }
}
