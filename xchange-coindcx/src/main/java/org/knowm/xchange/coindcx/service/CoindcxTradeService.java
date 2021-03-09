package org.knowm.xchange.coindcx.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindcx.CoindcxAdapters;
import org.knowm.xchange.coindcx.dto.*;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.*;

public class CoindcxTradeService extends CoindcxTradeServiceRaw implements TradeService {

  public CoindcxTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    CoindcxOrderStatusResponse newOrder =
            placeCoindcxMarketOrder(marketOrder, CoindcxOrderType.MARKET);

    // The return value contains details of any trades that have been immediately executed as a
    // result
    // of this order. Make these available to the application if it has provided a GeminiLimitOrder.
    if (marketOrder instanceof CoindcxMarketOrder) {
      CoindcxMarketOrder raw = (CoindcxMarketOrder) marketOrder;
      raw.setResponse(newOrder);
    }
    return String.valueOf(newOrder.getId());

  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    CoindcxOrderStatusResponse newOrder =
        placeCoindcxLimitOrder(limitOrder, CoindcxOrderType.LIMIT);

    // The return value contains details of any trades that have been immediately executed as a
    // result
    // of this order. Make these available to the application if it has provided a GeminiLimitOrder.
    if (limitOrder instanceof CoindcxLimitOrder) {
      CoindcxLimitOrder raw = (CoindcxLimitOrder) limitOrder;
      raw.setResponse(newOrder);
    }

    return String.valueOf(newOrder.getId());
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    //    return cancelGeminiOrder(orderId);
    return false;
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else {
      return false;
    }
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {

    Collection<Order> orders = new ArrayList<>(orderIds.length);

    for (String orderId : orderIds) {
      orders.add(CoindcxAdapters.adaptOrder(super.getGeminiOrderStatus(orderId)));
    }

    return orders;
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams tradeHistoryParams) throws IOException {
//    CoindcxAccountTradeHistory coindcxAccountTradeHistory
    String orderId = null;
    Integer limit = null;

    if (tradeHistoryParams instanceof TradeHistoryParamTransactionId) {
      TradeHistoryParamTransactionId tnxIdParams =
              (TradeHistoryParamTransactionId) tradeHistoryParams;
      orderId = tnxIdParams.getTransactionId();
    }

    if (tradeHistoryParams instanceof TradeHistoryParamLimit) {
      TradeHistoryParamLimit limitParams = (TradeHistoryParamLimit) tradeHistoryParams;
      limit = limitParams.getLimit();
    }

    List<CoindcxTradeHistoryResponse> coindcxTradeHistoryResponseList=getCoindcxAccountTradeHistory(new CoindcxAccountTradeHistory(orderId,limit));

    return CoindcxAdapters.adaptUserTrades(coindcxTradeHistoryResponseList);
  }
}
