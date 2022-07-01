package org.knowm.xchange.cexio.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cexio.CexIOAdapters;
import org.knowm.xchange.cexio.dto.trade.CexIOArchivedOrder;
import org.knowm.xchange.cexio.dto.trade.CexIOCancelReplaceOrderResponse;
import org.knowm.xchange.cexio.dto.trade.CexIOOpenOrder;
import org.knowm.xchange.cexio.dto.trade.CexIOOrder;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

/** Author: brox Since: 2/6/14 */
public class CexIOTradeService extends CexIOTradeServiceRaw implements TradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CexIOTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {

    List<CexIOOrder> cexIOOrderList;
    if (params instanceof OpenOrdersParamCurrencyPair) {
      cexIOOrderList = getCexIOOpenOrders(((OpenOrdersParamCurrencyPair) params).getCurrencyPair());
    } else {
      cexIOOrderList = getCexIOOpenOrders();
    }

    return CexIOAdapters.adaptOpenOrders(cexIOOrderList);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    /*
    Only in market order!
    Presently, the exchange is designed in such way that, depending on the BID/ASK the currency changes
      (accordingly, you must specify the amount in another currency)
    Example: CurrencyPair.BCH_USD, Order.OrderType.ASK, Amount = 0.02 (BCH)
    Example: CurrencyPair.BCH_USD, Order.OrderType.BID, Amount = 20 (USD)
    Сurrently cannot be implemented!
    */

    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    CexIOOrder order = placeCexIOLimitOrder(limitOrder);
    return Long.toString(order.getId());
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    return cancelCexIOOrder(orderId);
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else if (orderParams instanceof CancelOrderByCurrencyPair) {
      cancelCexIOOrders(((CancelOrderByCurrencyPair) orderParams).getCurrencyPair());
      return true;
    } else {
      throw new IllegalArgumentException(
          String.format("Unknown parameter type: %s", orderParams.getClass()));
    }
  }

  @Override
  public String changeOrder(LimitOrder limitOrder) throws IOException {
    CexIOCancelReplaceOrderResponse response =
        cancelReplaceCexIOOrder(
            limitOrder.getCurrencyPair(),
            limitOrder.getType(),
            limitOrder.getId(),
            limitOrder.getOriginalAmount(),
            limitOrder.getLimitPrice());
    return response.getId();
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    List<UserTrade> trades = new ArrayList<>();
    for (CexIOArchivedOrder cexIOArchivedOrder : archivedOrders(params)) {
      if (cexIOArchivedOrder.status.equals(
          "c")) // "d" — done (fully executed), "c" — canceled (not executed), "cd" — cancel-done//
        // (partially executed)
        continue;
      trades.add(CexIOAdapters.adaptArchivedOrder(cexIOArchivedOrder));
    }
    return new UserTrades(trades, Trades.TradeSortType.SortByTimestamp);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
    // TODO: return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    List<Order> orders = new ArrayList<>(orderQueryParams.length);
    for (OrderQueryParams params : orderQueryParams) {
      CexIOOpenOrder cexIOOrder = getOrderDetail(params.getOrderId());
      orders.add(CexIOAdapters.adaptOrder(cexIOOrder));
    }
    return orders;
  }
}
