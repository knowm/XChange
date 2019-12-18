package org.knowm.xchange.deribit.v2.service;

import java.io.IOException;
import org.knowm.xchange.deribit.v2.DeribitExchange;
import org.knowm.xchange.deribit.v2.dto.trade.Order;
import org.knowm.xchange.deribit.v2.dto.trade.OrderState;
import org.knowm.xchange.service.trade.TradeService;

public class DeribitTradeService extends DeribitTradeServiceRaw implements TradeService {

  public DeribitTradeService(DeribitExchange exchange) {
    super(exchange);
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    Order cancel = super.cancel(orderId);
    return cancel.getOrderState() == OrderState.cancelled;
  }
}
