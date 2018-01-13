package org.xchange.bitz.service;

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

public class BitZTradeService extends BitZTradeServiceRaw implements TradeService {

	public BitZTradeService(Exchange exchange) {
		super(exchange);
	}

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return "";
  }
	
  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }
  
  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return null;
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    return null;
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    return false;
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    return false;
  }
  
  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }
  
  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    throw new NotAvailableFromExchangeException();
  }
  
  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    throw new NotAvailableFromExchangeException();
  }
  
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    throw new NotAvailableFromExchangeException();
  }
}
