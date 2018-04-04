package org.knowm.xchange.btcc;

import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.btcc.dto.marketdata.BTCCTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

public class BTCCAdapters {

  public static Ticker adaptTicker(BTCCTicker btccTicker, CurrencyPair currencyPair) {

    BigDecimal last = btccTicker.getLast();
    BigDecimal high = btccTicker.getHigh();
    BigDecimal low = btccTicker.getLow();
    BigDecimal ask = btccTicker.getAskPrice();
    BigDecimal bid = btccTicker.getBidPrice();
    BigDecimal volume = btccTicker.getVolume();
    Date timestamp = new Date(btccTicker.getTimestamp());

    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .last(last)
        .high(high)
        .low(low)
        .volume(volume)
        .ask(ask)
        .bid(bid)
        .timestamp(timestamp)
        .build();
  }
}
