package org.knowm.xchange.bibox.service;

import java.io.IOException;
import java.util.Collection;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

/**
 * @author odrotleff
 */
public class BiboxTradeService extends BiboxTradeServiceRaw implements TradeService {

  public BiboxTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return placeBiboxLimitOrder(limitOrder).toString();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void verifyOrder(MarketOrder marketOrder) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String placeStopOrder(StopOrder arg0) throws IOException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void verifyOrder(LimitOrder limitOrder) {
    // TODO Auto-generated method stub
    
  }
}
