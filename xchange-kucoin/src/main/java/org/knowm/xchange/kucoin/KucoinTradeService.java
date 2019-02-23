package org.knowm.xchange.kucoin;

import java.io.IOException;
import java.util.Collection;

import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.kucoin.sdk.rest.response.OrderResponse;

public class KucoinTradeService extends KucoinTradeServiceRaw implements TradeService {

  KucoinTradeService(KucoinExchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return build(getKucoinOpenOrders(null, 0, 100).getItems());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams genericParams) throws IOException {
    if (!(genericParams instanceof OpenOrdersParamCurrencyPair))
      throw new ExchangeException("Only currency pair parameters are currently supported.");
    OpenOrdersParamCurrencyPair params = (OpenOrdersParamCurrencyPair) genericParams;
    String symbol = KucoinAdapters.adaptCurrencyPair(params.getCurrencyPair());
    return build(getKucoinOpenOrders(symbol, 1, 100).getItems());
  }

  @Override
  public OpenOrdersParamCurrencyPair createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  private OpenOrders build(Collection<OrderResponse> orders) {
    Builder<LimitOrder> openOrders = ImmutableList.builder();
    Builder<Order> hiddenOrders = ImmutableList.builder();
    orders.stream()
      .map(KucoinAdapters::adaptOrder)
      .forEach(o -> {
        if (o instanceof LimitOrder) {
          openOrders.add((LimitOrder) o);
        } else {
          hiddenOrders.add(o);
        }
      });
    return new OpenOrders(openOrders.build(), hiddenOrders.build());
  }
}
