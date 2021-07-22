package org.knowm.xchange.ascendex.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ascendex.AscendexAdapters;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.*;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamInstrument;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamInstrument;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

import java.io.IOException;
import java.util.Collection;

public class AscendexTradeService extends AscendexTradeServiceRaw implements TradeService {

  public AscendexTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return placeBitmaxOrder(
            AscendexAdapters.adaptLimitOrderToBitmaxPlaceOrderRequestPayload(limitOrder))
        .getInfo()
        .getOrderId();
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByPairAndIdParams) {
      cancelAllBitmaxOrdersBySymbol(
          ((CancelOrderByPairAndIdParams) orderParams).getCurrencyPair().toString());
      return true;
    } else if (orderParams instanceof CancelOrderByCurrencyPair) {
      cancelAllBitmaxOrdersBySymbol(
          ((CancelOrderByCurrencyPair) orderParams).getCurrencyPair().toString());
      return true;
    } else {
      throw new IOException(
          "Params must be instanceOf CancelOrderByPairAndIdParams in order to cancel an order on Bitmax.");
    }
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamInstrument();
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    if (params instanceof OpenOrdersParamCurrencyPair) {
      return AscendexAdapters.adaptOpenOrders(
          getBitmaxOpenOrders(((OpenOrdersParamCurrencyPair) params).getCurrencyPair().toString()));
    } else if (params instanceof OpenOrdersParamInstrument) {
      return AscendexAdapters.adaptOpenOrders(
          getBitmaxOpenOrders(((OpenOrdersParamInstrument) params).getInstrument().toString()));
    } else {
      throw new IOException(
          "Params must be instanceOf OpenOrdersParamCurrencyPair or OpenOrdersParamInstrument in order to get openOrders from Bitmax.");
    }
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new TradeHistoryParamsAll();
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    if (params instanceof TradeHistoryParamCurrencyPair) {
      return AscendexAdapters.adaptUserTrades(
          getBitmaxUserTrades(
              ((TradeHistoryParamCurrencyPair) params).getCurrencyPair().toString()));
    } else {
      throw new IOException("CurrencyPair must specified in order to get usertrades from Bitmax.");
    }
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return AscendexAdapters.adaptOpenOrders(getBitmaxOpenOrders(null));
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    return AscendexAdapters.adaptOpenOrderById(getBitmaxOrderById(orderIds[0]));
  }
}
