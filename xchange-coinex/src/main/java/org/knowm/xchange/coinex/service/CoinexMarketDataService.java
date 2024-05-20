package org.knowm.xchange.coinex.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.coinex.CoinexAdapters;
import org.knowm.xchange.coinex.CoinexErrorAdapter;
import org.knowm.xchange.coinex.CoinexExchange;
import org.knowm.xchange.coinex.dto.CoinexException;
import org.knowm.xchange.coinex.dto.marketdata.CoinexAllMarketStatisticsV1;
import org.knowm.xchange.coinex.dto.marketdata.CoinexSingleMarketStatisticsV1;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.CurrencyPairsParam;
import org.knowm.xchange.service.marketdata.params.InstrumentsParams;
import org.knowm.xchange.service.marketdata.params.Params;

public class CoinexMarketDataService extends CoinexMarketDataServiceRaw implements MarketDataService {


  public CoinexMarketDataService(CoinexExchange exchange) {
    super(exchange);
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    // parse parameters
    Collection<Instrument> instruments = Collections.emptyList();
    if (params instanceof InstrumentsParams) {
      instruments = ((InstrumentsParams) params).getInstruments();
    } else if (params instanceof CurrencyPairsParam) {
      instruments = ((CurrencyPairsParam) params).getCurrencyPairs().stream()
          .map(Instrument.class::cast)
          .collect(Collectors.toList());
    }

    try {
      // get all
      if (instruments.isEmpty()) {
        CoinexAllMarketStatisticsV1 coinexAllMarketStatisticsV1 = getCoinexAllMarketStatisticsV1();
        return coinexAllMarketStatisticsV1.getTickers().entrySet().stream()
            .map(entry -> CoinexAdapters.toTicker(entry.getKey(), entry.getValue(), coinexAllMarketStatisticsV1.getTimestamp()))
            .filter(ticker -> ticker.getInstrument() != null)
            .collect(Collectors.toList());
      }
      else {
        // get the first one
        Instrument instrument = instruments.iterator().next();
        return Collections.singletonList(getTicker(instrument));
      }

    } catch (CoinexException e) {
      throw CoinexErrorAdapter.adapt(e);
    }
  }

  @Override
  public Ticker getTicker(Instrument instrument, Object... args) throws IOException {
    try {
      CoinexSingleMarketStatisticsV1 singleMarketStatisticsV1 = getCoinexSingleMarketStatisticsV1(instrument);
      return CoinexAdapters.toTicker(instrument, singleMarketStatisticsV1.getTicker(), singleMarketStatisticsV1.getTimestamp());

    } catch (CoinexException e) {
      throw CoinexErrorAdapter.adapt(e);
    }
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return getTicker((Instrument) currencyPair, args);
  }

}
