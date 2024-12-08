package org.knowm.xchange.bitmex.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ArrayUtils;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.bitmex.dto.account.BitmexTicker;
import org.knowm.xchange.bitmex.dto.account.BitmexTicker.State;
import org.knowm.xchange.bitmex.dto.account.BitmexTicker.SymbolType;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexAsset;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPublicTrade;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

/**
 * Implementation of the market data service for Bitmex
 *
 * <ul>
 *   <li>Provides access to various market data values
 * </ul>
 */
public class BitmexMarketDataService extends BitmexMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitmexMarketDataService(BitmexExchange exchange) {

    super(exchange);
  }

  @Override
  public List<Ticker> getTickers(Params params) {
    return getActiveTickers().stream()
        .filter(bitmexTicker -> bitmexTicker.getSymbolType() == SymbolType.SPOT)
        .filter(bitmexTicker -> bitmexTicker.getState() == State.OPEN)
        .map(BitmexAdapters::toTicker)
        .collect(Collectors.toList());
  }

  @Override
  public Ticker getTicker(Instrument instrument, Object... args) throws IOException {
    String bitmexSymbol = BitmexAdapters.toString(instrument);
    List<BitmexTicker> bitmexTickers = getTicker(bitmexSymbol);

    if (bitmexTickers.isEmpty()) {
      return null;
    }
    return BitmexAdapters.toTicker(bitmexTickers.get(0));
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return getTicker((Instrument) currencyPair, args);
  }

  @Override
  public OrderBook getOrderBook(Instrument instrument, Object... args) throws IOException {
    Integer depth = (Integer) ArrayUtils.get(args, 0);
    String bitmexSymbol = BitmexAdapters.toString(instrument);

    return BitmexAdapters.toOrderBook(getBitmexDepth(bitmexSymbol, depth));
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return getOrderBook((Instrument) currencyPair, args);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    Integer limit = null;
    Long start = null;

    if (args.length > 0) {
      Object arg1 = args[0];
      if (arg1 instanceof Integer) {
        limit = (Integer) arg1;
      } else {
        throw new ExchangeException("args[0] must be of type Integer!");
      }
    }
    if (args.length > 1) {
      Object arg2 = args[1];
      if (arg2 instanceof Long) {
        start = (Long) arg2;
      } else {
        throw new ExchangeException("args[1] must be of type Long!");
      }
    }

    String bitmexSymbol = BitmexAdapters.toString(currencyPair);
    List<BitmexPublicTrade> trades = getBitmexTrades(bitmexSymbol, limit, start);
    return BitmexAdapters.adaptTrades(trades, currencyPair);
  }

  public List<Currency> getCurrencies() {
    return getAssets().stream()
        .filter(BitmexAsset::getEnabled)
        .map(BitmexAsset::getAsset)
        .collect(Collectors.toList());
  }

  public List<Instrument> getInstruments() {
    return getActiveTickers().stream()
        .filter(bitmexTicker -> bitmexTicker.getSymbolType() != SymbolType.UNKNOWN)
        .map(BitmexAdapters::toInstrument)
        .collect(Collectors.toList());
  }


}
