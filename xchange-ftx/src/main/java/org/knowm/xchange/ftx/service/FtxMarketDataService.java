package org.knowm.xchange.ftx.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.ftx.FtxAdapters;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class FtxMarketDataService extends FtxMarketDataServiceRaw implements MarketDataService {

  public FtxMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return FtxAdapters.adaptOrderBook(
        getFtxOrderbook(FtxAdapters.adaptCurrencyPairToFtxMarket(currencyPair)), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    return FtxAdapters.adaptTrades(
        getFtxTrades(FtxAdapters.adaptCurrencyPairToFtxMarket(currencyPair)).getResult(),
        currencyPair);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    String startTime = null;
    String endTime = null;
    Integer limit = 200;
    String resolution = "60";
    if (args.length > 0) {
      if (args[0] instanceof String) {
        startTime = args[0].toString();
      }
      if (args.length > 1) {
        if (args[1] instanceof String) {
          endTime = args[1].toString();
        }
      }
      if (args.length > 2) {
        if (args[2] instanceof Integer) {
          limit = (int) args[2];
        }
      }
      if (args.length > 3) {
        if (args[2] instanceof String) {
          resolution = args[3].toString();
        }
      }
    }
    return FtxAdapters.adaptTicker(
        getFtxMarket(FtxAdapters.adaptCurrencyPairToFtxMarket(currencyPair)),
        getFtxCandles(
            FtxAdapters.adaptCurrencyPairToFtxMarket(currencyPair),
            resolution,
            startTime,
            endTime,
            limit), // 60 seconds
        currencyPair);
  }

  @Override
  public Ticker getTicker(Instrument instrument, Object... args) throws IOException {
    CurrencyPair currencyPair = FtxAdapters.getCurrencyPairFromInstrument(instrument);
    return getTicker(currencyPair, args);
  }
}
