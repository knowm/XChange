package org.knowm.xchange.latoken.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.IOrderFlags;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.latoken.LatokenAdapters;
import org.knowm.xchange.latoken.LatokenErrorAdapter;
import org.knowm.xchange.latoken.dto.LatokenException;
import org.knowm.xchange.latoken.dto.trade.LatokenCancelledOrders;
import org.knowm.xchange.latoken.dto.trade.LatokenNewOrder;
import org.knowm.xchange.latoken.dto.trade.LatokenOrder;
import org.knowm.xchange.latoken.dto.trade.LatokenOrderStatus;
import org.knowm.xchange.latoken.dto.trade.LatokenTestOrder;
import org.knowm.xchange.latoken.dto.trade.LatokenUserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

public class LatokenTradeService extends LatokenTradeServiceRaw implements TradeService {

  public LatokenTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {

    try {
      if (params instanceof OpenOrdersParamCurrencyPair == false) {
        throw new ExchangeException("CurrencyPair is must be provided to get open orders.");
      }

      OpenOrdersParamCurrencyPair pairParams = (OpenOrdersParamCurrencyPair) params;
      CurrencyPair pair = pairParams.getCurrencyPair();
      if (pair == null) {
        throw new ExchangeException("CurrencyPair is must be provided to get open orders.");
      }

      List<LatokenOrder> latokenOpenOrders = getLatokenOpenOrders(pair, Integer.MAX_VALUE);
      return LatokenAdapters.adaptOpenOrders(this.exchange, latokenOpenOrders);

    } catch (LatokenException e) {
      throw LatokenErrorAdapter.adapt(e);
    }
  }

  @Override
  public String placeLimitOrder(LimitOrder order) throws IOException {

    LatokenNewOrder newOrder =
        placeLatokenNewOrder(
            order.getCurrencyPair(),
            getClientOrderId(order), // extract clientOrderId from flags, if given
            LatokenAdapters.toOrderSide(order.getType()),
            order.getLimitPrice(),
            order.getOriginalAmount());

    return newOrder.getOrderId();
  }

  /**
   * Tests order placement.
   *
   * @param order
   * @return {@code true} if test was successful
   * @throws IOException
   */
  public boolean placeTestOrder(LimitOrder order) throws IOException {

    LatokenTestOrder testOrder =
        placeLatokenTestOrder(
            order.getCurrencyPair(),
            getClientOrderId(order), // extract clientOrderId from flags, if given
            LatokenAdapters.toOrderSide(order.getType()),
            order.getLimitPrice(),
            order.getOriginalAmount());

    return testOrder.isSuccess();
  }

  /**
   * Extracts the {@code clientOrderId} from the order's {@link IOrderFlags flags}
   *
   * @param order
   * @return
   * @see LatokenOrderFlags
   */
  private String getClientOrderId(LimitOrder order) {

    String clientOrderId = null;
    for (IOrderFlags flags : order.getOrderFlags()) {
      if (flags instanceof LatokenOrderFlags) {
        LatokenOrderFlags of = (LatokenOrderFlags) flags;
        if (clientOrderId == null) {
          clientOrderId = of.getClientId();
        }
      }
    }
    return clientOrderId;
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    try {
      LatokenOrder canceledOrder = cancelLatokenOrder(orderId);
      return canceledOrder.getOrderStatus() != LatokenOrderStatus.active;

    } catch (LatokenException e) {
      throw LatokenErrorAdapter.adapt(e);
    }
  }

  @Override
  public boolean cancelOrder(CancelOrderParams params) throws IOException {

    if (params instanceof CancelOrderByIdParams == false) {
      throw new ExchangeException("OrderId must be provided to cancel an order.");
    }

    CancelOrderByIdParams paramId = (CancelOrderByIdParams) params;
    String orderId = paramId.getOrderId();
    if (orderId == null) {
      throw new ExchangeException("OrderId must be provided to cancel an order.");
    }

    return cancelOrder(orderId);
  }

  /**
   * Cancels all orders of a given {@link CurrencyPair}.
   *
   * @param pair
   * @return list of orderIds that were cancelled.
   * @throws IOException
   */
  public List<String> cancelOrders(CurrencyPair pair) throws IOException {

    try {
      LatokenCancelledOrders canceledOrders = cancelLatokenOrders(pair);
      return canceledOrders.getCancelledOrders();

    } catch (LatokenException e) {
      throw LatokenErrorAdapter.adapt(e);
    }
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    if (params instanceof TradeHistoryParamCurrencyPair == false) {
      throw new ExchangeException("CurrencyPair must be provided to get user trades.");
    }

    TradeHistoryParamCurrencyPair pairParams = (TradeHistoryParamCurrencyPair) params;
    CurrencyPair pair = pairParams.getCurrencyPair();
    if (pair == null) {
      throw new ExchangeException("CurrencyPair must be provided to get user trades.");
    }

    // Limit is an optional parameter
    Integer limit = null;
    if (params instanceof TradeHistoryParamLimit) {
      TradeHistoryParamLimit limitParams = (TradeHistoryParamLimit) params;
      limit = limitParams.getLimit();
    }

    try {
      LatokenUserTrades latokenTrades = getLatokenUserTrades(pair, limit);
      return LatokenAdapters.adaptUserTrades(this.exchange, latokenTrades);

    } catch (LatokenException e) {
      throw LatokenErrorAdapter.adapt(e);
    }
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new LatokenTradeHistoryParams();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {

    return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public Class getRequiredOrderQueryParamClass() {
    return OrderQueryParamCurrencyPair.class;
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... params) throws IOException {

    try {
      Collection<Order> orders = new ArrayList<>();

      // Latoken supports either getting an order by its orderId or getting list of orders by
      // pair/status (via LatokenQueryOrderParams)
      for (OrderQueryParams param : params) {
        if (param instanceof OrderQueryParamCurrencyPair) {
          OrderQueryParamCurrencyPair orderQueryParamCurrencyPair =
              (OrderQueryParamCurrencyPair) param;
          CurrencyPair pair = orderQueryParamCurrencyPair.getCurrencyPair();
          if (pair == null) {
            throw new ExchangeException("CurrencyPair must be provided to query an order.");
          }

          LatokenOrderStatus status = LatokenOrderStatus.active;
          Integer limit = null;
          if (param instanceof LatokenQueryOrderParams) {
            LatokenQueryOrderParams latokenParam = (LatokenQueryOrderParams) param;
            status = latokenParam.getStatus();
            limit = latokenParam.getLimit();
          }

          List<LatokenOrder> latokenOrders = getLatokenOrders(pair, status, limit);
          latokenOrders.forEach(
              latokenOrder -> orders.add(LatokenAdapters.adaptOrder(this.exchange, latokenOrder)));

        } else {
          if (param.getOrderId() == null) {
            throw new ExchangeException("OrderId must be provided to query an order.");
          }

          LatokenOrder latokenOrder = getLatokenOrder(param.getOrderId());
          orders.add(LatokenAdapters.adaptOrder(this.exchange, latokenOrder));
        }
      }

      return orders;
    } catch (LatokenException e) {
      throw LatokenErrorAdapter.adapt(e);
    }
  }
}
