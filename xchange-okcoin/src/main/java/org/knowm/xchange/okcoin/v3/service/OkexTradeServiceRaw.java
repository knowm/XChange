package org.knowm.xchange.okcoin.v3.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.okcoin.OkexExchangeV3;
import org.knowm.xchange.okcoin.v3.dto.trade.OkexOpenOrder;
import org.knowm.xchange.okcoin.v3.dto.trade.OkexTransaction;
import org.knowm.xchange.okcoin.v3.dto.trade.OrderBatchCancellationRequest;
import org.knowm.xchange.okcoin.v3.dto.trade.OrderCancellationRequest;
import org.knowm.xchange.okcoin.v3.dto.trade.OrderCancellationResponse;
import org.knowm.xchange.okcoin.v3.dto.trade.OrderPlacementRequest;
import org.knowm.xchange.okcoin.v3.dto.trade.OrderPlacementResponse;

public class OkexTradeServiceRaw extends OkexBaseService {

  protected OkexTradeServiceRaw(OkexExchangeV3 exchange) {
    super(exchange);
  }

  public OrderPlacementResponse placeAnOrder(OrderPlacementRequest req) throws IOException {
    OrderPlacementResponse res = okex.placeAnOrder(apikey, digest, timestamp(), passphrase, req);
    res.checkResult();
    return res;
  }

  public Map<String, List<OrderPlacementResponse>> placeMultipleOrders(
      List<OrderPlacementRequest> req) throws IOException {
    return okex.placeMultipleOrders(apikey, digest, timestamp(), passphrase, req);
  }

  public OrderCancellationResponse cancelAnOrder(String orderId, OrderCancellationRequest req)
      throws IOException {
    OrderCancellationResponse res =
        okex.cancelAnOrder(apikey, digest, timestamp(), passphrase, orderId, req);
    res.checkResult();
    return res;
  }

  public Object cancelAllOrders(List<OrderBatchCancellationRequest> req) throws IOException {
    return okex.cancelAllOrders(apikey, digest, timestamp(), passphrase, req);
  }

  public List<OkexOpenOrder> getOrderList(
      String instrumentId, String from, String to, Integer limit, String state) throws IOException {
    return okex.getOrderList(
        apikey, digest, timestamp(), passphrase, instrumentId, from, to, limit, state);
  }

  public List<OkexTransaction> getTransactionDetails(
      String orderId, String instrumentId, String from, String to, Integer limit)
      throws IOException {
    return okex.getTransactionDetails(
        apikey, digest, timestamp(), passphrase, orderId, instrumentId, from, to, limit);
  }
}
