package org.knowm.xchange.ftx.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
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
          getFtxOrderHistory(((TradeHistoryParamCurrencyPair) params).getCurrencyPair())
              .getResult());
    } else if (params instanceof TradeHistoryParamInstrument) {
      return FtxAdapters.adaptUserTrades(
          getFtxOrderHistory(((TradeHistoryParamInstrument) params).getInstrument()).getResult());
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
              ((CancelOrderByCurrencyPair) orderParams).getCurrencyPair().toString()));
    } else {
      throw new IOException(
          "CancelOrderParams must implement CancelOrderByCurrencyPair interface.");
    }
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    return getFtxAllOpenOrders().getResult().stream()
        .filter(
            ftxOrderDto ->
                Arrays.stream(orderIds).allMatch(orderId -> orderId.equals(ftxOrderDto.getId())))
        .map(FtxAdapters::adaptLimitOrder)
        .collect(Collectors.toList());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    if (params instanceof CurrencyPairParam) {
      return FtxAdapters.adaptOpenOrders(
          getFtxOpenOrders(((CurrencyPairParam) params).getCurrencyPair()));
    } else {
      throw new IOException("OpenOrdersParams must implement CurrencyPairParam interface.");
    }
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return FtxAdapters.adaptOpenOrders(getFtxAllOpenOrders());
  }
}
