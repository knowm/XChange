package org.knowm.xchange.lakebtc.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.lakebtc.dto.trade.LakeBTCCancelResponse;
import org.knowm.xchange.lakebtc.dto.trade.LakeBTCOrderResponse;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class LakeBTCTradeService extends LakeBTCTradeServiceRaw implements TradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public LakeBTCTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    final LakeBTCOrderResponse response = placeLakeBTCMarketOrder(marketOrder);
    return response.getId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    final LakeBTCOrderResponse response = placeLakeBTCLimitOrder(limitOrder);
    return response.getId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    final LakeBTCCancelResponse response = cancelLakeBTCOrder(orderId);
    return Boolean.valueOf(response.getResult());
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else {
      return false;
    }
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }
}
