package org.knowm.xchange.ftx.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.OpenPositions;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.ftx.FtxAdapters;
import org.knowm.xchange.ftx.dto.trade.CancelAllFtxOrdersParams;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.*;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

public class FtxTradeService extends FtxTradeServiceRaw implements TradeService {

  public FtxTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    return placeMarketOrderForSubaccount(
        exchange.getExchangeSpecification().getUserName(), marketOrder);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return placeLimitOrderForSubaccount(
        exchange.getExchangeSpecification().getUserName(), limitOrder);
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    return placeStopOrderForSubAccount(exchange.getExchangeSpecification().getUserName(), stopOrder);
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    return getTradeHistoryForSubaccount(exchange.getExchangeSpecification().getUserName(), params);
  }

  @Override
  public Collection<String> cancelAllOrders(CancelAllOrders orderParams) throws IOException {
    if(orderParams instanceof CancelAllFtxOrdersParams){
      cancelAllFtxOrders(exchange.getExchangeSpecification().getUserName(), (CancelAllFtxOrdersParams) orderParams);
      return Collections.singletonList("");
    } else {
      throw new IOException("Cancel all orders supports only "+ CancelAllFtxOrdersParams.class.getSimpleName());
    }
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    return cancelOrderForSubaccount(exchange.getExchangeSpecification().getUserName(), orderId);
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    return cancelOrderForSubaccount(exchange.getExchangeSpecification().getUserName(), orderParams);
  }

  @Override
  public Class[] getRequiredCancelOrderParamClasses() {
    return new Class[] {CancelOrderByCurrencyPair.class, CancelOrderByUserReferenceParams.class};
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    return getOrderFromSubaccount(exchange.getExchangeSpecification().getUserName(), orderIds);
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    return getOrderFromSubaccount(
        exchange.getExchangeSpecification().getUserName(),
        TradeService.toOrderIds(orderQueryParams));
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    return getOpenOrdersForSubaccount(exchange.getExchangeSpecification().getUserName(), params);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrdersForSubaccount(exchange.getExchangeSpecification().getUserName());
  }

  @Override
  public OpenPositions getOpenPositions() throws IOException {
    return getOpenPositionsForSubaccount(exchange.getExchangeSpecification().getUserName());
  }

  @Override
  public String changeOrder(LimitOrder limitOrder) throws IOException {
    if (limitOrder.getUserReference() != null) {
      return modifyFtxOrderByClientId(
              exchange.getExchangeSpecification().getUserName(),
              limitOrder.getId(),
              FtxAdapters.adaptModifyOrderToFtxOrderPayload(limitOrder))
          .getResult()
          .getClientId();
    } else {
      return modifyFtxOrder(
              exchange.getExchangeSpecification().getUserName(),
              limitOrder.getId(),
              FtxAdapters.adaptModifyOrderToFtxOrderPayload(limitOrder))
          .getResult()
          .getId();
    }
  }
}
