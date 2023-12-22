package org.knowm.xchange.bybit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.BybitBalance;
import org.knowm.xchange.bybit.dto.trade.BybitOrderDetails;
import org.knowm.xchange.bybit.service.BybitException;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.trade.LimitOrder;

public class BybitAdapters {

  public static final List<String> QUOTE_CURRENCIES = Arrays.asList("USDT", "USDC", "BTC", "DAI");

  public static Wallet adaptBybitBalances(List<BybitBalance> bybitBalances) {
    List<Balance> balances = new ArrayList<>(bybitBalances.size());
    for (BybitBalance bybitBalance : bybitBalances) {
      balances.add(
          new Balance(
              new Currency(bybitBalance.getCoin()),
              new BigDecimal(bybitBalance.getTotal()),
              new BigDecimal(bybitBalance.getFree())));
    }
    return Wallet.Builder.from(balances).build();
  }

  public static String getSideString(Order.OrderType type) {
    if (type == Order.OrderType.ASK) return "Sell";
    if (type == Order.OrderType.BID) return "Buy";
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

  public static String convertToBybitSymbol(String instrumentName) {
    return instrumentName.replace("/", "").toUpperCase();
  }

  public static CurrencyPair guessSymbol(String symbol) {
    for (String quoteCurrency : QUOTE_CURRENCIES) {
      if (symbol.endsWith(quoteCurrency)) {
        int splitIndex = symbol.lastIndexOf(quoteCurrency);
        return new CurrencyPair(symbol.substring(0, splitIndex), symbol.substring(splitIndex));
      }
    }
    int splitIndex = symbol.length() - 3;
    return new CurrencyPair(symbol.substring(0, splitIndex), symbol.substring(splitIndex));
  }

  public static LimitOrder adaptBybitOrderDetails(BybitOrderDetails bybitOrderResult) {
    LimitOrder limitOrder =
        new LimitOrder(
            getOrderType(bybitOrderResult.getSide()),
            new BigDecimal(bybitOrderResult.getOrigQty()),
            new BigDecimal(bybitOrderResult.getExecutedQty()),
            guessSymbol(bybitOrderResult.getSymbol()),
            bybitOrderResult.getOrderId(),
            new Date(Long.parseLong(bybitOrderResult.getTime())),
            new BigDecimal(bybitOrderResult.getPrice())) {};
    BigDecimal averagePrice = new BigDecimal(bybitOrderResult.getAvgPrice());
    limitOrder.setAveragePrice(averagePrice);
    limitOrder.setOrderStatus(Order.OrderStatus.valueOf(bybitOrderResult.getStatus()));
    return limitOrder;
  }

  public static <T> BybitException createBybitExceptionFromResult(BybitResult<T> walletBalances) {
    return new BybitException(
        walletBalances.getRetCode(),
        walletBalances.getRetMsg(),
        walletBalances.getExtCode(),
        walletBalances.getExtCode());
  }
}
