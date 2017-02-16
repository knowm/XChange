package org.knowm.xchange.kraken;

import java.util.HashSet;
import java.util.Set;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

/**
 * @author timmolter
 */
public class KrakenUtils {

  private static final Set<Currency> FIAT_CURRENCIES = new HashSet<Currency>();
  private static final Set<Currency> DIGITAL_CURRENCIES = new HashSet<Currency>();

  static {

    FIAT_CURRENCIES.add(KrakenAdapters.adaptCurrency("USD"));
    FIAT_CURRENCIES.add(KrakenAdapters.adaptCurrency("EUR"));
    FIAT_CURRENCIES.add(KrakenAdapters.adaptCurrency("JPY"));
    FIAT_CURRENCIES.add(KrakenAdapters.adaptCurrency("KRW"));
    FIAT_CURRENCIES.add(KrakenAdapters.adaptCurrency("GBP"));
    FIAT_CURRENCIES.add(KrakenAdapters.adaptCurrency("CAD"));

    DIGITAL_CURRENCIES.add(KrakenAdapters.adaptCurrency("BTC"));
    DIGITAL_CURRENCIES.add(KrakenAdapters.adaptCurrency("DAO"));
    DIGITAL_CURRENCIES.add(KrakenAdapters.adaptCurrency("ETH"));
    DIGITAL_CURRENCIES.add(KrakenAdapters.adaptCurrency("LTC"));
    DIGITAL_CURRENCIES.add(KrakenAdapters.adaptCurrency("NMC"));
    DIGITAL_CURRENCIES.add(KrakenAdapters.adaptCurrency("XBT"));
    DIGITAL_CURRENCIES.add(KrakenAdapters.adaptCurrency("XDG"));
    DIGITAL_CURRENCIES.add(KrakenAdapters.adaptCurrency("XLM"));
    DIGITAL_CURRENCIES.add(KrakenAdapters.adaptCurrency("XRP"));
    DIGITAL_CURRENCIES.add(KrakenAdapters.adaptCurrency("XVN"));
    DIGITAL_CURRENCIES.add(KrakenAdapters.adaptCurrency("ETC"));  
    DIGITAL_CURRENCIES.add(KrakenAdapters.adaptCurrency("XMR"));
    DIGITAL_CURRENCIES.add(KrakenAdapters.adaptCurrency("ZEC"));
    DIGITAL_CURRENCIES.add(KrakenAdapters.adaptCurrency("REP"));
  }

  /**
   * Private Constructor
   */
  private KrakenUtils() {

  }

  public static String createKrakenCurrencyPair(CurrencyPair currencyPair) {

    return createKrakenCurrencyPair(currencyPair.base, currencyPair.counter);
  }

  public static String createKrakenCurrencyPair(Currency tradableIdentifier, Currency currency) {

    return getKrakenCurrencyCode(tradableIdentifier) + getKrakenCurrencyCode(currency);
  }

  public static String getKrakenCurrencyCode(Currency currency) {

    if (FIAT_CURRENCIES.contains(currency)) {
      return "Z" + currency;

    } else if (DIGITAL_CURRENCIES.contains(currency)) {

      if (currency.getIso4217Currency() != null) {
        currency = currency.getIso4217Currency();
      }
      return "X" + currency;
    }

    throw new ExchangeException("Kraken does not support the currency code " + currency);
  }

  public static Currency addCurrencyAndGetCode(String krakenCurrencyString) {

    Currency currencyCode = KrakenAdapters.adaptCurrency(krakenCurrencyString);
    if (krakenCurrencyString.startsWith("X")) {
      DIGITAL_CURRENCIES.add(currencyCode);
    } else {
      FIAT_CURRENCIES.add(currencyCode);
    }

    return currencyCode;
  }
}
