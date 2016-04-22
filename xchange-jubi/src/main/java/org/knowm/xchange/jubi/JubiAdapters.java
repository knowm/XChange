package org.knowm.xchange.jubi;

import java.math.BigDecimal;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.jubi.dto.marketdata.JubiTicker;

/**
 * Created by Yingzhe on 3/16/2015.
 */
public class JubiAdapters {
  public static Ticker adaptTicker(JubiTicker ticker, CurrencyPair currencyPair) {
    BigDecimal high = ticker.getHigh();
    BigDecimal low = ticker.getLow();
    BigDecimal buy = ticker.getBuy();
    BigDecimal sell = ticker.getSell();
    BigDecimal last = ticker.getLast();
    BigDecimal volume = ticker.getVol();
    return new Ticker.Builder().currencyPair(currencyPair).last(last).high(high).low(low).bid(buy).ask(sell).volume(volume).build();
  }
}
