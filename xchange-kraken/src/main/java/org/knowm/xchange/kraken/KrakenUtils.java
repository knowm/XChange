package org.knowm.xchange.kraken;

import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.kraken.dto.marketdata.KrakenAsset;
import org.knowm.xchange.kraken.dto.marketdata.KrakenAssetPair;

/** @author timmolter */
public class KrakenUtils {

  private static Map<String, org.knowm.xchange.currency.CurrencyPair> assetPairMap =
      new HashMap<String, org.knowm.xchange.currency.CurrencyPair>();
  private static Map<org.knowm.xchange.currency.CurrencyPair, String> assetPairMapReverse =
      new HashMap<org.knowm.xchange.currency.CurrencyPair, String>();
  private static Map<String, Currency> assetsMap = new HashMap<String, Currency>();
  private static Map<Currency, String> assetsMapReverse = new HashMap<Currency, String>();

  /** Private Constructor */
  private KrakenUtils() {}

  public static void setKrakenAssetPairs(Map<String, KrakenAssetPair> pairs) {
    if (assetPairMap.isEmpty()) {
      for (Map.Entry<String, KrakenAssetPair> entry : pairs.entrySet()) {
        //  skip dark markets!
        if (!entry.getKey().endsWith(".d")) {
          org.knowm.xchange.currency.CurrencyPair pair =
              org.knowm.xchange.currency.CurrencyPair.build(
                  translateKrakenCurrencyCode(entry.getValue().getBase()),
                  translateKrakenCurrencyCode(entry.getValue().getQuote()));
          assetPairMap.put(entry.getKey(), pair);
          assetPairMapReverse.put(pair, entry.getKey());
        }
      }
    }
  }

  public static void setKrakenAssets(Map<String, KrakenAsset> assetSource) {
    if (assetsMap.isEmpty()) {
      for (Map.Entry<String, KrakenAsset> entry : assetSource.entrySet()) {
        assetsMap.put(entry.getKey(), Currency.valueOf(entry.getValue().getAltName()));
        assetsMapReverse.put(Currency.valueOf(entry.getValue().getAltName()), entry.getKey());
      }
    }
  }

  public static String createKrakenCurrencyPair(
      org.knowm.xchange.currency.CurrencyPair currencyPair) {
    return assetPairMapReverse.get(currencyPair);
  }

  public static org.knowm.xchange.currency.CurrencyPair translateKrakenCurrencyPair(
      String currencyPairIn) {
    org.knowm.xchange.currency.CurrencyPair pair = assetPairMap.get(currencyPairIn);
    if (pair == null) {
      // kraken can give short pairs back from open orders ?
      if (currencyPairIn.length() == 6) {
        Currency base = Currency.valueOf(currencyPairIn.substring(0, 3));
        if (base.getCommonlyUsedCurrency() != null) {
          base = base.getCommonlyUsedCurrency();
        }
        Currency counter = Currency.valueOf(currencyPairIn.substring(3, 6));
        if (counter.getCommonlyUsedCurrency() != null) {
          counter = counter.getCommonlyUsedCurrency();
        }
        pair = org.knowm.xchange.currency.CurrencyPair.build(base, counter);
      } else if (currencyPairIn.length() == 7) {
        Currency base = Currency.valueOf(currencyPairIn.substring(0, 4));
        if (base.getCommonlyUsedCurrency() != null) {
          base = base.getCommonlyUsedCurrency();
        }
        Currency counter = Currency.valueOf(currencyPairIn.substring(4, 7));
        if (counter.getCommonlyUsedCurrency() != null) {
          counter = counter.getCommonlyUsedCurrency();
        }
        pair = org.knowm.xchange.currency.CurrencyPair.build(base, counter);
      }
    }
    return pair;
  }

  public static String createKrakenCurrencyPair(Currency tradableIdentifier, Currency currency) {
    return createKrakenCurrencyPair(
        org.knowm.xchange.currency.CurrencyPair.build(tradableIdentifier, currency));
  }

  public static String getKrakenCurrencyCode(Currency currency) {
    if (currency.getIso4217Currency() != null) {
      currency = currency.getIso4217Currency();
    }
    String krakenCode = assetsMapReverse.get(currency);
    if (krakenCode == null) {
      throw new ExchangeException("Kraken does not support the currency code " + currency);
    }
    return krakenCode;
  }

  public static Currency translateKrakenCurrencyCode(String currencyIn) {
    Currency currencyOut = assetsMap.get(currencyIn);
    if (currencyOut == null) {
      throw new ExchangeException("Kraken does not support the currency code " + currencyIn);
    }
    return currencyOut.getCommonlyUsedCurrency();
  }
}
