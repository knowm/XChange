package org.knowm.xchange.quoine.service;

import java.io.IOException;
import java.util.Collection;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.quoine.QuoineAdapters;
import org.knowm.xchange.quoine.dto.trade.QuoineOrderResponse;
import org.knowm.xchange.quoine.dto.trade.QuoineOrdersList;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

/**
 * @author Matija Mazi
 */
public class QuoineTradeService extends QuoineTradeServiceRaw implements TradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public QuoineTradeService(Exchange exchange, boolean useMargin) {
    super(exchange, useMargin);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(
      OpenOrdersParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    QuoineOrdersList quoineOrdersList = listQuoineOrders();
    return QuoineAdapters.adapteOpenOrders(quoineOrdersList);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    QuoineOrderResponse quoinePlaceOrderResponse = placeMarketOrder(marketOrder.getCurrencyPair(),
        marketOrder.getType() == OrderType.ASK ? "sell" : "buy", marketOrder.getTradableAmount());
    return quoinePlaceOrderResponse.getId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    QuoineOrderResponse quoinePlaceOrderResponse = placeLimitOrder(limitOrder.getCurrencyPair(),
        limitOrder.getType() == OrderType.ASK ? "sell" : "buy", limitOrder.getTradableAmount(), limitOrder.getLimitPrice());
    return quoinePlaceOrderResponse.getId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    QuoineOrderResponse quoineOrderResponse = cancelQuoineOrder(orderId);

    return quoineOrderResponse.getId() != null && (quoineOrderResponse.getId().equals(orderId));
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

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Collection<Order> getOrder(
      String... orderIds) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

}
