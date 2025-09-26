package info.bitrich.xchangestream.binance;

import info.bitrich.xchangestream.binance.dto.market.BinanceRawTrade;
import info.bitrich.xchangestream.binance.dto.market.DepthBinanceWebSocketTransaction;
import info.bitrich.xchangestream.binance.dto.trade.BinanceWebsocketOrderAmendPayload;
import info.bitrich.xchangestream.binance.dto.trade.BinanceWebsocketPlaceOrderPayload;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.trade.OrderType;
import org.knowm.xchange.binance.dto.trade.TimeInForce;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.instrument.Instrument;

public class BinanceStreamingAdapters {

  public static Trade adaptRawTrade(BinanceRawTrade rawTrade, Instrument instrument) {
    return Trade.builder()
        .type(BinanceAdapters.convertType(rawTrade.isBuyerMarketMaker()))
        .originalAmount(rawTrade.getQuantity())
        .instrument(instrument)
        .price(rawTrade.getPrice())
        .makerOrderId(getMakerOrderId(rawTrade))
        .takerOrderId(getTakerOrderId(rawTrade))
        .timestamp(new Date(rawTrade.getTimestamp()))
        .id(String.valueOf(rawTrade.getTradeId()))
        .build();
  }

  private static String getMakerOrderId(BinanceRawTrade trade) {
    return String.valueOf(
        trade.isBuyerMarketMaker() ? trade.getBuyerOrderId() : trade.getSellerOrderId());
  }

  private static String getTakerOrderId(BinanceRawTrade trade) {
    return String.valueOf(
        trade.isBuyerMarketMaker() ? trade.getSellerOrderId() : trade.getBuyerOrderId());
  }

  public static OrderBook adaptFuturesOrderbook(DepthBinanceWebSocketTransaction binanceOrderBook) {
    List<LimitOrder> bids = new ArrayList<>();
    List<LimitOrder> asks = new ArrayList<>();
    Instrument instrument = BinanceAdapters.adaptSymbol(binanceOrderBook.getSymbol(), true);

    binanceOrderBook
        .getOrderBook()
        .asks
        .forEach(
            (key, value) ->
                asks.add(
                    new LimitOrder.Builder(Order.OrderType.ASK, instrument)
                        .limitPrice(key)
                        .originalAmount(value)
                        .build()));
    binanceOrderBook
        .getOrderBook()
        .bids
        .forEach(
            (key, value) ->
                bids.add(
                    new LimitOrder.Builder(Order.OrderType.BID, instrument)
                        .limitPrice(key)
                        .originalAmount(value)
                        .build()));

    return new OrderBook(Date.from(Instant.now()), asks, bids);
  }

  public static BinanceWebsocketOrderAmendPayload adaptAmendOrder(LimitOrder limitOrder) {
    Long id = null;
    if (limitOrder.getId() != null && !limitOrder.getId().isEmpty()) {
      id = Long.parseLong(limitOrder.getId());
    }
    return BinanceWebsocketOrderAmendPayload.builder()
        .symbol(BinanceAdapters.toSymbol(limitOrder.getInstrument()))
        .side(BinanceAdapters.convert(limitOrder.getType()))
        .orderId(id)
        .origClientOrderId(limitOrder.getUserReference())
        .price(limitOrder.getLimitPrice())
        .quantity(limitOrder.getOriginalAmount())
        .timestamp(System.currentTimeMillis()).build();
  }

  public static BinanceWebsocketPlaceOrderPayload adaptPlaceOrder(Order order) {
    boolean reduceOnly = false;
    if (order.getInstrument() instanceof FuturesContract) {
      switch (order.getType()) {
        case EXIT_ASK:
        case EXIT_BID:
          reduceOnly = true;
          break;
      }
      BinanceWebsocketPlaceOrderPayload payload = null;
      if (order instanceof LimitOrder) {
        TimeInForce tif = BinanceAdapters.getOrderFlag(order, TimeInForce.class).orElse(TimeInForce.GTC);
        payload = BinanceWebsocketPlaceOrderPayload.builder().type(OrderType.LIMIT).symbol(BinanceAdapters.toSymbol(order.getInstrument()))
            .side(BinanceAdapters.convert(order.getType()))
            .reduceOnly(reduceOnly)
            .quantity(order.getOriginalAmount())
            .price(((LimitOrder) order).getLimitPrice())
            .timestamp(System.currentTimeMillis())
            .timeInForce(tif)
            .newClientOrderId(order.getUserReference()).build();
        return payload;
      } else if (order instanceof MarketOrder) {
        payload = BinanceWebsocketPlaceOrderPayload.builder().type(OrderType.MARKET).symbol(BinanceAdapters.toSymbol(order.getInstrument()))
            .side(BinanceAdapters.convert(order.getType()))
            .reduceOnly(reduceOnly)
            .quantity(order.getOriginalAmount())
            .timestamp(System.currentTimeMillis())
            .newClientOrderId(order.getUserReference()).build();
      }
      return payload;
    }
    return null;
  }

}
