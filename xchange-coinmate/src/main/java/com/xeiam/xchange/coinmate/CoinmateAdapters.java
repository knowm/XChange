package com.xeiam.xchange.coinmate;

import com.xeiam.xchange.coinmate.dto.marketdata.CoinmateTicker;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Martin Stachon
 */
public class CoinmateAdapters {
    
    /**
   * Adapts a CoinmateTicker to a Ticker Object
   *
   * @param coinmateTicker The exchange specific ticker
   * @param currencyPair (e.g. BTC/USD)
   * @return The ticker
   */
  public static Ticker adaptTicker(CoinmateTicker coinmateTicker, CurrencyPair currencyPair) {

    BigDecimal last = coinmateTicker.getData().getLast();
    BigDecimal bid = coinmateTicker.getData().getBid();
    BigDecimal ask = coinmateTicker.getData().getAsk();
    BigDecimal high = coinmateTicker.getData().getHigh();
    BigDecimal low = coinmateTicker.getData().getLow();
    BigDecimal volume = coinmateTicker.getData().getAmount();

    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).high(high).low(low).volume(volume)
        .build();

  }

}
