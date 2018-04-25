package org.knowm.xchange.taurus;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Ticker.Builder;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.taurus.dto.account.TaurusBalance;
import org.knowm.xchange.taurus.dto.marketdata.TaurusOrderBook;
import org.knowm.xchange.taurus.dto.marketdata.TaurusTicker;
import org.knowm.xchange.taurus.dto.marketdata.TaurusTransaction;
import org.knowm.xchange.taurus.dto.trade.TaurusUserTransaction;
import org.knowm.xchange.taurus.dto.trade.TaurusUserTransaction.TransactionType;

public final class TaurusAdapters {

  private TaurusAdapters() {}

  public static AccountInfo adaptAccountInfo(TaurusBalance taurusBalance, String userName) {
    Balance cadBalance =
        new Balance.Builder()
            .setCurrency(Currency.CAD)
            .setTotal(taurusBalance.getCadBalance())
            .setAvailable(taurusBalance.getCadAvailable())
            .setFrozen(taurusBalance.getCadReserved())
            .createBalance();
    Balance btcBalance =
        new Balance.Builder()
            .setCurrency(Currency.BTC)
            .setTotal(taurusBalance.getBtcBalance())
            .setAvailable(taurusBalance.getBtcAvailable())
            .setFrozen(taurusBalance.getBtcReserved())
            .createBalance();

    return AccountInfo.build(
        userName, taurusBalance.getFee(), Wallet.build(cadBalance, btcBalance));
  }

  public static OrderBook adaptOrderBook(
      TaurusOrderBook taurusOrderBook, CurrencyPair currencyPair) {
    List<LimitOrder> asks = createOrders(currencyPair, OrderType.ASK, taurusOrderBook.getAsks());
    List<LimitOrder> bids = createOrders(currencyPair, OrderType.BID, taurusOrderBook.getBids());
    return new OrderBook(taurusOrderBook.getTimestamp(), asks, bids);
  }

  public static List<LimitOrder> createOrders(
      CurrencyPair currencyPair, OrderType orderType, List<List<BigDecimal>> orders) {
    List<LimitOrder> limitOrders = new ArrayList<>();
    for (List<BigDecimal> ask : orders) {
      checkArgument(
          ask.size() == 2, "Expected a pair (price, amount) but got {0} elements.", ask.size());
      limitOrders.add(createOrder(currencyPair, ask, orderType));
    }
    return limitOrders;
  }

  public static LimitOrder createOrder(
      CurrencyPair currencyPair, List<BigDecimal> priceAndAmount, OrderType orderType) {
    return new LimitOrder(
        orderType, priceAndAmount.get(1), currencyPair, "", null, priceAndAmount.get(0));
  }

  public static void checkArgument(boolean argument, String msgPattern, Object... msgArgs) {
    if (!argument) {
      throw new IllegalArgumentException(MessageFormat.format(msgPattern, msgArgs));
    }
  }

  public static Trades adaptTrades(TaurusTransaction[] transactions, CurrencyPair currencyPair) {
    List<Trade> trades = new ArrayList<>();
    long lastTradeId = 0;
    for (TaurusTransaction tx : transactions) {
      final long tradeId = tx.getTid();
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }
      trades.add(
          new Trade(
              null,
              tx.getAmount(),
              currencyPair,
              tx.getPrice(),
              tx.getDate(),
              String.valueOf(tradeId)));
    }

    return new Trades(trades, lastTradeId, TradeSortType.SortByID);
  }

  public static Trade adaptTrade(TaurusTransaction tx, CurrencyPair currencyPair) {
    final String tradeId = String.valueOf(tx.getTid());
    Date date = tx.getDate();
    return new Trade(null, tx.getAmount(), currencyPair, tx.getPrice(), date, tradeId);
  }

  public static Ticker adaptTicker(TaurusTicker tt, CurrencyPair currencyPair) {

    return new Builder()
        .currencyPair(currencyPair)
        .last(tt.getLast())
        .bid(tt.getBid())
        .ask(tt.getAsk())
        .high(tt.getHigh())
        .low(tt.getLow())
        .vwap(tt.getVwap())
        .volume(tt.getVolume())
        .timestamp(tt.getTimestamp())
        .build();
  }

  public static UserTrades adaptTradeHistory(TaurusUserTransaction[] taurusUserTransactions) {
    List<UserTrade> trades = new ArrayList<>();
    long lastTradeId = 0;
    for (TaurusUserTransaction taurusUserTransaction : taurusUserTransactions) {
      if (taurusUserTransaction
          .getType()
          .equals(TransactionType.trade)) { // skip account deposits and withdrawals.
        OrderType orderType =
            taurusUserTransaction.getCad().doubleValue() > 0.0 ? OrderType.ASK : OrderType.BID;
        BigDecimal originalAmount = taurusUserTransaction.getBtc();
        BigDecimal price = taurusUserTransaction.getPrice();
        Date timestamp = taurusUserTransaction.getDatetime();
        long transactionId = taurusUserTransaction.getId();
        if (transactionId > lastTradeId) {
          lastTradeId = transactionId;
        }
        final String tradeId = String.valueOf(transactionId);
        final String orderId = taurusUserTransaction.getOrderId();
        final BigDecimal feeAmount = taurusUserTransaction.getFee();
        final CurrencyPair pair = CurrencyPair.BTC_CAD;
        final Currency feeCurrency =
            orderType == OrderType.BID ? pair.getBase() : pair.getCounter();
        trades.add(
            new UserTrade(
                orderType,
                originalAmount,
                pair,
                price,
                timestamp,
                tradeId,
                orderId,
                feeAmount,
                feeCurrency));
      }
    }

    return new UserTrades(trades, lastTradeId, TradeSortType.SortByID);
  }
}
