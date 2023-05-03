package org.knowm.xchange.krakenfutures.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.krakenfutures.KrakenFuturesAdapters;
import org.knowm.xchange.dto.marketdata.*;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author Jean-Christophe Laruelle */
public class KrakenFuturesMarketDataService extends KrakenFuturesMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public KrakenFuturesMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(Instrument instrument, Object... args) throws IOException {
    return KrakenFuturesAdapters.adaptTicker(
            getKrakenFuturesTicker(instrument), instrument);
  }

  @Override
  public OrderBook getOrderBook(Instrument instrument, Object... args) throws IOException {
    return KrakenFuturesAdapters.adaptOrderBook(getKrakenFuturesOrderBook(instrument));
  }

  @Override
  public Trades getTrades(Instrument instrument, Object... args) throws IOException {
    return KrakenFuturesAdapters.adaptTrades(getKrakenFuturesTrades(instrument), instrument);
  }

  @Override
  public FundingRates getFundingRates() throws IOException {
    return KrakenFuturesAdapters.adaptFundingRates(getKrakenFuturesTickers());
  }

  @Override
  public FundingRate getFundingRate(Instrument instrument) throws IOException {
    return KrakenFuturesAdapters.adaptFundingRate(getKrakenFuturesTicker(instrument));
  }
}
