package org.knowm.xchange.bybit.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.bybit.BybitAdapters;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.marketdata.BybitTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
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

    BybitResult<List<BybitTicker>> response =
        getTicker24h(BybitAdapters.convertToBybitSymbol(instrument.toString()));

    if (response.getResult().isEmpty()) {
      return new Ticker.Builder().build();
    } else {
      BybitTicker bybitTicker = response.getResult().get(0);
      return new Ticker.Builder()
          .timestamp(response.getTimeNow())
          .instrument(instrument)
          .bid(bybitTicker.getBestBidPrice())
          .ask(bybitTicker.getBestAskPrice())
          .volume(bybitTicker.getVolume24h())
          .quoteVolume(bybitTicker.getTurnover24h())
          .last(bybitTicker.getLastPrice())
          .high(bybitTicker.getHighPrice())
          .low(bybitTicker.getLowPrice())
          .open(bybitTicker.getPrevPrice24h())
          .percentageChange(bybitTicker.getPrice24hPercentageChange())
          .build();
    }
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return getTicker((Instrument) currencyPair, args);
  }
}
