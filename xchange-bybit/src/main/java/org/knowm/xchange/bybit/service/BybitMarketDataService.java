package org.knowm.xchange.bybit.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.bybit.BybitAdapters;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.marketdata.tickers.BybitTicker;
import org.knowm.xchange.bybit.dto.marketdata.tickers.BybitTickers;
import org.knowm.xchange.bybit.dto.marketdata.tickers.linear.BybitLinearInverseTicker;
import org.knowm.xchange.bybit.dto.marketdata.tickers.option.BybitOptionTicker;
import org.knowm.xchange.bybit.dto.marketdata.tickers.spot.BybitSpotTicker;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.utils.Assert;

public class BybitMarketDataService extends BybitMarketDataServiceRaw implements MarketDataService {

  public BybitMarketDataService(BybitExchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(Instrument instrument, Object... args) throws IOException {
    Assert.notNull(instrument, "Null instrument");

    BybitCategory category = getCategory(args);

    BybitResult<BybitTickers<BybitTicker>> response =
        getTicker24h(category, BybitAdapters.convertToBybitSymbol(instrument.toString()));

    if (response.getResult().getList().isEmpty()) {
      return new Ticker.Builder().build();
    } else {
      BybitTicker bybitTicker = response.getResult().getList().get(0);

      switch (category) {
        case SPOT:
          return BybitAdapters.adaptBybitSpotTicker(
              instrument, response.getTime(), (BybitSpotTicker) bybitTicker);
        case LINEAR:
        case INVERSE:
          return BybitAdapters.adaptBybitLinearInverseTicker(
              instrument, response.getTime(), (BybitLinearInverseTicker) bybitTicker);
        case OPTION:
          return BybitAdapters.adaptBybitOptionTicker(
              instrument, response.getTime(), (BybitOptionTicker) bybitTicker);
        default:
          throw new IllegalStateException("Unexpected value: " + category);
      }
    }
  }

  private static BybitCategory getCategory(Object[] args) {
    if (args.length > 0 && args[0] != null) {
      return (BybitCategory) args[0];
    } else {
      return BybitCategory.LINEAR;
    }
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return getTicker((Instrument) currencyPair, args);
  }

  @Override
  public Map<Instrument, InstrumentMetaData> getInstruments() throws IOException {
    Map<Instrument, InstrumentMetaData> instrumentsMap = new HashMap<>();

    instrumentsMap.putAll(BybitAdapters.adaptBybitInstruments(getInstrumentsInfo(BybitCategory.SPOT, null, null, null, 1000, null).getResult().getList()));
    instrumentsMap.putAll(BybitAdapters.adaptBybitInstruments(getInstrumentsInfo(BybitCategory.LINEAR, null, null, null, 1000, null).getResult().getList()));
    instrumentsMap.putAll(BybitAdapters.adaptBybitInstruments(getInstrumentsInfo(BybitCategory.INVERSE, null, null, null, 1000, null).getResult().getList()));
    instrumentsMap.putAll(BybitAdapters.adaptBybitInstruments(getInstrumentsInfo(BybitCategory.OPTION, null, null, null, 1000, null).getResult().getList()));

    return instrumentsMap;
  }
  @Override
  public Map<Currency, CurrencyMetaData> getCurrencies() throws IOException {
    return new HashMap<>(BybitAdapters.adaptBybitCurrencies(
        getInstrumentsInfo(BybitCategory.SPOT, null, null, null, 1000, null).getResult()
            .getList()));
  }
}
