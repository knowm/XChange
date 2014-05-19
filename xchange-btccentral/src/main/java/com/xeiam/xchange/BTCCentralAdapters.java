package com.xeiam.xchange;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.BTCCentralTicker;
import com.xeiam.xchange.dto.marketdata.Ticker;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author kpysniak
 */
public class BTCCentralAdapters {

  /**
   * Singleton
   */
  private BTCCentralAdapters() { }

  /**
   * Adapts a BTCCentralTicker to a Ticker Object
   *
   * @param btcCentralTicker The exchange specific ticker
   * @param currencyPair (e.g. BTC/USD)
   * @return The ticker
   */
  public static Ticker adaptTicker(BTCCentralTicker btcCentralTicker, CurrencyPair currencyPair) {

    BigDecimal bid = btcCentralTicker.getBid();
    BigDecimal ask = btcCentralTicker.getAsk();
    BigDecimal high = btcCentralTicker.getHigh();
    BigDecimal low = btcCentralTicker.getLow();
    BigDecimal volume = btcCentralTicker.getVolume();
    Date timestamp = new Date(btcCentralTicker.getAt() * 1000L);

    return Ticker.TickerBuilder.newInstance().withCurrencyPair(currencyPair)
        .withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume)
        .withTimestamp(timestamp).build();
  }
}
