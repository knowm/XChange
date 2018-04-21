package org.knowm.xchange.huobi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.huobi.dto.marketdata.HuobiAsset;
import org.knowm.xchange.huobi.dto.marketdata.HuobiAssetPair;
import si.mazi.rescu.SynchronizedValueFactory;

public class HuobiUtils {

  private static Map<String, org.knowm.xchange.currency.CurrencyPair> assetPairMap =
      new HashMap<String, org.knowm.xchange.currency.CurrencyPair>();
  private static Map<org.knowm.xchange.currency.CurrencyPair, String> assetPairMapReverse =
      new HashMap<org.knowm.xchange.currency.CurrencyPair, String>();
  private static Map<String, Currency> assetMap = new HashMap<String, Currency>();
  private static Map<Currency, String> assetMapReverse = new HashMap<Currency, String>();

  private HuobiUtils() {}

  public static String createHuobiCurrencyPair(
      org.knowm.xchange.currency.CurrencyPair currencyPair) {
    String pair = assetPairMapReverse.get(currencyPair);
    if ((pair == null) || (pair.length() == 0)) {
      throw new ExchangeException(
          String.format("Huobi doesn't support currency pair %s", currencyPair.toString()));
    }
    return pair;
  }

  public static String createUTCDate(SynchronizedValueFactory<Long> nonce) {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    return dateFormat.format(new Date(nonce.createValue()));
  }

  public static void setHuobiAssets(HuobiAsset[] huobiAssets) {
    for (HuobiAsset entry : huobiAssets) {
      assetMap.put(entry.getAsset(), Currency.valueOf(entry.getAsset()));
      assetMapReverse.put(Currency.valueOf(entry.getAsset()), entry.getAsset());
    }
  }

  public static void setHuobiAssetPairs(HuobiAssetPair[] huobiAssetPairs) {
    for (HuobiAssetPair entry : huobiAssetPairs) {
      org.knowm.xchange.currency.CurrencyPair pair =
          org.knowm.xchange.currency.CurrencyPair.build(
              translateHuobiCurrencyCode(entry.getBaseCurrency()),
              translateHuobiCurrencyCode(entry.getQuoteCurrency()));
      assetPairMap.put(entry.getKey(), pair);
      assetPairMapReverse.put(pair, entry.getKey());
    }
  }

  public static Currency translateHuobiCurrencyCode(String currencyIn) {
    Currency currencyOut = assetMap.get(currencyIn);
    if (currencyOut == null) {
      throw new ExchangeException("Huobi does not support the currency code " + currencyIn);
    }
    return currencyOut.getCommonlyUsedCurrency();
  }

  public static org.knowm.xchange.currency.CurrencyPair translateHuobiCurrencyPair(
      String currencyPairIn) {
    org.knowm.xchange.currency.CurrencyPair pair = assetPairMap.get(currencyPairIn);
    if (pair == null) {
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
}
