package org.knowm.xchange.bitmex.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmex.BitmexException;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPrivateOrder;
import org.knowm.xchange.bitmex.dto.trade.BitmexPosition;

public class BitmexTradeServiceRaw extends BitmexBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  String apiKey = exchange.getExchangeSpecification().getApiKey();

  public BitmexTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public List<BitmexPosition> getBitmexPositions() throws IOException {

    try {
      return bitmex.getPositions(apiKey, exchange.getNonceFactory(), signatureCreator);
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public List<BitmexPosition> getBitmexPositions(String symbol) throws IOException {

    try {
      return bitmex.getPositions(apiKey, exchange.getNonceFactory(), signatureCreator, symbol, null);
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public List<BitmexPrivateOrder> getBitmexOrders(String symbol, String filter) throws IOException {
    ArrayList<BitmexPrivateOrder> orders = new ArrayList<>();

    for (int i = 0; orders.size() % 500 == 0; i++) {
      List<BitmexPrivateOrder> orderResponse = bitmex.getOrders(apiKey, exchange.getNonceFactory(), signatureCreator,
              symbol, filter, 500, i * 500, true, null, null);
      orders.addAll(orderResponse);
    }

    return orders;
  }

  public List<BitmexPrivateOrder> getBitmexOrders() throws IOException {
    return getBitmexOrders(null, null);
  }

  public BitmexPrivateOrder placeMarketOrder(String symbol, BigDecimal orderQuantity, String executionInstructions) {
    return bitmex.placeOrder(apiKey, exchange.getNonceFactory(), signatureCreator, symbol, orderQuantity.intValue(),
            null, null, "Market", executionInstructions);
  }

  public BitmexPrivateOrder placeLimitOrder(String symbol, BigDecimal orderQuantity, BigDecimal price, String executionInstructions) {
    return bitmex.placeOrder(apiKey, exchange.getNonceFactory(), signatureCreator, symbol, orderQuantity.intValue(),
            price, null, "Limit", executionInstructions);
  }

  public BitmexPrivateOrder placeStopOrder(String symbol, BigDecimal orderQuantity, BigDecimal stopPrice, String executionInstructions) {
    return bitmex.placeOrder(apiKey, exchange.getNonceFactory(), signatureCreator, symbol, orderQuantity.intValue(),
            null, stopPrice, "Stop", executionInstructions);
  }

  public boolean cancelBitmexOrder(String orderID) {
    List<BitmexPrivateOrder> orders = bitmex.cancelOrder(apiKey, exchange.getNonceFactory(), signatureCreator, orderID);
    return orders.get(0).getId().equals(orderID);
  }
}
