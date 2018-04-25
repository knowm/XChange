package org.knowm.xchange.bitmarket;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

/** @author kfonal */
public class BitMarketUtils {

  public static String currencyPairToBitMarketCurrencyPair(CurrencyPair currencyPair) {
    if (currencyPair.getBase().getCurrencyCode().equals("LiteMineX")
        && currencyPair.getCounter().getCurrencyCode().equals("BTC")) {
      return "LiteMineXBTC";
    } else {
      return currencyPair.getBase().getCurrencyCode() + currencyPair.getCounter().getCurrencyCode();
    }
  }

  public static CurrencyPair bitMarketCurrencyPairToCurrencyPair(String currencyPair) {
    if (currencyPair.equals("LiteMineXBTC")) {
      return CurrencyPair.build("LiteMineX", "BTC");
    } else if (currencyPair.length() == 6) {
      String ccyA = currencyPair.substring(0, 3);
      String ccyB = currencyPair.substring(3);
      return CurrencyPair.build(Currency.valueOf(ccyA), Currency.valueOf(ccyB));
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
