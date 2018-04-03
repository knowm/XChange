package org.knowm.xchange.koinim;

import static org.knowm.xchange.currency.Currency.BTC;
import static org.knowm.xchange.currency.Currency.TRY;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.koinim.dto.marketdata.KoinimTicker;

/** @author ahmetoz Various adapters for converting from Koinim DTOs to XChange DTOs */
public final class KoinimAdapters {

  private KoinimAdapters() {}

  /**
   * Adapts a KoinimTicker to a Ticker Object
   *
   * @param koinimTicker The exchange specific ticker
   * @param currencyPair
   * @return The ticker
   */
  public static Ticker adaptTicker(KoinimTicker koinimTicker, CurrencyPair currencyPair) {

    if (!currencyPair.equals(new CurrencyPair(BTC, TRY))) {
      throw new NotAvailableFromExchangeException();
    }

    if (koinimTicker != null) {
      return new Ticker.Builder()
          .currencyPair(new CurrencyPair(BTC, Currency.TRY))
          .last(koinimTicker.getSell())
          .bid(koinimTicker.getBid())
          .ask(koinimTicker.getAsk())
          .high(koinimTicker.getHigh())
          .low(koinimTicker.getLow())
          .volume(koinimTicker.getVolume())
          .vwap(koinimTicker.getAvg())
          .build();
    }
    return null;
  }
}
