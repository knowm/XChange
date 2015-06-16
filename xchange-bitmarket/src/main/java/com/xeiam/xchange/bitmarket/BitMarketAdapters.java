package com.xeiam.xchange.bitmarket;

import java.math.BigDecimal;
import java.util.*;

import com.xeiam.xchange.bitmarket.dto.BitMarketBaseResponse;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketAccount;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketOrderBook;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketTicker;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketTrade;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketOrder;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketOrdersResponse;
import com.xeiam.xchange.currency.Currencies;
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
 * @author kpysniak
 */
public class BitMarketAdapters {

  /**
   * Singleton
   */
  private BitMarketAdapters() {

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

    if (bitMarketTrades != null) {
      for (BitMarketTrade bitMarketTrade : bitMarketTrades) {
        Trade trade = new Trade(OrderType.BID, bitMarketTrade.getAmount(), currencyPair, bitMarketTrade.getPrice(), new Date(bitMarketTrade.getDate()*1000),
              bitMarketTrade.getTid());
        tradeList.add(trade);
      }
    }

    Trades trades = new Trades(tradeList, Trades.TradeSortType.SortByTimestamp);
    return trades;
  }

  public static AccountInfo adaptAccountInfo(BitMarketAccount bitMarketAccount) {

    List<Wallet> wallets = new ArrayList<Wallet>();

    for (Map.Entry<String, BigDecimal> entry : bitMarketAccount.getBalances().getAvailable().entrySet()) {
      wallets.add(new Wallet(entry.getKey(), entry.getValue(), "Available"));
    }

    /*
    for (Map.Entry<String, BigDecimal> entry : bitMarketAccount.getBalances().getBlocked().entrySet()) {
      Wallet wallet = new Wallet(entry.getKey(), entry.getValue(), "Blocked");
    }
    */
    return new AccountInfo(null, bitMarketAccount.getAccount().getCommissionTaker(), wallets);
  }

  public static List<LimitOrder> adaptOrders(Map<String, BitMarketOrdersResponse> ordersResponseMap) {
    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (BitMarketOrdersResponse ordersResponse : ordersResponseMap.values()) {
      for (BitMarketOrder bitMarketOrder : ordersResponse.getBuy()) {
        limitOrders.add(adaptOrder(bitMarketOrder));
      }

      for (BitMarketOrder bitMarketOrder : ordersResponse.getSell()) {
        limitOrders.add(adaptOrder(bitMarketOrder));
      }
    }
    return limitOrders;
  }
  public static LimitOrder adaptOrder(BitMarketOrder bitMarketOrder) {

    OrderType orderType = "sell".equalsIgnoreCase(bitMarketOrder.getType()) ? OrderType.ASK : OrderType.BID;
    CurrencyPair pair = new CurrencyPair(bitMarketOrder.getMarket().substring(0, 3), bitMarketOrder.getMarket().substring(3,6));
    return new LimitOrder(orderType, bitMarketOrder.getAmount(), pair, bitMarketOrder.getId(), new Date(bitMarketOrder.getTime()*1000), bitMarketOrder.getRate());
  }
}
