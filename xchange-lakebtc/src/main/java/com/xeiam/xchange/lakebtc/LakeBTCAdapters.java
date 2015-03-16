package com.xeiam.xchange.lakebtc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.lakebtc.dto.account.LakeBTCAccount;
import com.xeiam.xchange.lakebtc.dto.account.LakeBTCBalance;
import com.xeiam.xchange.lakebtc.dto.account.LakeBTCProfile;
import com.xeiam.xchange.lakebtc.dto.marketdata.LakeBTCOrderBook;
import com.xeiam.xchange.lakebtc.dto.marketdata.LakeBTCTicker;
import com.xeiam.xchange.lakebtc.dto.trade.LakeBTCTradeResponse;
import com.xeiam.xchange.utils.DateUtils;

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
    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (BigDecimal[] order : orders) {
      limitOrders.add(new LimitOrder(orderType, order[1], currencyPair, null, null, order[0]));
    }

    return limitOrders;
  }

  public static OrderBook adaptOrderBook(LakeBTCOrderBook lakeBTCOrderBook, CurrencyPair currencyPair) {
    return new OrderBook(null, transformArrayToLimitOrders(lakeBTCOrderBook.getAsks(), OrderType.ASK, currencyPair), transformArrayToLimitOrders(
        lakeBTCOrderBook.getBids(), OrderType.BID, currencyPair));
  }

  /**
   * Adapts a Transaction[] to a Trades Object
   *
   * @param transactions The LakeBtc transactions
   * @param currencyPair (e.g. BTC/USD)
   * @return The XChange Trades
   */
  public static Trades adaptTrades(LakeBTCTradeResponse[] transactions, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<Trade>();
    long lastTradeId = 0;
    for (LakeBTCTradeResponse trade : transactions) {
      final OrderType orderType = trade.getType().startsWith("buy") ? OrderType.BID : OrderType.ASK;
      trades.add(new Trade(orderType, trade.getAmount(), currencyPair, trade.getTotal(), DateUtils.fromMillisUtc(trade.getAt() * 1000L), trade
          .getId()));
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

    List<UserTrade> trades = new ArrayList<UserTrade>();
    long lastTradeId = 0;
    for (LakeBTCTradeResponse trade : transactions) {
      final OrderType orderType = trade.getType().startsWith("buy") ? OrderType.BID : OrderType.ASK;
      BigDecimal tradableAmount = trade.getAmount();
      BigDecimal price = trade.getTotal().abs();
      Date timestamp = DateUtils.fromMillisUtc(trade.getAt() * 1000L);

      final String tradeId = trade.getId();
      final CurrencyPair currencyPair = CurrencyPair.BTC_CNY;
      UserTrade userTrade = new UserTrade(orderType, tradableAmount, currencyPair, price, timestamp, tradeId, null, null, currencyPair.counterSymbol);
      trades.add(userTrade);
    }

    return new UserTrades(trades, lastTradeId, Trades.TradeSortType.SortByTimestamp);
  }

  /**
   * Adapts a LakeBTCAccount to a AccountInfo
   *
   * @param lakeBTCAccount
   * @return AccountInfo
   */
  public static AccountInfo adaptAccountInfo(LakeBTCAccount lakeBTCAccount) {

    // Adapt to XChange DTOs
    LakeBTCProfile profile = lakeBTCAccount.getProfile();
    LakeBTCBalance balance = lakeBTCAccount.getBalance();
    Wallet usdWallet = new Wallet(Currencies.USD, balance.getUSD());
    Wallet cnyWWallet = new Wallet(Currencies.CNY, balance.getCNY());
    Wallet btcWallet = new Wallet(Currencies.BTC, balance.getBTC());

    return new AccountInfo(profile.getId(), Arrays.asList(usdWallet, btcWallet, cnyWWallet));
  }
}
