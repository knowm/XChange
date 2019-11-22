package org.knowm.xchange.bitfinex.v1;

import org.knowm.xchange.bitfinex.dto.BitfinexException;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

/** A central place for shared Bitfinex properties */
public final class BitfinexUtils {

  /** private Constructor */
  private BitfinexUtils() {}

  public static String adaptXchangeCurrency(Currency xchangeSymbol) {

    if (xchangeSymbol == null) {
      return null;
    }

    return xchangeSymbol.toString().toLowerCase();
  }

  public static String toPairString(CurrencyPair currencyPair) {

    if (currencyPair == null) {
      return null;
    }

    String base = adaptXchangeCurrency(currencyPair.base);
    String counter = adaptXchangeCurrency(currencyPair.counter);
    return base + currencySeparator(base, counter) + counter;
  }

  /**
   * unfortunatelly we need to go this way, since the pairs at bitfinex are not very consequent see
   * dusk:xxx pairs at https://api.bitfinex.com/v1/symbols_details
   *
   * @param base
   * @param counter
   * @return
   */
  private static String currencySeparator(String base, String counter) {
    return base.length() > 3 || counter.length() > 3 ? ":" : "";
  }

  /**
   * From the reference documentation for field withdraw_type (2018-02-14); can be one of the
   * following ['bitcoin', 'litecoin', 'ethereum', 'ethereumc', 'mastercoin', 'zcash', 'monero',
   * 'wire', 'dash', 'ripple', 'eos', 'neo', 'aventus', 'qtum', 'eidoo'] From customer support via
   * email on 2018-02-27; "... After discussing with our developers, you can use API to withdraw BCH
   * and withdraw_type is bcash. ..."
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
      case "XRP":
        return "ripple";
      case "EOS":
        return "eos";
      case "NEO":
        return "neo";
      case "AVT":
        return "aventus";
      case "QTUM":
        return "qtum";
      case "EDO":
        return "eidoo";
      case "BTG":
        return "bgold";
      case "BCH":
        return "bcash";
      case "USDT":
        return "tetheruso";
      default:
        throw new BitfinexException("Cannot determine withdrawal type.");
    }
  }
}
