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
    final BigDecimal usdWithdrawing = bitstampBalance.getUsdBalance().subtract(bitstampBalance.getUsdAvailable())
            .subtract(bitstampBalance.getUsdReserved());
    final BigDecimal eurWithdrawing = bitstampBalance.getEurBalance().subtract(bitstampBalance.getEurAvailable())
            .subtract(bitstampBalance.getEurReserved());
    final BigDecimal btcWithdrawing = bitstampBalance.getBtcBalance().subtract(bitstampBalance.getBtcAvailable())
            .subtract(bitstampBalance.getBtcReserved());
    final BigDecimal xrpWithdrawing = bitstampBalance.getXrpBalance().subtract(bitstampBalance.getXrpAvailable())
            .subtract(bitstampBalance.getXrpReserved());
    Balance usdBalance = new Balance(Currency.USD, bitstampBalance.getUsdBalance(), bitstampBalance.getUsdAvailable(),
            bitstampBalance.getUsdReserved(), ZERO, ZERO, usdWithdrawing, ZERO);
    Balance eurBalance = new Balance(Currency.EUR, bitstampBalance.getEurBalance(), bitstampBalance.getEurAvailable(),
            bitstampBalance.getEurReserved(), ZERO, ZERO, eurWithdrawing, ZERO);
    Balance btcBalance = new Balance(Currency.BTC, bitstampBalance.getBtcBalance(), bitstampBalance.getBtcAvailable(),
            bitstampBalance.getBtcReserved(), ZERO, ZERO, btcWithdrawing, ZERO);
    Balance xrpBalance = new Balance(Currency.XRP, bitstampBalance.getXrpBalance(), bitstampBalance.getXrpAvailable(),
            bitstampBalance.getXrpReserved(), ZERO, ZERO, xrpWithdrawing, ZERO);


    return new AccountInfo(userName, bitstampBalance.getFee(), new Wallet(usdBalance, eurBalance, btcBalance, xrpBalance));
  }

  /**
   * Adapts a org.knowm.xchange.bitstamp.api.model.OrderBook to a OrderBook Object
   *
   * @param currencyPair (e.g. BTC/USD)
   * @param timeScale polled order books provide a timestamp in seconds, stream in ms
   * @return The XChange OrderBook
   */
  public static OrderBook adaptOrderBook(BitstampOrderBook bitstampOrderBook, CurrencyPair currencyPair, int timeScale) {

    List<LimitOrder> asks = createOrders(currencyPair, Order.OrderType.ASK, bitstampOrderBook.getAsks());
    List<LimitOrder> bids = createOrders(currencyPair, Order.OrderType.BID, bitstampOrderBook.getBids());
    Date date = new Date(bitstampOrderBook.getTimestamp() * timeScale); // polled order books provide a timestamp in seconds, stream in ms
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
   * @param transactions The Bitstamp transactions
   * @param currencyPair (e.g. BTC/USD)
   * @return The XChange Trades
   */
  public static Trades adaptTrades(BitstampTransaction[] transactions, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<Trade>();
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

    List<UserTrade> trades = new ArrayList<UserTrade>();
    long lastTradeId = 0;
    for (BitstampUserTransaction bitstampUserTransaction : bitstampUserTransactions) {
      if (bitstampUserTransaction.getType().equals(BitstampUserTransaction.TransactionType.trade)) { // skip account deposits and withdrawals.
        OrderType orderType = bitstampUserTransaction.getCounterAmount().doubleValue() > 0.0 ? OrderType.ASK : OrderType.BID;
        BigDecimal tradableAmount = bitstampUserTransaction.getBtc();
        BigDecimal price = bitstampUserTransaction.getPrice().abs();
        Date timestamp = BitstampUtils.parseDate(bitstampUserTransaction.getDatetime());
        long transactionId = bitstampUserTransaction.getId();
        if (transactionId > lastTradeId) {
          lastTradeId = transactionId;
        }
        final String tradeId = String.valueOf(transactionId);
        final String orderId = String.valueOf(bitstampUserTransaction.getOrderId());
        final BigDecimal feeAmount = bitstampUserTransaction.getFee();
        final CurrencyPair currencyPair = new CurrencyPair(Currency.BTC, new Currency(bitstampUserTransaction.getCounterCurrency()));

        UserTrade trade = new UserTrade(orderType, tradableAmount, currencyPair, price, timestamp, tradeId, orderId, feeAmount,
                Currency.getInstance(currencyPair.counter.getCurrencyCode()));
        trades.add(trade);
      }
    }

    return new UserTrades(trades, lastTradeId, TradeSortType.SortByID);
  }
}
