package org.knowm.xchange.btce.v3.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btce.v3.BTCEAdapters;
import org.knowm.xchange.btce.v3.BTCEAuthenticated;
import org.knowm.xchange.btce.v3.BTCEExchange;
import org.knowm.xchange.btce.v3.dto.marketdata.BTCEExchangeInfo;
import org.knowm.xchange.btce.v3.dto.trade.BTCECancelOrderResult;
import org.knowm.xchange.btce.v3.dto.trade.BTCEOrder;
import org.knowm.xchange.btce.v3.dto.trade.BTCEPlaceOrderResult;
import org.knowm.xchange.btce.v3.dto.trade.BTCETradeHistoryResult;
import org.knowm.xchange.btce.v3.dto.trade.BTCETransHistoryResult;
import org.knowm.xchange.btce.v3.service.trade.params.BTCETradeHistoryParams;
import org.knowm.xchange.btce.v3.service.trade.params.BTCETransHistoryParams;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
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
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.utils.DateUtils;

/**
 * @author Matija Mazi
 */
public class BTCETradeService extends BTCETradeServiceRaw implements TradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTCETradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(
      OpenOrdersParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    // todo: use the currency pair from params
    Map<Long, BTCEOrder> orders = getBTCEActiveOrders(null);
    return BTCEAdapters.adaptOrders(orders);
  }

  /**
   * Implementation note: this method calls placeLimitOrder with LimitOrder created from passed MarketOrder and either max price in case of BID or min
   * proce in case of ASK, taken from the remote metadata cached in BTCEExchange
   */
  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    BTCEExchangeInfo btceExchangeInfo = ((BTCEExchange) exchange).getBtceExchangeInfo();
    LimitOrder order = BTCEAdapters.createLimitOrder(marketOrder, btceExchangeInfo);
    return placeLimitOrder(order);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    BTCEOrder.Type type = limitOrder.getType() == Order.OrderType.BID ? BTCEOrder.Type.buy : BTCEOrder.Type.sell;

    String pair = BTCEAdapters.getPair(limitOrder.getCurrencyPair());

    BTCEOrder btceOrder = new BTCEOrder(0, null, limitOrder.getLimitPrice(), limitOrder.getTradableAmount(), type, pair);

    BTCEPlaceOrderResult result = placeBTCEOrder(btceOrder);
    return Long.toString(result.getOrderId());
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    BTCECancelOrderResult ret = cancelBTCEOrder(Long.parseLong(orderId));
    return (ret != null);
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      cancelOrder(((CancelOrderByIdParams) orderParams).orderId);
    }
    return false;
  }

  /**
   * Supported parameters: {@link TradeHistoryParamPaging} {@link TradeHistoryParamsIdSpan} {@link TradeHistoryParamsTimeSpan}
   * {@link TradeHistoryParamCurrencyPair} You can also override sorting order (default is descending) by using {@link BTCETradeHistoryParams}
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws ExchangeException, IOException {

    Long offset = null;
    Long count = null;
    Long startId = null;
    Long endId = null;
    BTCEAuthenticated.SortOrder sort = BTCEAuthenticated.SortOrder.DESC;
    Long startTime = null;
    Long endTime = null;
    String btcrPair = null;

    if (params instanceof TradeHistoryParamPaging) {
      TradeHistoryParamPaging pagingParams = (TradeHistoryParamPaging) params;
      Integer pageLength = pagingParams.getPageLength();
      Integer pageNumber = pagingParams.getPageNumber();
      if (pageNumber == null) {
        pageNumber = 0;
      }

      if (pageLength != null) {
        count = pageLength.longValue();
        offset = (long) (pageLength * pageNumber);
      } else {
        offset = pageNumber.longValue();
      }
    }

    if (params instanceof TradeHistoryParamsIdSpan) {
      TradeHistoryParamsIdSpan idParams = (TradeHistoryParamsIdSpan) params;
      startId = nullSafeToLong(idParams.getStartId());
      endId = nullSafeToLong(idParams.getEndId());
    }

    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan timeParams = (TradeHistoryParamsTimeSpan) params;
      startTime = nullSafeUnixTime(timeParams.getStartTime());
      endTime = nullSafeUnixTime(timeParams.getEndTime());
    }

    if (params instanceof TradeHistoryParamCurrencyPair) {
      CurrencyPair pair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
      if (pair != null) {
        btcrPair = BTCEAdapters.getPair(pair);
      }
    }

    if (params instanceof BTCETransHistoryParams) {
      sort = ((BTCETransHistoryParams) params).getSortOrder();
    }

    Map<Long, BTCETradeHistoryResult> resultMap = getBTCETradeHistory(offset, count, startId, endId, sort, startTime, endTime, btcrPair);
    return BTCEAdapters.adaptTradeHistory(resultMap);
  }

  private static Long nullSafeToLong(String str) {

    try {
      return (str == null || str.isEmpty()) ? null : Long.valueOf(str);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  private static Long nullSafeUnixTime(Date time) {
    return time != null ? DateUtils.toUnixTime(time) : null;
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new BTCETradeHistoryParams();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    Collection<Order> orders = new ArrayList<>(orderIds.length);
    
    for (String orderId : orderIds) {
        orders.add(BTCEAdapters.adaptOrderInfo(orderId, getBTCEOrderInfo(Long.valueOf(orderId))));
    }
    
    return orders;
  }

  /**
   * Retrieve TransHistory. :TODO: Return could be abstracted in a fashion similar to UserTrades and also used in additional service for BitStamp
   * exchange.
   *
   * @param params
   * @return Map of transaction id to BTCETransHistoryResult
   */
  public Map<Long, BTCETransHistoryResult> getTransHistory(BTCETransHistoryParams params) throws ExchangeException, IOException {

    Long offset = null;
    Long count = null;
    Long startId = null;
    Long endId = null;
    BTCEAuthenticated.SortOrder sort = BTCEAuthenticated.SortOrder.DESC;
    Long startTime = null;
    Long endTime = null;

    if (params instanceof TradeHistoryParamPaging) {
      TradeHistoryParamPaging pagingParams = params;
      Integer pageLength = pagingParams.getPageLength();
      Integer pageNumber = pagingParams.getPageNumber();
      if (pageNumber == null) {
        pageNumber = 0;
      }

      if (pageLength != null) {
        count = pageLength.longValue();
        offset = (long) (pageLength * pageNumber);
      } else {
        offset = pageNumber.longValue();
      }
    }

    if (params instanceof TradeHistoryParamsIdSpan) {
      TradeHistoryParamsIdSpan idParams = params;
      startId = nullSafeToLong(idParams.getStartId());
      endId = nullSafeToLong(idParams.getEndId());
    }

    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan timeParams = params;
      startTime = nullSafeUnixTime(timeParams.getStartTime());
      endTime = nullSafeUnixTime(timeParams.getEndTime());
    }

    if (params instanceof BTCETransHistoryParams) {
      sort = params.getSortOrder();
    }

    Map<Long, BTCETransHistoryResult> resultMap = getBTCETransHistory(offset, count, startId, endId, sort, startTime, endTime);

    return resultMap;
  }
}
