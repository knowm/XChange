package org.knowm.xchange.lakebtc;

import java.math.BigDecimal;
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
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.lakebtc.dto.account.LakeBTCAccount;
import org.knowm.xchange.lakebtc.dto.account.LakeBTCBalance;
import org.knowm.xchange.lakebtc.dto.account.LakeBTCProfile;
import org.knowm.xchange.lakebtc.dto.marketdata.LakeBTCOrderBook;
import org.knowm.xchange.lakebtc.dto.marketdata.LakeBTCTicker;
import org.knowm.xchange.lakebtc.dto.trade.LakeBTCTradeResponse;
import org.knowm.xchange.utils.DateUtils;

/**
 * @author kpysniak
 */
public class LakeBTCAdapters {

  // TODO move to metadata
  private static final String SIDE_BID = "";
  private static final int PRICE_SCALE = 8;
  private static final int PERCENT_DECIMAL_SHIFT = 2;

  /**
   * Singleton
   */
  private LakeBTCAdapters() {

  }

  public static Ticker adaptTicker(LakeBTCTicker lakeBTCTicker, CurrencyPair currencyPair) {

    BigDecimal ask = lakeBTCTicker.getAsk();
    BigDecimal bid = lakeBTCTicker.getBid();
    BigDecimal high = lakeBTCTicker.getHigh();
    BigDecimal low = lakeBTCTicker.getLow();
    BigDecimal last = lakeBTCTicker.getLast();
    BigDecimal volume = lakeBTCTicker.getVolume();

    return new Ticker.Builder().currencyPair(currencyPair).bid(bid).ask(ask).high(high).low(low).last(last).volume(volume).build();
  }

  private static List<LimitOrder> transformArrayToLimitOrders(BigDecimal[][] orders, OrderType orderType, CurrencyPair currencyPair) {
    List<LimitOrder> limitOrders = new ArrayList<>();
    for (BigDecimal[] order : orders) {
      limitOrders.add(new LimitOrder(orderType, order[1], currencyPair, null, null, order[0]));
    }

    return limitOrders;
  }

  public static OrderBook adaptOrderBook(LakeBTCOrderBook lakeBTCOrderBook, CurrencyPair currencyPair) {
    return new OrderBook(null, transformArrayToLimitOrders(lakeBTCOrderBook.getAsks(), OrderType.ASK, currencyPair),
        transformArrayToLimitOrders(lakeBTCOrderBook.getBids(), OrderType.BID, currencyPair));
  }

  /**
   * Adapts a Transaction[] to a Trades Object
   *
   * @param transactions The LakeBtc transactions
   * @param currencyPair (e.g. BTC/USD)
   * @return The XChange Trades
   */
  public static Trades adaptTrades(LakeBTCTradeResponse[] transactions, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<>();
    long lastTradeId = 0;
    for (LakeBTCTradeResponse trade : transactions) {
      final OrderType orderType = trade.getType().startsWith("buy") ? OrderType.BID : OrderType.ASK;
      trades.add(
          new Trade(orderType, trade.getAmount(), currencyPair, trade.getTotal(), DateUtils.fromMillisUtc(trade.getAt() * 1000L), trade.getId()));
    }

    return new Trades(trades, lastTradeId, Trades.TradeSortType.SortByTimestamp);
  }

  /**
   * Adapts a Transaction to a Trade Object
   *
   * @param tx The LakeBtc transaction
   * @param currencyPair (e.g. BTC/USD)
   * @param timeScale polled order books provide a timestamp in seconds, stream in ms
   * @return The XChange Trade
   */
  public static Trade adaptTrade(LakeBTCTradeResponse tx, CurrencyPair currencyPair, int timeScale) {

    final String tradeId = String.valueOf(tx.getId());
    Date date = DateUtils.fromMillisUtc(tx.getAt() * timeScale);// polled order
    // books provide
    // a timestamp
    // in seconds,
    // stream in ms
    return new Trade(null, tx.getAmount(), currencyPair, tx.getTotal(), date, tradeId);
  }

  /**
   * Adapt the user's trades
   *
   * @param transactions
   * @return
   */
  public static UserTrades adaptTradeHistory(LakeBTCTradeResponse[] transactions) {

    List<UserTrade> trades = new ArrayList<>();
    long lastTradeId = 0;
    for (LakeBTCTradeResponse trade : transactions) {
      final OrderType orderType = trade.getType().startsWith("buy") ? OrderType.BID : OrderType.ASK;
      BigDecimal tradableAmount = trade.getAmount();
      BigDecimal price = trade.getTotal().abs();
      Date timestamp = DateUtils.fromMillisUtc(trade.getAt() * 1000L);

      final String tradeId = trade.getId();
      final CurrencyPair currencyPair = CurrencyPair.BTC_CNY;
      UserTrade userTrade = new UserTrade(orderType, tradableAmount, currencyPair, price, timestamp, tradeId, null, null,
          Currency.getInstance(currencyPair.counter.getCurrencyCode()));
      trades.add(userTrade);
    }

    return new UserTrades(trades, lastTradeId, Trades.TradeSortType.SortByTimestamp);
  }

  /**
   * Adapts a LakeBTCAccount to an AccountInfo
   *
   * @param lakeBTCAccount
   * @return Wallet
   */
  public static AccountInfo adaptAccountInfo(LakeBTCAccount lakeBTCAccount) {

    // Adapt to XChange DTOs
    LakeBTCProfile profile = lakeBTCAccount.getProfile();
    LakeBTCBalance balance = lakeBTCAccount.getBalance();
    Balance usdBalance = new Balance(Currency.USD, balance.getUSD());
    Balance cnyWBalance = new Balance(Currency.CNY, balance.getCNY());
    Balance btcBalance = new Balance(Currency.BTC, balance.getBTC());

    return new AccountInfo(profile.getId(), new Wallet(usdBalance, btcBalance, cnyWBalance));
  }
}
