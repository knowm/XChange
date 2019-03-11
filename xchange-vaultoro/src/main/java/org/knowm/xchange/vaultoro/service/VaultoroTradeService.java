package org.knowm.xchange.vaultoro.service;

import java.io.IOException;
import java.util.Collection;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.vaultoro.VaultoroAdapters;
import org.knowm.xchange.vaultoro.dto.trade.VaultoroCancelOrderResponse;
import org.knowm.xchange.vaultoro.dto.trade.VaultoroNewOrderResponse;

public class VaultoroTradeService extends VaultoroTradeServiceRaw implements TradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public VaultoroTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public boolean cancelOrder(String arg0) throws IOException {

    try {
      VaultoroCancelOrderResponse response = super.cancelVaultoroOrder(arg0);
      if (response.getStatus().equals("success")) {
        return true;
      } else {
        return false;
      }
    } catch (ExchangeException e) {
      return false;
    }
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
  public TradeHistoryParams createTradeHistoryParams() {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    return VaultoroAdapters.adaptVaultoroOpenOrders(getVaultoroOrders());
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams arg0) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder arg0) throws IOException {

    VaultoroNewOrderResponse response =
        super.placeLimitOrder(
            arg0.getCurrencyPair(), arg0.getType(), arg0.getOriginalAmount(), arg0.getLimitPrice());
    return response.getData().getOrderID();
  }

  @Override
  public String placeMarketOrder(MarketOrder arg0) throws IOException {

    VaultoroNewOrderResponse response =
        super.placeMarketOrder(arg0.getCurrencyPair(), arg0.getType(), arg0.getOriginalAmount());
    return response.getData().getOrderID();
  }

  @Override
  public Collection<Order> getOrder(String... arg0) throws IOException {

    throw new NotAvailableFromExchangeException();
  }
}
