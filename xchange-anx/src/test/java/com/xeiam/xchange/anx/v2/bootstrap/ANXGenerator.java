package com.xeiam.xchange.anx.v2.bootstrap;

import static com.xeiam.xchange.currency.Currencies.AUD;
import static com.xeiam.xchange.currency.Currencies.BGC;
import static com.xeiam.xchange.currency.Currencies.BTC;
import static com.xeiam.xchange.currency.Currencies.CAD;
import static com.xeiam.xchange.currency.Currencies.CHF;
import static com.xeiam.xchange.currency.Currencies.CNY;
import static com.xeiam.xchange.currency.Currencies.DOGE;
import static com.xeiam.xchange.currency.Currencies.EGD;
import static com.xeiam.xchange.currency.Currencies.EUR;
import static com.xeiam.xchange.currency.Currencies.GBP;
import static com.xeiam.xchange.currency.Currencies.HKD;
import static com.xeiam.xchange.currency.Currencies.JPY;
import static com.xeiam.xchange.currency.Currencies.LTC;
import static com.xeiam.xchange.currency.Currencies.NMC;
import static com.xeiam.xchange.currency.Currencies.NZD;
import static com.xeiam.xchange.currency.Currencies.PPC;
import static com.xeiam.xchange.currency.Currencies.SGD;
import static com.xeiam.xchange.currency.Currencies.START;
import static com.xeiam.xchange.currency.Currencies.STR;
import static com.xeiam.xchange.currency.Currencies.USD;
import static com.xeiam.xchange.currency.Currencies.XRP;
import static com.xeiam.xchange.currency.CurrencyPair.DOGE_BTC;
import static com.xeiam.xchange.currency.CurrencyPair.LTC_BTC;
import static com.xeiam.xchange.currency.CurrencyPair.STR_BTC;
import static com.xeiam.xchange.currency.CurrencyPair.XRP_BTC;
import static java.lang.System.out;
import static java.math.BigDecimal.ONE;

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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.xeiam.xchange.anx.v2.dto.meta.ANXMarketMetaData;
import com.xeiam.xchange.anx.v2.dto.meta.ANXMetaData;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.meta.CurrencyMetaData;

public class ANXGenerator {

  static ObjectMapper mapper = new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true)
      .setSerializationInclusion(JsonInclude.Include.NON_NULL);

  static Set<String> cryptos = new HashSet<String>(Arrays.asList(BTC, LTC, DOGE, STR, XRP, START, EGD));
  static String[] fiats = { USD, EUR, GBP, HKD, AUD, CAD, NZD, SGD, JPY, CNY };

  // counter currencies for STARTCoin - all fiats but CNY
  static String[] fiatsStart = { USD, EUR, GBP, HKD, AUD, CAD, NZD, SGD, JPY };

  static CurrencyPair[] pairsOther = { LTC_BTC, DOGE_BTC, STR_BTC, XRP_BTC };

  // base currency -> min order size
  static Map<String, BigDecimal> minAmount = new HashMap<String, BigDecimal>();
  static Map<String, BigDecimal> maxAmount = new HashMap<String, BigDecimal>();
  static Map<String, CurrencyMetaData> currencyMap = new TreeMap<String, CurrencyMetaData>();

  static Set<CurrencyPair> pairs = new HashSet<CurrencyPair>();

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

    for (String crypto : cryptos) {
      currencyMap.put(crypto, new CurrencyMetaData(8));
    }

    currencyMap.put(CNY, new CurrencyMetaData(8));
    for (String fiat : fiats) {
      if (!currencyMap.containsKey(fiat))
        currencyMap.put(fiat, new CurrencyMetaData(2));
    }

    // extra currencies available, but not traded
    currencyMap.put(CHF, new CurrencyMetaData(2));
    currencyMap.put(NMC, new CurrencyMetaData(8));
    currencyMap.put(BGC, new CurrencyMetaData(8));
    currencyMap.put(PPC, new CurrencyMetaData(8));

    Collections.addAll(pairs, pairsOther);

    for (String base : Arrays.asList(BTC, EGD))
      for (String counter : fiats)
        pairs.add(new CurrencyPair(base, counter));

    for (String counter : fiatsStart) {
      pairs.add(new CurrencyPair(START, counter));
    }
  }

  static BigDecimal fee = new BigDecimal(".006");

  public static void main(String[] args) throws IOException {
    new ANXGenerator().run();
  }

  private void run() throws IOException {
    Map<CurrencyPair, ANXMarketMetaData> map = new TreeMap<CurrencyPair, ANXMarketMetaData>();

    for (CurrencyPair pair : pairs) {
      handleCurrencyPair(map, pair);
    }
    // TODO add RateLimits, fees
    ANXMetaData metaData = new ANXMetaData(map, currencyMap, null, null, null, null, null);

    mapper.writeValue(out, metaData);
    out.println();
    out.flush();
  }

  private void handleCurrencyPair(Map<CurrencyPair, ANXMarketMetaData> map, CurrencyPair currencyPair) {
    int amountScale = amountScale(currencyPair);
    BigDecimal minimumAmount = scaled(minAmount.get(currencyPair.baseSymbol), amountScale);
    BigDecimal maximumAmount = scaled(maxAmount.get(currencyPair.baseSymbol), amountScale);
    ANXMarketMetaData mmd = new ANXMarketMetaData(fee, minimumAmount, maximumAmount, priceScale(currencyPair));
    map.put(currencyPair, mmd);
  }

  BigDecimal scaled(BigDecimal value, int scale) {
    return value == null ? null : value.setScale(scale, RoundingMode.UNNECESSARY);
  }

  private int amountScale(CurrencyPair currencyPair) {
    return currencyMap.get(currencyPair.baseSymbol).scale;
  }

  int priceScale(CurrencyPair pair) {
    if (LTC_BTC.equals(pair) || (BTC.equals(pair.baseSymbol) && !cryptos.contains(pair.counterSymbol)))
      return 5;
    else
      return 8;
  }

}
