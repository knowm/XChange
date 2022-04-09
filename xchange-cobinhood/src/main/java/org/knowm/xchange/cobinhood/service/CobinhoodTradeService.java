package org.knowm.xchange.cobinhood.service;

import java.io.IOException;
import java.util.Collection;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cobinhood.CobinhoodAdapters;
import org.knowm.xchange.cobinhood.dto.trading.CobinhoodOpenOrdersParams;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

public class CobinhoodTradeService extends CobinhoodTradeServiceRaw implements TradeService {
  public CobinhoodTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return placeCobinhoodLimitOrder(limitOrder).getResult().getOrder().getId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    return cancelCobinhoodOrder(orderId).isSuccess();
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    return getCobinhoodOrder(TradeService.toOrderIds(orderQueryParams));
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(new CobinhoodOpenOrdersParams(null));
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    return CobinhoodAdapters.adaptOpenOrders(
        getCobinhoodOpenOrders((CobinhoodOpenOrdersParams) params).getResult());
  }

  public OpenOrders getOpenOrders(CurrencyPair pair) throws IOException {
    return CobinhoodAdapters.adaptOpenOrders(
        getCobinhoodOpenOrders(new CobinhoodOpenOrdersParams(pair)).getResult());
  }
}
