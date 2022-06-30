package org.knowm.xchange.mexc;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.mexc.dto.account.MEXCBalance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MEXCAdapters {

  public static Wallet adaptMEXCBalances(Map<String, MEXCBalance> mexcBalances) {
    List<Balance> balances = new ArrayList<>(mexcBalances.size());
    for (Map.Entry<String, MEXCBalance> mexcBalance : mexcBalances.entrySet()) {
      MEXCBalance mexcBalanceValue = mexcBalance.getValue();
      BigDecimal available = new BigDecimal(mexcBalanceValue.getAvailable());
      BigDecimal frozen = new BigDecimal(mexcBalanceValue.getFrozen());
      balances.add(new Balance(new Currency(mexcBalance.getKey()),
              frozen.add(available),
              available
      ));
    }
    return Wallet.Builder.from(balances).build();
  }

  public static String getSideString(Order.OrderType type) {
    if (type == Order.OrderType.ASK)
      return "ASK";
    if (type == Order.OrderType.BID)
      return "BID";
    throw new IllegalArgumentException("invalid order type");
  }

  public static Order.OrderType getOrderType(String side) {
    if ("sell".equalsIgnoreCase(side)) {
      return Order.OrderType.ASK;
    }
    if ("buy".equalsIgnoreCase(side)) {
      return Order.OrderType.BID;
    }
    throw new IllegalArgumentException("invalid order type");
  }

  public static String convertToMEXCSymbol(String instrumentName) {
    return instrumentName.replace("/", "_").toUpperCase();
  }


}
