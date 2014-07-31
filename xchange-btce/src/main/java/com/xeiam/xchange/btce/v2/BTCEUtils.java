package com.xeiam.xchange.btce.v2;

import java.util.Arrays;
import java.util.List;

import com.xeiam.xchange.currency.CurrencyPair;

/**
 * A central place for shared BTC-E properties
 */
@Deprecated
public final class BTCEUtils {

  /**
   * private Constructor
   */
  private BTCEUtils() {

  }

  public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(

  CurrencyPair.BTC_USD,

  CurrencyPair.BTC_RUR,

  CurrencyPair.BTC_EUR,

  CurrencyPair.LTC_BTC,

  CurrencyPair.LTC_USD,

  CurrencyPair.LTC_RUR,

  CurrencyPair.LTC_EUR,

  CurrencyPair.NMC_BTC,

  CurrencyPair.NMC_USD,

  CurrencyPair.USD_RUR,

  CurrencyPair.EUR_USD,

  CurrencyPair.NVC_BTC,

  CurrencyPair.NVC_USD,

  CurrencyPair.TRC_BTC,

  CurrencyPair.PPC_BTC,

  CurrencyPair.PPC_USD,

  CurrencyPair.FTC_BTC,

  CurrencyPair.XPM_BTC

  );

  /**
   * Checks if a given CurrencyPair is covered by this exchange
   * 
   * @param currencyPair
   * @return
   */
  public static boolean isValidCurrencyPair(CurrencyPair currencyPair) {

    return CURRENCY_PAIRS.contains(currencyPair);
  }

}
