package com.xeiam.xchange.cexio;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.xeiam.xchange.currency.CurrencyPair;

/**
 * Author: brox
 * Since: 2/6/14
 */

public class CexIOUtils {

  private static final long START_MILLIS = 1388534400000L; // Jan 1st, 2014 in milliseconds from epoch
  // counter for the nonce
  private static final AtomicInteger lastNonce = new AtomicInteger((int) ((System.currentTimeMillis() - START_MILLIS) / 250L));

  /**
   * private Constructor
   */
  private CexIOUtils() {

  }

  public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(

  CurrencyPair.LTC_BTC, CurrencyPair.NMC_BTC, CurrencyPair.GHs_BTC, CurrencyPair.GHs_NMC

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

  public static int nextNonce() {

    return lastNonce.incrementAndGet();
  }

}
