package com.xeiam.xchange.clevercoin;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.clevercoin.dto.account.CleverCoinBalance;
import com.xeiam.xchange.clevercoin.dto.marketdata.CleverCoinOrderBook;
import com.xeiam.xchange.clevercoin.dto.marketdata.CleverCoinTicker;
import com.xeiam.xchange.clevercoin.dto.marketdata.CleverCoinTransaction;
import com.xeiam.xchange.clevercoin.dto.trade.CleverCoinUserTransaction;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.Wallet;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.dto.account.Balance;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Various adapters for converting from CleverCoin DTOs to XChange DTOs
 */
public final class CleverCoinAdapters {

  /**
   * private Constructor
   */
  private CleverCoinAdapters() {

  }

  /**
   * Adapts a CleverCoinBalance to a Wallet
   *
   * @param cleverCoinBalance The CleverCoin balance
   * @return The account info
   */
  public static Wallet adaptWallet(CleverCoinBalance[] cleverCoinBalance) {

    List<Balance> balances = new ArrayList<Balance>();

    for (CleverCoinBalance currencybalance : cleverCoinBalance) {
      balances.add(new Balance(Currency.getInstance(currencybalance.getCurrency()), currencybalance.getBalance()));
    }

    // Adapt to XChange DTOs
    //Wallet usdWallet = new Wallet(Currencies.USD, cleverCoinBalance.getUsdBalance());
    //Wallet btcWallet = new Wallet(Currencies.BTC, cleverCoinBalance.getBtcBalance());
    return new Wallet(balances);
  }

  /**
   * Adapts a com.xeiam.xchange.clevercoin.api.model.OrderBook to a OrderBook Object
   *
   * @param currencyPair (e.g. BTC/USD)
   * @param timeScale polled order books provide a timestamp in seconds, stream in ms
   * @return The XChange OrderBook
   */
  public static OrderBook adaptOrderBook(CleverCoinOrderBook cleverCoinOrderBook, CurrencyPair currencyPair, int timeScale) {

    List<LimitOrder> asks = createOrders(currencyPair, Order.OrderType.ASK, cleverCoinOrderBook.getAsks());
    List<LimitOrder> bids = createOrders(currencyPair, Order.OrderType.BID, cleverCoinOrderBook.getBids());
    Date date = new Date(cleverCoinOrderBook.getTimestamp() * timeScale); // polled order books provide a timestamp in seconds, stream in ms
    return new OrderBook(date, asks, bids);
  }

  public static List<LimitOrder> createOrders(CurrencyPair currencyPair, Order.OrderType orderType, List<List<BigDecimal>> orders) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (List<BigDecimal> ask : orders) {
      checkArgument(ask.size() == 2, "Expected a pair (price, amount) but got {0} elements.", ask.size());
      limitOrders.add(createOrder(currencyPair, ask, orderType));
    }
    return limitOrders;
  }

  public static LimitOrder createOrder(CurrencyPair currencyPair, List<BigDecimal> priceAndAmount, Order.OrderType orderType) {

    return new LimitOrder(orderType, priceAndAmount.get(1), currencyPair, "", null, priceAndAmount.get(0));
  }

  public static void checkArgument(boolean argument, String msgPattern, Object... msgArgs) {

    if (!argument) {
      throw new IllegalArgumentException(MessageFormat.format(msgPattern, msgArgs));
    }
  }

  /**
   * Adapts a Transaction[] to a Trades Object
   *
   * @param transactions The CleverCoin transactions
   * @param currencyPair (e.g. BTC/USD)
   * @return The XChange Trades
   */
  public static Trades adaptTrades(CleverCoinTransaction[] transactions, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<Trade>();
    long lastTradeId = 0;
    for (CleverCoinTransaction tx : transactions) {
      final long tradeId = tx.getTid();
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }
      trades
          .add(new Trade(null, tx.getAmount(), currencyPair, tx.getPrice(), DateUtils.fromMillisUtc(tx.getDate() * 1000L), String.valueOf(tradeId)));
    }

    return new Trades(trades, lastTradeId, TradeSortType.SortByID);
  }

  /**
   * Adapts a Transaction to a Trade Object
   *
   * @param tx The CleverCoin transaction
   * @param currencyPair (e.g. BTC/USD)
   * @param timeScale polled order books provide a timestamp in seconds, stream in ms
   * @return The XChange Trade
   */
  public static Trade adaptTrade(CleverCoinTransaction tx, CurrencyPair currencyPair, int timeScale) {

    final String tradeId = String.valueOf(tx.getTid());
    Date date = DateUtils.fromMillisUtc(tx.getDate() * timeScale);// polled order books provide a timestamp in seconds, stream in ms
    return new Trade(null, tx.getAmount(), currencyPair, tx.getPrice(), date, tradeId);
  }

  /**
   * Adapts a CleverCoinTicker to a Ticker Object
   *
   * @param cleverCoinTicker The exchange specific ticker
   * @param currencyPair (e.g. BTC/USD)
   * @return The ticker
   */
  public static Ticker adaptTicker(CleverCoinTicker cleverCoinTicker, CurrencyPair currencyPair) {

    BigDecimal last = cleverCoinTicker.getLast();
    BigDecimal bid = cleverCoinTicker.getBid();
    BigDecimal ask = cleverCoinTicker.getAsk();
    BigDecimal high = cleverCoinTicker.getHigh();
    BigDecimal low = cleverCoinTicker.getLow();
    BigDecimal volume = cleverCoinTicker.getVolume();
    Date timestamp = new Date(cleverCoinTicker.getTimestamp() * 1000L);

    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).high(high).low(low).volume(volume).timestamp(timestamp)
        .build();

  }

  /**
   * Adapt the user's trades
   *
   * @param cleverCoinUserTransactions
   * @return
   */
  public static UserTrades adaptTradeHistory(CleverCoinUserTransaction[] cleverCoinUserTransactions) {

    List<UserTrade> trades = new ArrayList<UserTrade>();
    long lastTradeId = 0;
    for (CleverCoinUserTransaction cleverCoinUserTransaction : cleverCoinUserTransactions) {
      if (cleverCoinUserTransaction.getType().equals("buy") || cleverCoinUserTransaction.getType().equals("sell")) { // skip account deposits and withdrawals.
        OrderType orderType = (cleverCoinUserTransaction.getType().equals("sell") ? OrderType.ASK : OrderType.BID);
        BigDecimal tradableAmount = cleverCoinUserTransaction.getBtc();
        BigDecimal price = cleverCoinUserTransaction.getPrice().abs();
        Date timestamp = cleverCoinUserTransaction.getTime();
        long transactionId = cleverCoinUserTransaction.getId();
        if (transactionId > lastTradeId) {
          lastTradeId = transactionId;
        }
        final String tradeId = String.valueOf(transactionId);
        final String orderId = String.valueOf(cleverCoinUserTransaction.getOrderId());
        final BigDecimal feeAmount = new BigDecimal(0);
        final CurrencyPair currencyPair = CurrencyPair.BTC_EUR;

        UserTrade trade = new UserTrade(orderType, tradableAmount, currencyPair, price, timestamp, tradeId, orderId, feeAmount,
            currencyPair.counter.getCurrencyCode());
        trades.add(trade);
      }
    }
    return new UserTrades(trades, lastTradeId, TradeSortType.SortByID);
  }
}
