package org.knowm.xchange.luno;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

public class LunoUtil {

  public static String toLunoPair(CurrencyPair pair) {
    return toLunoCurrency(pair.base) + toLunoCurrency(pair.counter);
  }

  public static String toLunoCurrency(Currency c) {
    String in = c.getCommonlyUsedCurrency().getCurrencyCode();
    switch (in) {
      case "BTC":
        return "XBT";
      default:
        return in;
    }
  }

  public static Currency fromLunoCurrency(String c) {
    String in;
    switch (c) {
      case "XBT":
        in = "BTC";
        break;
      default:
        in = c;
    }
    return Currency.getInstance(in);
  }

  /**
   * see https://www.luno.com/en/api#withdrawals-create
   *
   * @param lunoCurrency
   * @return withdrawal type
   */
  public static String requestType(String lunoCurrency) {
    switch (lunoCurrency) {
      case "ZAR":
        return "ZAR_EFT";
      case "NAD":
        return "NAD_EFT";
      case "KES":
        return "KES_MPESA";
      case "MYR":
        return "MYR_IBG";
      case "IDR":
        return "IDR_LLG";
      default:
        throw new ExchangeException("Luno does not support withdrawals for " + lunoCurrency);
    }
  }

  /**
   * convert luno pair to xchange pair i.e. XBTZAR -> BTC/ZAR we assume, the pair has two currencies
   * with 3 chars length each <- not a very clean approach
   *
   * @param lunoPair
   * @return
   */
  public static CurrencyPair fromLunoPair(String lunoPair) {
    return new CurrencyPair(
        fromLunoCurrency(lunoPair.substring(0, 3)), fromLunoCurrency(lunoPair.substring(3)));
  }
}
