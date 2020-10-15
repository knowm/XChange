package org.knowm.xchange.coindcx.service;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindcx.dto.CoindcxException;
import org.knowm.xchange.coindcx.dto.CoindcxNewOrderRequest;
import org.knowm.xchange.coindcx.dto.CoindcxOrderStatusRequest;
import org.knowm.xchange.coindcx.dto.CoindcxOrderStatusResponse;
import org.knowm.xchange.coindcx.dto.CoindcxOrderType;
import org.knowm.xchange.coindcx.dto.CoindcxUtils;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;

public class CoindcxTradeServiceRaw extends CoindcxBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoindcxTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public CoindcxOrderStatusResponse placeCoindcxLimitOrder(
      LimitOrder limitOrder, CoindcxOrderType GeminiOrderType) throws IOException {

    String pair = CoindcxUtils.toPairString(limitOrder.getCurrencyPair());
    String type = limitOrder.getType().equals(Order.OrderType.BID) ? "buy" : "sell";
    String orderType = GeminiOrderType.toString();

    CoindcxNewOrderRequest request =
        new CoindcxNewOrderRequest(
            type,
            orderType,
            pair,
            limitOrder.getOriginalAmount(),
            limitOrder.getLimitPrice(),
            new Date().getTime());

    try {

      CoindcxOrderStatusResponse newOrder = coindcx.newOrder(apiKey, signatureCreator, request);

      return newOrder;
    } catch (CoindcxException e) {
      throw handleException(e);
    }
  }

  //  public boolean cancelGeminiOrder(String orderId) throws IOException {
  //
  //    try {
  //      gemini.cancelOrders(
  //          apiKey,
  //          payloadCreator,
  //          signatureCreator,
  //          new GeminiCancelOrderRequest(
  //              String.valueOf(exchange.getNonceFactory().createValue()), Long.valueOf(orderId)));
  //      return true;
  //    } catch (GeminiException e) {
  //      if (e.getMessage().equals("Order could not be cancelled.")) {
  //        return false;
  //      } else {
  //        throw handleException(e);
  //      }
  //    }
  //  }

  public CoindcxOrderStatusResponse getGeminiOrderStatus(String orderId) throws IOException {
    try {

      CoindcxOrderStatusResponse orderStatus =
          coindcx.orderStatus(
              apiKey, signatureCreator, new CoindcxOrderStatusRequest(UUID.fromString(orderId)));

      return orderStatus;
    } catch (CoindcxException e) {
      throw handleException(e);
    }
  }
}
