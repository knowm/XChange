package org.knowm.xchange.ccex.service;

import static org.knowm.xchange.service.trade.params.TradeHistoryParamsZero.PARAMS_ZERO;

import java.io.IOException;
import java.util.Collection;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ccex.CCEXAdapters;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class CCEXTradeService extends CCEXTradeServiceRaw implements TradeService {

  public CCEXTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(
      OpenOrdersParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return new OpenOrders(CCEXAdapters.adaptOpenOrders(getCCEXOpenOrders()));
  }

  @Override
  public String placeMarketOrder(
      MarketOrder marketOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    String id = placeCCEXLimitOrder(limitOrder);

    return id;
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    return cancelCCEXLimitOrder(orderId);
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      cancelOrder(((CancelOrderByIdParams) orderParams).orderId);
    }
    return false;
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    return new UserTrades(CCEXAdapters.adaptUserTrades(getCCEXTradeHistory()), TradeSortType.SortByTimestamp);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return PARAMS_ZERO;
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  @Override
  public Collection<Order> getOrder(
      String... orderIds) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

}