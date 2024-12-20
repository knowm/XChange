package org.knowm.xchange.bitfinex.v1;

import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.instrument.Instrument;

/** A central place for shared Bitfinex properties */
public final class BitfinexUtils {

  /** private Constructor */
  private BitfinexUtils() {}

  private static final String USDT_SYMBOL_BITFINEX = "UST";
  private static final String USDT_SYMBOL_XCHANGE = "USDT";

  public static String adaptXchangeCurrency(Currency xchangeSymbol) {

    if (xchangeSymbol == null) {
      return null;
    }

    if (USDT_SYMBOL_XCHANGE.equals(xchangeSymbol.toString())) {
      return USDT_SYMBOL_BITFINEX;
    }

    return xchangeSymbol.toString(); // .toLowerCase();
  }

  public static String toPairString(CurrencyPair currencyPair) {

    if (currencyPair == null) {
      return null;
    }

    String base = adaptXchangeCurrency(currencyPair.getBase());
    String counter = adaptXchangeCurrency(currencyPair.getCounter());
    return "t"
        + base
        + currencySeparator(base, counter)
        + adaptXchangeCurrency(currencyPair.getCounter());
  }

  public static String toPairStringV1(Instrument instrument) {

    if (instrument == null) {
      return null;
    }

    String base = StringUtils.lowerCase(adaptXchangeCurrency(instrument.getBase()));
    String counter = StringUtils.lowerCase(adaptXchangeCurrency(instrument.getCounter()));
    return base + currencySeparator(base, counter) + adaptXchangeCurrency(instrument.getCounter());
  }

  /**
   * Unfortunately we need to go this way, since the pairs at Bitfinex are not very consistent see
   * dusk:xxx pairs at https://api.bitfinex.com/v1/symbols_details the same for xxx:cnht
   *
   * @param base currency to build string with
   * @param counter currency to build string with
   * @return string based on pair
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
      case "CLO":
        return "clo";
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
