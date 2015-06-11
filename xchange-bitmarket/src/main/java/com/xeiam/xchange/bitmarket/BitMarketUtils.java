package com.xeiam.xchange.bitmarket;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;

/**
 * @author kfonal
 */
public class BitMarketUtils {
  public static String CurrencyPairToBitMarketCurrencyPair(CurrencyPair currencyPair) {

    if (currencyPair == CurrencyPair.BTC_PLN) {
      return "BTCPLN";
    } else if (currencyPair == CurrencyPair.BTC_EUR) {
      return "BTCEUR";
    } else if (currencyPair.baseSymbol.equals("LTC") && currencyPair.counterSymbol.equals("PLN")) {
      return "LTCPLN";
    } else if (currencyPair.baseSymbol.equals("LTC") && currencyPair.counterSymbol.equals("BTC")) {
      return "LTCBTC";
    } else if (currencyPair.baseSymbol.equals("LiteMineX") && currencyPair.counterSymbol.equals("BTC")) {
      return "LiteMineXBTC";
    } else {
      return null;
    }
  }

  public static CurrencyPair BitMarketCurrencyPairToCurrencyPair(String currencyPair) {

    if (currencyPair.equals("BTCPLN")) {
      return CurrencyPair.BTC_PLN;
    } else if (currencyPair.equals("BTCEUR")) {
      return CurrencyPair.BTC_EUR;
    } else if (currencyPair.equals("LTCPLN")) {
      return new CurrencyPair("LTC", "PLN");
    } else if (currencyPair.equals("LTCBTC")) {
      return CurrencyPair.LTC_BTC;
    } else if (currencyPair.equals("LiteMineXBTC")) {
      return new CurrencyPair("LiteMineX", "BTC");
    } else {
      return null;
    }
  }

  public static Order.OrderType BitMarketOrderTypeToOrderType(String bitmarketOrderType) {

    return bitmarketOrderType.equals("buy") ? Order.OrderType.ASK : Order.OrderType.BID;
  }

  public static String OrderTypeToBitMarketOrderType(Order.OrderType orderType) {

    return orderType == Order.OrderType.ASK ? "buy" : "sell";
  }
}
