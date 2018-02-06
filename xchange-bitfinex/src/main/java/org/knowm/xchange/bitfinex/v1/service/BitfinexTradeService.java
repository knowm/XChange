package org.knowm.xchange.bitfinex.v1.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitfinex.v1.BitfinexAdapters;
import org.knowm.xchange.bitfinex.v1.BitfinexOrderType;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexOrderFlags;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexOrderStatusResponse;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexTradeResponse;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.*;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.utils.DateUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class BitfinexTradeService extends BitfinexTradeServiceRaw implements TradeService {

  private static final OpenOrders noOpenOrders = new OpenOrders(new ArrayList<LimitOrder>());

  public BitfinexTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    BitfinexOrderStatusResponse[] activeOrders = getBitfinexOpenOrders();

    if (activeOrders.length <= 0) {
      return noOpenOrders;
    } else {
      return filterOrders(BitfinexAdapters.adaptOrders(activeOrders), params);
    }
  }

  /**
   * Bitfinex API does not provide filtering option. So we should filter orders ourselves
   */
  private OpenOrders filterOrders(OpenOrders rawOpenOrders,
                                  OpenOrdersParams params) {
    if (params == null) {
      return rawOpenOrders;
    }

    List<LimitOrder> openOrdersList = rawOpenOrders.getOpenOrders();
    openOrdersList.removeIf(openOrder -> !params.accept(openOrder));
    return new OpenOrders(openOrdersList);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    BitfinexOrderStatusResponse newOrder;
    if (marketOrder.hasFlag(BitfinexOrderFlags.MARGIN))

      newOrder = placeBitfinexMarketOrder(marketOrder, BitfinexOrderType.MARGIN_MARKET);

    else
      newOrder = placeBitfinexMarketOrder(marketOrder, BitfinexOrderType.MARKET);

    return String.valueOf(newOrder.getId());
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    BitfinexOrderStatusResponse newOrder;
    if (limitOrder.hasFlag(BitfinexOrderFlags.MARGIN)) {
      if (limitOrder.hasFlag(BitfinexOrderFlags.FILL_OR_KILL)) {
        newOrder = placeBitfinexLimitOrder(limitOrder, BitfinexOrderType.MARGIN_FILL_OR_KILL);
      }
      else if (limitOrder.hasFlag(BitfinexOrderFlags.TRAILING_STOP)) {
        newOrder = placeBitfinexLimitOrder(limitOrder, BitfinexOrderType.MARGIN_TRAILING_STOP);
      }
      else if (limitOrder.hasFlag(BitfinexOrderFlags.STOP)) {
        newOrder = placeBitfinexLimitOrder(limitOrder, BitfinexOrderType.MARGIN_STOP);
      } else {
        newOrder = placeBitfinexLimitOrder(limitOrder, BitfinexOrderType.MARGIN_LIMIT);
      }
    } else {
      if (limitOrder.hasFlag(BitfinexOrderFlags.FILL_OR_KILL)) {
        newOrder = placeBitfinexLimitOrder(limitOrder, BitfinexOrderType.FILL_OR_KILL);
      }
      else if (limitOrder.hasFlag(BitfinexOrderFlags.TRAILING_STOP)) {
        newOrder = placeBitfinexLimitOrder(limitOrder, BitfinexOrderType.TRAILING_STOP);
      }
      else if (limitOrder.hasFlag(BitfinexOrderFlags.STOP)) {
        newOrder = placeBitfinexLimitOrder(limitOrder, BitfinexOrderType.STOP);
      } else {
        newOrder = placeBitfinexLimitOrder(limitOrder, BitfinexOrderType.LIMIT);
      }
    }

    return String.valueOf(newOrder.getId());
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    return cancelBitfinexOrder(orderId);
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
   * @param params Implementation of {@link TradeHistoryParamCurrencyPair} is mandatory. Can optionally implement {@link TradeHistoryParamPaging} and
   * {@link TradeHistoryParamsTimeSpan#getStartTime()}. All other TradeHistoryParams types will be ignored.
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    final String symbol;
    if (params instanceof TradeHistoryParamCurrencyPair && ((TradeHistoryParamCurrencyPair) params).getCurrencyPair() != null) {
      symbol = BitfinexAdapters.adaptCurrencyPair(((TradeHistoryParamCurrencyPair) params).getCurrencyPair());
    } else {
      // Exchange will return the errors below if CurrencyPair is not provided.
      // field not on request: "Key symbol was not present."
      // field supplied but blank: "Key symbol may not be the empty string"
      throw new ExchangeException("CurrencyPair must be supplied");
    }

    long startTime = 0;
    Long endTime = null;
    int limit = 50;

    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan paramsTimeSpan = (TradeHistoryParamsTimeSpan) params;
      startTime = DateUtils.toUnixTimeNullSafe(paramsTimeSpan.getStartTime());
      endTime = DateUtils.toUnixTimeNullSafe(paramsTimeSpan.getEndTime());
    }

    if (params instanceof TradeHistoryParamPaging) {
      TradeHistoryParamPaging pagingParams = (TradeHistoryParamPaging) params;
      Integer pageLength = pagingParams.getPageLength();
      Integer pageNum = pagingParams.getPageNumber();
      limit = (pageLength != null && pageNum != null) ? pageLength * (pageNum + 1) : 50;
    }

    if (params instanceof TradeHistoryParamLimit) {
      TradeHistoryParamLimit tradeHistoryParamLimit = (TradeHistoryParamLimit) params;
      limit = tradeHistoryParamLimit.getLimit();
    }

    final BitfinexTradeResponse[] trades = getBitfinexTradeHistory(symbol, startTime, endTime, limit, null);
    return BitfinexAdapters.adaptTradeHistory(trades, symbol);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new BitfinexTradeHistoryParams(new Date(0), 50, CurrencyPair.BTC_USD);
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  public static class BitfinexTradeHistoryParams extends DefaultTradeHistoryParamsTimeSpan implements TradeHistoryParamCurrencyPair,
      TradeHistoryParamPaging {

    private int count;
    private CurrencyPair pair;
    private Integer pageNumber;

    public BitfinexTradeHistoryParams(Date startTime, int count, CurrencyPair pair) {

      super(startTime);

      this.count = count;
      this.pair = pair;
    }

    @Override
    public void setPageLength(Integer count) {

      this.count = count;
    }

    @Override
    public Integer getPageLength() {

      return count;
    }

    @Override
    public void setPageNumber(Integer pageNumber) {

      this.pageNumber = pageNumber;
    }

    @Override
    public Integer getPageNumber() {

      return pageNumber;
    }

    @Override
    public CurrencyPair getCurrencyPair() {

      return pair;
    }

    @Override
    public void setCurrencyPair(CurrencyPair pair) {

      this.pair = pair;
    }
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    List<Order> openOrders = new ArrayList<>();

    for (String orderId : orderIds) {

      BitfinexOrderStatusResponse orderStatus = getBitfinexOrderStatus(orderId);
      BitfinexOrderStatusResponse[] orderStatuses = new BitfinexOrderStatusResponse[1];
      if (orderStatus != null) {
        orderStatuses[0] = orderStatus;
        OpenOrders orders = BitfinexAdapters.adaptOrders(orderStatuses);
        openOrders.add(orders.getOpenOrders().get(0));
      }

    }
    return openOrders;

  }

  public BigDecimal getMakerFee() throws IOException {
    return getBitfinexAccountInfos()[0].getMakerFees();
  }

  public BigDecimal getTakerFee() throws IOException {
    return getBitfinexAccountInfos()[0].getTakerFees();
  }
}
