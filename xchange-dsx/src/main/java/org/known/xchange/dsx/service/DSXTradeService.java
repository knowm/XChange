package org.known.xchange.dsx.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.swing.SortOrder;

import org.knowm.xchange.Exchange;
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
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.utils.DateUtils;
import org.known.xchange.dsx.DSXAdapters;
import org.known.xchange.dsx.DSXAuthenticated;
import org.known.xchange.dsx.DSXExchange;
import org.known.xchange.dsx.dto.marketdata.DSXExchangeInfo;
import org.known.xchange.dsx.dto.trade.DSXCancelOrderResult;
import org.known.xchange.dsx.dto.trade.DSXOrder;
import org.known.xchange.dsx.dto.trade.DSXTradeHistoryResult;
import org.known.xchange.dsx.dto.trade.DSXTradeResult;

/**
 * @author Mikhail Wall
 */
public class DSXTradeService extends DSXTradeServiceRaw implements TradeService{

  /**
   * Constructor
   *
   * @param exchange
   */
  protected DSXTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

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

    DSXOrder dsxOrder = new DSXOrder(pair, type, limitOrder.getTradableAmount(), limitOrder.getLimitPrice(), null, 3, DSXOrder.OrderType.limit);

    DSXTradeResult result = tradeDSX(dsxOrder);
    return Long.toString(result.getOrderId());
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    DSXCancelOrderResult ret = cancelDSXOrder(Long.parseLong(orderId));
    return (ret != null);
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    Long from = null;
    Long count = null;
    Long fromId = null;
    Long endId = null;
    DSXAuthenticated.SortOrder sort = DSXAuthenticated.SortOrder.DESC;
    Long since = null;
    Long end = null;
    String dsxpair = null;

    if (params instanceof TradeHistoryParamPaging) {
      TradeHistoryParamPaging pagingParams = (TradeHistoryParamPaging) params;
      Integer pageLength = pagingParams.getPageLength();
      Integer pageNumber = pagingParams.getPageNumber();
      if (pageNumber == null) {
        pageNumber = 0;
      }

      if (pageLength != null) {
        count = pageLength.longValue();
        from = (long) (pageLength * pageNumber);
      } else {
        from = pageNumber.longValue();
      }
    }

    if (params instanceof TradeHistoryParamsIdSpan) {
      TradeHistoryParamsIdSpan idParams = (TradeHistoryParamsIdSpan) params;
      fromId = nullSafeToLong(idParams.getStartId());
      endId = nullSafeToLong(idParams.getEndId());
    }

    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan timeParams = (TradeHistoryParamsTimeSpan) params;
      since = nullSafeUnixTime(timeParams.getStartTime());
      end = nullSafeUnixTime(timeParams.getEndTime());
    }

    if (params instanceof TradeHistoryParamCurrencyPair) {
      CurrencyPair pair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
      if (pair != null) {
        dsxpair = DSXAdapters.getPair(pair);
      }
    }

//    if (params instanceof DSXTransHistor)

    Map<Long, DSXTradeHistoryResult> resultMap = getDSXTradeHistory(from, count, fromId, endId, sort, since, end, dsxpair);
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
    return null;
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return null;
  }
}
