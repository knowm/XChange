package org.knowm.xchange.liqui.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.liqui.LiquiAdapters;
import org.knowm.xchange.liqui.dto.LiquiException;
import org.knowm.xchange.liqui.dto.trade.LiquiCancelOrder;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LiquiTradeService extends LiquiTradeServiceRaw implements TradeService {

  private static final Logger LOG = LoggerFactory.getLogger(LiquiTradeService.class);

  public LiquiTradeService(final Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return LiquiAdapters.adaptActiveOrders(getActiveOrders());
  }

  @Override
  public OpenOrders getOpenOrders(final OpenOrdersParams params) throws IOException {
    if (params instanceof OpenOrdersParamCurrencyPair) {
      return LiquiAdapters.adaptActiveOrders(getActiveOrders(((OpenOrdersParamCurrencyPair) params).getCurrencyPair()));
    }

    throw new LiquiException("Unable to get open orders with the provided params: " + params);
  }

  @Override
  public String placeMarketOrder(final MarketOrder marketOrder) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(final LimitOrder limitOrder) throws IOException {
    return LiquiAdapters.adaptOrderId(placeLiquiLimitOrder(limitOrder));
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public boolean cancelOrder(final String orderId) throws IOException {
    try {
      final LiquiCancelOrder liquiCancelOrder = cancelOrder(Long.parseLong(orderId));
      return true;
    } catch (final LiquiException exception) {
      LOG.warn("Unable to cancel order({}).", orderId, exception);
      return false;
    }
  }

  @Override
  public boolean cancelOrder(final CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else {
      return false;
    }
  }

  @Override
  public UserTrades getTradeHistory(final TradeHistoryParams params) throws IOException {
    if (params instanceof LiquiTradeHistoryParams) {
      if (((LiquiTradeHistoryParams) params).getCurrencyPair() != null) {
        return LiquiAdapters.adaptTradesHistory(getTradeHistory());
      } else {
        return LiquiAdapters.adaptTradesHistory(getTradeHistory(((LiquiTradeHistoryParams) params).getCurrencyPair(),
            ((LiquiTradeHistoryParams) params).getAmount()));
      }
    }

    throw new LiquiException("Unable to get trade history with the provided params: " + params);
  }

  @Override
  public LiquiTradeHistoryParams createTradeHistoryParams() {
    return new LiquiTradeHistoryParams();
  }

  @Override
  public DefaultOpenOrdersParamCurrencyPair createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public Collection<Order> getOrder(final String... orderIds) throws IOException {
    final List<Order> orders = new ArrayList<>();

    for (final String orderId : orderIds) {
      orders.add(LiquiAdapters.adaptOrderInfo(getOrderInfo(Long.parseLong(orderId))));
    }
    return orders;
  }

  public static class LiquiTradeHistoryParams implements TradeHistoryParams {

    private CurrencyPair currencyPair = null;
    private int amount = 1000;

    public LiquiTradeHistoryParams() {
    }

    public void setCurrencyPair(final CurrencyPair currencyPair) {
      this.currencyPair = currencyPair;
    }

    public CurrencyPair getCurrencyPair() {
      return currencyPair;
    }

    public void setAmount(final int amount) {
      this.amount = amount;
    }

    public int getAmount() {
      return amount;
    }
  }
}
