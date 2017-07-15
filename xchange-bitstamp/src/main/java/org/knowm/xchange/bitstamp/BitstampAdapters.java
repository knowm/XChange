package org.knowm.xchange.bitstamp;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.bitstamp.dto.account.BitstampBalance;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampOrderBook;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampTicker;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampTransaction;
import org.knowm.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
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
import org.knowm.xchange.utils.DateUtils;

/**
 * Various adapters for converting from Bitstamp DTOs to XChange DTOs
 */
public final class BitstampAdapters {

  /**
   * private Constructor
   */
  private BitstampAdapters() {

  }

  /**
   * Adapts a BitstampBalance to an AccountInfo
   *
   * @param bitstampBalance The Bitstamp balance
   * @param userName The user name
   * @return The account info
   */
  public static AccountInfo adaptAccountInfo(BitstampBalance bitstampBalance, String userName) {

    // Adapt to XChange DTOs
      List<Balance> balances = new ArrayList<>();
      for (org.knowm.xchange.bitstamp.dto.account.BitstampBalance.Balance b : bitstampBalance.getBalances()) {
          Balance xchangeBalance = new Balance(new Currency(b.getCurrency().toUpperCase()), b.getBalance(), b.getAvailable(),
                  b.getReserved(), ZERO, ZERO, b.getBalance().subtract(b.getAvailable()).subtract(b.getReserved()), ZERO);
          balances.add(xchangeBalance);
      }
      return new AccountInfo(userName, bitstampBalance.getFee(), new Wallet(balances));
  }

  /**
   * Adapts a org.knowm.xchange.bitstamp.api.model.OrderBook to a OrderBook Object
   *
   * @param currencyPair (e.g. BTC/USD)
   * @param timeScale polled order books provide a timestamp in seconds, stream in ms
   * @return The XChange OrderBook
   */
  public static OrderBook adaptOrderBook(BitstampOrderBook bitstampOrderBook, CurrencyPair currencyPair) {
    List<LimitOrder> asks = createOrders(currencyPair, Order.OrderType.ASK, bitstampOrderBook.getAsks());
    List<LimitOrder> bids = createOrders(currencyPair, Order.OrderType.BID, bitstampOrderBook.getBids());
    return new OrderBook(bitstampOrderBook.getTimestamp(), asks, bids);
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

  public static void checkArgument(boolean argument, String msgPattern, Object... msgArgs) {

    if (!argument) {
      throw new IllegalArgumentException(MessageFormat.format(msgPattern, msgArgs));
    }
  }

  /**
   * Adapts a Transaction[] to a Trades Object
   *
   * @param transactions The Bitstamp transactions
   * @param currencyPair (e.g. BTC/USD)
   * @return The XChange Trades
   */
  public static Trades adaptTrades(BitstampTransaction[] transactions, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<>();
    long lastTradeId = 0;
    for (BitstampTransaction tx : transactions) {
      final long tradeId = tx.getTid();
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }
      trades.add(adaptTrade(tx, currencyPair, 1000));
    }

    return new Trades(trades, lastTradeId, TradeSortType.SortByID);
  }

  /**
   * Adapts a Transaction to a Trade Object
   *
   * @param tx The Bitstamp transaction
   * @param currencyPair (e.g. BTC/USD)
   * @param timeScale polled order books provide a timestamp in seconds, stream in ms
   * @return The XChange Trade
   */
  public static Trade adaptTrade(BitstampTransaction tx, CurrencyPair currencyPair, int timeScale) {

    OrderType orderType = tx.getType() == 0 ? OrderType.BID : OrderType.ASK;
    final String tradeId = String.valueOf(tx.getTid());
    Date date = DateUtils.fromMillisUtc(tx.getDate() * timeScale);// polled order books provide a timestamp in seconds, stream in ms
    return new Trade(orderType, tx.getAmount(), currencyPair, tx.getPrice(), date, tradeId);
  }

  /**
   * Adapts a BitstampTicker to a Ticker Object
   *
   * @param bitstampTicker The exchange specific ticker
   * @param currencyPair (e.g. BTC/USD)
   * @return The ticker
   */
  public static Ticker adaptTicker(BitstampTicker bitstampTicker, CurrencyPair currencyPair) {

    BigDecimal last = bitstampTicker.getLast();
    BigDecimal bid = bitstampTicker.getBid();
    BigDecimal ask = bitstampTicker.getAsk();
    BigDecimal high = bitstampTicker.getHigh();
    BigDecimal low = bitstampTicker.getLow();
    BigDecimal vwap = bitstampTicker.getVwap();
    BigDecimal volume = bitstampTicker.getVolume();
    Date timestamp = new Date(bitstampTicker.getTimestamp() * 1000L);

    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).high(high).low(low).vwap(vwap).volume(volume)
        .timestamp(timestamp).build();

  }

    /**
     * Adapt the user's trades
     *
     * @param bitstampUserTransactions
     * @return
     */
    public static UserTrades adaptTradeHistory(BitstampUserTransaction[] bitstampUserTransactions) {

        List<UserTrade> trades = new ArrayList<>();
        long lastTradeId = 0;
        for (BitstampUserTransaction t : bitstampUserTransactions) {
            if (!t.getType().equals(BitstampUserTransaction.TransactionType.trade)) { // skip account deposits and withdrawals.
                continue;
            }
            final OrderType orderType;
            if (t.getCounterAmount().doubleValue() == 0.0){
                orderType = t.getBaseAmount().doubleValue() < 0.0 ? OrderType.ASK : OrderType.BID;
            } else {
                orderType = t.getCounterAmount().doubleValue() > 0.0 ? OrderType.ASK : OrderType.BID;
            }

            long tradeId = t.getId();
            if (tradeId > lastTradeId) {
                lastTradeId = tradeId;
            }
            final CurrencyPair pair = new CurrencyPair(t.getBaseCurrency().toUpperCase(), t.getCounterCurrency().toUpperCase());
            UserTrade trade = new UserTrade(orderType, t.getBaseAmount().abs(), pair, t.getPrice().abs(), t.getDatetime()
                    , Long.toString(tradeId), Long.toString(t.getOrderId()), t.getFee(), new Currency(t.getFeeCurrency().toUpperCase()));
            trades.add(trade);
        }
        return new UserTrades(trades, lastTradeId, TradeSortType.SortByID);
    }
}
