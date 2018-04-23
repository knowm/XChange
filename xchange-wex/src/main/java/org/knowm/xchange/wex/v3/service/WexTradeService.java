package org.knowm.xchange.wex.v3.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
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
import org.knowm.xchange.wex.v3.WexAdapters;
import org.knowm.xchange.wex.v3.WexAuthenticated;
import org.knowm.xchange.wex.v3.WexExchange;
import org.knowm.xchange.wex.v3.dto.marketdata.WexExchangeInfo;
import org.knowm.xchange.wex.v3.dto.trade.WexCancelOrderResult;
import org.knowm.xchange.wex.v3.dto.trade.WexOrder;
import org.knowm.xchange.wex.v3.dto.trade.WexPlaceOrderResult;
import org.knowm.xchange.wex.v3.dto.trade.WexTradeHistoryResult;
import org.knowm.xchange.wex.v3.dto.trade.WexTransHistoryResult;
import org.knowm.xchange.wex.v3.service.trade.params.WexTradeHistoryParams;
import org.knowm.xchange.wex.v3.service.trade.params.WexTransHistoryParams;

/** @author Matija Mazi */
public class WexTradeService extends WexTradeServiceRaw implements TradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public WexTradeService(Exchange exchange) {

    super(exchange);
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
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    // todo: use the currency pair from params
    Map<Long, WexOrder> orders = getBTCEActiveOrders(null);
    return WexAdapters.adaptOrders(orders);
  }

  /**
   * Implementation note: this method calls placeLimitOrder with LimitOrder created from passed
   * MarketOrder and either max price in case of BID or min proce in case of ASK, taken from the
   * remote metadata cached in WexExchange
   */
  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    WexExchangeInfo wexExchangeInfo = ((WexExchange) exchange).getWexExchangeInfo();
    LimitOrder order = WexAdapters.createLimitOrder(marketOrder, wexExchangeInfo);
    return placeLimitOrder(order);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    WexOrder.Type type =
        limitOrder.getType() == Order.OrderType.BID ? WexOrder.Type.buy : WexOrder.Type.sell;

    String pair = WexAdapters.getPair(limitOrder.getCurrencyPair());

    WexOrder wexOrder =
        new WexOrder(
            0, null, limitOrder.getLimitPrice(), limitOrder.getOriginalAmount(), type, pair);

    WexPlaceOrderResult result = placeBTCEOrder(wexOrder);
    return Long.toString(result.getOrderId());
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    WexCancelOrderResult ret = cancelBTCEOrder(Long.parseLong(orderId));
    return (ret != null);
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else {
      return false;
    }
  }

  /**
   * Supported parameters: {@link TradeHistoryParamPaging} {@link TradeHistoryParamsIdSpan} {@link
   * TradeHistoryParamsTimeSpan} {@link TradeHistoryParamCurrencyPair} You can also override sorting
   * order (default is descending) by using {@link WexTradeHistoryParams}
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params)
      throws ExchangeException, IOException {

    Long offset = null;
    Long count = null;
    Long startId = null;
    Long endId = null;
    WexAuthenticated.SortOrder sort = WexAuthenticated.SortOrder.DESC;
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
        btcrPair = WexAdapters.getPair(pair);
      }
    }

    if (params instanceof WexTransHistoryParams) {
      sort = ((WexTransHistoryParams) params).getSortOrder();
    }

    Map<Long, WexTradeHistoryResult> resultMap =
        getBTCETradeHistory(offset, count, startId, endId, sort, startTime, endTime, btcrPair);
    return WexAdapters.adaptTradeHistory(resultMap);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new WexTradeHistoryParams();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    Collection<Order> orders = new ArrayList<>(orderIds.length);

    for (String orderId : orderIds) {
      orders.add(WexAdapters.adaptOrderInfo(orderId, getBTCEOrderInfo(Long.valueOf(orderId))));
    }

    return orders;
  }

  /**
   * Retrieve TransHistory. :TODO: Return could be abstracted in a fashion similar to UserTrades and
   * also used in additional service for BitStamp exchange.
   *
   * @param params
   * @return Map of transaction id to WexTransHistoryResult
   */
  public Map<Long, WexTransHistoryResult> getTransHistory(WexTransHistoryParams params)
      throws ExchangeException, IOException {

    Long offset = null;
    Long count = null;
    Long startId = null;
    Long endId = null;
    WexAuthenticated.SortOrder sort = WexAuthenticated.SortOrder.DESC;
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

    if (params instanceof WexTransHistoryParams) {
      sort = params.getSortOrder();
    }

    Map<Long, WexTransHistoryResult> resultMap =
        getBTCETransHistory(offset, count, startId, endId, sort, startTime, endTime);

    return resultMap;
  }
}
