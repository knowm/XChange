package org.knowm.xchange.deribit.v2.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.deribit.v2.DeribitExchange;
import org.knowm.xchange.deribit.v2.dto.Kind;
import org.knowm.xchange.deribit.v2.dto.trade.AdvancedOptions;
import org.knowm.xchange.deribit.v2.dto.trade.Order;
import org.knowm.xchange.deribit.v2.dto.trade.OrderPlacement;
import org.knowm.xchange.deribit.v2.dto.trade.OrderType;
import org.knowm.xchange.deribit.v2.dto.trade.SettlementType;
import org.knowm.xchange.deribit.v2.dto.trade.TimeInForce;
import org.knowm.xchange.deribit.v2.dto.trade.Trigger;
import org.knowm.xchange.deribit.v2.dto.trade.UserSettlements;
import org.knowm.xchange.deribit.v2.dto.trade.UserTrades;

public class DeribitTradeServiceRaw extends DeribitBaseService {

  public DeribitTradeServiceRaw(DeribitExchange exchange) {
    super(exchange);
  }

  public OrderPlacement buy(
      String instrumentName,
      BigDecimal amount,
      OrderType type,
      String label,
      BigDecimal price,
      TimeInForce timeInForce,
      BigDecimal maxShow,
      Boolean postOnly,
      Boolean rejectPostOnly,
      Boolean reduceOnly,
      BigDecimal triggerPrice,
      Trigger trigger,
      AdvancedOptions advanced,
      Boolean mmp)
      throws IOException {
    return deribitAuthenticated
        .buy(
            instrumentName,
            amount,
            type,
            label,
            price,
            timeInForce,
            maxShow,
            postOnly,
            rejectPostOnly,
            reduceOnly,
            triggerPrice,
            trigger,
            advanced,
            mmp,
            deribitAuth)
        .getResult();
  }

  public OrderPlacement sell(
      String instrumentName,
      BigDecimal amount,
      OrderType type,
      String label,
      BigDecimal price,
      TimeInForce timeInForce,
      BigDecimal maxShow,
      Boolean postOnly,
      Boolean rejectPostOnly,
      Boolean reduceOnly,
      BigDecimal triggerPrice,
      Trigger trigger,
      AdvancedOptions advanced,
      Boolean mmp)
      throws IOException {
    return deribitAuthenticated
        .sell(
            instrumentName,
            amount,
            type,
            label,
            price,
            timeInForce,
            maxShow,
            postOnly,
            rejectPostOnly,
            reduceOnly,
            triggerPrice,
            trigger,
            advanced,
            mmp,
            deribitAuth)
        .getResult();
  }

  public OrderPlacement edit(
      String orderId,
      BigDecimal amount,
      BigDecimal price,
      Boolean postOnly,
      Boolean rejectPostOnly,
      Boolean reduceOnly,
      BigDecimal triggerPrice,
      AdvancedOptions advanced,
      Boolean mmp)
      throws IOException {
    return deribitAuthenticated
        .edit(
            orderId,
            amount,
            price,
            postOnly,
            rejectPostOnly,
            reduceOnly,
            triggerPrice,
            advanced,
            mmp,
            deribitAuth)
        .getResult();
  }

  public Order cancel(String orderId) throws IOException {
    return deribitAuthenticated.cancel(orderId, deribitAuth).getResult();
  }

  public Integer cancelByLabel(String label) throws IOException {
    return deribitAuthenticated.cancelByLabel(label, deribitAuth).getResult();
  }

  public List<Order> getOpenOrdersByCurrency(String currency, Kind kind, String type)
      throws IOException {
    return deribitAuthenticated
        .getOpenOrdersByCurrency(currency, kind, type, deribitAuth)
        .getResult();
  }

  public List<Order> getOpenOrdersByInstrument(String instrumentName, String type)
      throws IOException {
    return deribitAuthenticated
        .getOpenOrdersByInstrument(instrumentName, type, deribitAuth)
        .getResult();
  }

  public UserTrades getUserTradesByCurrency(
      String currency,
      Kind kind,
      String startId,
      String endId,
      Integer count,
      Boolean includeOld,
      String sorting)
      throws IOException {
    return deribitAuthenticated
        .getUserTradesByCurrency(
            currency, kind, startId, endId, count, includeOld, sorting, deribitAuth)
        .getResult();
  }

  public UserTrades getUserTradesByCurrencyAndTime(
      String currency,
      Kind kind,
      Date startTimestamp,
      Date endTimestamp,
      Integer count,
      Boolean includeOld,
      String sorting)
      throws IOException {
    return deribitAuthenticated
        .getUserTradesByCurrencyAndTime(
            currency,
            kind,
            startTimestamp.getTime(),
            endTimestamp.getTime(),
            count,
            includeOld,
            sorting,
            deribitAuth)
        .getResult();
  }

  public UserTrades getUserTradesByInstrument(
      String instrumentName,
      Integer startSeq,
      Integer endSeq,
      Integer count,
      Boolean includeOld,
      String sorting)
      throws IOException {
    return deribitAuthenticated
        .getUserTradesByInstrument(
            instrumentName, startSeq, endSeq, count, includeOld, sorting, deribitAuth)
        .getResult();
  }

  public UserTrades getUserTradesByInstrumentAndTime(
      String instrumentName,
      Date startTimestamp,
      Date endTimestamp,
      Integer count,
      Boolean includeOld,
      String sorting)
      throws IOException {
    return deribitAuthenticated
        .getUserTradesByInstrumentAndTime(
            instrumentName,
            startTimestamp.getTime(),
            endTimestamp.getTime(),
            count,
            includeOld,
            sorting,
            deribitAuth)
        .getResult();
  }

  public UserSettlements getUserSettlementsByInstrument(
      String instrumentName, SettlementType type, Integer count, String continuation)
      throws IOException {
    return deribitAuthenticated
        .getSettlementHistoryByInstrument(instrumentName, type, count, continuation, deribitAuth)
        .getResult();
  }

  public List<Order> getOrderHistoryByCurrency(
      String currency,
      Kind kind,
      Integer count,
      Integer offset,
      Boolean includeOld,
      Boolean includeUnfilled)
      throws IOException {
    return deribitAuthenticated
        .getOrderHistoryByCurrency(
            currency, kind, count, offset, includeOld, includeUnfilled, deribitAuth)
        .getResult();
  }

  public List<Order> getOrderHistoryByInstrument(
      String instrumentName,
      Integer count,
      Integer offset,
      Boolean includeOld,
      Boolean includeUnfilled)
      throws IOException {
    return deribitAuthenticated
        .getOrderHistoryByInstrument(
            instrumentName, count, offset, includeOld, includeUnfilled, deribitAuth)
        .getResult();
  }

  public Order getOrderState(String orderId) throws IOException {
    return deribitAuthenticated.getOrderState(orderId, deribitAuth).getResult();
  }
}
