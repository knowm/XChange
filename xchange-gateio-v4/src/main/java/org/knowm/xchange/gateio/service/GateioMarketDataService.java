package org.knowm.xchange.gateio.service;

import org.apache.commons.lang3.Validate;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.GateioErrorAdapter;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.dto.GateioException;
import org.knowm.xchange.gateio.dto.marketdata.GateioCurrencyPairDetails;
import org.knowm.xchange.gateio.dto.marketdata.GateioOrderBook;
import org.knowm.xchange.gateio.dto.marketdata.GateioTicker;
import org.knowm.xchange.gateio.dto.marketdata.GateioTradeHistory;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GateioMarketDataService extends GateioMarketDataServiceRaw implements MarketDataService {

  public GateioMarketDataService(GateioExchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return getTicker((Instrument) currencyPair, args);
  }


  @Override
  public Ticker getTicker(Instrument instrument, Object... args) throws IOException {
    Validate.notNull(instrument);
    try {
      List<GateioTicker> tickers = getGateioTickers(instrument);
      Validate.validState(tickers.size() == 1);

      return GateioAdapters.toTicker(tickers.get(0));
    } catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }


  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    try {
      List<GateioTicker> tickers = getGateioTickers(null);

      return tickers.stream()
              .map(GateioAdapters::toTicker)
              .collect(Collectors.toList());
    } catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return getOrderBook((Instrument) currencyPair, args);
  }

  @Override
  public OrderBook getOrderBook(Instrument instrument, Object... args) throws IOException {

    GateioOrderBook gateioOrderBook = getGateioOrderBook(instrument);

    return GateioAdapters.toOrderBook(gateioOrderBook, instrument);

  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    GateioTradeHistory tradeHistory =
        (args != null && args.length > 0 && args[0] != null && args[0] instanceof String)
            ? super.getBTERTradeHistorySince(
                currencyPair.base.getCurrencyCode(),
                currencyPair.counter.getCurrencyCode(),
                (String) args[0])
            : super.getBTERTradeHistory(
                currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());

    return GateioAdapters.adaptTrades(tradeHistory, currencyPair);
  }


  public Map<Instrument, InstrumentMetaData> getMetaDataByInstrument() throws IOException {
    try {
      List<GateioCurrencyPairDetails> metadata = getCurrencyPairDetails();

      return metadata.stream()
          .collect(Collectors.toMap(
              gateioCurrencyPairDetails -> new CurrencyPair(gateioCurrencyPairDetails.getAsset(), gateioCurrencyPairDetails.getQuote()),
              GateioAdapters::toInstrumentMetaData
          ));
    }
    catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }


}
