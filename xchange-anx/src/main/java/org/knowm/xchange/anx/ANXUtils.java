package org.knowm.xchange.anx;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;

/**
 * A central place for shared ANX properties
 */
public final class ANXUtils {

  /**
   * private Constructor
   */
  private ANXUtils() {

  }

  public static final int BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR_2 = 100000000;

  public static final int VOLUME_AND_AMOUNT_MAX_SCALE = 8;

  /**
   * Find and match an order with id in orders
   *
   * @param orders
   * @param order
   * @param id
   * @return
   */
  public static boolean findLimitOrder(List<LimitOrder> orders, LimitOrder order, String id) {

    boolean found = false;

    for (LimitOrder openOrder : orders) {
      if (openOrder.getId().equalsIgnoreCase(id)) {
        if (order.getCurrencyPair().equals(openOrder.getCurrencyPair()) && (order.getOriginalAmount().compareTo(openOrder.getOriginalAmount()) == 0)
            && (order.getLimitPrice().compareTo(openOrder.getLimitPrice()) == 0)) {
          found = true;
        }
      }
    }

    return found;
  }

  public static int getMaxPriceScale(CurrencyPair currencyPair) {

    if (currencyPair.base.equals(Currency.BTC) || currencyPair.base.equals(Currency.LTC)) {
      return 5;
    } else {
      return 8;
    }
  }

  public static String urlEncode(String str) {

    try {
      return URLEncoder.encode(str, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Problem encoding, probably bug in code.", e);
    }
  }

  public static BigDecimal percentToFactor(BigDecimal percent) {
    int PERCENT_DECIMAL_SHIFT = 2;
    return percent.movePointLeft(PERCENT_DECIMAL_SHIFT);
  }
}
