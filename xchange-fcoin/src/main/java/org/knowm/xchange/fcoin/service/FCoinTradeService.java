package org.knowm.xchange.fcoin.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.fcoin.dto.trade.FCoinSide;
import org.knowm.xchange.service.trade.TradeService;

public class FCoinTradeService extends FCoinTradeServiceRaw implements TradeService {

  public FCoinTradeService(Exchange exchange) {
    super(exchange);
  }

  private FCoinSide getSide(Order.OrderType type) {
    switch (type) {
      case ASK:
      case EXIT_BID:
        return FCoinSide.sell;
      case BID:
      case EXIT_ASK:
        return FCoinSide.buy;
    }
    return null;
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    String symbol =
        limitOrder.getCurrencyPair().base.getCurrencyCode().toLowerCase()
            + limitOrder.getCurrencyPair().counter.getCurrencyCode().toLowerCase();
    FCoinSide side = getSide(limitOrder.getType());
    return placeLimitOrder(
        symbol, limitOrder.getOriginalAmount(), limitOrder.getLimitPrice(), side);
  }
}
