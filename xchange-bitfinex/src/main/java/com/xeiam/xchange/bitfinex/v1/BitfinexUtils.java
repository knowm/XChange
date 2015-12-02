package com.xeiam.xchange.bitfinex.v1;

import com.xeiam.xchange.bitfinex.v1.dto.BitfinexException;
import com.xeiam.xchange.currency.CurrencyPair;

/**
 * A central place for shared Bitfinex properties
 */
public final class BitfinexUtils {

  /**
   * private Constructor
   */
  private BitfinexUtils() {

  }

  public static String toPairString(CurrencyPair currencyPair) {

    return currencyPair.base.toString().toLowerCase() + currencyPair.counter.toString().toLowerCase();
  }

    /**
   * Can be  “bitcoin”, “litecoin” or “darkcoin” or “tether” or “wire”
   * @param currency
   * @return 
   */
  public static String convertToBitfinexWithdrawalType(String currency) {
      
      if(currency.toUpperCase().equals("BTC"))
          return "bitcoin";
      if(currency.toUpperCase().equals("LTC"))
          return "litecoin";
      if(currency.toUpperCase().equals("USD") )
          return "wire";
      if(currency.toUpperCase().equals("TETHER"))
          return "tether";
      
          throw new BitfinexException("Cannot determine withdrawal type.");
  }
}
