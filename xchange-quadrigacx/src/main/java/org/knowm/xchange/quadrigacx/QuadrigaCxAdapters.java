package org.knowm.xchange.quadrigacx;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.quadrigacx.dto.account.QuadrigaCxBalance;
import org.knowm.xchange.quadrigacx.dto.marketdata.QuadrigaCxOrderBook;
import org.knowm.xchange.quadrigacx.dto.marketdata.QuadrigaCxTicker;
import org.knowm.xchange.quadrigacx.dto.marketdata.QuadrigaCxTransaction;
import org.knowm.xchange.quadrigacx.dto.trade.QuadrigaCxUserTransaction;
import org.knowm.xchange.utils.DateUtils;

public final class QuadrigaCxAdapters {

  private QuadrigaCxAdapters() {
  }

  public static Ticker adaptTicker(QuadrigaCxTicker t, CurrencyPair currencyPair) {

    return new Ticker.Builder().currencyPair(currencyPair).last(t.getLast()).bid(t.getBid()).ask(t.getAsk()).high(t.getHigh()).low(t.getLow())
        .vwap(t.getVwap()).volume(t.getVolume()).timestamp(t.getTimestamp()).build();
  }

  public static Wallet adaptWallet(QuadrigaCxBalance quadrigacxBalance) {
    // Adapt to XChange DTOs
    List<Currency> currencies = quadrigacxBalance.getCurrencyList();
    List<Balance> balances = new ArrayList<>();
    for (Currency currency : currencies) {
      balances.add(new Balance(currency, quadrigacxBalance.getCurrencyBalance(currency), quadrigacxBalance.getCurrencyAvailable(currency),
          quadrigacxBalance.getCurrencyReserved(currency)));
    }

    return new Wallet(balances);
  }

  public static OrderBook adaptOrderBook(QuadrigaCxOrderBook quadrigacxOrderBook, CurrencyPair currencyPair, int timeScale) {

    List<LimitOrder> asks = createOrders(currencyPair, Order.OrderType.ASK, quadrigacxOrderBook.getAsks());
    List<LimitOrder> bids = createOrders(currencyPair, Order.OrderType.BID, quadrigacxOrderBook.getBids());
    Date date = new Date(quadrigacxOrderBook.getTimestamp() * timeScale); // polled order books provide a timestamp in seconds, stream in ms
    return new OrderBook(date, asks, bids);
  }

  public static List<LimitOrder> createOrders(CurrencyPair currencyPair, Order.OrderType orderType, List<List<BigDecimal>> orders) {

    List<LimitOrder> limitOrders = new ArrayList<>();
    for (List<BigDecimal> ask : orders) {
      checkArgument(ask.size() == 2, "Expected a pair (price, amount) but got {0} elements.", ask.size());
      limitOrders.add(createOrder(currencyPair, ask, orderType));
    }
    return limitOrders;
  }

  public static LimitOrder createOrder(CurrencyPair currencyPair, List<BigDecimal> priceAndAmount, Order.OrderType orderType) {

    return new LimitOrder(orderType, priceAndAmount.get(1), currencyPair, "", null, priceAndAmount.get(0));
  }

  /**
   * Adapts a Transaction[] to a Trades Object
   *
   * @param transactions The QuadrigaCx transactions
   * @param currencyPair (e.g. BTC/CAD)
   * @return The XChange Trades
   */
  public static Trades adaptTrades(QuadrigaCxTransaction[] transactions, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<>();
    long lastTradeId = 0;
    for (QuadrigaCxTransaction tx : transactions) {
      Order.OrderType type;
      switch (tx.getSide()) {
        case "buy":
          type = Order.OrderType.ASK;
          break;
        case "sell":
          type = Order.OrderType.BID;
          break;
        default:
          type = null;
      }
      final long tradeId = tx.getTid();
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }
      trades
          .add(new Trade(type, tx.getAmount(), currencyPair, tx.getPrice(), DateUtils.fromMillisUtc(tx.getDate() * 1000L), String.valueOf(tradeId)));
    }

    return new Trades(trades, lastTradeId, TradeSortType.SortByID);
  }

  public static void checkArgument(boolean argument, String msgPattern, Object... msgArgs) {

    if (!argument) {
      throw new IllegalArgumentException(MessageFormat.format(msgPattern, msgArgs));
    }
  }

  public static UserTrades adaptTradeHistory(QuadrigaCxUserTransaction[] quadrigacxUserTransactions, CurrencyPair currencyPair) {

    List<UserTrade> trades = new ArrayList<>();
    long lastTradeId = 0;

    for (QuadrigaCxUserTransaction quadrigacxUserTransaction : quadrigacxUserTransactions) {
      if (quadrigacxUserTransaction.getType().equals(QuadrigaCxUserTransaction.TransactionType.trade)) { // skip account deposits and withdrawals.
        boolean sell = quadrigacxUserTransaction.getCurrencyAmount(currencyPair.counter.getCurrencyCode()).doubleValue() > 0.0;
        Order.OrderType orderType = sell ? Order.OrderType.ASK : Order.OrderType.BID;
        BigDecimal tradableAmount = quadrigacxUserTransaction.getCurrencyAmount(currencyPair.base.getCurrencyCode());
        BigDecimal price = quadrigacxUserTransaction.getPrice().abs();
        Date timestamp = QuadrigaCxUtils.parseDate(quadrigacxUserTransaction.getDatetime());
        long transactionId = quadrigacxUserTransaction.getId();
        if (transactionId > lastTradeId) {
          lastTradeId = transactionId;
        }
        final String tradeId = String.valueOf(transactionId);
        final String orderId = String.valueOf(quadrigacxUserTransaction.getOrderId());
        final BigDecimal feeAmount = quadrigacxUserTransaction.getFee();

        String feeCurrency = sell ? currencyPair.counter.getCurrencyCode() : currencyPair.base.getCurrencyCode();
        UserTrade trade = new UserTrade(orderType, tradableAmount, currencyPair, price, timestamp, tradeId, orderId, feeAmount,
            Currency.getInstance(feeCurrency));
        trades.add(trade);
      }
    }

    return new UserTrades(trades, lastTradeId, Trades.TradeSortType.SortByID);
  }
}
