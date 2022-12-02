package org.knowm.xchange.coincheck;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.knowm.xchange.coincheck.dto.marketdata.CoincheckOrderBook;
import org.knowm.xchange.coincheck.dto.marketdata.CoincheckPair;
import org.knowm.xchange.coincheck.dto.marketdata.CoincheckTicker;
import org.knowm.xchange.coincheck.dto.marketdata.CoincheckTrade;
import org.knowm.xchange.coincheck.dto.marketdata.CoincheckTradesContainer;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CoincheckAdapter {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static Ticker createTicker(Instrument instrument, CoincheckTicker coincheck) {
    return new Ticker.Builder()
        .instrument(instrument)
        .last(coincheck.getLast())
        .bid(coincheck.getBid())
        .ask(coincheck.getAsk())
        .high(coincheck.getHigh())
        .low(coincheck.getLow())
        .volume(coincheck.getVolume())
        .timestamp(new Date(coincheck.getTimestamp() * 1000L))
        .build();
  }

  public static OrderBook createOrderBook(Instrument instrument, CoincheckOrderBook coincheck) {
    List<LimitOrder> bids = createOrders(Order.OrderType.BID, instrument, coincheck.getBids());
    List<LimitOrder> asks = createOrders(Order.OrderType.ASK, instrument, coincheck.getAsks());
    return new OrderBook(null, asks, bids);
  }

  public static final List<LimitOrder> createOrders(
      Order.OrderType orderType, Instrument instrument, List<List<BigDecimal>> data) {
    return data.stream()
        .map(d -> createOrder(orderType, instrument, d))
        .collect(Collectors.toList());
  }

  public static final LimitOrder createOrder(
      Order.OrderType orderType, Instrument instrument, List<BigDecimal> data) {
    return new LimitOrder.Builder(orderType, instrument)
        .limitPrice(data.get(0))
        .originalAmount(data.get(1))
        .orderStatus(Order.OrderStatus.OPEN)
        .build();
  }

  public static Trades createTrades(CoincheckTradesContainer container) {
    return createTrades(container.getData());
  }

  public static Trades createTrades(List<CoincheckTrade> coincheckTrades) {
    List<Trade> trades =
        coincheckTrades.stream().map(trade -> createTrade(trade)).collect(Collectors.toList());
    return new Trades(trades);
  }

  @SneakyThrows
  public static Trade createTrade(CoincheckTrade coincheckTrade) {
    CoincheckPair pair = CoincheckPair.stringToPair(coincheckTrade.getPair());
    return new Trade.Builder()
        .id(Long.toString(coincheckTrade.getId()))
        .instrument(pair.getPair())
        .originalAmount(coincheckTrade.getAmount())
        .price(coincheckTrade.getRate())
        .timestamp(coincheckTrade.getCreatedAt())
        .type(createOrderType(coincheckTrade.getOrderType()))
        .build();
  }

  public static Order.OrderType createOrderType(String orderType) {
    switch (orderType) {
      case "sell":
        return Order.OrderType.ASK;
      case "buy":
        return Order.OrderType.BID;
      default:
        throw new IllegalArgumentException("Unknown order type");
    }
  }
}
