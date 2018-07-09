package org.knowm.xchange.cobinhood.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cobinhood.dto.CobinhoodAdapters;
import org.knowm.xchange.cobinhood.dto.CobinhoodOrderSide;
import org.knowm.xchange.cobinhood.dto.CobinhoodOrderType;
import org.knowm.xchange.cobinhood.dto.CobinhoodResponse;
import org.knowm.xchange.cobinhood.dto.trading.CobinhoodOpenOrdersParams;
import org.knowm.xchange.cobinhood.dto.trading.CobinhoodOrder;
import org.knowm.xchange.cobinhood.dto.trading.CobinhoodOrders;
import org.knowm.xchange.cobinhood.dto.trading.CobinhoodPlaceOrderRequest;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;

import java.io.IOException;
import java.util.Collection;

public class CobinhoodTradeServiceRaw extends CobinhoodBaseService {

  protected CobinhoodTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  protected CobinhoodResponse<CobinhoodOrder.Container> placeCobinhoodLimitOrder(LimitOrder order)
      throws IOException {
    CobinhoodPlaceOrderRequest request =
        new CobinhoodPlaceOrderRequest(
            CobinhoodAdapters.adaptCurrencyPair(order.getCurrencyPair()),
            CobinhoodOrderSide.fromOrderType(order.getType()).name(),
            CobinhoodOrderType.limit,
            order.getLimitPrice().toString(),
            order.getOriginalAmount().toString());

    return cobinhood.placeOrder(apiKey, exchange.getNonceFactory(), request);
  }

  protected CobinhoodResponse<Void> cancelCobinhoodOrder(String orderId) {
    return cobinhood.cancelOrder(apiKey, exchange.getNonceFactory(), orderId);
  }

  protected Collection<Order> getCobinhoodOrder(String[] orderIds) {
    return null;
  }

  protected CobinhoodResponse<CobinhoodOrders> getCobinhoodOpenOrders(
      CobinhoodOpenOrdersParams params) {
    return cobinhood.getAllOrders(apiKey, params.getPairId(), params.getLimit());
  }
}
