package info.bitrich.xchangestream.gateio;

import info.bitrich.xchangestream.gateio.dto.response.balance.BalanceDTO;
import info.bitrich.xchangestream.gateio.dto.response.balance.GateioSingleSpotBalanceNotification;
import info.bitrich.xchangestream.gateio.dto.response.orderbook.GateioOrderBookNotification;
import info.bitrich.xchangestream.gateio.dto.response.orderbook.OrderBookDTO;
import info.bitrich.xchangestream.gateio.dto.response.ticker.GateioTickerNotification;
import info.bitrich.xchangestream.gateio.dto.response.ticker.TickerDTO;
import info.bitrich.xchangestream.gateio.dto.response.trade.GateioTradeNotification;
import info.bitrich.xchangestream.gateio.dto.response.trade.TradeDTO;
import info.bitrich.xchangestream.gateio.dto.response.usertrade.GateioSingleUserTradeNotification;
import info.bitrich.xchangestream.gateio.dto.response.usertrade.UserTradeDTO;
import java.util.Date;
import java.util.stream.Stream;
import lombok.SneakyThrows;
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

  @SneakyThrows
  public Ticker toTicker(GateioTickerNotification notification) {
    TickerDTO tickerDTO = notification.getResult();

    return new Ticker.Builder()
        .timestamp(Date.from(notification.getTimeMs()))
        .instrument(tickerDTO.getCurrencyPair())
        .last(tickerDTO.getLastPrice())
        .ask(tickerDTO.getLowestAsk())
        .bid(tickerDTO.getHighestBid())
        .percentageChange(tickerDTO.getChangePercent24h())
        .volume(tickerDTO.getBaseVolume())
        .quoteVolume(tickerDTO.getQuoteVolume())
        .high(tickerDTO.getHighPrice24h())
        .low(tickerDTO.getLowPrice24h())
        .build();
  }


  @SneakyThrows
  public Trade toTrade(GateioTradeNotification notification) {
    TradeDTO tradeDTO = notification.getResult();


    return new Trade.Builder()
        .type("sell".equals(tradeDTO.getSide()) ? OrderType.ASK : OrderType.BID)
        .originalAmount(tradeDTO.getAmount())
        .instrument(tradeDTO.getCurrencyPair())
        .price(tradeDTO.getPrice())
        .timestamp(Date.from(tradeDTO.getTimeMs()))
        .id(String.valueOf(tradeDTO.getId()))
        .build();
  }


  @SneakyThrows
  public UserTrade toUserTrade(GateioSingleUserTradeNotification notification) {
    UserTradeDTO userTradeDTO = notification.getResult();

    return new UserTrade.Builder()
        .type("sell".equals(userTradeDTO.getSide()) ? OrderType.ASK : OrderType.BID)
        .originalAmount(userTradeDTO.getAmount())
        .instrument(userTradeDTO.getCurrencyPair())
        .price(userTradeDTO.getPrice())
        .timestamp(Date.from(userTradeDTO.getTimeMs()))
        .id(String.valueOf(userTradeDTO.getId()))
        .orderId(String.valueOf(userTradeDTO.getOrderId()))
        .feeAmount(userTradeDTO.getFee())
        .feeCurrency(userTradeDTO.getFeeCurrency())
        .orderUserReference(userTradeDTO.getRemark())
        .build();
  }


  @SneakyThrows
  public Balance toBalance(GateioSingleSpotBalanceNotification notification) {
    BalanceDTO balanceDTO = notification.getResult();

    return Balance.builder()
        .currency(balanceDTO.getCurrency())
        .total(balanceDTO.getTotal())
        .available(balanceDTO.getAvailable())
        .frozen(balanceDTO.getFreeze())
        .timestamp(Date.from(balanceDTO.getTimeMs()))
        .build();
  }


  @SneakyThrows
  public OrderBook toOrderBook(GateioOrderBookNotification notification) {
    OrderBookDTO orderBookDTO = notification.getResult();

    Stream<LimitOrder> asks = orderBookDTO.getAsks().stream()
        .map(priceSizeEntry -> new LimitOrder(OrderType.ASK, priceSizeEntry.getSize(), orderBookDTO.getCurrencyPair(), null, null, priceSizeEntry.getPrice()));

    Stream<LimitOrder> bids = orderBookDTO.getAsks().stream()
        .map(priceSizeEntry -> new LimitOrder(OrderType.BID, priceSizeEntry.getSize(), orderBookDTO.getCurrencyPair(), null, null, priceSizeEntry.getPrice()));

    return new OrderBook(Date.from(orderBookDTO.getTimestamp()), asks, bids);
  }


}
