package org.knowm.xchange.bitbns.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitbns.dto.BitbnsException;
import org.knowm.xchange.bitbns.dto.BitbnsNewOrderRequest;
import org.knowm.xchange.bitbns.dto.BitbnsOrderPlaceStatusResponse;
import org.knowm.xchange.bitbns.dto.BitbnsOrderStatusResponse;
import org.knowm.xchange.bitbns.dto.BitbnsUtils;
import org.knowm.xchange.bitbns.dto.OrderStatusBody;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;

public class BitbnsTradeServiceRaw extends BitbnsBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitbnsTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BitbnsOrderPlaceStatusResponse placeCoindcxLimitOrder(LimitOrder limitOrder)
      throws IOException {

    String pair = BitbnsUtils.toPairString(limitOrder.getCurrencyPair());
    String type = limitOrder.getType().equals(Order.OrderType.BID) ? "BUY" : "SELL";
    //    String orderType = GeminiOrderType.toString();

    BitbnsNewOrderRequest request =
        new BitbnsNewOrderRequest(
            type,
            pair,
            limitOrder.getOriginalAmount().toPlainString(),
            limitOrder.getLimitPrice().toPlainString());
    String reqBody = new ObjectMapper().writeValueAsString(request);
    try {
      BitbnsOrderPlaceStatusResponse newOrder =
          pdax.newOrder(apiKey, payloadCreator, signatureCreator, reqBody);

      return newOrder;
    } catch (BitbnsException e) {
      throw handleException(e);
    }
  }

  public BitbnsOrderStatusResponse getOrderStatus(String orderId, String symbol)
      throws IOException {
    try {

      OrderStatusBody orderStatusBody = new OrderStatusBody();
      orderStatusBody.setEntry_id(Long.valueOf(orderId));
      orderStatusBody.setSymbol(symbol);
      BitbnsOrderStatusResponse orderStatus =
          pdax.orderStatus(apiKey, payloadCreator, signatureCreator, symbol, orderStatusBody);

      return orderStatus;
    } catch (BitbnsException e) {
      throw handleException(e);
    }
  }
}
