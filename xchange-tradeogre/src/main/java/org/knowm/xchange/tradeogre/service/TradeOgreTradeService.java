package org.knowm.xchange.tradeogre.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParam;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.tradeogre.TradeOgreAdapters;
import org.knowm.xchange.tradeogre.TradeOgreExchange;
import org.knowm.xchange.tradeogre.dto.trade.TradeOgreTrade;

public class TradeOgreTradeService extends TradeOgreTradeServiceRaw implements TradeService {

  public TradeOgreTradeService(TradeOgreExchange exchange) {
    super(exchange);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return placeOrder(limitOrder);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    List<LimitOrder> orders =
        TradeOgreAdapters.adaptOpenOrders(getOrders()).getOpenOrders().stream()
            .filter(params != null ? params::accept : o -> true)
            .collect(Collectors.toList());
    return new OpenOrders(orders);
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParam();
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    List<TradeOgreTrade> tradeHistory = getTradeHistory();
    return TradeOgreAdapters.adaptTradeHistory(tradeHistory);
  }
}
