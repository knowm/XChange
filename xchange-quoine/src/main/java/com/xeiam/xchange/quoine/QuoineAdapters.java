package com.xeiam.xchange.quoine;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.quoine.dto.marketdata.QuoineTicker;

public class QuoineAdapters {

  public static Ticker adaptTicker(QuoineTicker quoineTicker, CurrencyPair currencyPair) {

    Ticker.Builder builder = new Ticker.Builder();
    builder.ask(quoineTicker.getMarketAsk());
    builder.bid(quoineTicker.getMarketBid());
    builder.last(quoineTicker.getLastPrice24h());
    builder.volume(quoineTicker.getVolume24h());
    builder.currencyPair(currencyPair);
    return builder.build();
  }
}
