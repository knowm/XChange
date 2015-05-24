package com.xeiam.xchange.bitmarket;

import java.math.BigDecimal;
import java.util.*;

import com.xeiam.xchange.bitmarket.dto.account.BitMarketBalance;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketOrderBook;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketTicker;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;

/**
 * @author kpysniak, kfonal
 */
public class BitMarketAdapters {

  /**
   * Singleton
   */
  private BitMarketAdapters() {

  }

  /**
   * Adapts BitMarketBalance to AccountInfo
   *
   * @param balance
   * @param username
   * @return
   */
  public static AccountInfo adaptAccountInfo(BitMarketBalance balance, String username){

    Map<String, Wallet> wallets = new HashMap<String, Wallet>();

    for (Map.Entry<String, BigDecimal> entry : balance.getAvailable().entrySet()) {
      BigDecimal frozen = balance.getBlocked().containsKey(entry.getKey()) ?
          balance.getBlocked().get(entry.getKey()) :
          new BigDecimal("0");
      BigDecimal available = entry.getValue();
      wallets.put(entry.getKey(), new Wallet(entry.getKey(), available.add(frozen), available, frozen));
    }

    return new AccountInfo(username, wallets);
  }

  /**
   * Adapts BitMarket ticker to Ticker.
   *
   * @param bitMarketTicker
   * @param currencyPair
   * @return
   */
  public static Ticker adaptTicker(BitMarketTicker bitMarketTicker, CurrencyPair currencyPair) {

    BigDecimal bid = bitMarketTicker.getBid();
    BigDecimal ask = bitMarketTicker.getAsk();
    BigDecimal high = bitMarketTicker.getHigh();
    BigDecimal low = bitMarketTicker.getLow();
    BigDecimal volume = bitMarketTicker.getVolume();
    BigDecimal last = bitMarketTicker.getLast();

    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).high(high).low(low).volume(volume).build();
  }

  private static List<LimitOrder> transformArrayToLimitOrders(BigDecimal[][] orders, OrderType orderType, CurrencyPair currencyPair) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (BigDecimal[] order : orders) {
      limitOrders.add(new LimitOrder(orderType, order[1], currencyPair, null, new Date(), order[0]));
    }

    return limitOrders;
  }

  public static OrderBook adaptOrderBook(BitMarketOrderBook bitMarketOrderBook, CurrencyPair currencyPair) {

    OrderBook orderBook = new OrderBook(null, transformArrayToLimitOrders(bitMarketOrderBook.getAsks(), OrderType.ASK, currencyPair),
        transformArrayToLimitOrders(bitMarketOrderBook.getBids(), OrderType.BID, currencyPair));

    return orderBook;
  }

  public static Trades adaptTrades(BitMarketTrade[] bitMarketTrades, CurrencyPair currencyPair) {

    List<Trade> tradeList = new ArrayList<Trade>();

    for (BitMarketTrade bitMarketTrade : bitMarketTrades) {

      Trade trade = new Trade(OrderType.BID, bitMarketTrade.getAmount(), currencyPair, bitMarketTrade.getPrice(), new Date(bitMarketTrade.getDate()),
          bitMarketTrade.getTid());

      tradeList.add(trade);
    }

    Trades trades = new Trades(tradeList, Trades.TradeSortType.SortByTimestamp);
    return trades;
  }
}
