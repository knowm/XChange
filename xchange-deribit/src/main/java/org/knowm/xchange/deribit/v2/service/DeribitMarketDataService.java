package org.knowm.xchange.deribit.v2.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.deribit.v2.DeribitAdapters;
import org.knowm.xchange.deribit.v2.DeribitExchange;
import org.knowm.xchange.deribit.v2.dto.DeribitException;
import org.knowm.xchange.deribit.v2.dto.Kind;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitInstrument;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitOrderBook;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitTicker;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitTrades;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.ExchangeHealth;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class DeribitMarketDataService extends DeribitMarketDataServiceRaw
    implements MarketDataService {

  public DeribitMarketDataService(DeribitExchange exchange) {
    super(exchange);
  }

  public List<Currency> getCurrencies() throws IOException {
    return getDeribitCurrencies().stream()
        .map(DeribitAdapters::toCurrency)
        .collect(Collectors.toList());
  }

  @Override
  public ExchangeHealth getExchangeHealth() {

    try {
      if (!getDeribitPlatformStatus().getLocked()) {
        return ExchangeHealth.ONLINE;
      }
    } catch (DeribitException | IOException | ExchangeException e) {
      return ExchangeHealth.OFFLINE;
    }

    return ExchangeHealth.OFFLINE;
  }

  public List<Instrument> getInstruments() throws IOException {
    try {
      List<DeribitInstrument> deribitInstruments = getDeribitInstruments(null, null, null);

      return deribitInstruments.stream()
          .filter(DeribitInstrument::isActive)
          .filter( deribitInstrument -> Set.of(Kind.SPOT, Kind.OPTIONS, Kind.FUTURES).contains(deribitInstrument.getKind()))
          .map(DeribitAdapters::toInstrument)
          .distinct()
          .collect(Collectors.toList());
    } catch (DeribitException ex) {
      throw new ExchangeException(ex);
    }
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return getTicker((Instrument) currencyPair, args);
  }

  @Override
  public Ticker getTicker(Instrument instrument, Object... args) throws IOException {
    String deribitInstrumentName = DeribitAdapters.toString(instrument);
    DeribitTicker deribitTicker;

    try {
      deribitTicker = getDeribitTicker(deribitInstrumentName);
    } catch (DeribitException ex) {
      throw DeribitAdapters.adapt(ex);
    }
    return DeribitAdapters.adaptTicker(deribitTicker);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return getOrderBook((Instrument) currencyPair, args);
  }

  @Override
  public OrderBook getOrderBook(Instrument instrument, Object... args) throws IOException {
    String deribitInstrumentName = DeribitAdapters.toString(instrument);
    DeribitOrderBook deribitOrderBook;
    try {
      deribitOrderBook = getDeribitOrderBook(deribitInstrumentName, null);
    } catch (DeribitException ex) {
      throw new ExchangeException(ex);
    }

    return DeribitAdapters.adaptOrderBook(deribitOrderBook);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    return getTrades((Instrument) currencyPair, args);
  }

  @Override
  public Trades getTrades(Instrument instrument, Object... args) throws IOException {
    String deribitInstrumentName = DeribitAdapters.toString(instrument);
    DeribitTrades deribitTrades;

    try {
      deribitTrades =
          getLastTradesByInstrument(deribitInstrumentName, null, null, null, null, null);
    } catch (DeribitException ex) {
      throw new ExchangeException(ex);
    }

    return DeribitAdapters.adaptTrades(deribitTrades, instrument);
  }
}
