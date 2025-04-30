package org.knowm.xchange.bybit.service;

import static org.knowm.xchange.bybit.BybitAdapters.adaptBybitOrderDetails;
import static org.knowm.xchange.bybit.BybitAdapters.convertToBybitSymbol;
import static org.knowm.xchange.bybit.BybitAdapters.createBybitExceptionFromResult;
import static org.knowm.xchange.bybit.dto.trade.details.BybitHedgeMode.TWOWAY;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.knowm.xchange.bybit.BybitAdapters;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.BybitCancelAllOrdersResponse;
import org.knowm.xchange.bybit.dto.trade.BybitCancelAllOrdersParams;
import org.knowm.xchange.bybit.dto.trade.BybitCancelOrderParams;
import org.knowm.xchange.bybit.dto.trade.BybitOpenOrdersParam;
import org.knowm.xchange.bybit.dto.trade.BybitOrderResponse;
import org.knowm.xchange.bybit.dto.trade.BybitOrderType;
import org.knowm.xchange.bybit.dto.trade.details.BybitHedgeMode;
import org.knowm.xchange.bybit.dto.trade.details.BybitOrderDetail;
import org.knowm.xchange.bybit.dto.trade.details.BybitOrderDetails;
import org.knowm.xchange.bybit.dto.trade.details.BybitTimeInForce;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.IOrderFlags;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelAllOrders;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderByInstrument;
import org.knowm.xchange.service.trade.params.CancelOrderByUserReferenceParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class BybitTradeService extends BybitTradeServiceRaw implements TradeService {

  public BybitTradeService(BybitExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    BybitCategory category = BybitAdapters.getCategory(marketOrder.getInstrument());
    int positionIdx = getPositionIdx(marketOrder);
    boolean reduceOnly =
        marketOrder.getType().equals(OrderType.EXIT_ASK)
            || marketOrder.getType().equals(OrderType.EXIT_BID);
    BybitResult<BybitOrderResponse> orderResponseBybitResult =
        placeOrder(
            category,
            convertToBybitSymbol(marketOrder.getInstrument()),
            BybitAdapters.getSideString(marketOrder.getType()),
            BybitOrderType.MARKET,
            marketOrder.getOriginalAmount(),
            null,
            marketOrder.getUserReference(),
            null,
            null,
            null,
            null,
            reduceOnly,
            positionIdx,
            BybitTimeInForce.IOC);
    return orderResponseBybitResult.getResult().getOrderId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    BybitCategory category = BybitAdapters.getCategory(limitOrder.getInstrument());
    BybitTimeInForce timeInForce =
        getOrderFlag(limitOrder, BybitTimeInForce.class).orElse(BybitTimeInForce.GTC);
    int positionIdx = getPositionIdx(limitOrder);
    boolean reduceOnly =
        limitOrder.getType().equals(OrderType.EXIT_ASK)
            || limitOrder.getType().equals(OrderType.EXIT_BID);
    BybitResult<BybitOrderResponse> orderResponseBybitResult =
        placeOrder(
            category,
            convertToBybitSymbol(limitOrder.getInstrument()),
            BybitAdapters.getSideString(limitOrder.getType()),
            BybitOrderType.LIMIT,
            limitOrder.getOriginalAmount(),
            limitOrder.getLimitPrice(),
            limitOrder.getUserReference(),
            null,
            null,
            null,
            null,
            reduceOnly,
            positionIdx,
            timeInForce);
    return orderResponseBybitResult.getResult().getOrderId();
  }

  private <T extends IOrderFlags> Optional<T> getOrderFlag(Order order, Class<T> clazz) {
    return (Optional<T>)
        order.getOrderFlags().stream()
            .filter(flag -> clazz.isAssignableFrom(flag.getClass()))
            .findFirst();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    List<Order> results = new ArrayList<>();

    for (String orderId : orderIds) {
      for (BybitCategory category : BybitCategory.values()) {

        BybitResult<BybitOrderDetails<BybitOrderDetail>> bybitOrder =
            getBybitOrder(category, null, orderId);

        if (bybitOrder.getResult().getCategory().equals(category)
            && !bybitOrder.getResult().getList().isEmpty()) {
          BybitOrderDetail bybitOrderDetail = bybitOrder.getResult().getList().get(0);
          Order order = adaptBybitOrderDetails(bybitOrderDetail, category);
          results.add(order);
        }
      }
    }
    return results;
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    if (params instanceof BybitOpenOrdersParam) {
      BybitCategory category = ((BybitOpenOrdersParam) params).getCategory();
      Instrument instrument = ((BybitOpenOrdersParam) params).getInstrument();
      if (category == null) {
        throw new UnsupportedOperationException("Category is required");
      }
      BybitResult<BybitOrderDetails<BybitOrderDetail>> response =
          getBybitOrder(category, instrument, null);
      List<LimitOrder> limitOrders = new ArrayList<>();
      if (response != null) {
        for (BybitOrderDetail orderDetail : response.getResult().getList())
          limitOrders.add((LimitOrder) adaptBybitOrderDetails(orderDetail, category));
      } else {
        throw new UnsupportedOperationException(
            "Params must be instance of BybitCancelAllOrdersParams");
      }
      return new OpenOrders(limitOrders);
    }
    return null;
  }

  @Override
  public String changeOrder(LimitOrder order) throws IOException {
    BybitCategory category = BybitAdapters.getCategory(order.getInstrument());
    BybitResult<BybitOrderResponse> response =
        amendOrder(
            category,
            convertToBybitSymbol(order.getInstrument()),
            order.getId(),
            order.getUserReference(),
            null,
            order.getOriginalAmount().toString(),
            order.getLimitPrice().toString(),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null);
    if (response != null) {
      return response.getResult().getOrderId();
    }
    return "";
  }

  @Override
  public Class[] getRequiredCancelOrderParamClasses() {
    return new Class[] {
      CancelOrderByIdParams.class,
      CancelOrderByInstrument.class,
      CancelOrderByUserReferenceParams.class
    };
  }

  @Override
  public boolean cancelOrder(CancelOrderParams params) throws IOException {
    if (params instanceof BybitCancelOrderParams) {
      Instrument instrument = ((BybitCancelOrderParams) params).getInstrument();
      BybitCategory category = BybitAdapters.getCategory(instrument);
      String orderId = ((BybitCancelOrderParams) params).getOrderId();
      String userReference = ((BybitCancelOrderParams) params).getUserReference();
      if (instrument == null) {
        throw new UnsupportedOperationException(
            "Instrument and (orderId or userReference) required");
      }
      if ((orderId == null || orderId.isEmpty())
          && (userReference == null || userReference.isEmpty())) {
        throw new UnsupportedOperationException("OrderId or userReference is required");
      }
      BybitResult<BybitOrderResponse> response =
          cancelOrder(category, convertToBybitSymbol(instrument), orderId, userReference);
      if (!response.isSuccess()) {
        throw createBybitExceptionFromResult(response);
      }
      return true;
    } else {
      throw new UnsupportedOperationException("Params must be instance of BybitCancelOrderParams");
    }
  }

  @Override
  public Collection<String> cancelAllOrders(CancelAllOrders params) throws IOException {
    if (params instanceof BybitCancelAllOrdersParams) {
      Instrument instrument = ((BybitCancelAllOrdersParams) params).getSymbol();
      BybitCategory category = ((BybitCancelAllOrdersParams) params).getCategory();
      String baseCoin = ((BybitCancelAllOrdersParams) params).getBaseCoin();
      String settleCoin = ((BybitCancelAllOrdersParams) params).getSettleCoin();
      String orderFilter = ((BybitCancelAllOrdersParams) params).getOrderFilter();
      String stopOrderType = ((BybitCancelAllOrdersParams) params).getStopOrderType();
      if (category == null) {
        throw new UnsupportedOperationException("Category is required");
      }
      String symbol = "";
      BybitResult<BybitCancelAllOrdersResponse> response;
      switch (category) {
        case SPOT:
        case OPTION:
          break;
        case LINEAR:
        case INVERSE:
          {
            if (instrument != null) {
              symbol = convertToBybitSymbol(instrument);
            } else if (baseCoin == null && settleCoin == null) {
              throw new UnsupportedOperationException(
                  "For Linear or Inverse category, required instrument or baseCoin or settleCoin");
            }
          }
      }

      response =
          cancelAllOrders(
              category.getValue(), symbol, baseCoin, settleCoin, orderFilter, stopOrderType);
      if (response != null) {
        return response.getResult().getList().stream()
            .map(BybitOrderResponse::getOrderId)
            .collect(Collectors.toList());
      }
    } else {
      throw new UnsupportedOperationException(
          "Params must be instance of BybitCancelAllOrdersParams");
    }
    return null;
  }

  private int getPositionIdx(Order order) {
    BybitHedgeMode hedgeMode =
        getOrderFlag(order, BybitHedgeMode.class).orElse(BybitHedgeMode.ONEWAY);
    int positionIdx = 0;
    if (hedgeMode.equals(TWOWAY)) {
      positionIdx = 1;
      switch (order.getType()) {
        case ASK:
        case EXIT_ASK:
          {
            positionIdx = 2;
            break;
          }
        case BID:
        case EXIT_BID:
          {
            break;
          }
      }
    }
    return positionIdx;
  }
}
