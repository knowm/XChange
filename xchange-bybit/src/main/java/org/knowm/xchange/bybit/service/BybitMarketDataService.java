package org.knowm.xchange.bybit.service;

import java.io.IOException;
import org.knowm.xchange.bybit.BybitAdapters;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.marketdata.ticker.BybitTicker;
import org.knowm.xchange.bybit.dto.marketdata.ticker.BybitTickers;
import org.knowm.xchange.bybit.dto.marketdata.ticker.spot.BybitSpotTicker;
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

    BybitResult<BybitTickers<BybitTicker>> response =
        getTicker24h(BybitCategory.SPOT, BybitAdapters.convertToBybitSymbol(instrument.toString()));

    if (response.getResult().getList().isEmpty()) {
      return new Ticker.Builder().build();
    } else {
      BybitSpotTicker bybitSpotTicker = (BybitSpotTicker) response.getResult().getList().get(0);
      return new Ticker.Builder()
          .timestamp(response.getTime())
          .instrument(instrument)
          .bid(bybitSpotTicker.getBid1Price())
          .ask(bybitSpotTicker.getAsk1Price())
          .volume(bybitSpotTicker.getVolume24h())
          .quoteVolume(bybitSpotTicker.getTurnover24h())
          .last(bybitSpotTicker.getLastPrice())
          .high(bybitSpotTicker.getHighPrice24h())
          .low(bybitSpotTicker.getLowPrice24h())
          .open(bybitSpotTicker.getPrevPrice24h())
          .percentageChange(bybitSpotTicker.getPrice24hPcnt())
          .build();
    }
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return getTicker((Instrument) currencyPair, args);
  }
}
