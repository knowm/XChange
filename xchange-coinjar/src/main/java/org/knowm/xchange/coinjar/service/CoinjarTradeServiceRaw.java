package org.knowm.xchange.coinjar.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinjar.CoinjarException;
import org.knowm.xchange.coinjar.dto.CoinjarOrder;
import org.knowm.xchange.coinjar.dto.trading.CoinjarFill;
import org.knowm.xchange.coinjar.dto.trading.CoinjarOrderRequest;

class CoinjarTradeServiceRaw extends CoinjarBaseService {

  CoinjarTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  protected CoinjarOrder placeOrder(CoinjarOrderRequest request)
      throws CoinjarException, IOException {
    return coinjarTrading.placeOrder(authorizationHeader, request);
  }

  protected CoinjarOrder getOrder(String id) throws CoinjarException, IOException {
    return coinjarTrading.getOrder(authorizationHeader, id);
  }

  protected List<CoinjarOrder> getOpenOrders(Integer cursor) throws CoinjarException, IOException {
    return coinjarTrading.getOpenOrders(authorizationHeader, cursor);
  }

  protected CoinjarOrder cancelOrderById(String id) throws CoinjarException, IOException {
    return coinjarTrading.cancelOrder(authorizationHeader, id);
  }

  protected List<CoinjarFill> getFills(Integer cursor, String productId, String oid)
      throws CoinjarException, IOException {
    return coinjarTrading.getFills(authorizationHeader, cursor, productId, oid);
  }
}
