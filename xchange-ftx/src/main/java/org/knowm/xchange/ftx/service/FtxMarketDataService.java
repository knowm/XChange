package org.knowm.xchange.ftx.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.ftx.FtxAdapters;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

public class FtxMarketDataService extends FtxMarketDataServiceRaw implements MarketDataService {

  public FtxMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return null;
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    return null;
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return FtxAdapters.adaptOrderBook(
        getFtxOrderbook(FtxAdapters.adaptCurrencyPair(currencyPair)), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(Params params) throws IOException {
    return null;
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    return null;
  }

  @Override
  public Trades getTrades(Params params) throws IOException {
    return null;
  }
}
