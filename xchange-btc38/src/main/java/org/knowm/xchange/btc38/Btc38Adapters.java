package org.knowm.xchange.btc38;

import java.math.BigDecimal;

import org.knowm.xchange.btc38.dto.marketdata.Btc38Ticker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

/**
 * Created by Yingzhe on 12/18/2014.
 */
public class Btc38Adapters {

  public static Ticker adaptTicker(Btc38Ticker btc38Ticker, CurrencyPair currencyPair) {

    BigDecimal last = btc38Ticker.getLast();
    BigDecimal high = btc38Ticker.getHigh();
    BigDecimal low = btc38Ticker.getLow();
    BigDecimal ask = btc38Ticker.getSell();
    BigDecimal bid = btc38Ticker.getBuy();
    BigDecimal volume = btc38Ticker.getVol();

    return new Ticker.Builder().currencyPair(currencyPair).last(last).high(high).low(low).volume(volume).ask(ask).bid(bid).build();
  }
}
