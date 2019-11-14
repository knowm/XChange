package org.knowm.xchange.kraken;

import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.kraken.dto.marketdata.KrakenAsset;
import org.knowm.xchange.kraken.dto.marketdata.KrakenAssetPair;

/** @author timmolter */
public class KrakenUtils {

  private static Map<String, CurrencyPair> assetPairMap = new HashMap<String, CurrencyPair>();
  private static Map<CurrencyPair, String> assetPairMapReverse =
      new HashMap<CurrencyPair, String>();
  private static Map<String, Currency> assetsMap = new HashMap<String, Currency>();
  private static Map<Currency, String> assetsMapReverse = new HashMap<Currency, String>();

  /** Mapping of discontinued currencies to their standard name. */
  private static Map<String, String> discontinuedCurrencies;

  static {
    discontinuedCurrencies = new HashMap<>();
    discontinuedCurrencies.put("XICN", "ICN");
  }

  /** Private Constructor */
  private KrakenUtils() {}

  public static void setKrakenAssetPairs(Map<String, KrakenAssetPair> pairs) {
    if (assetPairMap.isEmpty()) {
      for (Map.Entry<String, KrakenAssetPair> entry : pairs.entrySet()) {
        //  skip dark markets!
        if (!entry.getKey().endsWith(".d")) {
          CurrencyPair pair =
              new CurrencyPair(
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
        assetsMap.put(entry.getKey(), Currency.getInstance(entry.getValue().getAltName()));
        assetsMapReverse.put(Currency.getInstance(entry.getValue().getAltName()), entry.getKey());
      }
    }
  }

  public static String createKrakenCurrencyPair(CurrencyPair currencyPair) {
    return assetPairMapReverse.get(currencyPair);
  }

  public static CurrencyPair translateKrakenCurrencyPair(String currencyPairIn) {
    CurrencyPair pair = assetPairMap.get(currencyPairIn);
    if (pair == null) {
      // kraken can give short pairs back from open orders ?
      if (currencyPairIn.length() >= 6 && currencyPairIn.length() <= 8) {
        int firstCurrencyLength = currencyPairIn.length() - 3;
        Currency base = Currency.getInstance(currencyPairIn.substring(0, firstCurrencyLength));
        if (base.getCommonlyUsedCurrency() != null) {
          base = base.getCommonlyUsedCurrency();
        }
        Currency counter =
            Currency.getInstance(
                currencyPairIn.substring(firstCurrencyLength, currencyPairIn.length()));
        if (counter.getCommonlyUsedCurrency() != null) {
          counter = counter.getCommonlyUsedCurrency();
        }
        pair = new CurrencyPair(base, counter);
      }
    }
    return pair;
  }

  public static String createKrakenCurrencyPair(Currency tradableIdentifier, Currency currency) {
    return createKrakenCurrencyPair(new CurrencyPair(tradableIdentifier, currency));
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
    if (discontinuedCurrencies.containsKey(currencyIn)) {
      return Currency.getInstance(discontinuedCurrencies.get(currencyIn));
    }
    Currency currencyOut = assetsMap.get(currencyIn);
    if (currencyOut == null) {
      throw new ExchangeException("Kraken does not support the currency code " + currencyIn);
    }
    return currencyOut.getCommonlyUsedCurrency();
  }

  public static void clearAssets() {
    assetPairMap.clear();
    assetPairMapReverse.clear();
    assetsMap.clear();
    assetsMapReverse.clear();
  }
}
