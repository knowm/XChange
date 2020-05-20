package org.knowm.xchange.coinfloor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.coinfloor.dto.account.CoinfloorBalance;
import org.knowm.xchange.coinfloor.dto.markedata.CoinfloorOrderBook;
import org.knowm.xchange.coinfloor.dto.markedata.CoinfloorTicker;
import org.knowm.xchange.coinfloor.dto.markedata.CoinfloorTransaction;
import org.knowm.xchange.coinfloor.dto.trade.CoinfloorOrder;
import org.knowm.xchange.coinfloor.dto.trade.CoinfloorUserTransaction;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.utils.DateUtils;

public class CoinfloorAdapters {

  public static Ticker adaptTicker(CoinfloorTicker rawTicker, CurrencyPair pair) {
    return new Ticker.Builder()
        .currencyPair(pair)
        .last(rawTicker.getLast())
        .bid(rawTicker.getBid())
        .ask(rawTicker.getAsk())
        .high(rawTicker.getHigh())
        .low(rawTicker.getLow())
        .vwap(rawTicker.getVwap())
        .volume(rawTicker.getVolume())
        .build();
  }

  public static OrderBook adaptOrderBook(CoinfloorOrderBook rawOrderBook, CurrencyPair pair) {

    List<LimitOrder> asks = createOrders(pair, Order.OrderType.ASK, rawOrderBook.getAsks());
    List<LimitOrder> bids = createOrders(pair, Order.OrderType.BID, rawOrderBook.getBids());
    return new OrderBook(null, asks, bids);
  }

  private static List<LimitOrder> createOrders(
      CurrencyPair pair, Order.OrderType orderType, List<List<BigDecimal>> orders) {

    List<LimitOrder> limitOrders = new ArrayList<>();
    if (orders == null) return limitOrders;

    for (List<BigDecimal> priceAndAmount : orders) {

      if (priceAndAmount.size() != 2) {
        throw new IllegalArgumentException(
            "Expected a price and amount pair but received: " + priceAndAmount);
      }
      LimitOrder order =
          new LimitOrder(orderType, priceAndAmount.get(1), pair, "", null, priceAndAmount.get(0));
      limitOrders.add(order);
    }
    return limitOrders;
  }

  public static Trades adaptTrades(CoinfloorTransaction[] transactions, CurrencyPair pair) {
    List<Trade> trades = new ArrayList<>();
    long lastTradeId = 0;
    for (CoinfloorTransaction tx : transactions) {
      long tradeId = tx.getTid();
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }

      long msSinceEpoch = tx.getDate() * 1000;
      Trade trade =
          new Trade.Builder()
              .originalAmount(tx.getAmount())
              .currencyPair(pair)
              .price(tx.getPrice())
              .timestamp(DateUtils.fromMillisUtc(msSinceEpoch))
              .id(String.valueOf(tradeId))
              .build();
      trades.add(trade);
    }

    return new Trades(trades, lastTradeId, TradeSortType.SortByID);
  }

  public static AccountInfo adaptAccountInfo(
      Collection<Currency> currencies, Collection<CoinfloorBalance> rawBalances) {
    Collection<Balance> balances = new ArrayList<>();
    for (Currency currency : currencies) {
      for (CoinfloorBalance rawBalance : rawBalances) {
        if (rawBalance.hasCurrency(currency)) {
          Balance balance = rawBalance.getBalance(currency);
          balances.add(balance);
          break;
        }
      }
    }
    Wallet wallet = Wallet.Builder.from(balances).build();
    return new AccountInfo(wallet);
  }

  public static UserTrades adaptTradeHistory(Collection<CoinfloorUserTransaction> transactions) {
    List<UserTrade> trades = new ArrayList<>();
    long lastTradeId = 0;
    for (CoinfloorUserTransaction transaction : transactions) {
      if (transaction.isTrade() == false) {
        continue; // skip deposits and withdrawals.
      }

      Date timestamp = CoinfloorUtils.parseDate(transaction.getDateTime());

      long transactionId = transaction.getId();
      if (transactionId > lastTradeId) {
        lastTradeId = transactionId;
      }
      final String tradeId = String.valueOf(transactionId);
      final String orderId = String.valueOf(transaction.getOrderId());
      final BigDecimal feeAmount = transaction.getFee();

      UserTrade trade =
          new UserTrade.Builder()
              .type(transaction.getSide())
              .originalAmount(transaction.getAmount().abs())
              .currencyPair(transaction.getCurrencyPair())
              .price(transaction.getPrice())
              .timestamp(timestamp)
              .id(tradeId)
              .orderId(orderId)
              .feeAmount(feeAmount)
              .feeCurrency(transaction.getCurrencyPair().counter)
              .build();
      trades.add(trade);
    }

    return new UserTrades(trades, lastTradeId, TradeSortType.SortByID);
  }

  public static OpenOrders adaptOpenOrders(Collection<CoinfloorOrder> openOrders) {
    List<LimitOrder> limitOrders = new ArrayList<>();
    for (CoinfloorOrder rawOrder : openOrders) {
      LimitOrder order =
          new LimitOrder(
              rawOrder.getSide(),
              rawOrder.getAmount(),
              rawOrder.getCurrencyPair(),
              Long.toString(rawOrder.getId()),
              CoinfloorUtils.parseDate(rawOrder.getDatetime()),
              rawOrder.getPrice());
      order.setOrderStatus(OrderStatus.NEW);
      limitOrders.add(order);
    }
    return new OpenOrders(limitOrders);
  }
}
