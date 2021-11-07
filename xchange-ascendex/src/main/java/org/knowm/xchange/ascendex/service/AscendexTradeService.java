package org.knowm.xchange.ascendex.service;

import java.io.IOException;
import java.util.Collection;
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

public class AscendexTradeService extends AscendexTradeServiceRaw implements TradeService {

  public AscendexTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return placeAscendexOrder(
            AscendexAdapters.adaptLimitOrderToAscendexPlaceOrderRequestPayload(limitOrder))
        .getInfo()
        .getOrderId();
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByPairAndIdParams) {
      cancelAllAscendexOrdersBySymbol(
          ((CancelOrderByPairAndIdParams) orderParams).getCurrencyPair().toString());
      return true;
    } else if (orderParams instanceof CancelOrderByCurrencyPair) {
      cancelAllAscendexOrdersBySymbol(
          ((CancelOrderByCurrencyPair) orderParams).getCurrencyPair().toString());
      return true;
    } else {
      throw new IOException(
          "Params must be instanceOf CancelOrderByPairAndIdParams in order to cancel an order on Ascendex.");
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
          getAscendexOpenOrders(
              ((OpenOrdersParamCurrencyPair) params).getCurrencyPair().toString()));
    } else if (params instanceof OpenOrdersParamInstrument) {
      return AscendexAdapters.adaptOpenOrders(
          getAscendexOpenOrders(((OpenOrdersParamInstrument) params).getInstrument().toString()));
    } else {
      throw new IOException(
          "Params must be instanceOf OpenOrdersParamCurrencyPair or OpenOrdersParamInstrument in order to get openOrders from Ascendex.");
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
          getAscendexUserTrades(
              ((TradeHistoryParamCurrencyPair) params).getCurrencyPair().toString()));
    } else {
      throw new IOException(
          "CurrencyPair must specified in order to get usertrades from Ascendex.");
    }
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return AscendexAdapters.adaptOpenOrders(getAscendexOpenOrders(null));
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    return AscendexAdapters.adaptOpenOrderById(getAscendexOrderById(orderIds[0]));
  }
}
