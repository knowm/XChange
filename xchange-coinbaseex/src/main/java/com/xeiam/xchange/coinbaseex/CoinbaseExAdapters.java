package com.xeiam.xchange.coinbaseex;

import com.xeiam.xchange.coinbaseex.dto.marketdata.CoinbaseExProductBook;
import com.xeiam.xchange.coinbaseex.dto.marketdata.CoinbaseExProductStats;
import com.xeiam.xchange.coinbaseex.dto.marketdata.CoinbaseExProductTicker;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Yingzhe on 4/6/2015.
 */
public class CoinbaseExAdapters {

  public static Ticker adaptTicker(CoinbaseExProductTicker ticker, CoinbaseExProductStats stats, CoinbaseExProductBook book, CurrencyPair currencyPair) {

    BigDecimal last = ticker != null ? ticker.getPrice() : null;
    BigDecimal high = stats != null ? stats.getHigh() : null;
    BigDecimal low = stats != null ? stats.getLow() : null;
    BigDecimal buy = book != null ? book.getBestBid().getPrice() : null;
    BigDecimal sell = book != null ? book.getBestAsk().getPrice() : null;
    BigDecimal volume = stats != null ? stats.getVolume() : null;
    Date date = ticker != null ? ticker.getTime() : new Date();

    return new Ticker.Builder().currencyPair(currencyPair).last(last).high(high).low(low).bid(buy).ask(sell).volume(volume).timestamp(date).build();
  }
}
