package org.knowm.xchange.coindeal.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindeal.CoindealAdapters;
import org.knowm.xchange.coindeal.CoindealErrorAdapter;
import org.knowm.xchange.coindeal.dto.CoindealException;
import org.knowm.xchange.coindeal.dto.trade.CoindealOrder;
import org.knowm.xchange.coindeal.dto.trade.CoindealTradeHistory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;

public class CoindealTradeServiceRaw extends CoindealBaseService {

  public CoindealTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<CoindealTradeHistory> getCoindealTradeHistory(
      CurrencyPair currencyPair, Integer limit) throws IOException {
    try {
      return coindeal.getTradeHistory(
          basicAuthentication, CoindealAdapters.adaptCurrencyPairToString(currencyPair), limit);
    } catch (CoindealException e) {
      throw CoindealErrorAdapter.adapt(e);
    }
  }

  public CoindealOrder placeOrder(LimitOrder limitOrder) throws IOException {
    try {
      return coindeal.placeOrder(
          basicAuthentication,
          CoindealAdapters.adaptCurrencyPairToString(limitOrder.getCurrencyPair()),
          CoindealAdapters.adaptOrderType(limitOrder.getType()),
          "limit",
          "GTC",
          limitOrder.getOriginalAmount().doubleValue(),
          limitOrder.getLimitPrice().doubleValue());
    } catch (CoindealException e) {
      throw CoindealErrorAdapter.adapt(e);
    }
  }

  public List<CoindealOrder> cancelCoindealOrders(CurrencyPair currencyPair) throws IOException {
    try {
      return coindeal.cancelOrders(
          basicAuthentication, CoindealAdapters.adaptCurrencyPairToString(currencyPair));
    } catch (CoindealException e) {
      throw CoindealErrorAdapter.adapt(e);
    }
  }

  public CoindealOrder cancelCoindealOrderById(String orderId) throws IOException {
    try {
      return coindeal.cancelOrderById(basicAuthentication, orderId);
    } catch (CoindealException e) {
      throw CoindealErrorAdapter.adapt(e);
    }
  }

  public List<CoindealOrder> getCoindealActiveOrders(CurrencyPair currencyPair) throws IOException {
    try {
      return coindeal.getActiveOrders(
          basicAuthentication, CoindealAdapters.adaptCurrencyPairToString(currencyPair));
    } catch (CoindealException e) {
      throw CoindealErrorAdapter.adapt(e);
    }
  }

  public CoindealOrder getCoindealOrderById(String orderId) throws IOException {
    try {
      return coindeal.getOrderById(basicAuthentication, orderId);
    } catch (CoindealException e) {
      throw CoindealErrorAdapter.adapt(e);
    }
  }
}
