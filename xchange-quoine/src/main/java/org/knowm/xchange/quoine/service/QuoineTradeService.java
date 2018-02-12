package org.knowm.xchange.quoine.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.quoine.QuoineAdapters;
import org.knowm.xchange.quoine.dto.trade.QuoineExecution;
import org.knowm.xchange.quoine.dto.trade.QuoineOrderResponse;
import org.knowm.xchange.quoine.dto.trade.QuoineOrdersList;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
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
      OpenOrdersParams params) throws IOException {
    QuoineOrdersList quoineOrdersList = listQuoineOrders();
    return QuoineAdapters.adapteOpenOrders(quoineOrdersList);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    QuoineOrderResponse quoinePlaceOrderResponse = placeMarketOrder(marketOrder.getCurrencyPair(),
        marketOrder.getType() == OrderType.ASK ? "sell" : "buy", marketOrder.getOriginalAmount());
    return quoinePlaceOrderResponse.getId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    QuoineOrderResponse quoinePlaceOrderResponse = placeLimitOrder(limitOrder.getCurrencyPair(),
        limitOrder.getType() == OrderType.ASK ? "sell" : "buy", limitOrder.getOriginalAmount(), limitOrder.getLimitPrice());
    return quoinePlaceOrderResponse.getId();
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    QuoineOrderResponse quoineOrderResponse = cancelQuoineOrder(orderId);

    return quoineOrderResponse.getId() != null && (quoineOrderResponse.getId().equals(orderId));
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
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    CurrencyPair currencyPair = null;
    Integer pageNumber = 1;
    Integer limit = 1000;

    if (params instanceof TradeHistoryParamCurrencyPair) {
      currencyPair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
    }

    if (params instanceof TradeHistoryParamPaging) {
      TradeHistoryParamPaging tradeHistoryParamPaging = (TradeHistoryParamPaging) params;
      pageNumber = tradeHistoryParamPaging.getPageNumber();
      limit = tradeHistoryParamPaging.getPageLength();
    }

    List<QuoineExecution> executions = executions(currencyPair, limit, pageNumber);

    if (currencyPair == null)
      throw new IllegalStateException("Need to specify TradeHistoryParamCurrencyPair");

    return new UserTrades(QuoineAdapters.adapt(executions, currencyPair), Trades.TradeSortType.SortByTimestamp);
  }

  @Override
  public Collection<Order> getOrder(
      String... orderIds) throws IOException {
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
