package org.knowm.xchange.bybit.service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ArrayUtils;
import org.knowm.xchange.bybit.BybitAdapters;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitCategorizedPayload;
import org.knowm.xchange.bybit.dto.marketdata.BybitOrderBook;
import org.knowm.xchange.bybit.dto.marketdata.BybitTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.CurrencyPairsParam;
import org.knowm.xchange.service.marketdata.params.InstrumentsParams;
import org.knowm.xchange.service.marketdata.params.Params;

public class BybitMarketDataService extends BybitMarketDataServiceRaw implements MarketDataService {

  public static final Integer DEFAULT_ORDER_BOOK_SIZE = 50;

  public BybitMarketDataService(BybitExchange exchange) {
    super(exchange);
  }


  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return getTicker((Instrument) currencyPair, args);
  }


  @Override
  public Ticker getTicker(Instrument instrument, Object... args) throws IOException {
    return getTickers((InstrumentsParams) () -> instrument == null ? null : Collections.singletonList(instrument)).stream()
        .findFirst()
        .orElse(null);
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    Instrument instrument = extractInstrument(params);

    BybitCategorizedPayload<BybitTicker> bybitTickers = getBybitTickers(instrument);
    return bybitTickers.getList().stream()
        .filter(bybitTicker -> bybitExchange.toCurrencyPair(bybitTicker.getSymbol()) != null)
        .map(bybitTicker -> BybitAdapters.toTicker(bybitTicker, bybitExchange.toCurrencyPair(bybitTicker.getSymbol())))
        .collect(Collectors.toList());
  }


  @Override
  public OrderBook getOrderBook(Params params) throws IOException {
    Instrument instrument = extractInstrument(params);
    return getOrderBook(instrument);
  }


  @Override
  public OrderBook getOrderBook(Instrument instrument, Object... args) throws IOException {
    Integer limit = (Integer) ArrayUtils.get(args, 0, DEFAULT_ORDER_BOOK_SIZE);

    BybitOrderBook bybitOrderBook = getBybitOrderBook(instrument, limit);
    return BybitAdapters.toOrderBook(bybitOrderBook, instrument);
  }


  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return getOrderBook((Instrument) currencyPair, args);
  }


  private Instrument extractInstrument(Params params) {
    Instrument instrument = null;
    if (params != null) {
      if (params instanceof CurrencyPairsParam) {
        instrument = ((CurrencyPairsParam) params).getCurrencyPairs().stream().findFirst().orElse(null);
      }
      else if (params instanceof InstrumentsParams) {
        instrument = ((InstrumentsParams) params).getInstruments().stream().findFirst().orElse(null);
      }
    }
    return instrument;
  }

}
