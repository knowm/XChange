package org.knowm.xchange.bitbay.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitbay.BitbayAdapters;
import org.knowm.xchange.bitbay.dto.trade.BitbayOrder;
import org.knowm.xchange.bitbay.dto.trade.BitbayTradeResponse;
import org.knowm.xchange.dto.Order;
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
import org.knowm.xchange.service.trade.params.TradeHistoryParamsZero;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

/**
 * @author Z. Dolezal
 */
public class BitbayTradeService extends BitbayTradeServiceRaw implements TradeService {

  public BitbayTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(
      OpenOrdersParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    List<BitbayOrder> response = getBitbayOpenOrders();
    return BitbayAdapters.adaptOpenOrders(response);
  }



  @Override
  public String placeMarketOrder(
      MarketOrder marketOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(
      LimitOrder limitOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    BitbayTradeResponse response = placeBitbayOrder(limitOrder);
    return String.valueOf(response.getOrderId());
  }

  @Override
  public boolean cancelOrder(
      String orderId) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    cancelBitbayOrder(Long.parseLong(orderId));
    return true;
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
    List<BitbayOrder> response = getBitbayOpenOrders();
    return BitbayAdapters.adaptTradeHistory(response);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new TradeHistoryParamsZero();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) {
    throw new NotAvailableFromExchangeException();
  }
}
