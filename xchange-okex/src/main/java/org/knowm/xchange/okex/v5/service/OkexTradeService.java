package org.knowm.xchange.okex.v5.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.okex.v5.OkexAdapters;
import org.knowm.xchange.okex.v5.OkexExchange;
import org.knowm.xchange.okex.v5.dto.OkexException;
import org.knowm.xchange.okex.v5.dto.OkexResponse;
import org.knowm.xchange.okex.v5.dto.trade.OkexCancelOrderRequest;
import org.knowm.xchange.okex.v5.dto.trade.OkexOrderDetails;
import org.knowm.xchange.okex.v5.dto.trade.OkexOrderResponse;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderByInstrument;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamInstrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamInstrument;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParamInstrument;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

/** Author: Max Gao (gaamox@tutanota.com) Created: 08-06-2021 */
public class OkexTradeService extends OkexTradeServiceRaw implements TradeService {
  public OkexTradeService(OkexExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    if (params instanceof TradeHistoryParamInstrument) {
      return OkexAdapters.adaptUserTrades(
          getOrderHistory(
                  "SPOT",
                  OkexAdapters.adaptInstrumentId(
                      ((TradeHistoryParamInstrument) params).getInstrument()),
                  null,
                  null,
                  null,
                  null)
              .getData());
    } else if (params instanceof TradeHistoryParamCurrencyPair) {
      return OkexAdapters.adaptUserTrades(
          getOrderHistory(
                  "SPOT",
                  OkexAdapters.adaptInstrumentId(
                      ((TradeHistoryParamCurrencyPair) params).getCurrencyPair()),
                  null,
                  null,
                  null,
                  null)
              .getData());
    } else {
      return OkexAdapters.adaptUserTrades(
          getOrderHistory("SPOT", null, null, null, null, null).getData());
    }
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return OkexAdapters.adaptOpenOrders(
        getOkexPendingOrder(null, null, null, null, null, null, null, null).getData());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    if (params instanceof OpenOrdersParamCurrencyPair) {
      return OkexAdapters.adaptOpenOrders(
          getOkexPendingOrder(
                  null,
                  null,
                  OkexAdapters.adaptInstrumentId(
                      ((OpenOrdersParamCurrencyPair) params).getCurrencyPair()),
                  null,
                  null,
                  null,
                  null,
                  null)
              .getData());
    } else if (params instanceof OpenOrdersParamInstrument) {
      return OkexAdapters.adaptOpenOrders(
          getOkexPendingOrder(
                  null,
                  null,
                  OkexAdapters.adaptInstrumentId(
                      ((OpenOrdersParamInstrument) params).getInstrument()),
                  null,
                  null,
                  null,
                  null,
                  null)
              .getData());
    } else {
      return OkexAdapters.adaptOpenOrders(
          getOkexPendingOrder(null, null, null, null, null, null, null, null).getData());
    }
  }

  @Override
  public Class getRequiredOrderQueryParamClass() {
    return OrderQueryParamInstrument.class;
  }

  public Order getOrder(OrderQueryParams orderQueryParams) throws IOException {
    Order result = null;
    if (orderQueryParams instanceof OrderQueryParamInstrument) {
      Instrument instrument = ((OrderQueryParamInstrument) orderQueryParams).getInstrument();
      String orderId = orderQueryParams.getOrderId();

      List<OkexOrderDetails> orderResults =
          getOkexOrder(OkexAdapters.adaptInstrumentId(instrument), orderId).getData();

      if (!orderResults.isEmpty()) {
        result = OkexAdapters.adaptOrder(orderResults.get(0));
      }
    } else {
      throw new IOException("OrderQueryParams must implement OrderQueryParamInstrument interface.");
    }
    return result;
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    ArrayList<Order> result = new ArrayList<>();
    for (OrderQueryParams orderQueryParam : orderQueryParams) {
      Order order = getOrder(orderQueryParam);
      if (order != null) {
        result.add(order);
      }
    }
    return result;
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException, FundsExceededException {
    OkexResponse<List<OkexOrderResponse>> okexResponse =
        placeOkexOrder(OkexAdapters.adaptOrder(limitOrder));

    if (okexResponse.isSuccess()) return okexResponse.getData().get(0).getOrderId();
    else
      throw new OkexException(
          okexResponse.getData().get(0).getMessage(),
          Integer.parseInt(okexResponse.getData().get(0).getCode()));
  }

  public List<String> placeLimitOrder(List<LimitOrder> limitOrders)
      throws IOException, FundsExceededException {
    return placeOkexOrder(
            limitOrders.stream().map(OkexAdapters::adaptOrder).collect(Collectors.toList()))
        .getData()
        .stream()
        .map(OkexOrderResponse::getOrderId)
        .collect(Collectors.toList());
  }

  @Override
  public String changeOrder(LimitOrder limitOrder) throws IOException, FundsExceededException {
    return amendOkexOrder(OkexAdapters.adaptAmendOrder(limitOrder)).getData().get(0).getOrderId();
  }

  public List<String> changeOrder(List<LimitOrder> limitOrders)
      throws IOException, FundsExceededException {
    return amendOkexOrder(
            limitOrders.stream().map(OkexAdapters::adaptAmendOrder).collect(Collectors.toList()))
        .getData()
        .stream()
        .map(OkexOrderResponse::getOrderId)
        .collect(Collectors.toList());
  }

  @Override
  public boolean cancelOrder(CancelOrderParams params) throws IOException {
    if (params instanceof CancelOrderByIdParams && params instanceof CancelOrderByInstrument) {

      String id = ((CancelOrderByIdParams) params).getOrderId();
      String instrumentId =
          OkexAdapters.adaptInstrumentId(((CancelOrderByInstrument) params).getInstrument());

      OkexCancelOrderRequest req =
          OkexCancelOrderRequest.builder().instrumentId(instrumentId).orderId(id).build();

      return "0".equals(cancelOkexOrder(req).getData().get(0).getCode());
    } else {
      throw new IOException(
          "CancelOrderParams must implement CancelOrderByIdParams and CancelOrderByInstrument interface.");
    }
  }

  @Override
  public Class[] getRequiredCancelOrderParamClasses() {
    return new Class[] {CancelOrderByIdParams.class, CancelOrderByInstrument.class};
  }

  public List<Boolean> cancelOrder(List<CancelOrderParams> params) throws IOException {
    return cancelOkexOrder(
            params.stream()
                .map(
                    param ->
                        OkexCancelOrderRequest.builder()
                            .orderId(((CancelOrderByIdParams) param).getOrderId())
                            .instrumentId(
                                OkexAdapters.adaptInstrumentId(
                                    ((CancelOrderByInstrument) param).getInstrument()))
                            .build())
                .collect(Collectors.toList()))
        .getData()
        .stream()
        .map(result -> "0".equals(result.getCode()))
        .collect(Collectors.toList());
  }
}
