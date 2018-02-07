package org.knowm.xchange.bitmarket.service;

import java.io.IOException;
import java.util.Collection;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmarket.BitMarketAdapters;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketHistoryOperationsResponse;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketHistoryTradesResponse;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketOrdersResponse;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketTradeResponse;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

/**
 * @author kfonal
 */
public class BitMarketTradeService extends BitMarketTradeServiceRaw implements TradeService {
  /**
   * Constructor
   *
   * @param exchange
   */
  public BitMarketTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(
      OpenOrdersParams params) throws IOException {
    BitMarketOrdersResponse response = getBitMarketOpenOrders();
    return BitMarketAdapters.adaptOpenOrders(response.getData());
  }

  @Override
  public String placeMarketOrder(
      MarketOrder marketOrder) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(
      LimitOrder limitOrder) throws IOException {

    BitMarketTradeResponse response = placeBitMarketOrder(limitOrder);
    return String.valueOf(response.getData().getId());
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public boolean cancelOrder(
      String id) throws IOException {

    cancelBitMarketOrder(id);
    return true;
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
  public UserTrades getTradeHistory(TradeHistoryParams tradeHistoryParams) throws IOException {

    BitMarketHistoryTradesResponse response = getBitMarketTradeHistory(tradeHistoryParams);
    BitMarketHistoryOperationsResponse response2 = getBitMarketOperationHistory(tradeHistoryParams);
    return BitMarketAdapters.adaptTradeHistory(response.getData(), response2.getData());
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new BitMarketHistoryParams();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  @Override
  public Collection<Order> getOrder(
      String... orderIds) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

}
