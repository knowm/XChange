package com.xeiam.xchange.coinsetter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.coinsetter.dto.account.CoinsetterAccount;
import com.xeiam.xchange.coinsetter.dto.marketdata.CoinsetterListDepth;
import com.xeiam.xchange.coinsetter.dto.marketdata.CoinsetterPair;
import com.xeiam.xchange.coinsetter.dto.marketdata.CoinsetterPairedDepth;
import com.xeiam.xchange.coinsetter.dto.marketdata.CoinsetterTicker;
import com.xeiam.xchange.coinsetter.dto.marketdata.CoinsetterTrade;
import com.xeiam.xchange.coinsetter.dto.order.response.CoinsetterOrder;
import com.xeiam.xchange.coinsetter.dto.order.response.CoinsetterOrderList;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.Wallet;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.account.Balance;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;

/**
 * Various adapters for converting from Coinsetter DTOs to XChange DTOs.
 */
public final class CoinsetterAdapters {

  private CoinsetterAdapters() {

  }

  public static CurrencyPair adaptCurrencyPair(String symbol) {

    return new CurrencyPair(symbol.substring(0, 3), symbol.substring(3, 6));
  }

  public static String adaptSymbol(CurrencyPair currencyPair) {

    return currencyPair.base.getCurrencyCode() + currencyPair.counter.getCurrencyCode();
  }

  public static OrderType adaptSide(String side) {

    return "BUY".equals(side) ? OrderType.BID : OrderType.ASK;
  }

  public static String adaptSide(OrderType orderType) {

    return orderType == OrderType.BID ? "BUY" : "SELL";
  }

  /**
   * Adapts {@link CoinsetterTicker} to {@link Ticker}.
   *
   * @param coinsetterTicker the {@link CoinsetterTicker}
   * @return the {@link Ticker}
   */
  public static Ticker adaptTicker(CoinsetterTicker coinsetterTicker) {

    return new Ticker.Builder().currencyPair(CurrencyPair.BTC_USD).timestamp(new Date(coinsetterTicker.getLast().getTimeStamp()))
        .ask(coinsetterTicker.getAsk().getPrice()).bid(coinsetterTicker.getBid().getPrice()).last(coinsetterTicker.getLast().getPrice())
        .volume(coinsetterTicker.getVolume()).build();
  }

  /**
   * Adapts {@link CoinsetterPairedDepth} to {@link OrderBook}
   *
   * @param coinsetterPairs array of the {@link CoinsetterPair}.
   * @return the {@link OrderBook}.
   */
  public static OrderBook adaptOrderBook(CoinsetterPair[] coinsetterPairs) {

    int length = coinsetterPairs.length;
    Date timeStamp = null;
    List<LimitOrder> asks;
    List<LimitOrder> bids;
    if (length > 0) {
      timeStamp = new Date(coinsetterPairs[0].getAsk().getTimeStamp());
      asks = new ArrayList<LimitOrder>(length);
      bids = new ArrayList<LimitOrder>(length);
      for (CoinsetterPair coinsetterPair : coinsetterPairs) {
        CoinsetterTrade ask = coinsetterPair.getAsk();
        CoinsetterTrade bid = coinsetterPair.getBid();
        asks.add(new LimitOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USD).limitPrice(ask.getPrice()).tradableAmount(ask.getSize()).build());
        bids.add(new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD).limitPrice(bid.getPrice()).tradableAmount(bid.getSize()).build());
      }
    } else {
      asks = Collections.emptyList();
      bids = Collections.emptyList();
    }
    return new OrderBook(timeStamp, asks, bids);
  }

  /**
   * Adapts {@link CoinsetterListDepth} to {@link OrderBook}
   *
   * @param coinsetterListDepth the {@link CoinsetterListDepth}.
   * @return the {@link OrderBook}.
   */
  public static OrderBook adaptOrderBook(CoinsetterListDepth coinsetterListDepth) {

    Date timeStamp = coinsetterListDepth.getTimeStamp() == null ? null : new Date(coinsetterListDepth.getTimeStamp());

    BigDecimal[][] asks = coinsetterListDepth.getAsks();
    BigDecimal[][] bids = coinsetterListDepth.getBids();

    int asksLength = asks.length;
    int bidsLength = bids.length;

    List<LimitOrder> askOrders = new ArrayList<LimitOrder>(asksLength);
    List<LimitOrder> bidOrders = new ArrayList<LimitOrder>(bidsLength);

    for (int i = asksLength - 1; i >= 0; i--) {
      BigDecimal[] ask = asks[i];
      LimitOrder order = new LimitOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USD).limitPrice(ask[0]).tradableAmount(ask[1]).build();
      askOrders.add(order);
    }

    for (int i = 0; i < bidsLength; i++) {
      BigDecimal[] bid = bids[i];
      LimitOrder order = new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD).limitPrice(bid[0]).tradableAmount(bid[1]).build();
      bidOrders.add(order);
    }

    return new OrderBook(timeStamp, askOrders, bidOrders);
  }

  public static Wallet adaptWallet(CoinsetterAccount account) {

    return new Wallet(
        new Balance(Currency.BTC, account.getBtcBalance()), new Balance(Currency.USD, account.getUsdBalance()));
  }

  public static OpenOrders adaptOpenOrders(CoinsetterOrderList orderList) {

    List<LimitOrder> openOrders = new ArrayList<LimitOrder>();
    for (CoinsetterOrder order : orderList.getOrderList()) {
      openOrders.add(adaptLimitOrder(order));
    }
    return new OpenOrders(openOrders);
  }

  public static LimitOrder adaptLimitOrder(CoinsetterOrder order) {

    return new LimitOrder.Builder(adaptSide(order.getSide()), adaptCurrencyPair(order.getSymbol())).id(order.getUuid().toString())
        .timestamp(order.getCreateDate()).limitPrice(order.getRequestedPrice()).tradableAmount(order.getOpenQuantity()).build();
  }

  public static Trade adaptTrade(CoinsetterTrade last) {

    return new Trade.Builder().type(null).currencyPair(CurrencyPair.BTC_USD).id(String.valueOf(last.getTickId())).price(last.getPrice())
        .timestamp(new Date(last.getTimeStamp())).tradableAmount(last.getSize()).build();
  }

}
