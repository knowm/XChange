package org.knowm.xchange.anx.v2.bootstrap;

import static java.lang.System.out;
import static java.math.BigDecimal.ONE;
import static org.knowm.xchange.currency.Currency.AUD;
import static org.knowm.xchange.currency.Currency.BGC;
import static org.knowm.xchange.currency.Currency.BTC;
import static org.knowm.xchange.currency.Currency.CAD;
import static org.knowm.xchange.currency.Currency.CHF;
import static org.knowm.xchange.currency.Currency.CNY;
import static org.knowm.xchange.currency.Currency.DOGE;
import static org.knowm.xchange.currency.Currency.EGD;
import static org.knowm.xchange.currency.Currency.EUR;
import static org.knowm.xchange.currency.Currency.GBP;
import static org.knowm.xchange.currency.Currency.HKD;
import static org.knowm.xchange.currency.Currency.JPY;
import static org.knowm.xchange.currency.Currency.LTC;
import static org.knowm.xchange.currency.Currency.NMC;
import static org.knowm.xchange.currency.Currency.NZD;
import static org.knowm.xchange.currency.Currency.PPC;
import static org.knowm.xchange.currency.Currency.SGD;
import static org.knowm.xchange.currency.Currency.START;
import static org.knowm.xchange.currency.Currency.STR;
import static org.knowm.xchange.currency.Currency.USD;
import static org.knowm.xchange.currency.Currency.XRP;
import static org.knowm.xchange.currency.CurrencyPair.DOGE_BTC;
import static org.knowm.xchange.currency.CurrencyPair.LTC_BTC;
import static org.knowm.xchange.currency.CurrencyPair.STR_BTC;
import static org.knowm.xchange.currency.CurrencyPair.XRP_BTC;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.knowm.xchange.anx.v2.dto.meta.ANXMarketMetaData;
import org.knowm.xchange.anx.v2.dto.meta.ANXMetaData;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;

public class ANXGenerator {

  static ObjectMapper mapper =
      new ObjectMapper()
          .configure(SerializationFeature.INDENT_OUTPUT, true)
          .setSerializationInclusion(JsonInclude.Include.NON_NULL);

  static Set<Currency> cryptos = new HashSet<>(Arrays.asList(BTC, LTC, DOGE, STR, XRP, START, EGD));
  static Currency[] fiats = {USD, EUR, GBP, HKD, AUD, CAD, NZD, SGD, JPY, CNY};

  // counter currencies for STARTCoin - all fiats but CNY
  static Currency[] fiatsStart = {USD, EUR, GBP, HKD, AUD, CAD, NZD, SGD, JPY};

  static CurrencyPair[] pairsOther = {LTC_BTC, DOGE_BTC, STR_BTC, XRP_BTC};

  // base currency -> min order size
  static Map<Currency, BigDecimal> minAmount = new HashMap<>();
  static Map<Currency, BigDecimal> maxAmount = new HashMap<>();
  static Map<Currency, CurrencyMetaData> currencyMap = new TreeMap<>();

  static Set<CurrencyPair> pairs = new HashSet<>();
  static BigDecimal fee = new BigDecimal(".006");

  static {
    minAmount.put(BTC, ONE.movePointLeft(2));
    minAmount.put(LTC, ONE.movePointLeft(1));
    minAmount.put(DOGE, ONE.movePointRight(4));
    minAmount.put(XRP, ONE.movePointLeft(2));
    minAmount.put(STR, ONE.movePointLeft(2));
    minAmount.put(START, null);
    minAmount.put(EGD, null);

    maxAmount.put(BTC, ONE.movePointRight(5));
    maxAmount.put(LTC, ONE.movePointRight(7));
    maxAmount.put(DOGE, ONE.movePointRight(10));
    maxAmount.put(XRP, ONE.movePointRight(5));
    maxAmount.put(STR, ONE.movePointRight(5));
    maxAmount.put(START, null);
    maxAmount.put(EGD, null);

    for (Currency crypto : cryptos) {
      currencyMap.put(crypto, new CurrencyMetaData(8, null));
    }

    currencyMap.put(CNY, new CurrencyMetaData(8, null));
    for (Currency fiat : fiats) {
      if (!currencyMap.containsKey(fiat)) {
        currencyMap.put(fiat, new CurrencyMetaData(2, null));
      }
    }

    // extra currencies available, but not traded
    currencyMap.put(CHF, new CurrencyMetaData(2, null));
    currencyMap.put(NMC, new CurrencyMetaData(8, null));
    currencyMap.put(BGC, new CurrencyMetaData(8, null));
    currencyMap.put(PPC, new CurrencyMetaData(8, null));

    Collections.addAll(pairs, pairsOther);

    for (Currency base : Arrays.asList(BTC, EGD)) {
      for (Currency counter : fiats) {
        pairs.add(new CurrencyPair(base, counter));
      }
    }

    for (Currency counter : fiatsStart) {
      pairs.add(new CurrencyPair(START, counter));
    }
  }

  public static void main(String[] args) throws IOException {
    new ANXGenerator().run();
  }

  private void run() throws IOException {

    Map<CurrencyPair, CurrencyPairMetaData> map = new TreeMap<>();

    for (CurrencyPair pair : pairs) {
      handleCurrencyPair(map, pair);
    }
    // TODO add RateLimits, fees
    ANXMetaData metaData = new ANXMetaData(map, currencyMap, null, null, null, null, null);

    mapper.writeValue(out, metaData);
    out.println();
    out.flush();
  }

  private void handleCurrencyPair(
      Map<CurrencyPair, CurrencyPairMetaData> map, CurrencyPair currencyPair) {
    int amountScale = amountScale(currencyPair);
    BigDecimal minimumAmount =
        scaled(minAmount.get(currencyPair.base.getCurrencyCode()), amountScale);
    BigDecimal maximumAmount =
        scaled(maxAmount.get(currencyPair.base.getCurrencyCode()), amountScale);
    ANXMarketMetaData mmd =
        new ANXMarketMetaData(fee, minimumAmount, maximumAmount, priceScale(currencyPair));
    map.put(currencyPair, mmd);
  }

  BigDecimal scaled(BigDecimal value, int scale) {
    return value == null ? null : value.setScale(scale, RoundingMode.UNNECESSARY);
  }

  private int amountScale(CurrencyPair currencyPair) {
    return currencyMap.get(currencyPair.base.getCurrencyCode()).getScale();
  }

  int priceScale(CurrencyPair pair) {
    if (LTC_BTC.equals(pair)
        || (BTC.equals(pair.base.getCurrencyCode())
            && !cryptos.contains(pair.counter.getCurrencyCode()))) {
      return 5;
    } else {
      return 8;
    }
  }
}
