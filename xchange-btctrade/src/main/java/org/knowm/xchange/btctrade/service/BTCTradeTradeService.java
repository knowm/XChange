package org.knowm.xchange.btctrade.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btctrade.BTCTradeAdapters;
import org.knowm.xchange.btctrade.dto.BTCTradeResult;
import org.knowm.xchange.btctrade.dto.trade.BTCTradeOrder;
import org.knowm.xchange.btctrade.dto.trade.BTCTradePlaceOrderResult;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.utils.DateUtils;

public class BTCTradeTradeService extends BTCTradeTradeServiceRaw implements TradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTCTradeTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(
      OpenOrdersParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return BTCTradeAdapters.adaptOpenOrders(getBTCTradeOrders(0, "open"));
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    final BTCTradePlaceOrderResult result;
    if (limitOrder.getType() == OrderType.BID) {
      result = buy(limitOrder.getTradableAmount(), limitOrder.getLimitPrice());
    } else {
      result = sell(limitOrder.getTradableAmount(), limitOrder.getLimitPrice());
    }
    return BTCTradeAdapters.adaptPlaceOrderResult(result);
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    BTCTradeResult result = cancelBTCTradeOrder(orderId);
    return BTCTradeAdapters.adaptResult(result);
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      cancelOrder(((CancelOrderByIdParams) orderParams).orderId);
    }
    return false;
  }

  /**
   * Optional parameters: start time (default 0 = all) of {@link TradeHistoryParamsTimeSpan}
   * <p/>
   * Required parameters: none
   * <p/>
   * Note this method makes 1+N remote calls, where N is the number of returned trades
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    long since = DateUtils.toUnixTime(((TradeHistoryParamsTimeSpan) params).getStartTime());
    BTCTradeOrder[] orders = getBTCTradeOrders(since, "all");
    BTCTradeOrder[] orderDetails = new BTCTradeOrder[orders.length];

    for (int i = 0; i < orders.length; i++) {
      orderDetails[i] = getBTCTradeOrder(orders[i].getId());
    }

    return BTCTradeAdapters.adaptTrades(orders, orderDetails);
  }

  /**
   * @return an instance of {@link TradeHistoryParamsTimeSpan}
   */
  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new DefaultTradeHistoryParamsTimeSpan(new Date(0));
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  @Override
  public Collection<Order> getOrder(
      String... orderIds) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

}
