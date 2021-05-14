package org.knowm.xchange.ftx.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.account.OpenPositions;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.ftx.FtxAdapters;
import org.knowm.xchange.ftx.dto.trade.CancelAllFtxOrdersParams;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.*;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class FtxTradeService extends FtxTradeServiceRaw implements TradeService {

  public FtxTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return placeNewFtxOrder(null, FtxAdapters.adaptLimitOrderToFtxOrderPayload(limitOrder))
        .getResult()
        .getId();
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    if (params instanceof TradeHistoryParamCurrencyPair) {
      return FtxAdapters.adaptUserTrades(
          getFtxOrderHistory(
                  FtxAdapters.adaptCurrencyPairToFtxMarket(
                      ((TradeHistoryParamCurrencyPair) params).getCurrencyPair()))
              .getResult());
    } else if (params instanceof TradeHistoryParamInstrument) {
      CurrencyPair currencyPair =
          new CurrencyPair(((TradeHistoryParamInstrument) params).getInstrument().toString());
      return FtxAdapters.adaptUserTrades(
          getFtxOrderHistory(FtxAdapters.adaptCurrencyPairToFtxMarket(currencyPair)).getResult());
    } else {
      throw new IOException(
          "TradeHistoryParams must implement TradeHistoryParamCurrencyPair or TradeHistoryParamInstrument interface.");
    }
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    return cancelFtxOrder(orderId);
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByCurrencyPair) {
      return cancelAllFtxOrders(
          new CancelAllFtxOrdersParams(
              FtxAdapters.adaptCurrencyPairToFtxMarket(
                  ((CancelOrderByCurrencyPair) orderParams).getCurrencyPair())));
    } else {
      throw new IOException(
          "CancelOrderParams must implement CancelOrderByCurrencyPair interface.");
    }
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    List<Order> orderList = new ArrayList<>();
    orderList.add(FtxAdapters.adaptLimitOrder(getFtxOrderStatus(orderIds[0]).getResult()));
    return orderList;
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    if (params instanceof CurrencyPairParam) {
      return FtxAdapters.adaptOpenOrders(
          getFtxOpenOrders(
              FtxAdapters.adaptCurrencyPairToFtxMarket(
                  ((CurrencyPairParam) params).getCurrencyPair())));
    } else {
      throw new IOException("OpenOrdersParams must implement CurrencyPairParam interface.");
    }
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return FtxAdapters.adaptOpenOrders(getFtxAllOpenOrders());
  }

  @Override
  public OpenPositions getOpenPositions() throws IOException {
    return FtxAdapters.adaptOpenPositions(getFtxPositions().getResult());
  }
}
