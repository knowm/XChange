package org.knowm.xchange.abucoins.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.abucoins.AbucoinsAdapters;
import org.knowm.xchange.abucoins.dto.AbucoinsOrderRequest;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

/**
 * Author: bryant_harris
 */

public class AbucoinsTradeService extends AbucoinsTradeServiceRaw implements TradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public AbucoinsTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    if ( params instanceof OpenOrdersParamCurrencyPair ) {
      OpenOrdersParamCurrencyPair cpParams = (OpenOrdersParamCurrencyPair) params;
      AbucoinsOrderRequest orderRequest = new AbucoinsOrderRequest(AbucoinsOrder.Status.open,
                                                                   AbucoinsAdapters.adaptCurrencyPairToProductID(cpParams.getCurrencyPair()));
      AbucoinsOrder[] openOrders = this.getAbucoinsOrders(orderRequest);
      return AbucoinsAdapters.adaptOpenOrders(openOrders);
    }
    
    return null;// AbucoinsAdapters.adaptOpenOrders(abucoinsOrderList);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    AbucoinsOrder order = placeAbucoinsLimitOrder(limitOrder);

    return order.getId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    deleteAbucoinsOrder(orderId);
    return true;
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByCurrencyPair) {
      CancelOrderByCurrencyPair cob = (CancelOrderByCurrencyPair) orderParams;
      deleteAllAbucoinsOrders(AbucoinsAdapters.adaptCurrencyPairToProductID(cob.getCurrencyPair()));
      return true;
    } else {
      return false;
    }
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    List<UserTrade> trades = new ArrayList<>();
    //for (AbucoinsArchivedOrder AbucoinsArchivedOrder : archivedOrders(params)) {
      //if (AbucoinsArchivedOrder.status.equals("c"))//"d" — done (fully executed), "c" — canceled (not executed), "cd" — cancel-done (partially executed)
        //continue;
      //trades.add(AbucoinsAdapters.adaptArchivedOrder(AbucoinsArchivedOrder));
    //}
    return new UserTrades(trades, Trades.TradeSortType.SortByTimestamp);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {

    List<Order> orders = new ArrayList<>();
    for (String orderId : orderIds) {
      AbucoinsOrder AbucoinsOrder = getAbucoinsOrder(orderId);
      orders.add(AbucoinsAdapters.adaptOrder(AbucoinsOrder));
    }
    return orders;
  }

}
