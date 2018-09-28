package org.knowm.xchange.exmo.holder;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.currency.CurrencyPair;

public class ExmoMarketDataHolder {

  private static ExmoMarketDataHolder ourInstance = new ExmoMarketDataHolder();

  private static Map<CurrencyPair, BigDecimal> closedPrices = new HashMap<>();

  public static ExmoMarketDataHolder getInstance() {
    return ourInstance;
  }

  private ExmoMarketDataHolder() {}

  public static BigDecimal getCloseBuyPrice(CurrencyPair currencyPair) {
    return closedPrices.get(currencyPair);
  }

  public static void updateCloseBuyPrice(CurrencyPair pair, BigDecimal price) {
    closedPrices.put(pair, price);
  }
}
