package com.xeiam.xchange.bter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import com.xeiam.xchange.bter.dto.BTEROrderType;
import com.xeiam.xchange.bter.dto.account.BTERFunds;
import com.xeiam.xchange.bter.dto.marketdata.BTERDepth;
import com.xeiam.xchange.bter.dto.marketdata.BTERPublicOrder;
import com.xeiam.xchange.bter.dto.marketdata.BTERTicker;
import com.xeiam.xchange.bter.dto.marketdata.BTERTradeHistory;
import com.xeiam.xchange.bter.dto.marketdata.BTERTradeHistory.BTERPublicTrade;
import com.xeiam.xchange.bter.dto.trade.BTEROpenOrder;
import com.xeiam.xchange.bter.dto.trade.BTEROpenOrders;
import com.xeiam.xchange.bter.dto.trade.BTERTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
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
 * Various adapters for converting from Bter DTOs to XChange DTOs
 */
public final class BTERAdapters {

  /**
   * private Constructor
   */
  private BTERAdapters() {

  }

  public static CurrencyPair adaptCurrencyPair(String pair) {

    final String[] currencies = pair.toUpperCase().split("_");
    return new CurrencyPair(currencies[0], currencies[1]);
  }

  public static Ticker adaptTicker(CurrencyPair currencyPair, BTERTicker bterTicker) {

    BigDecimal ask = bterTicker.getSell();
    BigDecimal bid = bterTicker.getBuy();
    BigDecimal last = bterTicker.getLast();
    BigDecimal low = bterTicker.getLow();
    BigDecimal high = bterTicker.getHigh();
    BigDecimal volume = bterTicker.getVolume(currencyPair.baseSymbol);

    return TickerBuilder.newInstance().withCurrencyPair(currencyPair).withAsk(ask).withBid(bid).withLast(last).withLow(low).withHigh(high).withVolume(volume).build();
  }

  public static LimitOrder adaptOrder(BTERPublicOrder order, CurrencyPair currencyPair, OrderType orderType) {

    return new LimitOrder(orderType, order.getAmount(), currencyPair, "", null, order.getPrice());
  }

  public static List<LimitOrder> adaptOrders(List<BTERPublicOrder> orders, CurrencyPair currencyPair, OrderType orderType) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (BTERPublicOrder bterOrder : orders) {
      limitOrders.add(adaptOrder(bterOrder, currencyPair, orderType));
    }

    return limitOrders;
  }

  public static OrderBook adaptOrderBook(BTERDepth depth, CurrencyPair currencyPair) {

    List<LimitOrder> asks = BTERAdapters.adaptOrders(depth.getAsks(), currencyPair, OrderType.ASK);
    Collections.reverse(asks);
    List<LimitOrder> bids = BTERAdapters.adaptOrders(depth.getBids(), currencyPair, OrderType.BID);

    return new OrderBook(null, asks, bids);
  }

  public static LimitOrder adaptOrder(BTEROpenOrder order, Collection<CurrencyPair> currencyPairs) {

    CurrencyPair possibleCurrencyPair = new CurrencyPair(order.getBuyCurrency(), order.getSellCurrency());
    if (!currencyPairs.contains(possibleCurrencyPair)) {
      BigDecimal price = order.getBuyAmount().divide(order.getSellAmount(), 8, RoundingMode.HALF_UP);
      return new LimitOrder(OrderType.ASK, order.getSellAmount(), new CurrencyPair(order.getSellCurrency(), order.getBuyCurrency()), order.getId(), null, price);
    }
    else {
      BigDecimal price = order.getSellAmount().divide(order.getBuyAmount(), 8, RoundingMode.HALF_UP);
      return new LimitOrder(OrderType.BID, order.getBuyAmount(), possibleCurrencyPair, order.getId(), null, price);
    }
  }

  public static OpenOrders adaptOpenOrders(BTEROpenOrders openOrders, Collection<CurrencyPair> currencyPairs) {

    List<LimitOrder> adaptedOrders = new ArrayList<LimitOrder>();
    for (BTEROpenOrder openOrder : openOrders.getOrders()) {
      adaptedOrders.add(adaptOrder(openOrder, currencyPairs));
    }

    return new OpenOrders(adaptedOrders);
  }

  public static OrderType adaptOrderType(BTEROrderType cryptoTradeOrderType) {

    return (cryptoTradeOrderType.equals(BTEROrderType.BUY)) ? OrderType.BID : OrderType.ASK;
  }

  public static Trade adaptTrade(BTERPublicTrade trade, CurrencyPair currencyPair) {

    OrderType orderType = adaptOrderType(trade.getType());
    Date timestamp = DateUtils.fromMillisUtc(trade.getDate() * 1000);

    return new Trade(orderType, trade.getAmount(), currencyPair, trade.getPrice(), timestamp, trade.getTradeId(), null);
  }

  public static Trades adaptTrades(BTERTradeHistory tradeHistory, CurrencyPair currencyPair) {

    List<Trade> tradeList = new ArrayList<Trade>();
    long lastTradeId = 0;
    for (BTERPublicTrade trade : tradeHistory.getTrades()) {
      String tradeIdString = trade.getTradeId();
      if (!tradeIdString.isEmpty()) {
        long tradeId = Long.valueOf(tradeIdString);
        if (tradeId > lastTradeId)
          lastTradeId = tradeId;
      }
      Trade adaptedTrade = adaptTrade(trade, currencyPair);
      tradeList.add(adaptedTrade);
    }

    return new Trades(tradeList, lastTradeId, TradeSortType.SortByTimestamp);
  }

  public static AccountInfo adaptAccountInfo(BTERFunds bterAccountInfo) {

    List<Wallet> wallets = new ArrayList<Wallet>();
    for (Entry<String, BigDecimal> funds : bterAccountInfo.getAvailableFunds().entrySet()) {
      String currency = funds.getKey().toUpperCase();
      BigDecimal amount = funds.getValue();
      wallets.add(new Wallet(currency, amount));
    }

    return new AccountInfo("", wallets);
  }

  public static Trades adaptUserTrades(List<BTERTrade> userTrades) {

    List<Trade> trades = new ArrayList<Trade>();
    for (BTERTrade userTrade : userTrades) {
      trades.add(adaptUserTrade(userTrade));
    }

    return new Trades(trades, TradeSortType.SortByTimestamp);
  }

  public static Trade adaptUserTrade(BTERTrade bterTrade) {

    OrderType orderType = adaptOrderType(bterTrade.getType());
    Date timestamp = DateUtils.fromMillisUtc(bterTrade.getTimeUnix() * 1000);
    CurrencyPair currencyPair = adaptCurrencyPair(bterTrade.getPair());

    return new Trade(orderType, bterTrade.getAmount(), currencyPair, bterTrade.getRate(), timestamp, bterTrade.getId(), null);
  }

}
