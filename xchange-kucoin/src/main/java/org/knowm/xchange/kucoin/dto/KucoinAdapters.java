package org.knowm.xchange.kucoin.dto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.kucoin.dto.account.KucoinUserInfoResponse;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinDealOrder;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinOrderBook;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinTicker;
import org.knowm.xchange.kucoin.dto.trading.KucoinActiveOrder;
import org.knowm.xchange.kucoin.dto.trading.KucoinActiveOrders;
import org.knowm.xchange.kucoin.dto.trading.KucoinDealtOrder;

public class KucoinAdapters {
  public static String adaptCurrencyPair(CurrencyPair pair) {
    return pair.base.getCurrencyCode() + "-" + pair.counter.getCurrencyCode();
  }
  
  public static Ticker adaptTicker(KucoinResponse<KucoinTicker> tickResponse, CurrencyPair pair) {
    KucoinTicker kcTick = tickResponse.getData();
    return new Ticker.Builder()
        .currencyPair(pair)
        .ask(kcTick.getBuy())
        .bid(kcTick.getSell())
        .high(kcTick.getHigh())
        .low(kcTick.getLow())
        .last(kcTick.getLastDealPrice())
        .volume(kcTick.getVol())
        .quoteVolume(kcTick.getVolValue())
        .timestamp(new Date(kcTick.getDatetime()))
        .build();
  }

  public static AccountInfo adaptAccountInfo(KucoinUserInfoResponse kucoinInfo) {
    return new AccountInfo(kucoinInfo.getUserInfo().getEmail(), Arrays.asList());
  }

  public static OrderBook adaptOrderBook(KucoinResponse<KucoinOrderBook> response,
      CurrencyPair currencyPair) {
    KucoinOrderBook kcOrders = response.getData();
    Date timestamp = new Date(response.getTimestamp());
    List<LimitOrder> asks = new LinkedList<>();
    kcOrders.getSell().stream()
        .forEach(s -> asks.add(adaptLimitOrder(currencyPair, OrderType.ASK, s, timestamp)));
    List<LimitOrder> bids = new LinkedList<>();
    kcOrders.getBuy().stream()
        .forEach(s -> bids.add(adaptLimitOrder(currencyPair, OrderType.BID, s, timestamp)));
    return new OrderBook(timestamp, asks, bids);
  }
  
  public static Trade adaptTrade(KucoinDealOrder kucoinTrade, CurrencyPair currencyPair) {
    return new Trade(kucoinTrade.getOrderType().getOrderType(),
        kucoinTrade.getAmount(), currencyPair, kucoinTrade.getPrice(),
        new Date(kucoinTrade.getTimestamp()), null);
  }
  
  private static LimitOrder adaptLimitOrder(CurrencyPair currencyPair, OrderType orderType,
      List<BigDecimal> kucoinLimitOrder, Date timestamp) {
    return new LimitOrder(orderType, kucoinLimitOrder.get(1), currencyPair, null, timestamp, kucoinLimitOrder.get(0));
  }

  public static OpenOrders adaptActiveOrders(CurrencyPair currencyPair, KucoinActiveOrders data) {
    List<LimitOrder> openOrders = new LinkedList<>();
    data.getBuy().stream().forEach(order -> openOrders.add(adaptActiveOrder(currencyPair, order)));
    data.getSell().stream().forEach(order -> openOrders.add(adaptActiveOrder(currencyPair, order)));
    return new OpenOrders(openOrders);
  }

  private static LimitOrder adaptActiveOrder(CurrencyPair currencyPair, KucoinActiveOrder order) {
    return new LimitOrder.Builder(order.getOrderType().getOrderType(), currencyPair)
        .timestamp(order.getTimestamp())
        .id(order.getOrderOid())
        .limitPrice(order.getPrice())
        .originalAmount(order.getAmount()) // this might be the remaining amount, not sure
        .cumulativeAmount(order.getDealAmount())
        .orderStatus(order.getDealAmount().compareTo(BigDecimal.ZERO) == 0 ?
            OrderStatus.NEW : OrderStatus.PARTIALLY_FILLED)
        .build();
  }

  public static UserTrades adaptUserTrades(List<KucoinDealtOrder> orders) {
    List<UserTrade> trades = new LinkedList<>();
    orders.stream().forEach(order -> trades.add(adaptUserTrade(order)));
    return new UserTrades(trades, TradeSortType.SortByTimestamp);
  }

  private static UserTrade adaptUserTrade(KucoinDealtOrder order) {
    return new UserTrade.Builder()
        .currencyPair(new CurrencyPair(order.getCoinType(), order.getCoinTypePair()))
        .orderId(order.getOid())
        .originalAmount(order.getAmount())
        .price(order.getDealPrice())
        .timestamp(new Date(order.getCreatedAt()))
        .type(order.getDirection().getOrderType())
        .feeAmount(order.getFee())
        .feeCurrency(order.getDirection().equals(KucoinOrderType.BUY)
            ? new Currency(order.getCoinType()) : new Currency(order.getCoinTypePair()))
        .build();
  }
}
