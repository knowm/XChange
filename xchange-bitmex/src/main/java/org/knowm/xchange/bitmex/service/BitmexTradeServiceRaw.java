package org.knowm.xchange.bitmex.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmex.BitmexException;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPrivateOrder;
import org.knowm.xchange.bitmex.dto.trade.BitmexPosition;
import org.knowm.xchange.bitmex.dto.trade.BitmexSide;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
        symbol, side == null ? null : side.toString(),
            orderQuantity.intValue(),
            null,
            null,
            "Market",
            null, executionInstructions);
  }

  public BitmexPrivateOrder placeLimitOrder(
          String symbol,
          BigDecimal orderQuantity,
          BigDecimal price,
          BitmexSide side, String clOrdId,
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
            clOrdId, executionInstructions);
  }

  public BitmexPrivateOrder replaceLimitOrder(
          String symbol,
          BigDecimal orderQuantity,
          BigDecimal price,
          String orderId,
          String clOrdId,
          String origClOrdId,
          String executionInstructions) {

    return bitmex.replaceOrder(
        apiKey,
        exchange.getNonceFactory(),
        signatureCreator,
            orderQuantity.intValue(),
        price,
        null,
        "Limit",
      //if clOrdID is not null we should not send orderID
      clOrdId != null ? null : orderId,
      clOrdId, origClOrdId,
       executionInstructions);
  }

  public BitmexPrivateOrder replaceStopOrder(
          BigDecimal orderQuantity,
          BigDecimal price,
          String orderID, String clOrdId,
          String origClOrdId,
          String executionInstructions) {
    return bitmex.replaceOrder(
        apiKey,
        exchange.getNonceFactory(),
        signatureCreator,
            orderQuantity.intValue(),
        null,
            price,
        "Limit",
            clOrdId != null ? null : orderID,
            clOrdId,
            origClOrdId, executionInstructions);
  }

  public BitmexPrivateOrder placeStopOrder(
          String symbol, BitmexSide side, BigDecimal orderQuantity, BigDecimal stopPrice, String executionInstructions) {
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
            null, executionInstructions);
  }

  public boolean cancelBitmexOrder(String orderID) {
    List<BitmexPrivateOrder> orders =
        bitmex.cancelOrder(apiKey, exchange.getNonceFactory(), signatureCreator, orderID);
    return orders.get(0).getId().equals(orderID);
  }


  public BitmexPosition updateLeveragePosition(String symbol, BigDecimal leverage) {
    BitmexPosition order =
        bitmex.updateLeveragePosition(apiKey, exchange.getNonceFactory(), signatureCreator, symbol,leverage);
    return order;
  }



}
