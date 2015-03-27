package com.xeiam.xchange.taurus;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.taurus.dto.account.TaurusBalance;
import com.xeiam.xchange.taurus.dto.marketdata.TaurusOrderBook;
import com.xeiam.xchange.taurus.dto.marketdata.TaurusTicker;
import com.xeiam.xchange.taurus.dto.marketdata.TaurusTransaction;
import com.xeiam.xchange.taurus.dto.trade.TaurusUserTransaction;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.dto.trade.Wallet;

public final class TaurusAdapters {

  private TaurusAdapters() {
  }

  public static AccountInfo adaptAccountInfo(TaurusBalance taurusBalance, String userName) {
    Wallet cadWallet = new Wallet(Currencies.CAD, taurusBalance.getCadBalance());
    Wallet btcWallet = new Wallet(Currencies.BTC, taurusBalance.getBtcBalance());

    return new AccountInfo(userName, taurusBalance.getFee(), Arrays.asList(cadWallet, btcWallet));
  }

  public static OrderBook adaptOrderBook(TaurusOrderBook taurusOrderBook, CurrencyPair currencyPair) {
    List<LimitOrder> asks = createOrders(currencyPair, OrderType.ASK, taurusOrderBook.getAsks());
    List<LimitOrder> bids = createOrders(currencyPair, OrderType.BID, taurusOrderBook.getBids());
    return new OrderBook(taurusOrderBook.getTimestamp(), asks, bids);
  }

  public static List<LimitOrder> createOrders(CurrencyPair currencyPair, OrderType orderType, List<List<BigDecimal>> orders) {
    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (List<BigDecimal> ask : orders) {
      checkArgument(ask.size() == 2, "Expected a pair (price, amount) but got {0} elements.", ask.size());
      limitOrders.add(createOrder(currencyPair, ask, orderType));
    }
    return limitOrders;
  }

  public static LimitOrder createOrder(CurrencyPair currencyPair, List<BigDecimal> priceAndAmount, OrderType orderType) {
    return new LimitOrder(orderType, priceAndAmount.get(1), currencyPair, "", null, priceAndAmount.get(0));
  }

  public static void checkArgument(boolean argument, String msgPattern, Object... msgArgs) {
    if (!argument) {
      throw new IllegalArgumentException(MessageFormat.format(msgPattern, msgArgs));
    }
  }

  public static Trades adaptTrades(TaurusTransaction[] transactions, CurrencyPair currencyPair) {
    List<Trade> trades = new ArrayList<Trade>();
    long lastTradeId = 0;
    for (TaurusTransaction tx : transactions) {
      final long tradeId = tx.getTid();
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }
      trades.add(new Trade(null, tx.getAmount(), currencyPair, tx.getPrice(), tx.getDate(), String.valueOf(tradeId)));
    }

    return new Trades(trades, lastTradeId, TradeSortType.SortByID);
  }

  public static Trade adaptTrade(TaurusTransaction tx, CurrencyPair currencyPair) {
    final String tradeId = String.valueOf(tx.getTid());
    Date date = tx.getDate();
    return new Trade(null, tx.getAmount(), currencyPair, tx.getPrice(), date, tradeId);
  }

  public static Ticker adaptTicker(TaurusTicker tt, CurrencyPair currencyPair) {

    return new Ticker.Builder().currencyPair(currencyPair).last(tt.getLast()).bid(tt.getBid()).ask(tt.getAsk())
        .high(tt.getHigh()).low(tt.getLow()).vwap(tt.getVwap()).volume(tt.getVolume()).timestamp(tt.getTimestamp())
        .build();
  }

  public static UserTrades adaptTradeHistory(TaurusUserTransaction[] taurusUserTransactions) {
    List<UserTrade> trades = new ArrayList<UserTrade>();
    long lastTradeId = 0;
    for (TaurusUserTransaction taurusUserTransaction : taurusUserTransactions) {
      if (taurusUserTransaction.getType().equals(TaurusUserTransaction.TransactionType.trade)) { // skip account deposits and withdrawals.
        OrderType orderType = taurusUserTransaction.getCad().doubleValue() > 0.0 ? OrderType.ASK : OrderType.BID;
        BigDecimal tradableAmount = taurusUserTransaction.getBtc();
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
        final String feeCurrency = orderType == OrderType.BID ? pair.baseSymbol : pair.counterSymbol;
        trades.add(new UserTrade(orderType, tradableAmount, pair, price, timestamp, tradeId, orderId, feeAmount, feeCurrency));
      }
    }

    return new UserTrades(trades, lastTradeId, TradeSortType.SortByID);
  }
}
