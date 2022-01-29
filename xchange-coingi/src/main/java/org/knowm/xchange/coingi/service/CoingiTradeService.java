package org.knowm.xchange.coingi.service;

import static org.knowm.xchange.dto.Order.OrderType.BID;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coingi.CoingiAdapters;
import org.knowm.xchange.coingi.CoingiErrorAdapter;
import org.knowm.xchange.coingi.dto.CoingiException;
import org.knowm.xchange.coingi.dto.trade.CoingiCancelOrderRequest;
import org.knowm.xchange.coingi.dto.trade.CoingiGetOrderHistoryRequest;
import org.knowm.xchange.coingi.dto.trade.CoingiGetOrderRequest;
import org.knowm.xchange.coingi.dto.trade.CoingiOrder;
import org.knowm.xchange.coingi.dto.trade.CoingiOrdersList;
import org.knowm.xchange.coingi.dto.trade.CoingiPlaceLimitOrderRequest;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

public class CoingiTradeService extends CoingiTradeServiceRaw implements TradeService {
  public CoingiTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    try {
      CoingiGetOrderHistoryRequest orderHistoryRequest = new CoingiGetOrderHistoryRequest();
      orderHistoryRequest.setStatus(0);
      orderHistoryRequest.setPageNumber(1);
      orderHistoryRequest.setPageSize(50);
      CoingiOrdersList list = getCoingiOrderHistory(orderHistoryRequest);
      return CoingiAdapters.adaptOpenOrders(list);
    } catch (CoingiException e) {
      throw CoingiErrorAdapter.adapt(e);
    }
  }

  @Override
  public String placeMarketOrder(MarketOrder order) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String placeStopOrder(org.knowm.xchange.dto.trade.StopOrder stopOrder)
      throws java.io.IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder order) throws IOException {
    try {
      CoingiPlaceLimitOrderRequest request =
          new CoingiPlaceLimitOrderRequest()
              .setCurrencyPair(CoingiAdapters.adaptCurrency(order.getCurrencyPair()))
              .setOrderType(order.getType().equals(BID) ? 0 : 1)
              .setPrice(order.getLimitPrice())
              .setVolume(order.getOriginalAmount());

      return placeCoingiLimitOrder(request).getResult();
    } catch (CoingiException e) {
      throw CoingiErrorAdapter.adapt(e);
    }
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    try {
      // if it doesn't return an error and CoingiOrder is returned, then the cancellation is
      // successful
      CoingiCancelOrderRequest request = new CoingiCancelOrderRequest();
      request.setOrderId(orderId);
      return cancelCoingiOrder(request) != null;
    } catch (CoingiException e) {
      throw CoingiErrorAdapter.adapt(e);
    }
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    try {
      if (orderParams instanceof CancelOrderByIdParams) {
        return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
      } else {
        return false;
      }
    } catch (CoingiException e) {
      throw CoingiErrorAdapter.adapt(e);
    }
  }

  /** Required parameter types: {@link TradeHistoryParamPaging#getPageLength()} */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams p) throws IOException {
    try {
      if (p instanceof CoingiTradeHistoryParams) {
        CoingiTradeHistoryParams params = (CoingiTradeHistoryParams) p;

        CoingiGetOrderHistoryRequest request = new CoingiGetOrderHistoryRequest();

        if (params.getCurrencyPair() != null) request.setCurrencyPair(params.getCurrencyPair());

        request.setPageNumber(params.getPageNumber());
        request.setPageSize(params.getPageSize());

        if (params.getStatus() != null) request.setStatus(params.getStatus());

        if (params.getType() != null) request.setType(params.getType());

        CoingiOrdersList orderList = getCoingiOrderHistory(request);
        return CoingiAdapters.adaptTradeHistory(orderList);
      }

      return null;
    } catch (CoingiException e) {
      throw CoingiErrorAdapter.adapt(e);
    }
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new CoingiTradeHistoryParams(null, 1, 30, null, null);
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  public Collection<Order> getOrderImpl(String... orderIds) throws IOException {
    try {
      Collection<Order> orders = new ArrayList<>();
      for (String orderId : orderIds) {
        CoingiGetOrderRequest request = new CoingiGetOrderRequest().setOrderId(orderId);
        CoingiOrder coingiOrder;

        coingiOrder = getCoingiOrder(request);

        CurrencyPair currencyPair = CoingiAdapters.adaptCurrency(coingiOrder.getCurrencyPair());
        Date date = new Date(coingiOrder.getTimestamp() * 1000);
        Order order =
            new LimitOrder(
                coingiOrder.getType() == 0 ? Order.OrderType.BID : Order.OrderType.ASK,
                coingiOrder.getOriginalBaseAmount(),
                currencyPair,
                coingiOrder.getId(),
                date,
                coingiOrder.getPrice());
        order.setOrderStatus(CoingiAdapters.adaptOrderStatus(coingiOrder.getStatus()));
        orders.add(order);
      }

      return orders;
    } catch (CoingiException e) {
      throw CoingiErrorAdapter.adapt(e);
    }
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    return getOrderImpl(TradeService.toOrderIds(orderQueryParams));
  }
}
