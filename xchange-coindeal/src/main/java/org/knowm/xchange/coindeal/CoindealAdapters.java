package org.knowm.xchange.coindeal;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.coindeal.dto.account.CoindealBalance;
import org.knowm.xchange.coindeal.dto.marketdata.CoindealOrderBook;
import org.knowm.xchange.coindeal.dto.trade.CoindealOrder;
import org.knowm.xchange.coindeal.dto.trade.CoindealTradeHistory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.utils.DateUtils;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

public final class CoindealAdapters {

  public static UserTrades adaptToUserTrades(List<CoindealTradeHistory> coindealTradeHistoryList)
      throws InvalidFormatException {
    List<UserTrade> userTrades = new ArrayList<>();

    for (CoindealTradeHistory coindealTradeHistory : coindealTradeHistoryList) {
      CurrencyPair currencyPair =
          CurrencyPairDeserializer.getCurrencyPairFromString(coindealTradeHistory.getSymbol());
      userTrades.add(
          new UserTrade.Builder()
              .type(
                  (coindealTradeHistory.getSide().equals("BUY"))
                      ? Order.OrderType.BID
                      : Order.OrderType.ASK)
              .originalAmount(coindealTradeHistory.getQuantity())
              .currencyPair(currencyPair)
              .price(coindealTradeHistory.getPrice())
              .timestamp(DateUtils.fromRfc3339DateString(coindealTradeHistory.getTimestamp()))
              .id(coindealTradeHistory.getId())
              .orderId(coindealTradeHistory.getOrderId())
              .feeAmount(coindealTradeHistory.getFee())
              .feeCurrency(
                  (coindealTradeHistory.getSide().equals("BUY")
                      ? currencyPair.base
                      : currencyPair.counter))
              .build());
    }

    return new UserTrades(userTrades, Trades.TradeSortType.SortByTimestamp);
  }

  public static AccountInfo adaptToAccountInfo(List<CoindealBalance> coindealBalances) {
    List<Balance> balances = new ArrayList<>();
    Currency currency = null;
    for (CoindealBalance coindealBalance : coindealBalances) {
      switch (coindealBalance.getCurrency()) {
        case "Bitcoin":
          currency = Currency.BTC;
          break;
        case "Ethereum":
          currency = Currency.ETH;
          break;
        case "Bitcoin Cash ABC":
          currency = Currency.BCH;
          break;
        case "Euro":
          currency = Currency.EUR;
          break;
        case "Litecoin":
          currency = Currency.LTC;
          break;
        case "US Dollar":
          currency = Currency.USD;
          break;
      }
      if (currency != null) {
        balances.add(
            new Balance(
                currency,
                coindealBalance.getAvailable().add(coindealBalance.getReserved()),
                coindealBalance.getAvailable(),
                coindealBalance.getReserved()));
      }
      currency = null;
    }

    return new AccountInfo(Wallet.Builder.from(balances).build());
  }

  public static OrderBook adaptOrderBook(
      CoindealOrderBook coindealOrderBook, CurrencyPair currencyPair) {
    List<LimitOrder> asks = new ArrayList<>();
    coindealOrderBook
        .getAsks()
        .forEach(
            coindealOrderBookEntry -> {
              asks.add(
                  new LimitOrder(
                      Order.OrderType.ASK,
                      coindealOrderBookEntry.getAmount(),
                      currencyPair,
                      null,
                      null,
                      coindealOrderBookEntry.getPrice()));
            });
    List<LimitOrder> bids = new ArrayList<>();
    coindealOrderBook
        .getBids()
        .forEach(
            coindealOrderBookEntry -> {
              bids.add(
                  new LimitOrder(
                      Order.OrderType.BID,
                      coindealOrderBookEntry.getAmount(),
                      currencyPair,
                      null,
                      null,
                      coindealOrderBookEntry.getPrice()));
            });
    return new OrderBook(null, asks, bids, true);
  }

  public static OpenOrders adaptToOpenOrders(List<CoindealOrder> coindealActiveOrders)
      throws InvalidFormatException {
    List<LimitOrder> limitOrders = new ArrayList<>();

    for (CoindealOrder coindealOrder : coindealActiveOrders) {
      limitOrders.add(
          new LimitOrder.Builder(
                  adaptOrderType(coindealOrder.getSide()),
                  CurrencyPairDeserializer.getCurrencyPairFromString(coindealOrder.getSymbol()))
              .limitPrice(coindealOrder.getPrice())
              .originalAmount(coindealOrder.getQuantity())
              .cumulativeAmount(coindealOrder.getCumQuantity())
              .timestamp(DateUtils.fromISODateString(coindealOrder.getCreatedAt()))
              .id(coindealOrder.getClientOrderId())
              .build());
    }

    return new OpenOrders(limitOrders);
  }

  public static Order adaptOrder(CoindealOrder coindealOrder) throws InvalidFormatException {
    return new LimitOrder.Builder(
            adaptOrderType(coindealOrder.getSide()),
            CurrencyPairDeserializer.getCurrencyPairFromString(coindealOrder.getSymbol()))
        .limitPrice(coindealOrder.getPrice())
        .originalAmount(coindealOrder.getQuantity())
        .cumulativeAmount(coindealOrder.getCumQuantity())
        .timestamp(DateUtils.fromISODateString(coindealOrder.getCreatedAt()))
        .id(coindealOrder.getClientOrderId())
        .build();
  }

  public static String adaptCurrencyPairToString(CurrencyPair currencyPair) {
    if (currencyPair == null) {
      return null;
    } else {
      return currencyPair.toString().replace("/", "").toUpperCase();
    }
  }

  public static String adaptOrderType(Order.OrderType orderType) {
    return orderType.equals(Order.OrderType.ASK) ? "sell" : "buy";
  }

  public static Order.OrderType adaptOrderType(String coindealOrderType) {
    return coindealOrderType.equals("sell") ? Order.OrderType.ASK : Order.OrderType.BID;
  }
}
