package org.knowm.xchange.coinbene.dto;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.coinbene.dto.account.CoinbeneCoinBalances;
import org.knowm.xchange.coinbene.dto.marketdata.CoinbeneOrder;
import org.knowm.xchange.coinbene.dto.marketdata.CoinbeneOrderBook;
import org.knowm.xchange.coinbene.dto.marketdata.CoinbeneTicker;
import org.knowm.xchange.coinbene.dto.marketdata.CoinbeneTrade;
import org.knowm.xchange.coinbene.dto.trading.CoinbeneLimitOrder;
import org.knowm.xchange.coinbene.dto.trading.CoinbeneOrders;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;

public class CoinbeneAdapters {

  public static String adaptCurrencyPair(CurrencyPair pair) {

    return (pair.base.getCurrencyCode() + pair.counter.getCurrencyCode()).toLowerCase();
  }

  private static CurrencyPair adaptSymbol(String symbol) {
    try {
      return new CurrencyPair(symbol.substring(0, 3), symbol.substring(3));
    } catch (RuntimeException e) {
      throw new IllegalArgumentException("Not supported Coinbene symbol: " + symbol, e);
    }
  }

  public static String adaptOrderType(OrderType type) {
    switch (type) {
      case BID:
        return "buy-limit";
      case ASK:
        return "sell-limit";
      default:
        throw new IllegalArgumentException("Unsupported order type: " + type);
    }
  }

  public static Ticker adaptTicker(CoinbeneTicker.Container container) {
    CoinbeneTicker ticker = container.getTicker();

    return new Ticker.Builder()
        .currencyPair(adaptSymbol(ticker.getSymbol()))
        .bid(ticker.getBid())
        .ask(ticker.getAsk())
        .high(ticker.getDayHigh())
        .low(ticker.getDayLow())
        .last(ticker.getLast())
        .volume(ticker.getDayVolume())
        .timestamp(new Date(container.getTimestamp()))
        .build();
  }

  public static OrderBook adaptOrderBook(
      CoinbeneOrderBook.Container response, CurrencyPair currencyPair) {

    CoinbeneOrderBook orders = response.getOrderBook();
    List<LimitOrder> asks = new LinkedList<>();
    orders.getAsks().forEach(order -> asks.add(adaptOrder(currencyPair, OrderType.ASK, order)));
    List<LimitOrder> bids = new LinkedList<>();
    orders.getBids().forEach(order -> bids.add(adaptOrder(currencyPair, OrderType.BID, order)));
    return new OrderBook(null, asks, bids);
  }

  public static Trade adaptTrade(CoinbeneTrade trade, CurrencyPair pair) {

    return new Trade.Builder()
        .price(trade.getPrice())
        .originalAmount(trade.getQuantity())
        .currencyPair(pair)
        .type(trade.getTake().getOrderType())
        .timestamp(new Date(trade.getTimestamp()))
        .id(trade.getTradeId())
        .build();
  }

  public static OpenOrders adaptOpenOrders(CoinbeneOrders orders) {

    return new OpenOrders(
        orders
            .getOrders()
            .stream()
            .map(CoinbeneAdapters::adaptLimitOrder)
            .collect(Collectors.toList()));
  }

  public static AccountInfo adaptAccountInfo(CoinbeneCoinBalances balances) {
    Wallet wallet =
        new Wallet(
            null,
            balances
                .getBalances()
                .stream()
                .map(
                    balance ->
                        new Balance(
                            new Currency(balance.getAsset()),
                            balance.getTotal(),
                            balance.getAvailable(),
                            balance.getReserved()))
                .collect(Collectors.toList()));

    return new AccountInfo(wallet);
  }

  private static LimitOrder adaptOrder(
      CurrencyPair currencyPair, OrderType orderType, CoinbeneOrder coinbeneOrder) {

    return new LimitOrder(
        orderType, coinbeneOrder.getQuantity(), currencyPair, null, null, coinbeneOrder.getPrice());
  }

  public static LimitOrder adaptLimitOrder(CoinbeneLimitOrder order) {

    return new LimitOrder.Builder(null, adaptSymbol(order.getSymbol()))
        .id(order.getOrderId())
        .timestamp(new Date(order.getCreateTime()))
        .orderStatus(order.getOrderStatus().getStatus())
        .limitPrice(order.getPrice())
        .cumulativeAmount(order.getFilledQuantity())
        .originalAmount(order.getOrderQuantity())
        .build();
  }
}
