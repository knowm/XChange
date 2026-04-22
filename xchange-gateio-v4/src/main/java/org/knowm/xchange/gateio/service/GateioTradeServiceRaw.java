package org.knowm.xchange.gateio.service;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Validate;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.dto.trade.GateioFuturesOrderResponse;
import org.knowm.xchange.gateio.dto.trade.GateioFuturesOrderRequest;
import org.knowm.xchange.gateio.dto.trade.GateioSpotOrderRequest;
import org.knowm.xchange.gateio.dto.trade.GateioSpotOrderResponse;
import org.knowm.xchange.gateio.dto.trade.GateioUserTradeRaw;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.*;

import java.io.IOException;
import java.util.*;

public class GateioTradeServiceRaw extends GateioBaseService {

  public GateioTradeServiceRaw(GateioExchange exchange) {
    super(exchange);
  }

  public List<GateioSpotOrderResponse> listOrders(Instrument instrument, OrderStatus orderStatus)
      throws IOException {
    // validate arguments
    Objects.requireNonNull(orderStatus);
    Set<OrderStatus> allowedOrderStatuses = EnumSet.of(OrderStatus.OPEN, OrderStatus.CLOSED);
    Validate.validState(
        allowedOrderStatuses.contains(orderStatus),
        "Allowed order statuses are: {}",
        allowedOrderStatuses);
    Objects.requireNonNull(instrument);

    return gateioV4Authenticated.listOrders(
        apiKey,
        exchange.getNonceFactory(),
        gateioV4ParamsDigest,
        GateioAdapters.toGateioInstrument(instrument),
        GateioAdapters.toGateioInstrument(orderStatus));
  }

  public List<GateioUserTradeRaw> getGateioUserTrades(TradeHistoryParams params)
      throws IOException {
    // get arguments
    CurrencyPair currencyPair =
        params instanceof TradeHistoryParamCurrencyPair
            ? ((CurrencyPairParam) params).getCurrencyPair()
            : null;
    Integer pageLength =
        params instanceof TradeHistoryParamPaging
            ? ((TradeHistoryParamPaging) params).getPageLength()
            : null;
    Integer pageNumber =
        params instanceof TradeHistoryParamPaging
            ? ((TradeHistoryParamPaging) params).getPageNumber()
            : null;
    String orderId =
        params instanceof TradeHistoryParamTransactionId
            ? ((TradeHistoryParamTransactionId) params).getTransactionId()
            : null;
    Long from = null;
    Long to = null;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan paramsTimeSpan = ((TradeHistoryParamsTimeSpan) params);
      from =
          paramsTimeSpan.getStartTime() != null
              ? paramsTimeSpan.getStartTime().getTime() / 1000
              : null;
      to =
          paramsTimeSpan.getEndTime() != null ? paramsTimeSpan.getEndTime().getTime() / 1000 : null;
    }

    // if no pagination is given, get all records in chunks
    if (ObjectUtils.allNull(pageLength, pageNumber)) {
      List<GateioUserTradeRaw> result = new ArrayList<>();
      List<GateioUserTradeRaw> chunk;
      Integer currentPageNumber = 1;

      do {
        chunk =
            gateioV4Authenticated.getTradingHistory(
                apiKey,
                exchange.getNonceFactory(),
                gateioV4ParamsDigest,
                GateioAdapters.toGateioInstrument(currencyPair),
                1000,
                currentPageNumber,
                orderId,
                null,
                from,
                to);
        currentPageNumber++;
        result.addAll(chunk);
      } while (!chunk.isEmpty());

      return result;
    }

    return gateioV4Authenticated.getTradingHistory(
        apiKey,
        exchange.getNonceFactory(),
        gateioV4ParamsDigest,
        GateioAdapters.toGateioInstrument(currencyPair),
        pageLength,
        pageNumber,
        orderId,
        null,
        from,
        to);
  }

  public GateioSpotOrderResponse createOrder(GateioSpotOrderRequest gateioOrder) throws IOException {
    return gateioV4Authenticated.createOrder(
        apiKey, exchange.getNonceFactory(), gateioV4ParamsDigest, gateioOrder);
  }

  public GateioFuturesOrderResponse createFuturesOrder(GateioFuturesOrderRequest gateioFuturesOrder) throws IOException {
    Instrument instrument = GateioAdapters.fromGateioInstrument(gateioFuturesOrder.getContract(), true);
    String settle = (instrument instanceof FuturesContract) ? instrument.getCounter().getCurrencyCode().toLowerCase() : "usdt";
    return gateioV4Authenticated.createFuturesOrder(
        apiKey, exchange.getNonceFactory(), gateioV4ParamsDigest, null, settle, gateioFuturesOrder);
  }

  public GateioSpotOrderResponse getOrder(String orderId, Instrument instrument) throws IOException {
    return gateioV4Authenticated.getOrder(
        apiKey,
        exchange.getNonceFactory(),
        gateioV4ParamsDigest,
        orderId,
        GateioAdapters.toGateioInstrument(instrument));
  }

  public GateioFuturesOrderResponse getFuturesOrder(String orderId, Instrument instrument) throws IOException {
    String settle = (instrument instanceof FuturesContract) ? ((FuturesContract) instrument).getCounter().getCurrencyCode().toLowerCase() : "usdt";
    return gateioV4Authenticated.getFuturesOrder(
        apiKey,
        exchange.getNonceFactory(),
        gateioV4ParamsDigest,
        null,
        settle,
        orderId);
  }

  public GateioSpotOrderResponse cancelOrderRaw(String orderId, Instrument instrument) throws IOException {
    return gateioV4Authenticated.cancelOrder(
        apiKey,
        exchange.getNonceFactory(),
        gateioV4ParamsDigest,
        orderId,
        GateioAdapters.toGateioInstrument(instrument));
  }

  public GateioFuturesOrderResponse cancelFuturesOrderRaw(String orderId, Instrument instrument) throws IOException {
    String settle = (instrument instanceof FuturesContract) ? instrument.getCounter().getCurrencyCode().toLowerCase() : "usdt";
    return gateioV4Authenticated.cancelFuturesOrder(
        apiKey,
        exchange.getNonceFactory(),
        gateioV4ParamsDigest,
        null,
        settle,
        orderId);
  }

  public GateioSpotOrderResponse amendSpotOrder(String orderId, Instrument instrument, Map<String, Object> request) throws IOException {
    return gateioV4Authenticated.amendOrder(
        apiKey,
        exchange.getNonceFactory(),
        gateioV4ParamsDigest,
        orderId,
        GateioAdapters.toGateioInstrument(instrument),
        request);
  }

  public GateioFuturesOrderResponse amendFuturesOrder(String orderId, Instrument instrument, Map<String, Object> request) throws IOException {
    String settle = instrument.getCounter().getCurrencyCode().toLowerCase();
    return gateioV4Authenticated.amendFuturesOrder(
        apiKey,
        exchange.getNonceFactory(),
        gateioV4ParamsDigest,
        null,
        settle,
        orderId,
        request);
  }
}
