package org.knowm.xchange.coingi.service;

import static org.knowm.xchange.dto.Order.OrderType.BID;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coingi.CoingiAdapters;
import org.knowm.xchange.coingi.CoingiAuthenticated;
import org.knowm.xchange.coingi.dto.CoingiException;
import org.knowm.xchange.coingi.dto.request.CancelOrderRequest;
import org.knowm.xchange.coingi.dto.request.GetOrderHistoryRequest;
import org.knowm.xchange.coingi.dto.request.PlaceLimitOrderRequest;
import org.knowm.xchange.coingi.dto.trade.CoingiOrdersList;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class CoingiTradeService extends CoingiTradeServiceRaw implements TradeService {
  public CoingiTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException, CoingiException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws ExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String placeMarketOrder(MarketOrder order) throws IOException, CoingiException {
    /*     int orderType = order.getType().equals(BID) ? 0 : 1;
    CoingiPlaceOrderResponse coingiPlaceOrderResponse = placeCoingiMarketOrder(order.getCurrencyPair(), orderType, order.getOriginalAmount(), order.ge);
    if (coingiPlaceOrderResponse. != null) {
      throw new ExchangeException(coingiPlaceOrderResponse.getErrorMessage());
    }
    return Integer.toString(coingiPlaceOrderResponse.getId());
          */

    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String placeStopOrder(org.knowm.xchange.dto.trade.StopOrder stopOrder)
      throws java.io.IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder order) throws IOException, CoingiException {
    PlaceLimitOrderRequest request =
        new PlaceLimitOrderRequest()
            .setCurrencyPair(new CoingiAuthenticated.Pair(order.getCurrencyPair()).toString())
            .setOrderType(order.getType().equals(BID) ? 0 : 1)
            .setPrice(order.getLimitPrice())
            .setVolume(order.getRemainingAmount());

    return placeCoingiLimitOrder(request).getResult();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException, CoingiException {
    // if it doesn't return an error and CoingiOrder is returned, then the cancellation is
    // successful
    CancelOrderRequest request = new CancelOrderRequest();
    request.setOrderId(orderId);
    return cancelCoingiOrder(request) != null;
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else {
      return false;
    }
  }

  /** Required parameter types: {@link TradeHistoryParamPaging#getPageLength()} */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams p) throws IOException {
    CoingiOrdersList orderList;

    if (p instanceof CoingiTradeHistoryParams) {
      CoingiTradeHistoryParams params = (CoingiTradeHistoryParams) p;

      GetOrderHistoryRequest request = new GetOrderHistoryRequest();
      request.setCurrencyPair(Optional.of(params.getCurrencyPair()));
      request.setPageNumber(params.getPageNumber());
      request.setPageSize(params.getPageSize());
      request.setStatus(Optional.of(params.getStatus()));
      request.setType(Optional.of(params.getType()));

      orderList = getCoingiOrderHistory(request);
      return CoingiAdapters.adaptTradeHistory(orderList);
    }

    return null;
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new CoingiTradeHistoryParams(null, 1, 30, null, null);
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    /*Collection<Order> orders = new ArrayList<>();
    for (String orderId : orderIds) {
        GetOrderRequest request = new GetOrderRequest().setOrderId(orderId);
        CoingiOrder coingiOrder = getCoingiOrder(request);
        CoingiAdapters.adapt
        Order order = new LimitOrder(coingiOrder.getType() == 0 ? );
        orders.add(order);
    }
    */
    throw new NotYetImplementedForExchangeException();
  }
}
