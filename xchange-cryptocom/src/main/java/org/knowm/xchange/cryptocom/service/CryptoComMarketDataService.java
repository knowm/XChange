package org.knowm.xchange.cryptocom.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.cryptocom.CryptoComAdapters;
import org.knowm.xchange.cryptocom.CryptoComExchange;
import org.knowm.xchange.cryptocom.dto.CryptoComException;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

public class CryptoComMarketDataService extends CryptoComMarketDataServiceRaw
    implements MarketDataService {

  public CryptoComMarketDataService(
      CryptoComExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  @Override
  public Ticker getTicker(Instrument instrument, Object... args) throws IOException {
    return CryptoComAdapters.adaptTicker(
        getCryptoComTicker(CryptoComAdapters.toInstrumentName(instrument)));
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException, CryptoComException {
    return CryptoComAdapters.adaptTickers(getCryptoComTickers());
  }

  @Override
  public OrderBook getOrderBook(Instrument instrument, Object... args) throws IOException {
    if (!(instrument instanceof CurrencyPair)) {
      throw new NotYetImplementedForExchangeException("getOrderBook");
    }
    Integer depth =
        args != null && args.length > 0 && args[0] instanceof Integer ? (Integer) args[0] : null;
    return CryptoComAdapters.adaptOrderBook(
        getCryptoComOrderBook(CryptoComAdapters.toInstrumentName(instrument), depth),
        (CurrencyPair) instrument);
  }

  @Override
  public Trades getTrades(Instrument instrument, Object... args) throws IOException {
    if (!(instrument instanceof CurrencyPair)) {
      throw new NotYetImplementedForExchangeException("getTrades");
    }
    Integer count =
        args != null && args.length > 0 && args[0] instanceof Integer ? (Integer) args[0] : null;
    return CryptoComAdapters.adaptTrades(
        getCryptoComTrades(CryptoComAdapters.toInstrumentName(instrument), count),
        (CurrencyPair) instrument);
  }
}
