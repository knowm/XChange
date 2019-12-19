package org.knowm.xchange.bitfinex.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitfinex.BitfinexErrorAdapter;
import org.knowm.xchange.bitfinex.dto.BitfinexException;
import org.knowm.xchange.bitfinex.v1.BitfinexOrderType;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexOrderFlags;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexOrderStatusResponse;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexReplaceOrderRequest;
import org.knowm.xchange.bitfinex.v2.dto.trade.Trade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.*;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.utils.DateUtils;

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
    try {
      BitfinexOrderStatusResponse[] activeOrders = getBitfinexOpenOrders();

      if (activeOrders.length <= 0) {
        return noOpenOrders;
      } else {
        return filterOrders(BitfinexAdapters.adaptOrders(activeOrders), params);
      }
    } catch (BitfinexException e) {
      throw BitfinexErrorAdapter.adapt(e);
    }
  }

  /** Bitfinex API does not provide filtering option. So we should filter orders ourselves */
  @SuppressWarnings("unchecked")
  private OpenOrders filterOrders(OpenOrders rawOpenOrders, OpenOrdersParams params) {
    if (params == null) {
      return rawOpenOrders;
    }

    List<LimitOrder> openOrdersList = rawOpenOrders.getOpenOrders();
    openOrdersList.removeIf(openOrder -> !params.accept(openOrder));

    return new OpenOrders(openOrdersList, (List<Order>) rawOpenOrders.getHiddenOrders());
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    try {
      BitfinexOrderStatusResponse newOrder;
      if (marketOrder.hasFlag(BitfinexOrderFlags.MARGIN))
        newOrder = placeBitfinexMarketOrder(marketOrder, BitfinexOrderType.MARGIN_MARKET);
      else newOrder = placeBitfinexMarketOrder(marketOrder, BitfinexOrderType.MARKET);

      return String.valueOf(newOrder.getId());
    } catch (BitfinexException e) {
      throw BitfinexErrorAdapter.adapt(e);
    }
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    try {
      BitfinexOrderType type = BitfinexAdapters.adaptOrderFlagsToType(limitOrder.getOrderFlags());
      BitfinexOrderStatusResponse newOrder = placeBitfinexLimitOrder(limitOrder, type);
      return String.valueOf(newOrder.getId());
    } catch (BitfinexException e) {
      throw BitfinexErrorAdapter.adapt(e);
    }
  }

  @Override
  public String changeOrder(LimitOrder order) throws IOException {

    boolean useRemaining =
        order.getOriginalAmount() == null || order.hasFlag(BitfinexOrderFlags.USE_REMAINING);

    BitfinexReplaceOrderRequest request =
        new BitfinexReplaceOrderRequest(
            String.valueOf(exchange.getNonceFactory().createValue()),
            Long.valueOf(order.getId()),
            BitfinexAdapters.adaptCurrencyPair(order.getCurrencyPair()),
            order.getOriginalAmount(),
            order.getLimitPrice(),
            "bitfinex",
            BitfinexAdapters.adaptOrderType(order.getType()),
            BitfinexAdapters.adaptOrderFlagsToType(order.getOrderFlags()).getValue(),
            order.hasFlag(BitfinexOrderFlags.HIDDEN),
            order.hasFlag(BitfinexOrderFlags.POST_ONLY),
            useRemaining);
    try {
      BitfinexOrderStatusResponse response =
          bitfinex.replaceOrder(apiKey, payloadCreator, signatureCreator, request);
      return String.valueOf(response.getId());
    } catch (BitfinexException e) {
      throw BitfinexErrorAdapter.adapt(e);
    }
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    if (stopOrder.getLimitPrice() != null) {
      throw new NotYetImplementedForExchangeException(
          "Limit stops are not supported by the Bitfinex v1 API.");
    }
    LimitOrder limitOrder =
        new LimitOrder(
            stopOrder.getType(),
            stopOrder.getOriginalAmount(),
            stopOrder.getCurrencyPair(),
            stopOrder.getId(),
            stopOrder.getTimestamp(),
            stopOrder.getStopPrice());
    limitOrder.setOrderFlags(stopOrder.getOrderFlags());
    limitOrder.setLeverage(stopOrder.getLeverage());
    limitOrder.addOrderFlag(BitfinexOrderFlags.STOP);
    return placeLimitOrder(limitOrder);
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    try {
      return cancelBitfinexOrder(orderId);
    } catch (BitfinexException e) {
      throw BitfinexErrorAdapter.adapt(e);
    }
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    try {
      if (orderParams instanceof CancelOrderByIdParams) {
        return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
      }
      if (orderParams instanceof CancelAllOrders) {
        return cancelAllBitfinexOrders();
      }

      throw new IllegalArgumentException(
          String.format("Unknown parameter type: %s", orderParams.getClass()));
    } catch (BitfinexException e) {
      throw BitfinexErrorAdapter.adapt(e);
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
    try {
      String symbol = null;
      if (params instanceof TradeHistoryParamCurrencyPair
          && ((TradeHistoryParamCurrencyPair) params).getCurrencyPair() != null) {
        symbol =
            BitfinexAdapters.adaptCurrencyPair(
                ((TradeHistoryParamCurrencyPair) params).getCurrencyPair());
      }

      Long startTime = 0L;
      Long endTime = null;
      Long limit = 50L;
      Long sort = null;

      if (params instanceof TradeHistoryParamsTimeSpan) {
        TradeHistoryParamsTimeSpan paramsTimeSpan = (TradeHistoryParamsTimeSpan) params;
        startTime = DateUtils.toMillisNullSafe(paramsTimeSpan.getStartTime());
        endTime = DateUtils.toMillisNullSafe(paramsTimeSpan.getEndTime());
      }

      if (params instanceof TradeHistoryParamLimit) {
        TradeHistoryParamLimit tradeHistoryParamLimit = (TradeHistoryParamLimit) params;
        if (tradeHistoryParamLimit.getLimit() != null) {
          limit = Long.valueOf(tradeHistoryParamLimit.getLimit());
        }
      }

      if (params instanceof TradeHistoryParamsSorted) {
        TradeHistoryParamsSorted tradeHistoryParamsSorted = (TradeHistoryParamsSorted) params;
        sort = tradeHistoryParamsSorted.getOrder() == TradeHistoryParamsSorted.Order.asc ? 1L : -1L;
      }

      final List<Trade> trades = getBitfinexTradesV2(symbol, startTime, endTime, limit, sort);
      return BitfinexAdapters.adaptTradeHistoryV2(trades);
    } catch (BitfinexException e) {
      throw BitfinexErrorAdapter.adapt(e);
    }
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new BitfinexTradeHistoryParams();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    try {
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
    } catch (BitfinexException e) {
      throw BitfinexErrorAdapter.adapt(e);
    }
  }

  public BigDecimal getMakerFee() throws IOException {
    return getBitfinexAccountInfos()[0].getMakerFees();
  }

  public BigDecimal getTakerFee() throws IOException {
    return getBitfinexAccountInfos()[0].getTakerFees();
  }

  public static class BitfinexTradeHistoryParams extends DefaultTradeHistoryParamsTimeSpan
      implements TradeHistoryParamCurrencyPair, TradeHistoryParamLimit, TradeHistoryParamsSorted {

    private CurrencyPair pair;
    private Integer limit;
    private Order order;

    public BitfinexTradeHistoryParams() {}

    @Override
    public CurrencyPair getCurrencyPair() {

      return pair;
    }

    @Override
    public void setCurrencyPair(CurrencyPair pair) {

      this.pair = pair;
    }

    @Override
    public Integer getLimit() {
      return this.limit;
    }

    @Override
    public void setLimit(Integer limit) {
      this.limit = limit;
    }

    @Override
    public Order getOrder() {
      return this.order;
    }

    @Override
    public void setOrder(Order order) {
      this.order = order;
    }
  }
}
