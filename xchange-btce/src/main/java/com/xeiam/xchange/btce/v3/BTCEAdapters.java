package com.xeiam.xchange.btce.v3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.btce.v3.dto.account.BTCEAccountInfo;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCETicker;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCETrade;
import com.xeiam.xchange.btce.v3.dto.trade.BTCEOrder;
import com.xeiam.xchange.btce.v3.dto.trade.BTCETradeHistoryResult;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Various adapters for converting from BTCE DTOs to XChange DTOs
 */
public final class BTCEAdapters {

  public static final Logger log = LoggerFactory.getLogger(BTCEAdapters.class);

  /**
   * private Constructor
   */
  private BTCEAdapters() {

  }

  /**
   * Adapts a List of BTCEOrders to a List of LimitOrders
   *
   * @param bTCEOrders
   * @param currencyPair
   * @param orderTypeString
   * @param id
   * @return
   */
  public static List<LimitOrder> adaptOrders(List<BigDecimal[]> bTCEOrders, CurrencyPair currencyPair, String orderTypeString, String id) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    OrderType orderType = orderTypeString.equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;

    for (BigDecimal[] btceOrder : bTCEOrders) {
      limitOrders.add(adaptOrder(btceOrder[1], btceOrder[0], currencyPair, orderType, id));
    }

    return limitOrders;
  }

  /**
   * Adapts a BTCEOrder to a LimitOrder
   *
   * @param amount
   * @param price
   * @param currencyPair
   * @param orderType
   * @param id
   * @return
   */
  public static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, CurrencyPair currencyPair, OrderType orderType, String id) {

    return new LimitOrder(orderType, amount, currencyPair, id, null, price);
  }

  /**
   * Adapts a BTCETradeV3 to a Trade Object
   *
   * @param bTCETrade BTCE trade object v.3
   * @param tradableIdentifier First currency in the pair
   * @param currency Second currency in the pair
   * @return The XChange Trade
   */
  public static Trade adaptTrade(BTCETrade bTCETrade, CurrencyPair currencyPair) {

    OrderType orderType = bTCETrade.getTradeType().equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = bTCETrade.getAmount();
    BigDecimal price = bTCETrade.getPrice();
    Date date = DateUtils.fromMillisUtc(bTCETrade.getDate() * 1000L);

    final String tradeId = String.valueOf(bTCETrade.getTid());
    return new Trade(orderType, amount, currencyPair, price, date, tradeId);
  }

  /**
   * Adapts a BTCETradeV3[] to a Trades Object
   *
   * @param bTCETrades The BTCE trade data returned by API v.3
   * @param tradableIdentifier First currency of the pair
   * @param currency Second currency of the pair
   * @return The trades
   */
  public static Trades adaptTrades(BTCETrade[] bTCETrades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<Trade>();
    long lastTradeId = 0;
    for (BTCETrade bTCETrade : bTCETrades) {
      // Date is reversed order. Insert at index 0 instead of appending
      long tradeId = bTCETrade.getTid();
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }
      tradesList.add(0, adaptTrade(bTCETrade, currencyPair));
    }
    return new Trades(tradesList, lastTradeId, TradeSortType.SortByID);
  }

  /**
   * Adapts a BTCETicker to a Ticker Object
   *
   * @param bTCETicker
   * @return
   */
  public static Ticker adaptTicker(BTCETicker bTCETicker, CurrencyPair currencyPair) {

    BigDecimal last = bTCETicker.getLast();
    BigDecimal bid = bTCETicker.getSell();
    BigDecimal ask = bTCETicker.getBuy();
    BigDecimal high = bTCETicker.getHigh();
    BigDecimal low = bTCETicker.getLow();
    BigDecimal avg = bTCETicker.getAvg();
    BigDecimal volume = bTCETicker.getVolCur();
    Date timestamp = DateUtils.fromMillisUtc(bTCETicker.getUpdated() * 1000L);

    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).high(high).low(low).vwap(avg).volume(volume).timestamp(timestamp)
        .build();
  }

  public static AccountInfo adaptAccountInfo(BTCEAccountInfo btceAccountInfo) {

    List<Wallet> wallets = new ArrayList<Wallet>();
    Map<String, BigDecimal> funds = btceAccountInfo.getFunds();

    for (String lcCurrency : funds.keySet()) {
      String currency = lcCurrency.toUpperCase();

      wallets.add(new Wallet(currency, funds.get(lcCurrency)));
    }
    return new AccountInfo(null, wallets);
  }

  public static OpenOrders adaptOrders(Map<Long, BTCEOrder> btceOrderMap) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (Long id : btceOrderMap.keySet()) {
      BTCEOrder bTCEOrder = btceOrderMap.get(id);
      OrderType orderType = bTCEOrder.getType() == BTCEOrder.Type.buy ? OrderType.BID : OrderType.ASK;
      String[] pair = bTCEOrder.getPair().split("_");
      BigDecimal price = bTCEOrder.getRate();
      Date timestamp = DateUtils.fromMillisUtc(bTCEOrder.getTimestampCreated() * 1000L);
      CurrencyPair currencyPair = new CurrencyPair(pair[0].toUpperCase(), pair[1].toUpperCase());

      limitOrders.add(new LimitOrder(orderType, bTCEOrder.getAmount(), currencyPair, Long.toString(id), timestamp, price));
    }
    return new OpenOrders(limitOrders);
  }

  public static UserTrades adaptTradeHistory(Map<Long, BTCETradeHistoryResult> tradeHistory) {

    List<UserTrade> trades = new ArrayList<UserTrade>(tradeHistory.size());
    for (Entry<Long, BTCETradeHistoryResult> entry : tradeHistory.entrySet()) {
      BTCETradeHistoryResult result = entry.getValue();
      OrderType type = result.getType() == BTCETradeHistoryResult.Type.buy ? OrderType.BID : OrderType.ASK;
      BigDecimal price = result.getRate();
      BigDecimal tradableAmount = result.getAmount();
      Date timeStamp = DateUtils.fromMillisUtc(result.getTimestamp() * 1000L);
      String orderId = String.valueOf(result.getOrderId());
      String tradeId = String.valueOf(entry.getKey());
      CurrencyPair currencyPair = adaptCurrencyPair(result.getPair());
      trades.add(new UserTrade(type, tradableAmount, currencyPair, price, timeStamp, tradeId, orderId, null, null));
    }
    return new UserTrades(trades, TradeSortType.SortByTimestamp);
  }

  public static CurrencyPair adaptCurrencyPair(String btceCurrencyPair) {

    String[] currencies = btceCurrencyPair.split("_");
    return new CurrencyPair(currencies[0].toUpperCase(), currencies[1].toUpperCase());
  }

  public static String adaptCurrencyPair(CurrencyPair currencyPair) {
    return (currencyPair.baseSymbol + "_" + currencyPair.counterSymbol).toLowerCase();
  }

  public static List<CurrencyPair> adaptCurrencyPairs(Iterable<String> btcePairs) {

    List<CurrencyPair> pairs = new ArrayList<CurrencyPair>();
    for (String btcePair : btcePairs) {
      pairs.add(adaptCurrencyPair(btcePair));
    }

    return pairs;
  }

}
