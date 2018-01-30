package org.knowm.xchange.kucoin.service;

import java.io.IOException;
import java.util.Collection;

import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

/**
 * @author Jan Akerman
 */
public class KucoinTradeService implements TradeService {

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    throw new NotYetImplementedForExchangeException("This operation is not yet implemented for this exchange");
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    throw new NotYetImplementedForExchangeException("This operation is not yet implemented for this exchange");
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    throw new NotYetImplementedForExchangeException("This operation is not yet implemented for this exchange");
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    throw new NotYetImplementedForExchangeException("This operation is not yet implemented for this exchange");
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    throw new NotYetImplementedForExchangeException("This operation is not yet implemented for this exchange");
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    throw new NotYetImplementedForExchangeException("This operation is not yet implemented for this exchange");
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    throw new NotYetImplementedForExchangeException("This operation is not yet implemented for this exchange");
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    throw new NotYetImplementedForExchangeException("This operation is not yet implemented for this exchange");
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    throw new NotYetImplementedForExchangeException("This operation is not yet implemented for this exchange");
  }

  @Override
  public void verifyOrder(LimitOrder limitOrder) {
    throw new NotYetImplementedForExchangeException("This operation is not yet implemented for this exchange");
  }

  @Override
  public void verifyOrder(MarketOrder marketOrder) {
    throw new NotYetImplementedForExchangeException("This operation is not yet implemented for this exchange");
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    throw new NotYetImplementedForExchangeException("This operation is not yet implemented for this exchange");
  }
}

