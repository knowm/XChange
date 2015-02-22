package com.xeiam.xchange.generator.anx.v2;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.xeiam.xchange.anx.v2.dto.ANXMarketMetaData;
import com.xeiam.xchange.anx.v2.dto.ANXMetaData;
import com.xeiam.xchange.currency.CurrencyPair;

import static com.xeiam.xchange.currency.Currencies.*;
import static com.xeiam.xchange.currency.CurrencyPair.*;
import static java.math.BigDecimal.ONE;

public class ANXGenerator {

  private static final String STR = "STR";
  private static final CurrencyPair STR_BTC = new CurrencyPair(STR, BTC);
  private static final CurrencyPair XRP_BTC = new CurrencyPair(XRP, BTC);

  ObjectMapper mapper = new ObjectMapper();

  {
    mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }

  CurrencyPair[] pairs = { BTC_USD, BTC_HKD, BTC_EUR, BTC_CAD, BTC_AUD, BTC_SGD, BTC_JPY, BTC_CHF, BTC_GBP, BTC_NZD, LTC_BTC, DOGE_BTC, STR_BTC, XRP_BTC };

  String[] cryptos = { BTC, LTC, DOGE, STR, XRP };

  // base currency -> min order size
  static Map<String, BigDecimal> minAmount = new HashMap<String, BigDecimal>();
  static Map<String, BigDecimal> maxAmount = new HashMap<String, BigDecimal>();

  static {
    minAmount.put(BTC, ONE.movePointLeft(2));
    minAmount.put(LTC, ONE.movePointLeft(1));
    minAmount.put(DOGE, ONE.movePointRight(4));
    minAmount.put(XRP, ONE.movePointLeft(2));
    minAmount.put(STR, ONE.movePointLeft(2));

    maxAmount.put(BTC, ONE.movePointRight(5));
    maxAmount.put(LTC, ONE.movePointRight(7));
    maxAmount.put(DOGE, ONE.movePointRight(10));
    maxAmount.put(XRP, ONE.movePointRight(5));
    maxAmount.put(STR, ONE.movePointRight(5));
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

    ANXMetaData metaData = new ANXMetaData(map, fee, 30, 60, 50000, 5);

    mapper.writeValue(System.out, metaData);
    System.out.println();
  }

  private void handleCurrencyPair(Map<CurrencyPair, ANXMarketMetaData> map, CurrencyPair currencyPair) {
    int scale = scale(currencyPair);
    BigDecimal minimumAmount = minAmount.get(currencyPair.baseSymbol).setScale(scale, RoundingMode.UNNECESSARY);
    BigDecimal maximumAmount = maxAmount.get(currencyPair.baseSymbol).setScale(scale, RoundingMode.UNNECESSARY);
    ANXMarketMetaData mmd = new ANXMarketMetaData(minimumAmount, maximumAmount);
    map.put(currencyPair, mmd);
  }

  int scale(CurrencyPair pair) {
    if (LTC_BTC.equals(pair) || (BTC.equals(pair.baseSymbol) && !new HashSet<String>(Arrays.asList(cryptos)).contains(pair.counterSymbol)))
      return 5;
    else
      return 8;
  }

}
