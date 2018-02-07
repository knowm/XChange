package org.knowm.xchange.bibox.dto;

import java.util.Date;

import org.knowm.xchange.bibox.dto.marketdata.BiboxTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

public class BiboxAdapters {

  public static String toBiboxPair(CurrencyPair pair) {

    return pair.base.getCurrencyCode() + "_" + pair.counter.getCurrencyCode();
  }

  public static Ticker adaptTicker(BiboxTicker ticker, CurrencyPair currencyPair) {
    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .ask(ticker.getSell())
        .bid(ticker.getBuy())
        .high(ticker.getHigh())
        .low(ticker.getLow())
        .last(ticker.getLast())
        .volume(ticker.getVol())
        .timestamp(new Date(ticker.getTimestamp()))
        .build();
  }
}
