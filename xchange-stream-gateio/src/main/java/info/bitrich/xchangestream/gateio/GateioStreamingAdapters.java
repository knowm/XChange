package info.bitrich.xchangestream.gateio;

import info.bitrich.xchangestream.gateio.dto.response.balance.BalancePayload;
import info.bitrich.xchangestream.gateio.dto.response.balance.GateioSingleSpotBalanceNotification;
import info.bitrich.xchangestream.gateio.dto.response.orderbook.GateioOrderBookNotification;
import info.bitrich.xchangestream.gateio.dto.response.orderbook.OrderBookPayload;
import info.bitrich.xchangestream.gateio.dto.response.ticker.GateioTickerNotification;
import info.bitrich.xchangestream.gateio.dto.response.ticker.TickerPayload;
import info.bitrich.xchangestream.gateio.dto.response.trade.GateioTradeNotification;
import info.bitrich.xchangestream.gateio.dto.response.trade.TradePayload;
import info.bitrich.xchangestream.gateio.dto.response.usertrade.GateioSingleUserTradeNotification;
import info.bitrich.xchangestream.gateio.dto.response.usertrade.UserTradePayload;
import java.util.Date;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;

@UtilityClass
public class GateioStreamingAdapters {

  public Ticker toTicker(GateioTickerNotification notification) {
    TickerPayload tickerPayload = notification.getResult();

    return new Ticker.Builder()
        .timestamp(Date.from(notification.getTimeMs()))
        .instrument(tickerPayload.getCurrencyPair())
        .last(tickerPayload.getLastPrice())
        .ask(tickerPayload.getLowestAsk())
        .bid(tickerPayload.getHighestBid())
        .percentageChange(tickerPayload.getChangePercent24h())
        .volume(tickerPayload.getBaseVolume())
        .quoteVolume(tickerPayload.getQuoteVolume())
        .high(tickerPayload.getHighPrice24h())
        .low(tickerPayload.getLowPrice24h())
        .build();
  }


  public Trade toTrade(GateioTradeNotification notification) {
    TradePayload tradePayload = notification.getResult();


    return new Trade.Builder()
        .type(tradePayload.getSide())
        .originalAmount(tradePayload.getAmount())
        .instrument(tradePayload.getCurrencyPair())
        .price(tradePayload.getPrice())
        .timestamp(Date.from(tradePayload.getTimeMs()))
        .id(String.valueOf(tradePayload.getId()))
        .build();
  }


  public UserTrade toUserTrade(GateioSingleUserTradeNotification notification) {
    UserTradePayload userTradePayload = notification.getResult();

    return new UserTrade.Builder()
        .type(userTradePayload.getSide())
        .originalAmount(userTradePayload.getAmount())
        .instrument(userTradePayload.getCurrencyPair())
        .price(userTradePayload.getPrice())
        .timestamp(Date.from(userTradePayload.getTimeMs()))
        .id(String.valueOf(userTradePayload.getId()))
        .orderId(String.valueOf(userTradePayload.getOrderId()))
        .feeAmount(userTradePayload.getFee())
        .feeCurrency(userTradePayload.getFeeCurrency())
        .orderUserReference(userTradePayload.getRemark())
        .build();
  }


  public Balance toBalance(GateioSingleSpotBalanceNotification notification) {
    BalancePayload balancePayload = notification.getResult();

    return new Balance.Builder()
        .currency(balancePayload.getCurrency())
        .total(balancePayload.getTotal())
        .available(balancePayload.getAvailable())
        .frozen(balancePayload.getFreeze())
        .timestamp(Date.from(balancePayload.getTimeMs()))
        .build();
  }


  public OrderBook toOrderBook(GateioOrderBookNotification notification) {
    OrderBookPayload orderBookPayload = notification.getResult();

    Stream<LimitOrder> asks = orderBookPayload.getAsks().stream()
        .map(priceSizeEntry -> new LimitOrder(OrderType.ASK, priceSizeEntry.getSize(), orderBookPayload.getCurrencyPair(), null, null, priceSizeEntry.getPrice()));

    Stream<LimitOrder> bids = orderBookPayload.getAsks().stream()
        .map(priceSizeEntry -> new LimitOrder(OrderType.BID, priceSizeEntry.getSize(), orderBookPayload.getCurrencyPair(), null, null, priceSizeEntry.getPrice()));

    return new OrderBook(Date.from(orderBookPayload.getTimestamp()), asks, bids);
  }


}
