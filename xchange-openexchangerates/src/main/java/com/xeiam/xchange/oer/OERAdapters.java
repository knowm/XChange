package com.xeiam.xchange.oer;

import java.math.BigDecimal;
import java.util.Date;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;

/**
 * Various adapters for converting from OER DTOs to XChange DTOs
 */
public final class OERAdapters {

  /**
   * private Constructor
   */
  private OERAdapters() {

  }

  public static Ticker adaptTicker(CurrencyPair currencyPair, Double exchangeRate, Long timestamp) {

    BigDecimal last = BigDecimal.valueOf(exchangeRate);
    Date timestampDate = new Date(timestamp);
    return new Ticker.Builder().currencyPair(currencyPair).last(last).timestamp(timestampDate).build();
  }

}
