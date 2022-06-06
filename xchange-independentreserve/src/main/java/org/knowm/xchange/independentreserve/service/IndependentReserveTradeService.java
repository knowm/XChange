package org.knowm.xchange.independentreserve.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.independentreserve.IndependentReserveAdapters;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveOrderDetailsResponse;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

public class IndependentReserveTradeService extends IndependentReserveTradeServiceRaw
    implements TradeService {

  public IndependentReserveTradeService(Exchange exchange) {
    super(exchange);
  }

  /** Assumes asking for the first 50 orders with the currency pair BTCUSD + ETHUSD */
  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    // null: get orders for all currencies
    String primaryCurrency = null;
    String secondaryCurrency = null;
    if (params instanceof OpenOrdersParamCurrencyPair) {
      final CurrencyPair cp = ((OpenOrdersParamCurrencyPair) params).getCurrencyPair();
      if (cp != null) {
        primaryCurrency = cp.base.getCurrencyCode();
        secondaryCurrency = cp.counter.getCurrencyCode();
      }
    }
    return IndependentReserveAdapters.adaptOpenOrders(
        getIndependentReserveOpenOrders(primaryCurrency, secondaryCurrency, 1));
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return independentReservePlaceLimitOrder(
        limitOrder.getCurrencyPair(),
        limitOrder.getType(),
        limitOrder.getLimitPrice(),
        limitOrder.getOriginalAmount());
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    return independentReservePlaceMarketOrder(
        marketOrder.getInstrument(), marketOrder.getType(), marketOrder.getOriginalAmount());
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    return independentReserveCancelOrder(orderId);
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else {
      return false;
    }
  }

  /** Optional parameters: {@link TradeHistoryParamPaging#getPageNumber()} indexed from 0 */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    int pageNumber = ((TradeHistoryParamPaging) params).getPageNumber() + 1;
    return IndependentReserveAdapters.adaptTradeHistory(
        getIndependentReserveTradeHistory(pageNumber));
  }

  @Override
  public TradeHistoryParamPaging createTradeHistoryParams() {
    return new DefaultTradeHistoryParamPaging(null, 0);
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    List<Order> res = new ArrayList<>(orderQueryParams.length);
    for (OrderQueryParams orderQueryParam : orderQueryParams) {
      IndependentReserveOrderDetailsResponse orderDetailsResponse =
          getOrderDetails(orderQueryParam.getOrderId());
      res.add(IndependentReserveAdapters.adaptOrderDetails(orderDetailsResponse));
    }
    return res;
  }
}
