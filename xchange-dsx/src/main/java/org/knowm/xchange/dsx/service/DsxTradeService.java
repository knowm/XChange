package org.knowm.xchange.dsx.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dsx.DsxAdapters;
import org.knowm.xchange.dsx.dto.DsxOrder;
import org.knowm.xchange.dsx.dto.DsxOwnTrade;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByUserReferenceParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

public class DsxTradeService extends DsxTradeServiceRaw implements TradeService {

  public DsxTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    List<DsxOrder> openOrdersRaw = getOpenOrdersRaw();
    return DsxAdapters.adaptOpenOrders(openOrdersRaw);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    return placeMarketOrderRaw(marketOrder).id;
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return placeLimitOrderRaw(limitOrder).id;
  }

  /**
   * @param orderParams - {@link CancelOrderParams} of type {@link CancelOrderByUserReferenceParams}
   */
  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByUserReferenceParams) {
      String clientOrderId = ((CancelOrderByUserReferenceParams) orderParams).getUserReference();
      DsxOrder cancelOrderRaw = cancelOrderRaw(clientOrderId);
      return "canceled".equals(cancelOrderRaw.status);
    } else {
      throw new ExchangeException(
          "Need userReference for cancelling orders. Use CancelOrderByUserReferenceParams.");
    }
  }

  @Override
  public Class[] getRequiredCancelOrderParamClasses() {
    return new Class[] {CancelOrderByUserReferenceParams.class};
  }

  /** Required parameters: {@link TradeHistoryParamPaging} {@link TradeHistoryParamCurrencyPair} */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    Integer limit = 1000;
    long offset = 0;

    if (params instanceof TradeHistoryParamLimit) {
      limit = ((TradeHistoryParamLimit) params).getLimit();
    }

    if (params instanceof TradeHistoryParamOffset) {
      TradeHistoryParamOffset tradeHistoryParamOffset = (TradeHistoryParamOffset) params;
      offset = tradeHistoryParamOffset.getOffset();
    }

    String symbol = null;
    if (params instanceof TradeHistoryParamCurrencyPair) {
      CurrencyPair pair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
      symbol = DsxAdapters.adaptCurrencyPair(pair);
    }

    List<DsxOwnTrade> tradeHistoryRaw = getTradeHistoryRaw(symbol, limit, offset);
    return DsxAdapters.adaptTradeHistory(tradeHistoryRaw);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new DsxTradeHistoryParams(null, 100, 0L);
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  @Override
  public Class getRequiredOrderQueryParamClass() {
    return OrderQueryParamCurrencyPair.class;
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    if (orderQueryParams == null) {
      return new ArrayList<>();
    }

    Collection<Order> orders = new ArrayList<>();
    for (OrderQueryParams param : orderQueryParams) {
      if (!(param instanceof OrderQueryParamCurrencyPair)) {
        throw new ExchangeException(
            "Parameters must be an instance of OrderQueryParamCurrencyPair");
      }
      DsxOrder rawOrder =
          getDsxOrder(
              DsxAdapters.adaptCurrencyPair(
                  ((OrderQueryParamCurrencyPair) param).getCurrencyPair()),
              param.getOrderId());

      if (rawOrder != null) orders.add(DsxAdapters.adaptOrder(rawOrder));
    }

    return orders;
  }
}
