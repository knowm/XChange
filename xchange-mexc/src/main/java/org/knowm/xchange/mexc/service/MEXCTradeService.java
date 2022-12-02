package org.knowm.xchange.mexc.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.mexc.MEXCAdapters;
import org.knowm.xchange.mexc.dto.MEXCResult;
import org.knowm.xchange.mexc.dto.trade.MEXCOrder;
import org.knowm.xchange.mexc.dto.trade.MEXCOrderRequestPayload;
import org.knowm.xchange.service.trade.TradeService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MEXCTradeService extends MEXCTradeServiceRaw implements TradeService {

  public MEXCTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    try {
      MEXCOrderRequestPayload orderRequestPayload = MEXCAdapters.adaptOrder(limitOrder);
      return placeOrder(orderRequestPayload).getData();
    } catch (MEXCException e) {
      throw new ExchangeException(e);
    }
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    try {
      MEXCResult<List<MEXCOrder>> ordersResult = getOrders(Arrays.asList(orderIds));
      return ordersResult.getData().stream().map(MEXCAdapters::adaptOrder).collect(Collectors.toList());
    } catch (MEXCException e) {
      throw new ExchangeException(e);
    }
  }

}
