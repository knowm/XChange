package org.knowm.xchange.bitmarket.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmarket.BitMarketAdapters;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketHistoryOperationsResponse;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketHistoryTradesResponse;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketOrdersResponse;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketTradeResponse;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import si.mazi.rescu.IRestProxyFactory;

/** @author kfonal */
public class BitMarketTradeService extends BitMarketTradeServiceRaw implements TradeService {
  /**
   * Constructor
   *
   * @param exchange
   */
  public BitMarketTradeService(Exchange exchange, IRestProxyFactory restProxyFactory) {
    super(exchange, restProxyFactory);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    BitMarketOrdersResponse response = getBitMarketOpenOrders();
    return BitMarketAdapters.adaptOpenOrders(response.getData());
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    BitMarketTradeResponse response = placeBitMarketOrder(limitOrder);
    return String.valueOf(response.getData().getId());
  }

  @Override
  public boolean cancelOrder(String id) throws IOException {

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
}
