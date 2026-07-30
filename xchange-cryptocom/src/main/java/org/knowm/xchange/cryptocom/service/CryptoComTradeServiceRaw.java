package org.knowm.xchange.cryptocom.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.cryptocom.CryptoComExchange;
import org.knowm.xchange.cryptocom.dto.CryptoComException;
import org.knowm.xchange.cryptocom.dto.CryptoComRequest;
import org.knowm.xchange.cryptocom.dto.CryptoComResponse;
import org.knowm.xchange.cryptocom.dto.trade.CryptoComOrder;
import org.knowm.xchange.cryptocom.dto.trade.CryptoComOrderAck;
import org.knowm.xchange.cryptocom.dto.trade.CryptoComOrderSide;
import org.knowm.xchange.cryptocom.dto.trade.CryptoComOrderType;
import org.knowm.xchange.cryptocom.dto.trade.CryptoComTimeInForce;
import org.knowm.xchange.cryptocom.dto.trade.CryptoComUserTrade;

public class CryptoComTradeServiceRaw extends CryptoComBaseService {

  protected CryptoComTradeServiceRaw(
      CryptoComExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  public CryptoComOrderAck createCryptoComOrder(
      String instrumentName,
      CryptoComOrderSide side,
      CryptoComOrderType type,
      String price,
      String quantity,
      CryptoComTimeInForce timeInForce,
      String clientOid)
      throws IOException, CryptoComException {
    Map<String, Object> params = new HashMap<>();
    params.put("instrument_name", instrumentName);
    params.put("side", side.name());
    params.put("type", type.name());
    if (price != null) {
      params.put("price", price);
    }
    // Crypto.com requires the spend amount for MARKET BUY orders under "notional" (quote
    // currency) rather than "quantity" (base currency); every other combination uses "quantity".
    if (type == CryptoComOrderType.MARKET && side == CryptoComOrderSide.BUY) {
      params.put("notional", quantity);
    } else {
      params.put("quantity", quantity);
    }
    if (timeInForce != null) {
      params.put("time_in_force", timeInForce.name());
    }
    if (clientOid != null) {
      params.put("client_oid", clientOid);
    }
    CryptoComRequest request = buildRequest("private/create-order", params);
    CryptoComResponse response = decorateApiCall(() -> cryptoCom.createOrder(request)).call();
    return toObject(response.getResult(), CryptoComOrderAck.class);
  }

  public CryptoComOrderAck cancelCryptoComOrder(String orderId)
      throws IOException, CryptoComException {
    Map<String, Object> params = new HashMap<>();
    params.put("order_id", orderId);
    CryptoComRequest request = buildRequest("private/cancel-order", params);
    CryptoComResponse response = decorateApiCall(() -> cryptoCom.cancelOrder(request)).call();
    return toObject(response.getResult(), CryptoComOrderAck.class);
  }

  public void cancelAllCryptoComOrders(String instrumentName)
      throws IOException, CryptoComException {
    Map<String, Object> params = new HashMap<>();
    if (instrumentName != null) {
      params.put("instrument_name", instrumentName);
    }
    CryptoComRequest request = buildRequest("private/cancel-all-orders", params);
    decorateApiCall(() -> cryptoCom.cancelAllOrders(request)).call();
  }

  public List<CryptoComOrder> getCryptoComOpenOrders(String instrumentName)
      throws IOException, CryptoComException {
    Map<String, Object> params = new HashMap<>();
    if (instrumentName != null) {
      params.put("instrument_name", instrumentName);
    }
    CryptoComRequest request = buildRequest("private/get-open-orders", params);
    CryptoComResponse response = decorateApiCall(() -> cryptoCom.getOpenOrders(request)).call();
    return getDataList(response, CryptoComOrder.class);
  }

  public CryptoComOrder getCryptoComOrderDetail(String orderId)
      throws IOException, CryptoComException {
    Map<String, Object> params = new HashMap<>();
    params.put("order_id", orderId);
    CryptoComRequest request = buildRequest("private/get-order-detail", params);
    CryptoComResponse response = decorateApiCall(() -> cryptoCom.getOrderDetail(request)).call();
    return toObject(response.getResult(), CryptoComOrder.class);
  }

  public List<CryptoComOrder> getCryptoComOrderHistory(
      String instrumentName, Long startTime, Long endTime, Integer limit)
      throws IOException, CryptoComException {
    CryptoComRequest request =
        buildRequest(
            "private/get-order-history", historyParams(instrumentName, startTime, endTime, limit));
    CryptoComResponse response = decorateApiCall(() -> cryptoCom.getOrderHistory(request)).call();
    return getDataList(response, CryptoComOrder.class);
  }

  public List<CryptoComUserTrade> getCryptoComUserTrades(
      String instrumentName, Long startTime, Long endTime, Integer limit)
      throws IOException, CryptoComException {
    CryptoComRequest request =
        buildRequest(
            "private/get-trades", historyParams(instrumentName, startTime, endTime, limit));
    CryptoComResponse response = decorateApiCall(() -> cryptoCom.getUserTrades(request)).call();
    return getDataList(response, CryptoComUserTrade.class);
  }

  private Map<String, Object> historyParams(
      String instrumentName, Long startTime, Long endTime, Integer limit) {
    Map<String, Object> params = new HashMap<>();
    if (instrumentName != null) {
      params.put("instrument_name", instrumentName);
    }
    if (startTime != null) {
      params.put("start_time", startTime);
    }
    if (endTime != null) {
      params.put("end_time", endTime);
    }
    if (limit != null) {
      params.put("limit", limit);
    }
    return params;
  }
}
