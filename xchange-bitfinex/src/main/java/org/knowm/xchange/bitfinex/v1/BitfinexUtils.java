package org.knowm.xchange.bitfinex.v1;

import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

/** A central place for shared Bitfinex properties */
public final class BitfinexUtils {

  /** private Constructor */
  private BitfinexUtils() {}

  public static String adaptXchangeCurrency(Currency xchangeSymbol) {

    if (xchangeSymbol == null) {
      return null;
    }

    return xchangeSymbol.toString(); // .toLowerCase();
  }

  public static String toPairString(CurrencyPair currencyPair) {

    if (currencyPair == null) {
      return null;
    }

    String base = adaptXchangeCurrency(currencyPair.base);
    String counter = adaptXchangeCurrency(currencyPair.counter);
    return "t"
        + base
        + currencySeparator(base, counter)
        + adaptXchangeCurrency(currencyPair.counter);
  }

  public static String toPairStringV1(CurrencyPair currencyPair) {

    if (currencyPair == null) {
      return null;
    }

    String base = StringUtils.lowerCase(adaptXchangeCurrency(currencyPair.base));
    String counter = StringUtils.lowerCase(adaptXchangeCurrency(currencyPair.counter));
    return base + currencySeparator(base, counter) + adaptXchangeCurrency(currencyPair.counter);
  }

  /**
   * unfortunatelly we need to go this way, since the pairs at bitfinex are not very consequent see
   * dusk:xxx pairs at https://api.bitfinex.com/v1/symbols_details the same for xxx:cnht
   *
   * @param base
   * @return
   */
  private static String currencySeparator(String base, String counter) {
    if (base.length() > 3 || counter.length() > 3) {
      return ":";
    }
    return "";
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
        throw new ExchangeException("Cannot determine withdrawal type.");
    }
  }
}
