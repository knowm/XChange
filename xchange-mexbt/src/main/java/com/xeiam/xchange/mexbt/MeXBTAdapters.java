package com.xeiam.xchange.mexbt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.Balance;
import com.xeiam.xchange.dto.account.Wallet;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.mexbt.dto.account.MeXBTBalance;
import com.xeiam.xchange.mexbt.dto.account.MeXBTBalanceResponse;
import com.xeiam.xchange.mexbt.dto.account.MeXBTDepositAddress;
import com.xeiam.xchange.mexbt.dto.account.MeXBTDepositAddressesResponse;
import com.xeiam.xchange.mexbt.dto.account.MeXBTOpenOrder;
import com.xeiam.xchange.mexbt.dto.account.MeXBTOpenOrdersInfo;
import com.xeiam.xchange.mexbt.dto.account.MeXBTOpenOrdersResponse;
import com.xeiam.xchange.mexbt.dto.account.MeXBTTradeResponse;
import com.xeiam.xchange.mexbt.dto.account.MeXBTUserTrade;
import com.xeiam.xchange.mexbt.dto.marketdata.MeXBTOrderBook;
import com.xeiam.xchange.mexbt.dto.marketdata.MeXBTTicker;
import com.xeiam.xchange.mexbt.dto.marketdata.MeXBTTrade;
import com.xeiam.xchange.mexbt.dto.streaming.MeXBTStreamingTradeOrOrder;

public final class MeXBTAdapters {

  private MeXBTAdapters() {
  }

  public static String toCurrencyPair(CurrencyPair currencyPair) {
    return (currencyPair.base.getCurrencyCode() + currencyPair.counter.getCurrencyCode()).toLowerCase();
  }

  public static CurrencyPair adaptCurrencyPair(String ins) {
    return new CurrencyPair(ins.substring(0, 3), ins.substring(3, 6));
  }

  public static String toSide(OrderType orderType) {
    return orderType == OrderType.BID ? "buy" : "sell";
  }

  public static OrderType adaptSide(int side) {
    return side == 0 ? OrderType.BID : OrderType.ASK;
  }

  public static OrderBook adaptOrderBook(CurrencyPair currencyPair, MeXBTOrderBook meXBTOrderBook) {
    final List<LimitOrder> asks = new ArrayList<LimitOrder>(meXBTOrderBook.getAsks().length);
    final List<LimitOrder> bids = new ArrayList<LimitOrder>(meXBTOrderBook.getBids().length);

    for (final BigDecimal[] order : meXBTOrderBook.getAsks()) {
      asks.add(new LimitOrder.Builder(OrderType.ASK, currencyPair).limitPrice(order[0]).tradableAmount(order[1]).build());
    }
    for (final BigDecimal[] order : meXBTOrderBook.getBids()) {
      bids.add(new LimitOrder.Builder(OrderType.BID, currencyPair).limitPrice(order[0]).tradableAmount(order[1]).build());
    }
    return new OrderBook(null, asks, bids);
  }

  public static Trade adaptTrade(CurrencyPair currencyPair, MeXBTTrade meXBTTrade) {
    return new Trade.Builder().currencyPair(currencyPair).id(String.valueOf(meXBTTrade.getTid())).timestamp(meXBTTrade.getDate())
        .tradableAmount(meXBTTrade.getAmount()).price(meXBTTrade.getPrice()).build();
  }

  public static Trades adaptTrades(CurrencyPair currencyPair, MeXBTTrade[] meXBTTrades) {
    List<Trade> trades = new ArrayList<Trade>(meXBTTrades.length);
    for (MeXBTTrade meXBTTrade : meXBTTrades) {
      trades.add(adaptTrade(currencyPair, meXBTTrade));
    }
    return new Trades(trades, TradeSortType.SortByID);
  }

  public static Ticker adaptTicker(CurrencyPair currencyPair, MeXBTTicker meXBTTicker) {
    return new Ticker.Builder().currencyPair(currencyPair).last(meXBTTicker.getLast()).bid(meXBTTicker.getBid()).ask(meXBTTicker.getAsk())
        .high(meXBTTicker.getHigh()).low(meXBTTicker.getLow()).volume(meXBTTicker.getVolume24Hour()).build();
  }

  public static Wallet adaptWallet(MeXBTBalanceResponse balanceResponse) {
    List<Balance> wallet = new ArrayList<Balance>(balanceResponse.getCurrencies().length);
    for (MeXBTBalance mxbtBalance : balanceResponse.getCurrencies()) {
      Balance balance = new Balance.Builder().currency(Currency.getInstance(mxbtBalance.getName()))
          .available(mxbtBalance.getBalance()).frozen(mxbtBalance.getHold()) .build();
      wallet.add(balance);
    }
    return new Wallet(wallet);
  }

  public static String getDepositAddress(MeXBTDepositAddressesResponse depositAddressesResponse, String currency) {
    for (MeXBTDepositAddress address : depositAddressesResponse.getAddresses()) {
      if (address.getName().equals(currency)) {
        return address.getDepositAddress();
      }
    }
    return null;
  }

  public static OpenOrders adaptOpenOrders(MeXBTOpenOrdersResponse openOrdersResponse) {
    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (MeXBTOpenOrdersInfo openOrdersInfo : openOrdersResponse.getOpenOrdersInfo()) {
      CurrencyPair currencyPair = adaptCurrencyPair(openOrdersInfo.getIns());
      for (MeXBTOpenOrder openOrder : openOrdersInfo.getOpenOrders()) {
        LimitOrder limitOrder = new LimitOrder.Builder(adaptSide(openOrder.getSide()), currencyPair).id(String.valueOf(openOrder.getServerOrderId()))
            .timestamp(openOrder.getReceiveTime()).limitPrice(openOrder.getPrice()).tradableAmount(openOrder.getQtyRemaining()).build();
        limitOrders.add(limitOrder);
      }
    }
    return new OpenOrders(limitOrders);
  }

  public static UserTrades adaptUserTrades(MeXBTTradeResponse tradeResponse) {
    List<UserTrade> userTrades = new ArrayList<UserTrade>(tradeResponse.getTrades().length);
    CurrencyPair currencyPair = adaptCurrencyPair(tradeResponse.getIns());
    for (MeXBTUserTrade t : tradeResponse.getTrades()) {
      UserTrade userTrade = new UserTrade.Builder().currencyPair(currencyPair).id(String.valueOf(t.getTid())).price(t.getPx())
          .tradableAmount(t.getQty()).timestamp(t.getTime()).build();
      userTrades.add(userTrade);
    }
    return new UserTrades(userTrades, TradeSortType.SortByID);
  }

  public static LimitOrder adaptOrder(String ins, MeXBTStreamingTradeOrOrder too) {
    return new LimitOrder.Builder(adaptSide(too.getSide()), adaptCurrencyPair(ins)).id(String.valueOf(too.getId())).timestamp(too.getTimestamp())
        .limitPrice(too.getPrice()).tradableAmount(too.getQuantity()).build();
  }

  public static Trade adaptTrade(String ins, MeXBTStreamingTradeOrOrder too) {
    return new Trade.Builder().currencyPair(adaptCurrencyPair(ins)).id(String.valueOf(too.getId())).timestamp(too.getTimestamp())
        .price(too.getPrice()).tradableAmount(too.getQuantity()).type(adaptSide(too.getSide())).build();
  }

}
