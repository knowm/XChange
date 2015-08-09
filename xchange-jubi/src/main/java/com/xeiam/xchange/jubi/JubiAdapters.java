package com.xeiam.xchange.jubi;

import java.math.BigDecimal;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.jubi.dto.marketdata.JubiTicker;

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
