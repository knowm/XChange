package org.knowm.xchange.deribit.v2.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.deribit.v2.DeribitExchange;
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
      Boolean reduceOnly,
      BigDecimal stopPrice,
      Trigger trigger,
      AdvancedOptions advanced)
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
            reduceOnly,
            stopPrice,
            trigger,
            advanced,
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
      Boolean reduceOnly,
      BigDecimal stopPrice,
      Trigger trigger,
      AdvancedOptions advanced)
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
            reduceOnly,
            stopPrice,
            trigger,
            advanced,
            deribitAuth)
        .getResult();
  }

  public OrderPlacement edit(String orderID, BigDecimal amount, BigDecimal price)
      throws IOException {
    return deribitAuthenticated.edit(orderID, amount, price, deribitAuth).getResult();
  }

  public Order cancel(String orderId) throws IOException {
    return deribitAuthenticated.cancel(orderId, deribitAuth).getResult();
  }

  public List<Order> getOpenOrdersByInstrument(String instrumentName, String type)
      throws IOException {
    return deribitAuthenticated
        .getOpenOrdersByInstrument(instrumentName, type, deribitAuth)
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
      Date startTimestsamp,
      Date endTimestamp,
      Integer count,
      Boolean includeOld,
      String sorting)
      throws IOException {
    return deribitAuthenticated
        .getUserTradesByInstrumentAndTime(
            instrumentName,
            startTimestsamp.getTime(),
            endTimestamp.getTime(),
            count,
            includeOld,
            sorting,
            deribitAuth)
        .getResult();
  }

  public UserSettlements getUserSettlementsByInstrument(
      String instrumentName, SettlementType type, Integer count, String continuation) throws IOException {
    return deribitAuthenticated
        .getSettlementHistoryByInstrument(instrumentName, type, count, continuation, deribitAuth)
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
}
