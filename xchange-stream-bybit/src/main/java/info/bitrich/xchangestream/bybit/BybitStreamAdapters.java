package info.bitrich.xchangestream.bybit;

import static org.knowm.xchange.bybit.BybitAdapters.getOrderType;

import dto.marketdata.BybitPublicOrder;
import dto.trade.BybitTrade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import dto.marketdata.BybitOrderbook;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;

public class BybitStreamAdapters {
  public static OrderBook adaptOrderBook(BybitOrderbook bybitOrderbooks, Instrument instrument) {
    List<LimitOrder> asks = new ArrayList<>();
    List<LimitOrder> bids = new ArrayList<>();
    Date timestamp = new Date(Long.parseLong(bybitOrderbooks.getTs()));
    bybitOrderbooks.getData().getAsk()
        .forEach(bybitAsk -> asks.add(
            adaptOrderBookOrder(bybitAsk, instrument, OrderType.ASK,timestamp)));

    bybitOrderbooks.getData().getBid()
        .forEach(bybitBid -> bids.add(
            adaptOrderBookOrder(bybitBid, instrument, OrderType.BID,timestamp)));

    return new OrderBook(timestamp,asks, bids);
  }

  public static Trades adaptTrades(List<BybitTrade> bybitTrades, Instrument instrument) {
    List<Trade> trades = new ArrayList<>();

    bybitTrades.forEach(
        bybitTrade ->
            trades.add(
                new Trade.Builder()
                    .id(bybitTrade.getTradeId())
                    .instrument(instrument)
                    .originalAmount(bybitTrade.getTradeSize())
                    .price(bybitTrade.getTradePrice())
                    .timestamp(bybitTrade.getTimestamp())
                    .type(getOrderType(bybitTrade.getSide()))
                    .build()));

    return new Trades(trades);
  }

  public static LimitOrder adaptOrderBookOrder(BybitPublicOrder bybitPublicOrder,
      Instrument instrument, Order.OrderType orderType, Date timestamp) {

    return new LimitOrder(orderType, new BigDecimal(bybitPublicOrder.getSize()), instrument, "",
        timestamp, new BigDecimal(bybitPublicOrder.getPrice()));
  }


}
