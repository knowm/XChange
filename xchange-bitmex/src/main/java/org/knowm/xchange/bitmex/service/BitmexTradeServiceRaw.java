package org.knowm.xchange.bitmex.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmex.BitmexException;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPrivateOrder;
import org.knowm.xchange.bitmex.dto.trade.BitmexPosition;
import org.knowm.xchange.bitmex.dto.trade.BitmexSide;

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
      return bitmex.getPositions(
          apiKey, exchange.getNonceFactory(), signatureCreator, symbol, null);
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public List<BitmexPrivateOrder> getBitmexOrders(String symbol, String filter) throws IOException {
    ArrayList<BitmexPrivateOrder> orders = new ArrayList<>();

    for (int i = 0; orders.size() % 500 == 0; i++) {
      List<BitmexPrivateOrder> orderResponse =
          bitmex.getOrders(
              apiKey,
              exchange.getNonceFactory(),
              signatureCreator,
              symbol,
              filter,
              500,
              i * 500,
              true,
              null,
              null);
      orders.addAll(orderResponse);
    }

    return orders;
  }

  public List<BitmexPrivateOrder> getBitmexOrders() throws IOException {
    return getBitmexOrders(null, null);
  }

  public BitmexPrivateOrder placeMarketOrder(
      String symbol, BitmexSide side, BigDecimal orderQuantity, String executionInstructions) {
    return bitmex.placeOrder(
        apiKey,
        exchange.getNonceFactory(),
        signatureCreator,
        symbol,
        side == null ? null : side.toString(),
        orderQuantity.intValue(),
        null,
        null,
        "Market",
        null,
        executionInstructions);
  }

  public BitmexPrivateOrder placeLimitOrder(
      String symbol,
      BigDecimal orderQuantity,
      BigDecimal price,
      BitmexSide side,
      String clOrdID,
      String executionInstructions) {
    return bitmex.placeOrder(
        apiKey,
        exchange.getNonceFactory(),
        signatureCreator,
        symbol,
        side == null ? null : side.getCapitalized(),
        orderQuantity.intValue(),
        price,
        null,
        "Limit",
        clOrdID,
        executionInstructions);
  }

  public BitmexPrivateOrder replaceLimitOrder(
      String symbol,
      BigDecimal orderQuantity,
      BigDecimal price,
      String orderId,
      String clOrdID,
      String origClOrdID) {

    return bitmex.replaceOrder(
        apiKey,
        exchange.getNonceFactory(),
        signatureCreator,
        orderQuantity.intValue(),
        price,
        null,
        "Limit",
        // if clOrdID is not null we should not send orderID
        clOrdID != null ? null : orderId,
        clOrdID,
        origClOrdID);
  }

  public BitmexPrivateOrder replaceStopOrder(
      BigDecimal orderQuantity,
      BigDecimal price,
      String orderID,
      String clOrdID,
      String origClOrdId) {
    return bitmex.replaceOrder(
        apiKey,
        exchange.getNonceFactory(),
        signatureCreator,
        orderQuantity.intValue(),
        null,
        price,
        "Limit",
        clOrdID != null ? null : orderID,
        clOrdID,
        origClOrdId);
  }

  public BitmexPrivateOrder placeStopOrder(
      String symbol,
      BitmexSide side,
      BigDecimal orderQuantity,
      BigDecimal stopPrice,
      String executionInstructions,
      String clOrdID) {
    return bitmex.placeOrder(
        apiKey,
        exchange.getNonceFactory(),
        signatureCreator,
        symbol,
        side == null ? null : side.getCapitalized(),
        orderQuantity.intValue(),
        null,
        stopPrice,
        "Stop",
        clOrdID,
        executionInstructions);
  }

  public List<BitmexPrivateOrder> cancelAllOrders() {
    return cancelAllOrders(null, null, null);
  }

  public List<BitmexPrivateOrder> cancelAllOrders(String symbol, String filter, String text) {
    return bitmex.cancelAllOrders(
        apiKey, exchange.getNonceFactory(), signatureCreator, symbol, filter, text);
  }

  public List<BitmexPrivateOrder> cancelBitmexOrder(String orderID) {
    return cancelBitmexOrder(orderID, null);
  }

  public List<BitmexPrivateOrder> cancelBitmexOrder(String orderID, String clOrdID) {
    List<BitmexPrivateOrder> orders =
        bitmex.cancelOrder(
            apiKey,
            exchange.getNonceFactory(),
            signatureCreator,
            clOrdID != null ? null : orderID,
            clOrdID);
    return orders;
  }

  public BitmexPosition updateLeveragePosition(String symbol, BigDecimal leverage) {
    BitmexPosition order =
        bitmex.updateLeveragePosition(
            apiKey, exchange.getNonceFactory(), signatureCreator, symbol, leverage);
    return order;
  }
}
