package org.knowm.xchange.bitfinex.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.knowm.xchange.bitfinex.BitfinexErrorAdapter;
import org.knowm.xchange.bitfinex.BitfinexExchange;
import org.knowm.xchange.bitfinex.dto.BitfinexException;
import org.knowm.xchange.bitfinex.service.trade.params.BitfinexOpenOrdersParams;
import org.knowm.xchange.bitfinex.service.trade.params.BitfinexOrderQueryParams;
import org.knowm.xchange.bitfinex.v1.BitfinexOrderType;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexOrderFlags;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexOrderStatusResponse;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexReplaceOrderRequest;
import org.knowm.xchange.bitfinex.v2.dto.BitfinexExceptionV2;
import org.knowm.xchange.bitfinex.v2.dto.trade.BitfinexOrderDetails;
import org.knowm.xchange.bitfinex.v2.dto.trade.BitfinexTrade;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.OpenPositions;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelAllOrders;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;
import org.knowm.xchange.utils.DateUtils;

public class BitfinexTradeService extends BitfinexTradeServiceRaw implements TradeService {

  private static final OpenOrders noOpenOrders = new OpenOrders(new ArrayList<LimitOrder>());

  public BitfinexTradeService(
      BitfinexExchange exchange, ResilienceRegistries resilienceRegistries) {

    super(exchange, resilienceRegistries);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    CurrencyPair currencyPair = null;
    List<Long> ids = null;
    if (params instanceof BitfinexOpenOrdersParams) {
      BitfinexOpenOrdersParams bitfinexOpenOrdersParams = (BitfinexOpenOrdersParams) params;
      currencyPair = bitfinexOpenOrdersParams.getCurrencyPair();
      ids = bitfinexOpenOrdersParams.getIds();
    }
    try {

      List<BitfinexOrderDetails> bitfinexOrderDetails =
          getBitfinexActiveOrdersV2(currencyPair, ids);

      List<LimitOrder> limitOrders =
          bitfinexOrderDetails.stream()
              .map(BitfinexAdapters::toLimitOrder)
              .collect(Collectors.toList());

      return new OpenOrders(limitOrders);

    } catch (BitfinexExceptionV2 e) {
      throw BitfinexErrorAdapter.adapt(e);
    }
  }

  @Override
  public OpenPositions getOpenPositions() throws IOException {
    var positions =
        getBitfinexActivePositionsV2().stream()
            .map(BitfinexAdapters::toOpenPosition)
            .collect(Collectors.toList());

    return OpenPositions.builder().openPositions(positions).build();
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

      final List<BitfinexTrade> bitfinexTrades =
          getBitfinexTradesV2(symbol, startTime, endTime, limit, sort);
      return BitfinexAdapters.adaptTradeHistoryV2(bitfinexTrades);
    } catch (BitfinexException e) {
      throw BitfinexErrorAdapter.adapt(e);
    }
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new BitfinexTradeHistoryParams();
  }

  @Override
  public BitfinexOpenOrdersParams createOpenOrdersParams() {
    return BitfinexOpenOrdersParams.builder().build();
  }

  @Override
  public Class getRequiredOrderQueryParamClass() {
    return BitfinexOrderQueryParams.class;
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    // parse parameters
    CurrencyPair currencyPair = null;
    Instant from = null;
    Instant to = null;
    Long limit = null;
    if (orderQueryParams.length > 0 && orderQueryParams[0] instanceof BitfinexOrderQueryParams) {
      BitfinexOrderQueryParams bitfinexOrderQueryParams =
          (BitfinexOrderQueryParams) orderQueryParams[0];
      currencyPair = bitfinexOrderQueryParams.getCurrencyPair();
      from = bitfinexOrderQueryParams.getFrom();
      to = bitfinexOrderQueryParams.getTo();
      limit = bitfinexOrderQueryParams.getLimit();
    }

    List<Long> ids =
        Arrays.stream(orderQueryParams)
            .map(OrderQueryParams::getOrderId)
            .map(Long::parseLong)
            .collect(Collectors.toList());

    try {
      List<BitfinexOrderDetails> inactiveOrderDetails =
          getBitfinexOrdersHistory(currencyPair, ids, from, to, limit);

      List<BitfinexOrderDetails> bitfinexOrderDetails =
          getBitfinexActiveOrdersV2(currencyPair, ids);

      return Stream.concat(inactiveOrderDetails.stream(), bitfinexOrderDetails.stream())
          .map(BitfinexAdapters::toOrder)
          .collect(Collectors.toList());

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
