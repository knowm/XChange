package org.knowm.xchange.gateio.service;

import org.apache.commons.lang3.Validate;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.GateioErrorAdapter;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.dto.GateioException;
import org.knowm.xchange.gateio.dto.account.GateioOrder;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParamInstrument;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

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
      return List.of(GateioAdapters.toOrder(gateioOrder));
    }
    catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }
  }

  @Override
  public TradeHistoryParamCurrencyPair createTradeHistoryParams() {
    return new DefaultTradeHistoryParamCurrencyPair();
  }


}
