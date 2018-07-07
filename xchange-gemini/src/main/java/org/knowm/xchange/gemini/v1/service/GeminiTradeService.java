package org.knowm.xchange.gemini.v1.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.gemini.v1.GeminiAdapters;
import org.knowm.xchange.gemini.v1.GeminiOrderType;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiLimitOrder;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiOrderStatusResponse;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiTradeResponse;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.*;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.utils.DateUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class GeminiTradeService extends GeminiTradeServiceRaw implements TradeService {

  private static final OpenOrders noOpenOrders = new OpenOrders(new ArrayList<LimitOrder>());

  private final MarketDataService marketDataService;

  public GeminiTradeService(Exchange exchange, MarketDataService marketDataService) {

    super(exchange);
    this.marketDataService = marketDataService;
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    GeminiOrderStatusResponse[] activeOrders = getGeminiOpenOrders();

    if (activeOrders.length <= 0) {
      return noOpenOrders;
    } else {
      return GeminiAdapters.adaptOrders(activeOrders);
    }
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    Ticker ticker = marketDataService.getTicker(marketOrder.getCurrencyPair());

    BigDecimal price;
    if(marketOrder.getType().equals(Order.OrderType.BID)) {
      price = ticker.getLast().multiply(new BigDecimal(10.0));

    } else {
      price = ticker.getLast().divide(new BigDecimal(10.0));
    }


    return placeLimitOrder(
            new LimitOrder(
                    marketOrder.getType(),
                    marketOrder.getOriginalAmount(),
                    marketOrder.getCurrencyPair(),
                    marketOrder.getId(),
                    marketOrder.getTimestamp(),
                    price
            )
    );
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
    return null;
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {

    Collection<Order> orders = new ArrayList<>(orderIds.length);

    for (String orderId : orderIds) {
      orders.add(GeminiAdapters.adaptOrder(super.getGeminiOrderStatus(orderId)));
    }

    return orders;
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
