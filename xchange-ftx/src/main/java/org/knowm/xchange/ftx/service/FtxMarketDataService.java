package org.knowm.xchange.ftx.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
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
  public OrderBook getOrderBook(Instrument instrument, Object... args) throws IOException {
    return FtxAdapters.adaptOrderBook(
        getFtxOrderbook(FtxAdapters.adaptInstrumentToFtxMarket(instrument)), instrument);
  }

  @Override
  public Trades getTrades(Instrument instrument, Object... args) throws IOException {
    return FtxAdapters.adaptTrades(
        getFtxTrades(FtxAdapters.adaptInstrumentToFtxMarket(instrument)).getResult(), instrument);
  }

  @Override
  public Ticker getTicker(Instrument instrument, Object... args) throws IOException {
    return FtxAdapters.adaptTicker(
        getFtxMarket(FtxAdapters.adaptInstrumentToFtxMarket(instrument)),
        getFtxCandles(FtxAdapters.adaptInstrumentToFtxMarket(instrument), "60"), // 60 seconds
        instrument);
  }
}
