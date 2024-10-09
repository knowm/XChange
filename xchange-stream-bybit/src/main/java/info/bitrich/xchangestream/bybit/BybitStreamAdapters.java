package info.bitrich.xchangestream.bybit;

import static org.knowm.xchange.bybit.BybitAdapters.adaptBybitOrderStatus;

import static org.knowm.xchange.bybit.BybitAdapters.getOrderType;
import static org.knowm.xchange.bybit.BybitAdapters.guessSymbol;

import dto.marketdata.BybitPublicOrder;
import dto.trade.BybitComplexOrderChanges;
import dto.trade.BybitComplexOrderChanges.TimeInForce;
import dto.trade.BybitComplexPositionChanges;
import dto.trade.BybitOrderChangesResponse;
import dto.trade.BybitOrderChangesResponse.BybitOrderChanges;
import dto.trade.BybitPositionChangesResponse.BybitPositionChanges;
import dto.trade.BybitTrade;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import dto.marketdata.BybitOrderbook;
import org.knowm.xchange.bybit.dto.trade.BybitSide;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.account.OpenPosition.Type;
import org.knowm.xchange.dto.account.OpenPositions;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trade.Builder;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.instrument.Instrument;

public class BybitStreamAdapters {

  public static OrderBook adaptOrderBook(BybitOrderbook bybitOrderbooks, Instrument instrument) {
    List<LimitOrder> asks = new ArrayList<>();
    List<LimitOrder> bids = new ArrayList<>();
    Date timestamp = new Date(Long.parseLong(bybitOrderbooks.getTs()));
    bybitOrderbooks.getData().getAsk()
        .forEach(bybitAsk -> asks.add(
            adaptOrderBookOrder(bybitAsk, instrument, OrderType.ASK, timestamp)));

    bybitOrderbooks.getData().getBid()
        .forEach(bybitBid -> bids.add(
            adaptOrderBookOrder(bybitBid, instrument, OrderType.BID, timestamp)));

    return new OrderBook(timestamp, asks, bids);
  }

  public static Trades adaptTrades(List<BybitTrade> bybitTrades, Instrument instrument) {
    List<Trade> trades = new ArrayList<>();

    bybitTrades.forEach(
        bybitTrade ->
            trades.add(
                new Builder()
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
      Instrument instrument, OrderType orderType, Date timestamp) {

    return new LimitOrder(orderType, new BigDecimal(bybitPublicOrder.getSize()), instrument, "",
        timestamp, new BigDecimal(bybitPublicOrder.getPrice()));
  }

  public static List<Order> adaptOrdersChanges(List<BybitOrderChanges> bybitOrderChanges) {
    Date date = new Date();
    List<Order> orders = new ArrayList<>();
    for (BybitOrderChanges bybitOrderChange : bybitOrderChanges) {
      date.setTime(Long.parseLong(bybitOrderChange.getUpdatedTime()));
      Order.OrderType orderType = getOrderType(bybitOrderChange.getSide());
      Order.Builder builder = null;
      switch (bybitOrderChange.getOrderType()) {
        case LIMIT:
          builder = new LimitOrder.Builder(orderType, guessSymbol(bybitOrderChange.getSymbol(),
              bybitOrderChange.getCategory())).limitPrice(
              new BigDecimal(bybitOrderChange.getPrice()));
          break;
        case MARKET:
          builder = new MarketOrder.Builder(orderType, guessSymbol(bybitOrderChange.getSymbol()));
          break;
      }
      if (!bybitOrderChange.getAvgPrice().isEmpty()) {
        builder.averagePrice(new BigDecimal(bybitOrderChange.getAvgPrice()));
      }
      builder.fee(new BigDecimal(bybitOrderChange.getCumExecFee())).
          leverage(bybitOrderChange.getIsLeverage()).id(bybitOrderChange.getOrderId())
          .orderStatus(adaptBybitOrderStatus(bybitOrderChange.getOrderStatus()))
          .timestamp(date).cumulativeAmount(new BigDecimal(bybitOrderChange.getCumExecQty()))
          .originalAmount(new BigDecimal(bybitOrderChange.getQty()))
          .id(bybitOrderChange.getOrderId())
          .userReference(bybitOrderChange.getOrderLinkId());
      orders.add(builder.build());
    }
    return orders;
  }

  public static OpenPositions adaptPositionChanges(
      List<BybitPositionChanges> bybitPositionChanges) {
    OpenPositions openPositions = new OpenPositions(new ArrayList<>());
    for (BybitPositionChanges position : bybitPositionChanges) {
      OpenPosition.Type type = null;
      if (!position.getSide().isEmpty()) {
        type = position.getSide().equals("Buy") ? Type.LONG : Type.SHORT;
      }
      BigDecimal liqPrice = null;
      if (!position.getLiqPrice().isEmpty()) {
        liqPrice = new BigDecimal(position.getLiqPrice());
      }
      OpenPosition openPosition = new OpenPosition(guessSymbol(position.getSymbol(),
          position.getCategory()), type, new BigDecimal(position.getSize()),
          new BigDecimal(position.getEntryPrice()), liqPrice,
          new BigDecimal(position.getUnrealisedPnl()));
      openPositions.getOpenPositions().add(openPosition);
    }
    return openPositions;
  }

  public static List<BybitComplexPositionChanges> adaptComplexPositionChanges(
      List<BybitPositionChanges> data) {
    List<BybitComplexPositionChanges> result = new ArrayList<>();
    for (BybitPositionChanges position : data) {
      OpenPosition.Type type = null;
      if (!position.getSide().isEmpty()) {
        type = position.getSide().equals("Buy") ? Type.LONG : Type.SHORT;
      }
      BigDecimal liqPrice = null;
      if (!position.getLiqPrice().isEmpty()) {
        liqPrice = new BigDecimal(position.getLiqPrice());
      }
      BigDecimal bustPrice = null;
      if(!position.getBustPrice().isEmpty()) {
        bustPrice = new BigDecimal(position.getBustPrice());
      }
      BigDecimal sessionAvgPrice = null;
      if (!position.getSessionAvgPrice().isEmpty()) {
        sessionAvgPrice = new BigDecimal(position.getSessionAvgPrice());
      }
      BybitComplexPositionChanges positionChanges = new BybitComplexPositionChanges(
          guessSymbol(position.getSymbol(),
              position.getCategory()), type, new BigDecimal(position.getSize()),
          new BigDecimal(position.getEntryPrice()), liqPrice,
          new BigDecimal(position.getUnrealisedPnl()), position.getPositionIdx(),
          position.getTradeMode(), position.getRiskId(), position.getRiskLimitValue(),
          new BigDecimal(position.getMarkPrice()),
          new BigDecimal(position.getPositionBalance()), position.getAutoAddMargin(),
          new BigDecimal(position.getPositionMM()),
          new BigDecimal(position.getPositionIM()),
          bustPrice, new BigDecimal(position.getPositionValue()),
          new BigDecimal(position.getLeverage()),
          new BigDecimal(position.getTakeProfit()), new BigDecimal(position.getStopLoss()),
          new BigDecimal(position.getTrailingStop()),
          new BigDecimal(position.getCurRealisedPnl()),
          sessionAvgPrice, position.getPositionStatus(),
          position.getAdlRankIndicator(), position.isReduceOnly(),
          position.getMmrSysUpdatedTime(), position.getLeverageSysUpdatedTime(),
          Long.parseLong(position.getCreatedTime()), Long.parseLong(position.getUpdatedTime()),
          position.getSeq());
      result.add(positionChanges);
    }
    return result;
  }

  public static List<BybitComplexOrderChanges> adaptComplexOrdersChanges(
      List<BybitOrderChanges> data) {
    List<BybitComplexOrderChanges> result = new ArrayList<>();
    for (BybitOrderChanges change : data) {
      Order.OrderType orderType = getOrderType(change.getSide());
      BigDecimal avgPrice =
          change.getAvgPrice().isEmpty() ? null : new BigDecimal(change.getAvgPrice());
      BybitComplexOrderChanges orderChanges = new BybitComplexOrderChanges(orderType,
          new BigDecimal(change.getQty()), guessSymbol(change.getSymbol(), change.getCategory()),
          change.getOrderId(),
          new Date(Long.parseLong(change.getCreatedTime())), avgPrice,
          new BigDecimal(change.getCumExecQty()), new BigDecimal(change.getCumExecFee()),
          adaptBybitOrderStatus(change.getOrderStatus()), change.getOrderLinkId(),
          change.getCategory(), new BigDecimal(change.getPrice()), change.getSide(),
          new BigDecimal(change.getLeavesQty()), new BigDecimal(change.getLeavesValue()),
          new BigDecimal(change.getCumExecValue()), change.getFeeCurrency(),
          TimeInForce.valueOf(change.getTimeInForce().toUpperCase()), change.getOrderType(),
          change.isReduceOnly(), new Date(Long.parseLong(change.getUpdatedTime())));
      result.add(orderChanges);
    }
    return result;
  }

}
