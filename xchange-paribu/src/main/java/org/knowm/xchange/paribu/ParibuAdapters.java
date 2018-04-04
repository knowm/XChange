package org.knowm.xchange.paribu;

import static org.knowm.xchange.currency.Currency.BTC;
import static org.knowm.xchange.currency.Currency.TRY;

import java.math.BigDecimal;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.paribu.dto.marketdata.BTC_TL;
import org.knowm.xchange.paribu.dto.marketdata.ParibuTicker;

/** @author semihunaldi Various adapters for converting from Paribu DTOs to XChange DTOs */
public final class ParibuAdapters {

  private ParibuAdapters() {}

  /**
   * Adapts a ParibuTicker to a Ticker Object
   *
   * @param paribuTicker The exchange specific ticker
   * @param currencyPair
   * @return The ticker
   */
  public static Ticker adaptTicker(ParibuTicker paribuTicker, CurrencyPair currencyPair) {
    if (!currencyPair.equals(new CurrencyPair(BTC, TRY))) {
      throw new NotAvailableFromExchangeException();
    }
    BTC_TL btcTL = paribuTicker.getBtcTL();
    if (btcTL != null) {
      BigDecimal last = btcTL.getLast();
      BigDecimal lowestAsk = btcTL.getLowestAsk();
      BigDecimal highestBid = btcTL.getHighestBid();
      BigDecimal volume = btcTL.getVolume();
      BigDecimal high24hr = btcTL.getHigh24hr();
      BigDecimal low24hr = btcTL.getLow24hr();
      return new Ticker.Builder()
          .currencyPair(new CurrencyPair(BTC, Currency.TRY))
          .last(last)
          .bid(highestBid)
          .ask(lowestAsk)
          .high(high24hr)
          .low(low24hr)
          .volume(volume)
          .build();
    }
    return null;
  }
}
