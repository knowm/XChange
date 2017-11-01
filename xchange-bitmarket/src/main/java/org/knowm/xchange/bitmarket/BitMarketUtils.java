package org.knowm.xchange.bitmarket;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

/**
 * @author kfonal
 */
public class BitMarketUtils {

  public static String currencyPairToBitMarketCurrencyPair(CurrencyPair currencyPair) {
    if (currencyPair.base.getCurrencyCode().equals("LiteMineX") && currencyPair.counter.getCurrencyCode().equals("BTC")) {
      return "LiteMineXBTC";
    } else {
      return currencyPair.base.getCurrencyCode() + currencyPair.counter.getCurrencyCode();
    }
  }

  public static CurrencyPair bitMarketCurrencyPairToCurrencyPair(String currencyPair) {
    if (currencyPair.equals("LiteMineXBTC")) {
      return new CurrencyPair("LiteMineX", "BTC");
    } else if (currencyPair.length() == 6) {
      String ccyA = currencyPair.substring(0, 3);
      String ccyB = currencyPair.substring(3);
      return new CurrencyPair(Currency.getInstance(ccyA), Currency.getInstance(ccyB));
    } else {
      throw new IllegalStateException("Cannot convert '" + currencyPair + "' into a CurrencyPair");
    }
  }

  public static Order.OrderType bitMarketOrderTypeToOrderType(String bitmarketOrderType) {
    return bitmarketOrderType.equals("sell") ? Order.OrderType.ASK : Order.OrderType.BID;
  }

  public static String orderTypeToBitMarketOrderType(Order.OrderType orderType) {
    return orderType == Order.OrderType.ASK ? "sell" : "buy";
  }
}
