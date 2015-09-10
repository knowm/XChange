package com.xeiam.xchange.btce.v3.service.polling;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btce.v3.BTCEAdapters;
import com.xeiam.xchange.btce.v3.BTCEAuthenticated;
import com.xeiam.xchange.btce.v3.BTCEExchange;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCEExchangeInfo;
import com.xeiam.xchange.btce.v3.dto.trade.BTCECancelOrderResult;
import com.xeiam.xchange.btce.v3.dto.trade.BTCEOrder;
import com.xeiam.xchange.btce.v3.dto.trade.BTCEPlaceOrderResult;
import com.xeiam.xchange.btce.v3.dto.trade.BTCETradeHistoryResult;
import com.xeiam.xchange.btce.v3.dto.trade.BTCETransHistoryResult;
import com.xeiam.xchange.btce.v3.service.polling.trade.params.BTCETradeHistoryParams;
import com.xeiam.xchange.btce.v3.service.polling.trade.params.BTCETransHistoryParams;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamCurrencyPair;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamsIdSpan;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamsTimeSpan;
import com.xeiam.xchange.utils.DateUtils;

/**
 * @author Matija Mazi
 */
public class BTCETradeService extends BTCETradeServiceRaw implements PollingTradeService {

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

  /**
   * @param arguments Vararg list of optional (nullable) arguments: (Long) arguments[0] Number of transactions to return (String) arguments[1]
   *        TradableIdentifier (String) arguments[2] TransactionCurrency (Long) arguments[3] Starting ID
   * @return Trades object
   * @throws IOException
   */
  @Override
  public UserTrades getTradeHistory(Object... arguments) throws IOException {

    Long numberOfTransactions = Long.MAX_VALUE;
    String tradableIdentifier = "";
    String transactionCurrency = "";
    Long id = null;
    try {
      numberOfTransactions = (Long) arguments[0];
      tradableIdentifier = (String) arguments[1];
      transactionCurrency = (String) arguments[2];
      id = (Long) arguments[3];
    } catch (ArrayIndexOutOfBoundsException e) {
      // ignore, can happen if no arg given.
    }
    String pair = null;
    if (!tradableIdentifier.equals("") && !transactionCurrency.equals("")) {
      pair = String.format("%s_%s", tradableIdentifier, transactionCurrency).toLowerCase();
    }
    Map<Long, BTCETradeHistoryResult> resultMap = getBTCETradeHistory(null, numberOfTransactions, id, id, BTCEAuthenticated.SortOrder.DESC, null,
        null, pair);
    return BTCEAdapters.adaptTradeHistory(resultMap);
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
        btcrPair = BTCEAdapters.adaptCurrencyPair(pair);
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
  public com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams createTradeHistoryParams() {
    return new BTCETradeHistoryParams();
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

    if (params instanceof BTCETransHistoryParams) {
      sort = ((BTCETransHistoryParams) params).getSortOrder();
    }

    Map<Long, BTCETransHistoryResult> resultMap = getBTCETransHistory(offset, count, startId, endId, sort, startTime, endTime);

    return resultMap;
  }
}
