package org.knowm.xchange.bibox.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bibox.dto.BiboxAdapters;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

/** @author odrotleff */
public class BiboxTradeService extends BiboxTradeServiceRaw implements TradeService {

  public BiboxTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return BiboxAdapters.adaptOpenOrders(getBiboxOpenOrders());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    return getOpenOrders();
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    return placeBiboxMarketOrder(marketOrder).toString();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return placeBiboxLimitOrder(limitOrder).toString();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    cancelBiboxOrder(orderId);
    return true;
  }

  public boolean cancelOrders(List<String> orderIds) throws IOException {
    cancelBiboxOrders(orderIds);
    return true;
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else {
      throw new ExchangeException("Need order ID for cancelling orders.");
    }
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    return BiboxAdapters.adaptUserTrades(getBiboxOrderHistory());
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    throw new NotYetImplementedForExchangeException(
        "This operation is not yet implemented for this exchange");
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    throw new NotYetImplementedForExchangeException(
        "This operation is not yet implemented for this exchange");
  }

  @Override
  public String placeStopOrder(StopOrder arg0) throws IOException {
    throw new NotYetImplementedForExchangeException(
        "This operation is not yet implemented for this exchange");
  }
}
