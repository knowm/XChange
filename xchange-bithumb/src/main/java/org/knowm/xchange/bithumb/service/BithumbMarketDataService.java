package org.knowm.xchange.bithumb.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bithumb.BithumbAdapters;
import org.knowm.xchange.bithumb.BithumbErrorAdapter;
import org.knowm.xchange.bithumb.BithumbException;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

public class BithumbMarketDataService extends BithumbMarketDataServiceRaw
    implements MarketDataService {

  public BithumbMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    try {
      return BithumbAdapters.adaptTicker(getBithumbTicker(currencyPair), currencyPair);
    } catch (BithumbException e) {
      throw BithumbErrorAdapter.adapt(e);
    }
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    try {
      return BithumbAdapters.adaptTickers(getBithumbTickers());
    } catch (BithumbException e) {
      throw BithumbErrorAdapter.adapt(e);
    }
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    try {
      return BithumbAdapters.adaptOrderBook(getBithumbOrderBook(currencyPair));
    } catch (BithumbException e) {
      throw BithumbErrorAdapter.adapt(e);
    }
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    try {
      return BithumbAdapters.adaptTransactions(getBithumbTrades(currencyPair), currencyPair);
    } catch (BithumbException e) {
      throw BithumbErrorAdapter.adapt(e);
    }
  }
}
