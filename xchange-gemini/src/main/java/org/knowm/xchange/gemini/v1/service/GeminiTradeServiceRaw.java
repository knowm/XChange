package org.knowm.xchange.gemini.v1.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.IOrderFlags;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.gemini.v1.GeminiOrderType;
import org.knowm.xchange.gemini.v1.GeminiUtils;
import org.knowm.xchange.gemini.v1.dto.GeminiException;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiCancelOrderRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiNewOrderRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiNonceOnlyRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiOrderFlags;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiOrderStatusRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiOrderStatusResponse;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiPastTradesRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiTradeResponse;

public class GeminiTradeServiceRaw extends GeminiBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GeminiTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public GeminiOrderStatusResponse[] getGeminiOpenOrders() throws IOException {

    try {
      GeminiOrderStatusResponse[] activeOrders = Gemini.activeOrders(apiKey, payloadCreator, signatureCreator,
          new GeminiNonceOnlyRequest("/v1/orders", String.valueOf(exchange.getNonceFactory().createValue())));
      return activeOrders;
    } catch (GeminiException e) {
      throw new ExchangeException(e);
    }
  }

  public GeminiOrderStatusResponse placeGeminiLimitOrder(LimitOrder limitOrder, GeminiOrderType GeminiOrderType) throws IOException {

    String pair = GeminiUtils.toPairString(limitOrder.getCurrencyPair());
    String type = limitOrder.getType().equals(Order.OrderType.BID) ? "buy" : "sell";
    String orderType = GeminiOrderType.toString();

    Set<IOrderFlags> flags = limitOrder.getOrderFlags();
    Object[] options;
    if (flags.isEmpty()) {
      options = null;
    } else {
      ArrayList<String> list = new ArrayList<String>();
      if (flags.contains(GeminiOrderFlags.IMMEDIATE_OR_CANCEL)) {
        list.add("immediate-or-cancel");
      }
      if (flags.contains(GeminiOrderFlags.POST_ONLY)) {
        list.add("maker-or-cancel");
      }
      options = list.toArray();
    }

    GeminiNewOrderRequest request = new GeminiNewOrderRequest(String.valueOf(exchange.getNonceFactory().createValue()), pair,
        limitOrder.getTradableAmount(), limitOrder.getLimitPrice(), "Gemini", type, orderType, options);

    try {
      GeminiOrderStatusResponse newOrder = Gemini.newOrder(apiKey, payloadCreator, signatureCreator, request);
      return newOrder;
    } catch (GeminiException e) {
      throw new ExchangeException(e);
    }
  }

  public boolean cancelGeminiOrder(String orderId) throws IOException {

    try {
      Gemini.cancelOrders(apiKey, payloadCreator, signatureCreator,
          new GeminiCancelOrderRequest(String.valueOf(exchange.getNonceFactory().createValue()), Integer.valueOf(orderId)));
      return true;
    } catch (GeminiException e) {
      if (e.getMessage().equals("Order could not be cancelled.")) {
        return false;
      } else {
        throw new ExchangeException(e);
      }
    }
  }

  public GeminiOrderStatusResponse getGeminiOrderStatus(String orderId) throws IOException {

    try {
      GeminiOrderStatusResponse orderStatus = Gemini.orderStatus(apiKey, payloadCreator, signatureCreator,
          new GeminiOrderStatusRequest(String.valueOf(exchange.getNonceFactory().createValue()), Integer.valueOf(orderId)));
      return orderStatus;
    } catch (GeminiException e) {
      throw new ExchangeException(e);
    }

  }

  public GeminiTradeResponse[] getGeminiTradeHistory(String symbol, long timestamp, int limit) throws IOException {

    try {
      GeminiTradeResponse[] trades = Gemini.pastTrades(apiKey, payloadCreator, signatureCreator,
          new GeminiPastTradesRequest(String.valueOf(exchange.getNonceFactory().createValue()), symbol, timestamp, limit));
      return trades;
    } catch (GeminiException e) {
      throw new ExchangeException(e);
    }
  }

}
