package info.bitrich.xchangestream.coinmate.v2;

import info.bitrich.xchangestream.coinmate.v2.dto.CoinmateWebSocketTrade;
import info.bitrich.xchangestream.coinmate.v2.dto.CoinmateWebSocketUserTrade;
import info.bitrich.xchangestream.coinmate.v2.dto.CoinmateWebsocketOpenOrder;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;

public class CoinmateStreamingAdapter {

  public static String getChannelPostfix(CurrencyPair currencyPair) {
    return currencyPair.base.toString().toUpperCase()
        + "_"
        + currencyPair.counter.toString().toUpperCase();
  }

  public static UserTrades adaptWebSocketUserTrades(
      List<CoinmateWebSocketUserTrade> coinmateWebSocketUserTrades, CurrencyPair currencyPair) {
    List<UserTrade> userTrades = new ArrayList<>();
    coinmateWebSocketUserTrades.forEach(
        (coinmateWebSocketUserTrade) -> {
          userTrades.add(
              new UserTrade.Builder()
                  .type(
                      (coinmateWebSocketUserTrade.getUserOrderType().equals("SELL"))
                          ? Order.OrderType.ASK
                          : Order.OrderType.BID)
                  .originalAmount(BigDecimal.valueOf(coinmateWebSocketUserTrade.getAmount()))
                  .currencyPair(currencyPair)
                  .price(BigDecimal.valueOf(coinmateWebSocketUserTrade.getPrice()))
                  .timestamp(
                      Date.from(Instant.ofEpochMilli(coinmateWebSocketUserTrade.getTimestamp())))
                  .id(coinmateWebSocketUserTrade.getTransactionId())
                  .orderId(
                      (coinmateWebSocketUserTrade.getUserOrderType().equals("SELL"))
                          ? coinmateWebSocketUserTrade.getSellOrderId()
                          : coinmateWebSocketUserTrade.getBuyOrderId())
                  .feeAmount(BigDecimal.valueOf(coinmateWebSocketUserTrade.getFee()))
                  .feeCurrency(currencyPair.counter)
                  .build());
        });
    return new UserTrades(userTrades, Trades.TradeSortType.SortByTimestamp);
  }

  public static OpenOrders adaptWebsocketOpenOrders(
      List<CoinmateWebsocketOpenOrder> coinmateWebsocketOpenOrders, CurrencyPair currencyPair) {
    List<LimitOrder> openOrders = new ArrayList<>();
    coinmateWebsocketOpenOrders.forEach(
        (coinmateWebsocketOpenOrder) -> {
          openOrders.add(
              new LimitOrder.Builder(
                      (coinmateWebsocketOpenOrder.getOrderType().contains("SELL"))
                          ? Order.OrderType.ASK
                          : Order.OrderType.BID,
                      currencyPair)
                  .originalAmount(BigDecimal.valueOf(coinmateWebsocketOpenOrder.getAmount()))
                  .cumulativeAmount(
                      BigDecimal.valueOf(coinmateWebsocketOpenOrder.getOriginalOrderSize()))
                  .id(coinmateWebsocketOpenOrder.getId())
                  .timestamp(
                      Date.from(Instant.ofEpochMilli(coinmateWebsocketOpenOrder.getTimestamp())))
                  .limitPrice(BigDecimal.valueOf(coinmateWebsocketOpenOrder.getPrice()))
                  .orderStatus(fromString(coinmateWebsocketOpenOrder.getOrderChangePushEvent()))
                  .build());
        });
    return new OpenOrders(openOrders);
  }

  private static Order.OrderStatus fromString(String orderChangePushEvent) {
    if (orderChangePushEvent == null) {
      return Order.OrderStatus.UNKNOWN;
    }
    switch (orderChangePushEvent) {
      case "CREATION":
        return Order.OrderStatus.NEW;
      case "UPDATE":
        return Order.OrderStatus.PARTIALLY_FILLED;
      case "REMOVAL":
        return Order.OrderStatus.CLOSED;
      case "SNAPSHOT":
        return Order.OrderStatus.OPEN;
    }
    return Order.OrderStatus.UNKNOWN;
  }

  public static Trade adaptTrade(CoinmateWebSocketTrade webSocketTrade, CurrencyPair currencyPair) {
    return new Trade(
        webSocketTrade.getType().equals("BUY") ? Order.OrderType.BID : Order.OrderType.ASK,
        webSocketTrade.getAmount(),
        currencyPair,
        webSocketTrade.getPrice(),
        new java.util.Date(webSocketTrade.getTimestamp()),
        null,
        webSocketTrade.getType().equals("BUY")
            ? webSocketTrade.getSellOrderId().toString()
            : webSocketTrade.getBuyOrderId().toString(),
        webSocketTrade.getType().equals("BUY")
            ? webSocketTrade.getBuyOrderId().toString()
            : webSocketTrade.getSellOrderId().toString());
  }
}
