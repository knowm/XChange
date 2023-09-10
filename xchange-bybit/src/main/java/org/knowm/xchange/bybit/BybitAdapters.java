package org.knowm.xchange.bybit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.BybitCoinBalance;
import org.knowm.xchange.bybit.dto.trade.BybitOrderDetail;
import org.knowm.xchange.bybit.dto.trade.BybitOrderStatus;
import org.knowm.xchange.bybit.dto.trade.BybitSide;
import org.knowm.xchange.bybit.service.BybitException;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.trade.LimitOrder;

public class BybitAdapters {

  public static final List<String> QUOTE_CURRENCIES = Arrays.asList("USDT", "USDC", "BTC", "DAI");

  public static Wallet adaptBybitBalances(List<BybitCoinBalance> bybitCoinBalances) {
    List<Balance> balances = new ArrayList<>(bybitCoinBalances.size());
    for (BybitCoinBalance bybitCoinBalance : bybitCoinBalances) {
      balances.add(
          new Balance(
              new Currency(bybitCoinBalance.getCoin()),
              new BigDecimal(bybitCoinBalance.getEquity()),
              new BigDecimal(bybitCoinBalance.getAvailableToWithdraw())));
    }
    return Wallet.Builder.from(balances).build();
  }

  public static BybitSide getSideString(Order.OrderType type) {
    if (type == Order.OrderType.ASK) return BybitSide.SELL;
    if (type == Order.OrderType.BID) return BybitSide.BUY;
    throw new IllegalArgumentException("invalid order type");
  }

  public static Order.OrderType getOrderType(BybitSide side) {
    if ("sell".equalsIgnoreCase(side.name())) {
      return Order.OrderType.ASK;
    }
    if ("buy".equalsIgnoreCase(side.name())) {
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

  public static LimitOrder adaptBybitOrderDetails(BybitOrderDetail bybitOrderResult) {
    LimitOrder limitOrder =
        new LimitOrder(
            getOrderType(bybitOrderResult.getSide()),
            bybitOrderResult.getQty(),
            bybitOrderResult.getCumExecQty(),
            guessSymbol(bybitOrderResult.getSymbol()),
            bybitOrderResult.getOrderId(),
            bybitOrderResult.getCreatedTime(),
            bybitOrderResult.getPrice()) {};
    limitOrder.setAveragePrice(bybitOrderResult.getAvgPrice());
    limitOrder.setOrderStatus(adaptBybitOrderStatus(bybitOrderResult.getOrderStatus()));
    return limitOrder;
  }

  private static OrderStatus adaptBybitOrderStatus(BybitOrderStatus orderStatus) {
    switch (orderStatus) {
      case CREATED:
        return OrderStatus.OPEN;
      case NEW:
        return OrderStatus.NEW;
      case REJECTED:
        return OrderStatus.REJECTED;
      case PARTIALLY_FILLED:
      case ACTIVE:
        return OrderStatus.PARTIALLY_FILLED;
      case PARTIALLY_FILLED_CANCELED:
        return OrderStatus.PARTIALLY_CANCELED;
      case FILLED:
        return OrderStatus.FILLED;
      case CANCELLED:
        return OrderStatus.CANCELED;
      case UNTRIGGERED:
      case TRIGGERED:
        return OrderStatus.UNKNOWN;
      case DEACTIVATED:
        return OrderStatus.STOPPED;
      default:
        throw new IllegalStateException("Unexpected value: " + orderStatus);
    }
  }

  public static <T> BybitException createBybitExceptionFromResult(BybitResult<T> walletBalances) {
    return new BybitException(
        walletBalances.getRetCode(), walletBalances.getRetMsg(), walletBalances.getRetExtInfo());
  }
}
