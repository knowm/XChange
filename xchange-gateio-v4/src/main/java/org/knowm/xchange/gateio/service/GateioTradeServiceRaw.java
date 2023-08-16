package org.knowm.xchange.gateio.service;

import java.io.IOException;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.Validate;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.dto.account.GateioOrder;
import org.knowm.xchange.gateio.dto.trade.GateioUserTrade;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.CurrencyPairParam;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParamTransactionId;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

public class GateioTradeServiceRaw extends GateioBaseService {

  public GateioTradeServiceRaw(GateioExchange exchange) {
    super(exchange);
  }


  public List<GateioOrder> listOrders(Instrument instrument, OrderStatus orderStatus) throws IOException {
    // validate arguments
    Validate.notNull(orderStatus);
    Set<OrderStatus> allowedOrderStatuses = EnumSet.of(OrderStatus.OPEN, OrderStatus.CLOSED);
    Validate.validState(allowedOrderStatuses.contains(orderStatus), "Allowed order statuses are: {}", allowedOrderStatuses);
    Validate.notNull(instrument);

    return gateioV4Authenticated.listOrders(apiKey, exchange.getNonceFactory(),
        gateioV4ParamsDigest, GateioAdapters.toString(instrument), GateioAdapters.toString(orderStatus)
    );

  }


  public List<GateioUserTrade> getGateioUserTrades(TradeHistoryParams params) throws IOException {
    // get arguments
    CurrencyPair currencyPair = params instanceof TradeHistoryParamCurrencyPair ? ((CurrencyPairParam) params).getCurrencyPair() : null;
    Integer pageLength = params instanceof TradeHistoryParamPaging ? ((TradeHistoryParamPaging) params).getPageLength() : null;
    Integer pageNumber = params instanceof TradeHistoryParamPaging ? ((TradeHistoryParamPaging) params).getPageNumber() : null;
    String orderId = params instanceof TradeHistoryParamTransactionId ? ((TradeHistoryParamTransactionId) params).getTransactionId() : null;
    Long from = null;
    Long to = null;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan paramsTimeSpan = ((TradeHistoryParamsTimeSpan) params);
      from = paramsTimeSpan.getStartTime() != null ? paramsTimeSpan.getStartTime().getTime() / 1000 : null;
      to = paramsTimeSpan.getEndTime() != null ? paramsTimeSpan.getEndTime().getTime() / 1000 : null;
    }

    return gateioV4Authenticated.getTradingHistory(apiKey, exchange.getNonceFactory(),
        gateioV4ParamsDigest, GateioAdapters.toString(currencyPair),
        pageLength, pageNumber, orderId, null, from, to);
  }


  public String getGateioUserTradesRaw(TradeHistoryParams params) throws IOException {
    // get arguments
    CurrencyPair currencyPair = params instanceof TradeHistoryParamCurrencyPair ? ((CurrencyPairParam) params).getCurrencyPair() : null;
    Integer pageLength = params instanceof TradeHistoryParamPaging ? ((TradeHistoryParamPaging) params).getPageLength() : null;
    Integer pageNumber = params instanceof TradeHistoryParamPaging ? ((TradeHistoryParamPaging) params).getPageNumber() : null;
    String orderId = params instanceof TradeHistoryParamTransactionId ? ((TradeHistoryParamTransactionId) params).getTransactionId() : null;
    Long from = null;
    Long to = null;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan paramsTimeSpan = ((TradeHistoryParamsTimeSpan) params);
      from = paramsTimeSpan.getStartTime() != null ? paramsTimeSpan.getStartTime().getTime() / 1000 : null;
      to = paramsTimeSpan.getEndTime() != null ? paramsTimeSpan.getEndTime().getTime() / 1000 : null;
    }

    return gateioV4Authenticated.getTradingHistoryRaw(apiKey, exchange.getNonceFactory(),
        gateioV4ParamsDigest, GateioAdapters.toString(currencyPair),
        pageLength, pageNumber, orderId, null, from, to);
  }


  public GateioOrder createOrder(GateioOrder gateioOrder) throws IOException {
    return gateioV4Authenticated.createOrder(apiKey, exchange.getNonceFactory(), gateioV4ParamsDigest, gateioOrder);
  }


  public GateioOrder getOrder(String orderId, Instrument instrument) throws IOException {
    return gateioV4Authenticated.getOrder(apiKey, exchange.getNonceFactory(), gateioV4ParamsDigest,
        orderId, GateioAdapters.toString(instrument));
  }


  public GateioOrder cancelOrderRaw(String orderId, Instrument instrument) throws IOException {
    return gateioV4Authenticated.cancelOrder(apiKey, exchange.getNonceFactory(), gateioV4ParamsDigest,
        orderId, GateioAdapters.toString(instrument));
  }


}
