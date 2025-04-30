package org.knowm.xchange.bitmex.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.Validate;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPrivateOrder;
import org.knowm.xchange.bitmex.dto.params.FilterParam;
import org.knowm.xchange.bitmex.dto.trade.BitmexExecutionInstruction;
import org.knowm.xchange.bitmex.dto.trade.BitmexOrderFlags;
import org.knowm.xchange.bitmex.dto.trade.BitmexPlaceOrderParameters;
import org.knowm.xchange.bitmex.dto.trade.BitmexReplaceOrderParameters;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelAllOrders;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.CurrencyPairParam;
import org.knowm.xchange.service.trade.params.InstrumentParam;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOrderId;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParam;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamInstrument;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

public class BitmexTradeService extends BitmexTradeServiceRaw implements TradeService {

  public BitmexTradeService(BitmexExchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParam();
  }

  @Override
  public OpenOrders getOpenOrders() throws ExchangeException {
    return getOpenOrders(new DefaultOpenOrdersParam());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws ExchangeException {
    FilterParam.FilterParamBuilder builder = FilterParam.builder().isOpen(true);
    if (params instanceof OpenOrdersParamInstrument) {
      builder.instrument(((InstrumentParam) params).getInstrument());
    }

    List<LimitOrder> limitOrders =
        getBitmexOrders(null, builder.build(), null, null, null).stream()
            .map(BitmexAdapters::toOrder)
            .filter(Objects::nonNull)
            .map(LimitOrder.class::cast)
            .filter(params::accept)
            .collect(Collectors.toList());

    return new OpenOrders(limitOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws ExchangeException {
    return placeOrder(
            BitmexPlaceOrderParameters.builder()
                .instrument(marketOrder.getInstrument())
                .side(marketOrder.getType())
                .orderQuantity(marketOrder.getOriginalAmount())
                .clientOid(marketOrder.getUserReference())
                .build())
        .getId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws ExchangeException {
    BitmexPlaceOrderParameters.BitmexPlaceOrderParametersBuilder b =
        BitmexPlaceOrderParameters.builder()
            .instrument(limitOrder.getInstrument())
            .orderQuantity(limitOrder.getOriginalAmount())
            .price(limitOrder.getLimitPrice())
            .side(limitOrder.getType())
            .clientOid(limitOrder.getUserReference());
    if (limitOrder.hasFlag(BitmexOrderFlags.POST)) {
      b.executionInstruction(BitmexExecutionInstruction.PARTICIPATE_DO_NOT_INITIATE);
    }
    return placeOrder(b.build()).getId();
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws ExchangeException {
    return placeOrder(
            BitmexPlaceOrderParameters.builder()
                .instrument(stopOrder.getInstrument())
                .side(stopOrder.getType())
                .orderQuantity(stopOrder.getOriginalAmount())
                .stopPrice(stopOrder.getStopPrice())
                .clientOid(stopOrder.getUserReference())
                .build())
        .getId();
  }

  @Override
  public String changeOrder(LimitOrder limitOrder) throws ExchangeException {

    BitmexPrivateOrder order =
        replaceOrder(
            new BitmexReplaceOrderParameters.Builder()
                .setOrderId(limitOrder.getId())
                .setOrderQuantity(limitOrder.getOriginalAmount())
                .setPrice(limitOrder.getLimitPrice())
                .build());
    return order.getId();
  }

  @Override
  public boolean cancelOrder(CancelOrderParams params) throws ExchangeException {
    if (params instanceof CancelAllOrders) {
      List<BitmexPrivateOrder> orders = cancelAllOrders();
      return !orders.isEmpty();
    }

    Validate.isInstanceOf(CancelOrderByIdParams.class, params);
    String orderId = ((CancelOrderByIdParams) params).getOrderId();

    List<BitmexPrivateOrder> orders = cancelBitmexOrder(orderId);

    return orders.isEmpty() || orders.get(0).getId().equals(orderId);
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    String[] orderIds = TradeService.toOrderIds(orderQueryParams);

    FilterParam filterParam = FilterParam.builder().orderIds(Arrays.asList(orderIds)).build();

    List<BitmexPrivateOrder> privateOrders = getBitmexOrders(null, filterParam, null, null, null);
    return privateOrders.stream()
        .map(BitmexAdapters::toOrder)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new BitmexTradeHistoryParams();
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    FilterParam.FilterParamBuilder filterParamBuilder = FilterParam.builder();

    if (params instanceof TradeHistoryParamOrderId) {
      filterParamBuilder.orderId(((TradeHistoryParamOrderId) params).getOrderId());
    }

    String symbol = null;
    if (params instanceof TradeHistoryParamCurrencyPair) {
      symbol = BitmexAdapters.toString(((CurrencyPairParam) params).getCurrencyPair());
    }
    Long start = null;
    if (params instanceof TradeHistoryParamOffset) {
      start = ((TradeHistoryParamOffset) params).getOffset();
    }
    Date startTime = null;
    Date endTime = null;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan timeSpan = (TradeHistoryParamsTimeSpan) params;
      startTime = timeSpan.getStartTime();
      endTime = timeSpan.getEndTime();
    }
    int count = 100;
    if (params instanceof TradeHistoryParamLimit) {
      TradeHistoryParamLimit limit = (TradeHistoryParamLimit) params;
      if (limit.getLimit() != null) {
        count = limit.getLimit();
      }
    }

    boolean reverse =
        (params instanceof TradeHistoryParamsSorted)
            && ((TradeHistoryParamsSorted) params).getOrder()
                == TradeHistoryParamsSorted.Order.desc;

    List<UserTrade> userTrades =
        getTradeHistory(
                symbol, filterParamBuilder.build(), null, count, start, reverse, startTime, endTime)
            .stream()
            .map(BitmexAdapters::toUserTrade)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    return new UserTrades(userTrades, TradeSortType.SortByTimestamp);
  }
}
