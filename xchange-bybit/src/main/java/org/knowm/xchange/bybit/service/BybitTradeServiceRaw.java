package org.knowm.xchange.bybit.service;

import static org.knowm.xchange.bybit.BybitAdapters.createBybitExceptionFromResult;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.BybitAdapters;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.trade.BybitExecType;
import org.knowm.xchange.bybit.dto.trade.BybitTradeHistoryResponse;
import org.knowm.xchange.bybit.dto.trade.BybitOrderResponse;
import org.knowm.xchange.bybit.dto.trade.BybitOrderType;
import org.knowm.xchange.bybit.dto.trade.BybitSide;
import org.knowm.xchange.bybit.dto.trade.details.BybitOrderDetail;
import org.knowm.xchange.bybit.dto.trade.details.BybitOrderDetails;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.instrument.Instrument;

public class BybitTradeServiceRaw extends BybitBaseService {

  public BybitTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BybitResult<BybitOrderDetails<BybitOrderDetail>> getBybitOrder(
      BybitCategory category, String orderId) throws IOException {
    BybitResult<BybitOrderDetails<BybitOrderDetail>> order =
        bybitAuthenticated.getOpenOrders(
            apiKey, signatureCreator, nonceFactory, category.getValue(), orderId);
    if (!order.isSuccess()) {
      throw createBybitExceptionFromResult(order);
    }
    return order;
  }

  public BybitResult<BybitOrderResponse> placeOrder(
      BybitCategory category,
      String symbol,
      BybitSide side,
      BybitOrderType orderType,
      BigDecimal qty)
      throws IOException {
    BybitResult<BybitOrderResponse> placeOrder =
        bybitAuthenticated.placeOrder(
            apiKey,
            signatureCreator,
            nonceFactory,
            category.getValue(),
            symbol,
            side.getValue(),
            orderType.getValue(),
            qty);
    if (!placeOrder.isSuccess()) {
      throw createBybitExceptionFromResult(placeOrder);
    }
    return placeOrder;
  }

  public BybitResult<BybitTradeHistoryResponse> getBybitTradeHistory(
      BybitCategory category,
      Instrument instrument,
      String orderId,
      String userReferenceId,
      Currency baseCoin,
      Date startTime,
      Date endTime,
      BybitExecType execType,
      Integer limit,
      String cursor)
      throws BybitException, IOException {
    BybitResult<BybitTradeHistoryResponse> userTrades =
        bybitAuthenticated.getBybitTradeHistory(
            apiKey,
            signatureCreator,
            nonceFactory,
            category.getValue(),
            BybitAdapters.adaptBybitSymbol(instrument),
            orderId,
            userReferenceId,
            baseCoin == null ? null : baseCoin.getCurrencyCode(),
            startTime == null ? null : startTime.toInstant().toEpochMilli(),
            endTime == null ? null : endTime.toInstant().toEpochMilli(),
            execType == null ? null : execType.getValue(),
            limit,
            cursor);
    if (!userTrades.isSuccess()) {
      throw createBybitExceptionFromResult(userTrades);
    }
    return userTrades;
  }
}
