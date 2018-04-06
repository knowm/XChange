package org.knowm.xchange.bx;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import org.knowm.xchange.bx.dto.marketdata.BxAssetPair;
import org.knowm.xchange.bx.dto.trade.BxTradeHistory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.exceptions.ExchangeException;
import si.mazi.rescu.SynchronizedValueFactory;

public class BxUtils {

  private static Map<String, CurrencyPair> assetPairMap = new HashMap<>();
  private static Map<CurrencyPair, String> assetPairMapReverse = new HashMap<>();
  private static Map<String, Currency> assetMap = new HashMap<>();
  private static Map<Currency, String> assetMapReverse = new HashMap<>();

  public static void setBxAssetPairs(Map<String, BxAssetPair> pairs) {
    for (String id : pairs.keySet()) {
      if (pairs.get(id).isActive()) {
        setAsset(pairs.get(id).getPrimaryCurrency());
        setAsset(pairs.get(id).getSecondaryCurrency());
        CurrencyPair pair =
            new CurrencyPair(
                pairs.get(id).getPrimaryCurrency(), pairs.get(id).getSecondaryCurrency());
        assetPairMap.put(id, pair);
        assetPairMapReverse.put(pair, id);
      }
    }
  }

  private static void setAsset(String currency) {
    assetMap.put(currency, Currency.getInstance(currency));
    assetMapReverse.put(Currency.getInstance(currency), currency);
  }

  public static CurrencyPair translateBxCurrencyPair(String pairId) {
    return assetPairMap.get(pairId);
  }

  public static Map<Currency, CurrencyMetaData> getCurrencies() {
    Map<Currency, CurrencyMetaData> currencies = new HashMap<>();
    for (String key : assetMap.keySet()) {
      currencies.put(assetMap.get(key), new CurrencyMetaData(0, null));
    }
    return currencies;
  }

  public static String createBxCurrencyPair(CurrencyPair currencyPair) {
    String pair = assetPairMapReverse.get(currencyPair);
    if ((pair == null) || pair.isEmpty()) {
      throw new ExchangeException(
          String.format("BX doesn't support currency pair %s", currencyPair.toString()));
    }
    return pair;
  }

  public static String createUTCDate(SynchronizedValueFactory<Long> nonce) {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    return dateFormat.format(new Date(nonce.createValue()));
  }

  public static Currency translateCurrency(String currency) {
    return assetMap.get(currency);
  }

  public static Map<String, List<BxTradeHistory>> prepareHistory(BxTradeHistory[] histories) {
    Map<String, List<BxTradeHistory>> historyMap = new HashMap<>();
    for (BxTradeHistory history : histories) {
      List<BxTradeHistory> list =
          historyMap.computeIfAbsent(String.valueOf(history.getRefId()), k -> new ArrayList<>());
      list.add(history);
    }
    for (String key : historyMap.keySet()) {
      historyMap.get(key).sort(Comparator.comparingLong(BxTradeHistory::getTransactionId));
    }
    return historyMap;
  }
}
