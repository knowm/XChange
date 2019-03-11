package org.knowm.xchange.koineks;

import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.koineks.dto.marketdata.BaseKoineksTicker;
import org.knowm.xchange.koineks.dto.marketdata.KoineksCurrency;
import org.knowm.xchange.koineks.dto.marketdata.KoineksTicker;

/** @author semihunaldi Various adapters for converting from Koineks DTOs to XChange DTOs */
public final class KoineksAdapters {

  private KoineksAdapters() {}

  /**
   * Adapts a KoineksTicker to a Ticker Object
   *
   * @param koineksTicker The exchange specific ticker
   * @param currencyPair
   * @return The ticker
   */
  public static Ticker adaptTicker(KoineksTicker koineksTicker, CurrencyPair currencyPair) {
    switch (currencyPair.base.getCurrencyCode()) {
      case KoineksCurrency.BTC:
        return getTickerOf(koineksTicker.getKoineksBTCTicker(), currencyPair.base);
      case KoineksCurrency.ETH:
        return getTickerOf(koineksTicker.getKoineksETHTicker(), currencyPair.base);
      case KoineksCurrency.LTC:
        return getTickerOf(koineksTicker.getKoineksLTCTicker(), currencyPair.base);
      case KoineksCurrency.DASH:
        return getTickerOf(koineksTicker.getKoineksDASHTicker(), currencyPair.base);
      case KoineksCurrency.DOGE:
        return getTickerOf(koineksTicker.getKoineksDOGETicker(), currencyPair.base);
      default:
        throw new NotAvailableFromExchangeException();
    }
  }

  private static Ticker getTickerOf(BaseKoineksTicker koineksTicker, Currency currency) {
    if (koineksTicker != null) {
      BigDecimal last = koineksTicker.getCurrent();
      BigDecimal lowestAsk = koineksTicker.getAsk();
      BigDecimal highestBid = koineksTicker.getBid();
      BigDecimal volume = koineksTicker.getVolume();
      BigDecimal high24hr = koineksTicker.getHigh();
      BigDecimal low24hr = koineksTicker.getLow();
      String timestampStr = koineksTicker.getTimestamp();
      Date timestamp = new Date(Long.valueOf(timestampStr));
      return new Ticker.Builder()
          .currencyPair(new CurrencyPair(currency, Currency.TRY))
          .last(last)
          .bid(highestBid)
          .ask(lowestAsk)
          .high(high24hr)
          .low(low24hr)
          .timestamp(timestamp)
          .volume(volume)
          .build();
    }
    return null;
  }
}
