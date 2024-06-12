package org.knowm.xchange.gemini.v1.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.gemini.v1.GeminiAdapters;
import org.knowm.xchange.gemini.v1.GeminiOrderType;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiCancelAllOrdersParams;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiLimitOrder;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiOrderStatusResponse;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiTradeResponse;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelAllOrders;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;
import org.knowm.xchange.utils.DateUtils;

public class GeminiTradeService extends GeminiTradeServiceRaw implements TradeService {

  private static final OpenOrders noOpenOrders = new OpenOrders(new ArrayList<LimitOrder>());

  public GeminiTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(null);
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    GeminiOrderStatusResponse[] activeOrders = getGeminiOpenOrders();

    if (activeOrders.length <= 0) {
      return noOpenOrders;
    } else {
      if (params != null && params instanceof OpenOrdersParamCurrencyPair) {
        OpenOrdersParamCurrencyPair openOrdersParamCurrencyPair =
            (OpenOrdersParamCurrencyPair) params;
        return GeminiAdapters.adaptOrders(
            activeOrders, openOrdersParamCurrencyPair.getCurrencyPair());
      }
      return GeminiAdapters.adaptOrders(activeOrders);
    }
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    GeminiOrderStatusResponse newOrder = placeGeminiLimitOrder(limitOrder, GeminiOrderType.LIMIT);

    // The return value contains details of any trades that have been immediately executed as a
    // result
    // of this order. Make these available to the application if it has provided a GeminiLimitOrder.
    if (limitOrder instanceof GeminiLimitOrder) {
      GeminiLimitOrder raw = (GeminiLimitOrder) limitOrder;
      raw.setResponse(newOrder);
    }

    return String.valueOf(newOrder.getId());
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    return cancelGeminiOrder(orderId);
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
  public Collection<String> cancelAllOrders(CancelAllOrders orderParams) throws IOException {
    if (orderParams instanceof GeminiCancelAllOrdersParams) {
      return Arrays.stream(
              cancelAllGeminiOrders(
                      ((GeminiCancelAllOrdersParams) orderParams).isSessionOnly(),
                      ((GeminiCancelAllOrdersParams) orderParams).getAccount())
                  .getDetails()
                  .getCancelledOrders())
          .mapToObj(id -> String.valueOf(id))
          .collect(Collectors.toList());
    } else {
      return null;
    }
  }

  /**
   * @param params Implementation of {@link TradeHistoryParamCurrencyPair} is mandatory. Can
   *     optionally implement {@link TradeHistoryParamPaging} and {@link
   *     TradeHistoryParamsTimeSpan#getStartTime()}. All other TradeHistoryParams types will be
   *     ignored.
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    final String symbol;
    if (params instanceof TradeHistoryParamCurrencyPair
        && ((TradeHistoryParamCurrencyPair) params).getCurrencyPair() != null) {
      symbol =
          GeminiAdapters.adaptCurrencyPair(
              ((TradeHistoryParamCurrencyPair) params).getCurrencyPair());
    } else {
      // Exchange will return the errors below if CurrencyPair is not provided.
      // field not on request: "Key symbol was not present."
      // field supplied but blank: "Key symbol may not be the empty string"
      throw new ExchangeException("CurrencyPair must be supplied");
    }

    final long timestamp;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      Date startTime = ((TradeHistoryParamsTimeSpan) params).getStartTime();
      timestamp = DateUtils.toUnixTime(startTime);
    } else {
      timestamp = 0;
    }

    Integer limit;
    if (params instanceof TradeHistoryParamPaging) {
      TradeHistoryParamPaging pagingParams = (TradeHistoryParamPaging) params;
      Integer pageLength = pagingParams.getPageLength();
      Integer pageNum = pagingParams.getPageNumber();
      limit = (pageLength != null && pageNum != null) ? pageLength * (pageNum + 1) : 50;
    } else {
      limit = 50;
    }

    if (params instanceof TradeHistoryParamLimit) {
      limit = ((TradeHistoryParamLimit) params).getLimit();
    }

    final GeminiTradeResponse[] trades = getGeminiTradeHistory(symbol, timestamp, limit);
    return GeminiAdapters.adaptTradeHistory(trades, symbol);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new GeminiTradeHistoryParams(CurrencyPair.BTC_USD, 500, new Date(0));
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... params) throws IOException {

    Collection<Order> orders = new ArrayList<>(params.length);

    for (OrderQueryParams param : params) {
      orders.add(GeminiAdapters.adaptOrder(super.getGeminiOrderStatus(param)));
    }

    return orders;
  }

  @Getter
  @Setter
  public static class GeminiOrderQueryParams implements OrderQueryParams {
    private String orderId;
    private String clientOrderId;
    private boolean includeTrades;
    private String account;

    public GeminiOrderQueryParams(
        String orderId, String clientOrderId, boolean includeTrades, String account) {
      this.orderId = orderId;
      this.clientOrderId = clientOrderId;
      this.includeTrades = includeTrades;
      this.account = account;
    }

    @Override
    public String getOrderId() {
      return orderId;
    }

    @Override
    public void setOrderId(String orderId) {
      this.orderId = orderId;
    }
  }

  public static class GeminiTradeHistoryParams
      implements TradeHistoryParamCurrencyPair, TradeHistoryParamLimit, TradeHistoryParamsTimeSpan {
    private CurrencyPair currencyPair;
    private Integer limit;
    private Date startTime;

    public GeminiTradeHistoryParams(CurrencyPair currencyPair, Integer limit, Date startTime) {
      this.currencyPair = currencyPair;
      this.limit = limit;
      this.startTime = startTime;
    }

    public GeminiTradeHistoryParams() {}

    @Override
    public CurrencyPair getCurrencyPair() {
      return currencyPair;
    }

    @Override
    public void setCurrencyPair(CurrencyPair currencyPair) {
      this.currencyPair = currencyPair;
    }

    @Override
    public Integer getLimit() {
      return limit;
    }

    @Override
    public void setLimit(Integer limit) {
      this.limit = limit;
    }

    @Override
    public Date getStartTime() {
      return startTime;
    }

    @Override
    public void setStartTime(Date startTime) {
      this.startTime = startTime;
    }

    @Override
    public Date getEndTime() {
      return null;
    }

    @Override
    public void setEndTime(Date endTime) {
      // ignored
    }
  }
}
