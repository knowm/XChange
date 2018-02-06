package org.knowm.xchange.dsx.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dsx.DSXAdapters;
import org.knowm.xchange.dsx.DSXAuthenticatedV2;
import org.knowm.xchange.dsx.DSXExchange;
import org.knowm.xchange.dsx.dto.marketdata.DSXExchangeInfo;
import org.knowm.xchange.dsx.dto.trade.DSXCancelAllOrdersResult;
import org.knowm.xchange.dsx.dto.trade.DSXOrder;
import org.knowm.xchange.dsx.dto.trade.DSXTradeHistoryResult;
import org.knowm.xchange.dsx.dto.trade.DSXTradeResult;
import org.knowm.xchange.dsx.dto.trade.DSXTransHistoryResult;
import org.knowm.xchange.dsx.service.trade.params.DSXTradeHistoryParams;
import org.knowm.xchange.dsx.service.trade.params.DSXTransHistoryParams;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.utils.DateUtils;

/**
 * @author Mikhail Wall
 */
public class DSXTradeService extends DSXTradeServiceRaw implements TradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public DSXTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params)
      throws IOException {

    Map<Long, DSXOrder> orders = getDSXActiveOrders(null);
    return DSXAdapters.adaptOrders(orders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    DSXExchangeInfo dsxExchangeInfo = ((DSXExchange) exchange).getDsxExchangeInfo();
    LimitOrder order = DSXAdapters.createLimitOrder(marketOrder, dsxExchangeInfo);
    return placeLimitOrder(order);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    DSXOrder.Type type = limitOrder.getType() == Order.OrderType.BID ? DSXOrder.Type.buy : DSXOrder.Type.sell;

    String pair = DSXAdapters.getPair(limitOrder.getCurrencyPair());

    DSXOrder dsxOrder = new DSXOrder(pair, type, limitOrder.getOriginalAmount(), limitOrder.getOriginalAmount(), limitOrder.getLimitPrice(),
        3, DSXOrder.OrderType.limit, null);

    DSXTradeResult result = tradeDSX(dsxOrder);
    return Long.toString(result.getOrderId());
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    return cancelDSXOrder(Long.parseLong(orderId));
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else {
      return false;
    }
  }

  public boolean cancelAllOrders() throws IOException {

    DSXCancelAllOrdersResult ret = cancelAllDSXOrders();
    return (ret != null);
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    int count = 1000;//this is the max
    Long fromId = null;
    Long endId = null;
    DSXAuthenticatedV2.SortOrder sort = DSXAuthenticatedV2.SortOrder.DESC;
    Long since = null;
    Long end = null;
    String dsxpair = null;

    if (params instanceof TradeHistoryParamLimit) {
      TradeHistoryParamLimit pagingParams = (TradeHistoryParamLimit) params;
      Integer limit = pagingParams.getLimit();
      if (limit != null)
        count = limit;
    }

    if (params instanceof TradeHistoryParamsIdSpan) {
      TradeHistoryParamsIdSpan idParams = (TradeHistoryParamsIdSpan) params;
      fromId = nullSafeToLong(idParams.getStartId());
      endId = nullSafeToLong(idParams.getEndId());
    }

    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan timeParams = (TradeHistoryParamsTimeSpan) params;
      since = DateUtils.toMillisNullSafe(timeParams.getStartTime());
      end = DateUtils.toMillisNullSafe(timeParams.getEndTime());
    }

    if (params instanceof TradeHistoryParamCurrencyPair) {
      CurrencyPair pair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
      if (pair != null) {
        dsxpair = DSXAdapters.getPair(pair);
      }
    }

    if (params instanceof TradeHistoryParamsSorted) {
      TradeHistoryParamsSorted tradeHistoryParamsSorted = (TradeHistoryParamsSorted) params;
      TradeHistoryParamsSorted.Order order = tradeHistoryParamsSorted.getOrder();
      if (order != null)
        sort = order.equals(TradeHistoryParamsSorted.Order.desc) ? DSXAuthenticatedV2.SortOrder.DESC : DSXAuthenticatedV2.SortOrder.ASC;
    }

    Map<Long, DSXTradeHistoryResult> resultMap = getDSXTradeHistory(count, fromId, endId, sort, since, end, dsxpair);
    return DSXAdapters.adaptTradeHistory(resultMap);
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

    return new DSXTradeHistoryParams();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {

    return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  public Map<Long, DSXTransHistoryResult> getTransHistory(DSXTransHistoryParams params) throws ExchangeException, IOException {
    Integer count = params.getLimit();

    Long startId = nullSafeToLong(params.getStartId());
    Long endId = nullSafeToLong(params.getEndId());

    Long startTime = nullSafeUnixTime(params.getStartTime());
    Long endTime = nullSafeUnixTime(params.getEndTime());

    DSXAuthenticatedV2.SortOrder sort = params.getOrder().equals(TradeHistoryParamsSorted.Order.desc) ? DSXAuthenticatedV2.SortOrder.DESC : DSXAuthenticatedV2.SortOrder.ASC;
    DSXTransHistoryResult.Status status = params.getStatus();
    DSXTransHistoryResult.Type type = params.getType();

    Currency c = params.getCurrency();
    String currency = c == null ? null : c.getCurrencyCode();

    return getDSXTransHistory(count, startId, endId, sort, startTime, endTime, type, status, currency);
  }
}
