package org.knowm.xchange.bitfinex.v1;

import org.knowm.xchange.bitfinex.v1.dto.BitfinexException;
import org.knowm.xchange.currency.CurrencyPair;

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
     * can be one of the following ['bitcoin', 'litecoin', 'ethereum', 'ethereumc', 'mastercoin', 'zcash', 'monero', 'wire', 'dash']
     * 
     * @param currency
     * @return
     */
    public static String convertToBitfinexWithdrawalType(String currency) {
        switch (currency.toUpperCase()) {
        case "BTC":
            return "bitcoin";
        case "LTC":
            return "litecoin";
        case "ETH":
            return "ethereum";
        case "ETC":
            return "ethereumc";
        case "ZEC":
            return "zcash";
        case "XMR":
            return "monero";
        case "USD":
            return "mastercoin";
        case "DASH":
            return "dash";
        case "TETHER":
            return "tether";
        default:
            throw new BitfinexException("Cannot determine withdrawal type.");
        }

    }
}
