package org.knowm.xchange.kucoin.service;

import java.io.IOException;
import java.util.Collection;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class KucoinTradeService extends KucoinTradeServiceRaw implements TradeService {

  public KucoinTradeService(Exchange exchange) {
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
    
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    
    return order(limitOrder).getData().getOrderOid();
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
  public void verifyOrder(LimitOrder limitOrder) {
    // TODO Auto-generated method stub
    
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

}
