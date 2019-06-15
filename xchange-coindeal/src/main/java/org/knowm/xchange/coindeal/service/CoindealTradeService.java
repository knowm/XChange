package org.knowm.xchange.coindeal.service;

import java.io.IOException;
import java.util.Collection;

import com.sun.istack.internal.NotNull;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindeal.CoindealAdapters;
import org.knowm.xchange.coindeal.dto.CoindealException;
import org.knowm.xchange.coindeal.dto.trade.CoindealOrder;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsAll;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

public final class CoindealTradeService extends CoindealTradeServiceRaw implements TradeService {

  public CoindealTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    try{
      return CoindealAdapters.adaptToOpenOrders(getCoindealOpenOrders(null));
    }catch (CoindealException e){
      throw new ExchangeException(e.getMessage());
    }
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    try {
      if (params instanceof OpenOrdersParamCurrencyPair) {
        return CoindealAdapters.adaptToOpenOrders(getCoindealOpenOrders(((OpenOrdersParamCurrencyPair) params).getCurrencyPair()));
      } else {
        throw new IOException("OpenOrdersParams must be instance of OpenOrdersParamCurrencyPair.");
      }
    }catch (CoindealException e){
      throw new ExchangeException(e.getMessage());
    }
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    try {
      return placeCoindealOrder(limitOrder).getClientOrderId();
    }catch (CoindealException e){
      throw new ExchangeException(e.getMessage());
    }
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    try {
      return deleteCoindealOrderById(orderId) != null;
    }catch (CoindealException e){
      throw new ExchangeException(e.getMessage());
    }
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    try{
      if(orderParams instanceof CancelOrderByCurrencyPair){
        return deleteCoindealOrders(((CancelOrderByCurrencyPair)orderParams).getCurrencyPair()) != null;
      }else {
        throw new IOException("CancelOrderParams must be instance of CancelOrderByCurrencyPair.");
      }
    }catch (CoindealException e){
      throw new ExchangeException(e.getMessage());
    }
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    try {
      return CoindealAdapters.adaptToUserTrades(getCoindealTradeHistory((TradeHistoryParamsAll) params));
    }catch (CoindealException e){
      throw new ExchangeException(e.getMessage());
    }
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new TradeHistoryParamsAll();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public void verifyOrder(LimitOrder limitOrder) {}

  @Override
  public void verifyOrder(MarketOrder marketOrder) {}

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
