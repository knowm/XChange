package org.knowm.xchange.bx.service;

import java.io.IOException;
import java.util.*;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bx.BxAdapters;
import org.knowm.xchange.bx.BxUtils;
import org.knowm.xchange.bx.dto.trade.BxOrder;
import org.knowm.xchange.bx.dto.trade.BxTradeHistory;
import org.knowm.xchange.bx.dto.trade.results.BxCancelOrderResult;
import org.knowm.xchange.bx.dto.trade.results.BxCreateOrderResult;
import org.knowm.xchange.bx.dto.trade.results.BxOrdersResult;
import org.knowm.xchange.bx.dto.trade.results.BxTradeHistoryResult;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

public class BxTradeServiceRaw extends BxBaseService {

  public BxTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public boolean cancelBxOrder(String orderId) throws IOException {
    BxCancelOrderResult result =
        bx.cancelOrder(
            null,
            orderId,
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory(),
            signatureCreator);
    checkResult(result);
    return result.isSuccess();
  }

  public Map<String, List<BxTradeHistory>> getBxTradeHistory(TradeHistoryParams tradeHistoryParams)
      throws IOException {
    String startDate = null;
    String endDate = null;
    if (tradeHistoryParams != null) {
      if (tradeHistoryParams instanceof BxTradeHistoryParams) {
        startDate = ((BxTradeHistoryParams) tradeHistoryParams).getStartDate();
        endDate = ((BxTradeHistoryParams) tradeHistoryParams).getEndDate();
      } else {
        throw new ExchangeException(
            "Unsupported class of params: " + tradeHistoryParams.getClass().getName());
      }
    }
    BxTradeHistoryResult result =
        bx.getTradeHistory(
            null,
            null,
            startDate,
            endDate,
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory(),
            signatureCreator);
    return BxUtils.prepareHistory(checkResult(result));
  }

  public BxOrder[] getBxOrders() throws IOException {
    BxOrdersResult result =
        bx.getOrders(
            null,
            null,
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory(),
            signatureCreator);
    return checkResult(result);
  }

  public Collection<Order> getBxOrder(String... orderIds) throws IOException {
    List<Order> orders = new ArrayList<>();
    BxOrder[] bxOrders = getBxOrders();
    for (BxOrder order : bxOrders) {
      for (String orderId : orderIds) {
        if (order.getOrderId().equals(orderId)) {
          orders.add(BxAdapters.adaptOrder(order));
        }
      }
    }
    return orders;
  }

  public String placeBxLimitOrder(LimitOrder limitOrder) throws IOException {
    BxCreateOrderResult result =
        bx.createOrder(
            BxUtils.createBxCurrencyPair(limitOrder.getCurrencyPair()),
            BxAdapters.adaptOrderType(limitOrder.getType()),
            limitOrder.getOriginalAmount().toString(),
            limitOrder.getLimitPrice().toString(),
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory(),
            signatureCreator);
    return checkResult(result);
  }
}
