package org.knowm.xchange.upbit.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.upbit.UpbitAdapters;
import org.knowm.xchange.upbit.dto.trade.UpbitOrderResponse;

public class UpbitTradeService extends UpbitTradeServiceRaw implements TradeService {

  /** @param exchange */
  public UpbitTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return super.limitOrder(limitOrder).getUuid();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    return super.cancelOrderRaw(orderId) != null;
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    ArrayList<Order> rtn = new ArrayList<>();
    for (String orderId : orderIds) {
      UpbitOrderResponse res = super.getOrderRaw(orderId);
      rtn.add(UpbitAdapters.adaptOrderInfo(res));
    }
    return rtn;
  }
}
