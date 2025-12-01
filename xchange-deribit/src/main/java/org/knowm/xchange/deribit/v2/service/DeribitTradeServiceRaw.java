package org.knowm.xchange.deribit.v2.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.deribit.v2.DeribitExchange;
import org.knowm.xchange.deribit.v2.dto.Kind;
import org.knowm.xchange.deribit.v2.dto.account.DeribitPosition;
import org.knowm.xchange.deribit.v2.dto.trade.AdvancedOptions;
import org.knowm.xchange.deribit.v2.dto.trade.DeribitOrder;
import org.knowm.xchange.deribit.v2.dto.trade.DeribitUserTrades;
import org.knowm.xchange.deribit.v2.dto.trade.OrderPlacement;
import org.knowm.xchange.deribit.v2.dto.trade.OrderType;
import org.knowm.xchange.deribit.v2.dto.trade.SettlementType;
import org.knowm.xchange.deribit.v2.dto.trade.TimeInForce;
import org.knowm.xchange.deribit.v2.dto.trade.Trigger;
import org.knowm.xchange.deribit.v2.dto.trade.UserSettlements;

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
            deribitDigest)
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
            deribitDigest)
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
            deribitDigest)
        .getResult();
  }

  public DeribitOrder cancel(String orderId) throws IOException {
    return deribitAuthenticated.cancel(orderId, deribitDigest).getResult();
  }

  public Integer cancelByLabel(String label) throws IOException {
    return deribitAuthenticated.cancelByLabel(label, deribitDigest).getResult();
  }

  public Integer cancelAll() throws IOException {
    return deribitAuthenticated.cancelAll(deribitDigest).getResult();
  }

  public List<DeribitOrder> getOpenOrdersByCurrency(String currency, Kind kind, String type)
      throws IOException {
    return deribitAuthenticated
        .getOpenOrdersByCurrency(currency, kind, type, deribitDigest)
        .getResult();
  }

  public List<DeribitOrder> getOpenOrdersByInstrument(String instrumentName, String type)
      throws IOException {
    return deribitAuthenticated
        .getOpenOrdersByInstrument(instrumentName, type, deribitDigest)
        .getResult();
  }

  public DeribitUserTrades getUserTradesByCurrency(
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
            currency, kind, startId, endId, count, includeOld, sorting, deribitDigest)
        .getResult();
  }

  public DeribitUserTrades getUserTradesByCurrencyAndTime(
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
            deribitDigest)
        .getResult();
  }

  public DeribitUserTrades getUserTradesByInstrument(
      String instrumentName,
      Integer startSeq,
      Integer endSeq,
      Integer count,
      Boolean includeOld,
      String sorting)
      throws IOException {
    return deribitAuthenticated
        .getUserTradesByInstrument(
            instrumentName, startSeq, endSeq, count, includeOld, sorting, deribitDigest)
        .getResult();
  }

  public DeribitUserTrades getUserTradesByInstrumentAndTime(
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
            deribitDigest)
        .getResult();
  }

  public UserSettlements getUserSettlementsByInstrument(
      String instrumentName, SettlementType type, Integer count, String continuation)
      throws IOException {
    return deribitAuthenticated
        .getSettlementHistoryByInstrument(instrumentName, type, count, continuation, deribitDigest)
        .getResult();
  }

  public List<DeribitOrder> getOrderHistoryByCurrency(
      String currency,
      Kind kind,
      Integer count,
      Integer offset,
      Boolean includeOld,
      Boolean includeUnfilled)
      throws IOException {
    return deribitAuthenticated
        .getOrderHistoryByCurrency(
            currency, kind, count, offset, includeOld, includeUnfilled, deribitDigest)
        .getResult();
  }

  public List<DeribitOrder> getOrderHistoryByInstrument(
      String instrumentName,
      Integer count,
      Integer offset,
      Boolean includeOld,
      Boolean includeUnfilled)
      throws IOException {
    return deribitAuthenticated
        .getOrderHistoryByInstrument(
            instrumentName, count, offset, includeOld, includeUnfilled, deribitDigest)
        .getResult();
  }

  public DeribitOrder getOrderState(String orderId) throws IOException {
    return deribitAuthenticated.getOrderState(orderId, deribitDigest).getResult();
  }

  public List<DeribitPosition> getPositions(String currency, Kind kind) throws IOException {
    return deribitAuthenticated.getPositions(currency, kind, deribitDigest).getResult();
  }

}
