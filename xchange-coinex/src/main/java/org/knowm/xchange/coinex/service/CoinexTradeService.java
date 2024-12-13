package org.knowm.xchange.coinex.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.Validate;
import org.knowm.xchange.coinex.CoinexAdapters;
import org.knowm.xchange.coinex.CoinexErrorAdapter;
import org.knowm.xchange.coinex.CoinexExchange;
import org.knowm.xchange.coinex.dto.CoinexException;
import org.knowm.xchange.coinex.dto.account.CoinexOrder;
import org.knowm.xchange.coinex.service.params.CoinexOpenOrdersParams;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultCancelOrderByInstrumentAndIdParams;
import org.knowm.xchange.service.trade.params.InstrumentParam;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamLimit;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamOffset;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParamInstrument;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

public class CoinexTradeService extends CoinexTradeServiceRaw implements TradeService {

  public CoinexTradeService(CoinexExchange exchange) {
    super(exchange);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    try {
      CoinexOrder coinexOrder = createOrder(CoinexAdapters.toCoinexOrder(marketOrder));
      return String.valueOf(coinexOrder.getOrderId());
    } catch (CoinexException e) {
      throw CoinexErrorAdapter.adapt(e);
    }
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(null);
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    CoinexOpenOrdersParams.CoinexOpenOrdersParamsBuilder builder = CoinexOpenOrdersParams.builder();

    if (params instanceof InstrumentParam) {
      builder.instrument(((InstrumentParam) params).getInstrument());
    }

    if (params instanceof OpenOrdersParamLimit) {
      builder.limit(((OpenOrdersParamLimit) params).getLimit());
    }

    if (params instanceof OpenOrdersParamOffset) {
      builder.offset(((OpenOrdersParamOffset) params).getOffset());
    }

    List<LimitOrder> limitOrders = pendingOrders(builder.build()).stream()
        .map(CoinexAdapters::toOrder)
        .map(LimitOrder.class::cast)
        .collect(Collectors.toList());

    return new OpenOrders(limitOrders);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    try {
      CoinexOrder coinexOrder = createOrder(CoinexAdapters.toCoinexOrder(limitOrder));
      return String.valueOf(coinexOrder.getOrderId());
    } catch (CoinexException e) {
      throw CoinexErrorAdapter.adapt(e);
    }
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    Validate.validState(orderQueryParams.length == 1);
    Validate.isInstanceOf(OrderQueryParamInstrument.class, orderQueryParams[0]);

    OrderQueryParamInstrument params = (OrderQueryParamInstrument) orderQueryParams[0];

    try {
      CoinexOrder order = orderStatus(params.getInstrument(), params.getOrderId());
      return Collections.singletonList(CoinexAdapters.toOrder(order));
    } catch (CoinexException e) {
      throw CoinexErrorAdapter.adapt(e);
    }
  }
}
