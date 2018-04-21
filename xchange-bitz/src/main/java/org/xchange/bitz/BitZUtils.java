package org.xchange.bitz;

import org.knowm.xchange.currency.Currency;

public class BitZUtils {

  // TODO: Add Test
  public static String toPairString(org.knowm.xchange.currency.CurrencyPair currency) {
    return String.format(
        "%s_%s",
        currency.getBase().getCurrencyCode().toLowerCase(),
        currency.getCounter().getCurrencyCode().toLowerCase());
  }

  public static org.knowm.xchange.currency.CurrencyPair toCurrencyPair(String pairstring) {
    String[] parts = pairstring.split("_");

    if (parts.length == 2) {
      Currency base = Currency.getInstanceNoCreate(parts[0]);
      Currency counter = Currency.getInstanceNoCreate(parts[1]);

      if (base != null && counter != null) {
        return org.knowm.xchange.currency.CurrencyPair.build(base, counter);
      }
    }

    return null;
  }

  // TODO: Add Test
  public static String toNonceString(long value) {
    return String.format("%06d", Math.abs(value) % 100000);
  }
}
