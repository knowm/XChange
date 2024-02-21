package org.knowm.xchange.gateio.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.Validate;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.GateioErrorAdapter;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.dto.GateioException;
import org.knowm.xchange.gateio.dto.account.GateioOrder;
import org.knowm.xchange.gateio.service.params.GateioTradeHistoryParams;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultCancelOrderByInstrumentAndIdParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParamInstrument;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

public class GateioTradeService extends GateioTradeServiceRaw implements TradeService {

  public GateioTradeService(GateioExchange exchange) {
    super(exchange);
  }


  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    try {
      GateioOrder order = createOrder(GateioAdapters.toGateioOrder(marketOrder));
      return order.getId();
    }
    catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }


  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    try {
      GateioOrder order = createOrder(GateioAdapters.toGateioOrder(limitOrder));
      return order.getId();
    }
    catch (GateioException e) {
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
      GateioOrder gateioOrder = getOrder(params.getOrderId(), params.getInstrument());
      return Collections.singletonList(GateioAdapters.toOrder(gateioOrder));
    }
    catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }


  public Order cancelOrder(String orderId, Instrument instrument) throws IOException {
    try {
      GateioOrder gateioOrder = cancelOrderRaw(orderId, instrument);
      return GateioAdapters.toOrder(gateioOrder);
    }
    catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }


  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    Validate.isInstanceOf(DefaultCancelOrderByInstrumentAndIdParams.class, orderParams);
    DefaultCancelOrderByInstrumentAndIdParams params = (DefaultCancelOrderByInstrumentAndIdParams) orderParams;

    try {
      Order order = cancelOrder(params.getOrderId(), params.getInstrument());
      return order.getStatus() == OrderStatus.CANCELED;
    }
    catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    try {
      List<UserTrade> userTradeList = getGateioUserTrades(params).stream()
          .map(GateioAdapters::toUserTrade)
          .collect(Collectors.toList());
      return new UserTrades(userTradeList, TradeSortType.SortByID);
    }
    catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }

  @Override
  public Class[] getRequiredCancelOrderParamClasses() {
    return new Class[] {DefaultCancelOrderByInstrumentAndIdParams.class};
  }


  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return GateioTradeHistoryParams.builder().build();
  }


}
