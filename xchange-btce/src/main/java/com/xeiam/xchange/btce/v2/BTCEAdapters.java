package com.xeiam.xchange.btce.v2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.btce.v2.dto.account.BTCEAccountInfo;
import com.xeiam.xchange.btce.v2.dto.marketdata.BTCETickerWrapper;
import com.xeiam.xchange.btce.v2.dto.marketdata.BTCETrade;
import com.xeiam.xchange.btce.v2.dto.trade.BTCEOrder;
import com.xeiam.xchange.btce.v2.dto.trade.BTCETradeHistoryResult;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Various adapters for converting from BTCE DTOs to XChange DTOs
 */
@Deprecated
public final class BTCEAdapters {

  private static final Logger log = LoggerFactory.getLogger(BTCEAdapters.class);

  /**
   * private Constructor
   */
  private BTCEAdapters() {

  }

  /**
   * Adapts a List of BTCEOrders to a List of LimitOrders
   * 
   * @param bTCEOrders
   * @param currency
   * @param orderType
   * @param id
   * @return
   */
  public static List<LimitOrder> adaptOrders(List<BigDecimal[]> bTCEOrders, CurrencyPair currencyPair, String orderType, String id) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (BigDecimal[] btceOrder : bTCEOrders) {
      // Bid orderbook is reversed order. Insert at index 0 instead of appending
      if (orderType.equalsIgnoreCase("bid")) {
        limitOrders.add(0, adaptOrder(btceOrder[1], btceOrder[0], currencyPair, orderType, id));
      }
      else {
        limitOrders.add(adaptOrder(btceOrder[1], btceOrder[0], currencyPair, orderType, id));
      }
    }

    return limitOrders;
  }

  /**
   * Adapts a BTCEOrder to a LimitOrder
   * 
   * @param amount
   * @param price
   * @param currency
   * @param orderTypeString
   * @param id
   * @return
   */
  public static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, CurrencyPair currencyPair, String orderTypeString, String id) {

    // place a limit order
    OrderType orderType = orderTypeString.equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;

    return new LimitOrder(orderType, amount, currencyPair, id, null, price);

  }

  /**
   * Adapts a BTCETrade to a Trade Object
   * 
   * @param bTCETrade
   *          A BTCE trade
   * @return The XChange Trade
   */
  public static Trade adaptTrade(BTCETrade bTCETrade) {

    OrderType orderType = bTCETrade.getTradeType().equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = bTCETrade.getAmount();
    String currency = bTCETrade.getCurrency();
    BigDecimal price = bTCETrade.getPrice();
    String tradableIdentifier = bTCETrade.getTradeableIdentifier();
    CurrencyPair currencyPair = new CurrencyPair(tradableIdentifier, currency);
    Date date = DateUtils.fromMillisUtc(bTCETrade.getDate() * 1000L);

    final String tradeId = String.valueOf(bTCETrade.getTid());
    return new Trade(orderType, amount, currencyPair, price, date, tradeId);
  }

  /**
   * Adapts a BTCETrade[] to a Trades Object
   * 
   * @param BTCETrades
   *          The BTCE trade data
   * @return The trades
   */
  public static Trades adaptTrades(BTCETrade[] BTCETrades) {

    List<Trade> tradesList = new ArrayList<Trade>();
    for (BTCETrade BTCETrade : BTCETrades) {
      // Date is reversed order. Insert at index 0 instead of appending
      tradesList.add(0, adaptTrade(BTCETrade));
    }
    return new Trades(tradesList, TradeSortType.SortByID);
  }

  /**
   * Adapts a BTCETicker to a Ticker Object
   * 
   * @param bTCETicker
   * @return
   */
  public static Ticker adaptTicker(BTCETickerWrapper bTCETicker, CurrencyPair currencyPair) {

    BigDecimal last = bTCETicker.getTicker().getLast();
    BigDecimal bid = bTCETicker.getTicker().getSell();
    BigDecimal ask = bTCETicker.getTicker().getBuy();
    BigDecimal high = bTCETicker.getTicker().getHigh();
    BigDecimal low = bTCETicker.getTicker().getLow();
    BigDecimal volume = bTCETicker.getTicker().getVolCur();
    Date timestamp = DateUtils.fromMillisUtc(bTCETicker.getTicker().getServerTime() * 1000L);

    return TickerBuilder.newInstance().withCurrencyPair(currencyPair).withLast(last).withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume).withTimestamp(timestamp).build();
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

  public static Trades adaptTradeHistory(Map<Long, BTCETradeHistoryResult> tradeHistory) {

    List<Trade> trades = new ArrayList<Trade>(tradeHistory.size());
    for (Entry<Long, BTCETradeHistoryResult> entry : tradeHistory.entrySet()) {
      BTCETradeHistoryResult result = entry.getValue();
      OrderType type = result.getType() == BTCETradeHistoryResult.Type.buy ? OrderType.BID : OrderType.ASK;
      String[] pair = result.getPair().split("_");
      BigDecimal price = result.getRate();
      BigDecimal tradableAmount = result.getAmount();
      Date timeStamp = DateUtils.fromMillisUtc(result.getTimestamp() * 1000L);
      String tradeId = String.valueOf(entry.getKey());
      String orderId = String.valueOf(result.getOrderId());
      CurrencyPair currencyPair = new CurrencyPair(pair[0].toUpperCase(), pair[1].toUpperCase());

      trades.add(new Trade(type, tradableAmount, currencyPair, price, timeStamp, tradeId, orderId));
    }
    return new Trades(trades, TradeSortType.SortByTimestamp);
  }

}
