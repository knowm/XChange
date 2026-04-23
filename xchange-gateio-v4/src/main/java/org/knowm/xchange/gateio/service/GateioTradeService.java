package org.knowm.xchange.gateio.service;

import jakarta.ws.rs.NotSupportedException;
import org.apache.commons.lang3.Validate;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.GateioErrorAdapter;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.dto.GateioException;
import org.knowm.xchange.gateio.dto.trade.GateioCancelOrderParams;
import org.knowm.xchange.gateio.dto.trade.GateioFuturesOrderResponse;
import org.knowm.xchange.gateio.dto.trade.GateioSpotOrderResponse;
import org.knowm.xchange.gateio.service.params.GateioTradeHistoryParams;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultCancelOrderByInstrumentAndIdParams;
import org.knowm.xchange.service.trade.params.InstrumentParam;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParamInstrument;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GateioTradeService extends GateioTradeServiceRaw implements TradeService {

  public GateioTradeService(GateioExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    Validate.isInstanceOf(InstrumentParam.class, params);
    Instrument instrument = ((InstrumentParam) params).getInstrument();
    List<LimitOrder> limitOrders =
        listOrders(instrument, OrderStatus.OPEN).stream()
            .map(GateioAdapters::toOrder)
            .map(LimitOrder.class::cast)
            .collect(Collectors.toList());
    return new OpenOrders(limitOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    try {
      if (marketOrder.getInstrument() instanceof FuturesContract) {
        GateioFuturesOrderResponse order = createFuturesOrder(GateioAdapters.toGateioFuturesOrder(marketOrder, exchange.getExchangeMetaData().getInstruments().get(marketOrder.getInstrument()).getContractValue()));
        return String.valueOf(order.getId());
      } else {
        GateioSpotOrderResponse order = createOrder(GateioAdapters.toGateioSpotOrderRequest(marketOrder));
        return order.getId();
      }
    } catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    try {
      if (limitOrder.getInstrument() instanceof FuturesContract) {
        GateioFuturesOrderResponse order = createFuturesOrder(GateioAdapters.toGateioFuturesOrder(limitOrder
            , exchange.getExchangeMetaData().getInstruments().get(limitOrder.getInstrument()).getContractValue()));
        return String.valueOf(order.getId());
      } else {
        GateioSpotOrderResponse order = createOrder(GateioAdapters.toGateioSpotOrderRequest(limitOrder));
        return order.getId();
      }
    } catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    // todo: implement getting of several orders
    Validate.validState(orderQueryParams.length == 1);
    Validate.isInstanceOf(OrderQueryParamInstrument.class, orderQueryParams[0]);

    OrderQueryParamInstrument params = (OrderQueryParamInstrument) orderQueryParams[0];

    try {
      if (params.getInstrument() instanceof FuturesContract) {
        GateioFuturesOrderResponse gateioOrder = getFuturesOrder(params.getOrderId(), params.getInstrument());
        return Collections.singletonList(GateioAdapters.toOrder(gateioOrder));
      } else {
        GateioSpotOrderResponse gateioOrder = getOrder(params.getOrderId(), params.getInstrument());
        return Collections.singletonList(GateioAdapters.toOrder(gateioOrder));
      }
    } catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }

  /**
   * it's possbile to use UserReferenceId(text in Gateio) as orderId
   */
  public Order cancelOrder(String orderId, Instrument instrument) throws IOException {
    try {
      if (instrument instanceof FuturesContract) {
        GateioFuturesOrderResponse gateioOrder = cancelFuturesOrderRaw(orderId, instrument);
        return GateioAdapters.toOrder(gateioOrder);
      } else {
        GateioSpotOrderResponse gateioOrder = cancelOrderRaw(orderId, instrument);
        return GateioAdapters.toOrder(gateioOrder);
      }
    } catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    try {
      String id = "";
      Instrument instrument = null;
      if (orderParams instanceof DefaultCancelOrderByInstrumentAndIdParams) {
        DefaultCancelOrderByInstrumentAndIdParams params =
            (DefaultCancelOrderByInstrumentAndIdParams) orderParams;
        id = params.getOrderId();
        instrument = params.getInstrument();
      } else {
        if (orderParams instanceof GateioCancelOrderParams) {
          GateioCancelOrderParams params = (GateioCancelOrderParams) orderParams;
          instrument = params.getInstrument();
          if (params.getUserReference() != null) {
            id = params.getUserReference();
          } else if (params.getOrderId() != null) {
            id = params.getOrderId();
          }
        }
      }
      if (!id.isEmpty() && instrument != null) {
        Order order = cancelOrder(id, instrument);
        return order.getStatus() == OrderStatus.CANCELED;
      } else throw new NotSupportedException("id or instrument is empty");
    } catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }

  @Override
  public String changeOrder(LimitOrder limitOrder) throws IOException {
    try {
      Map<String, Object> request = new HashMap<>();
      request.put("amount", limitOrder.getOriginalAmount());
      request.put("price", limitOrder.getLimitPrice());
      String id;
      if (limitOrder.getUserReference() != null) {
        id = limitOrder.getUserReference();
      } else id = limitOrder.getId();
      if (limitOrder.getInstrument() instanceof FuturesContract) {
        GateioFuturesOrderResponse response = amendFuturesOrder(id, limitOrder.getInstrument(), request);
        return String.valueOf(response.getId());
      } else {
        GateioSpotOrderResponse response = amendSpotOrder(id, limitOrder.getInstrument(), request);
        return response.getId();
      }
    } catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    try {
      List<UserTrade> userTradeList =
          getGateioUserTrades(params).stream()
              .map(GateioAdapters::toUserTrade)
              .collect(Collectors.toList());
      return new UserTrades(userTradeList, TradeSortType.SortByID);
    } catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }

  @Override
  public Class[] getRequiredCancelOrderParamClasses() {
    return new Class[]{DefaultCancelOrderByInstrumentAndIdParams.class};
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return GateioTradeHistoryParams.builder().build();
  }
}
