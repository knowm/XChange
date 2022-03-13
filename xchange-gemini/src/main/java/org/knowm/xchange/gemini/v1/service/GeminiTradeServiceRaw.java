package org.knowm.xchange.gemini.v1.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.IOrderFlags;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.gemini.v1.GeminiOrderType;
import org.knowm.xchange.gemini.v1.GeminiUtils;
import org.knowm.xchange.gemini.v1.dto.GeminiException;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiCancelAllOrdersRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiCancelAllOrdersResponse;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiCancelOrderRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiNewOrderRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiNonceOnlyRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiOrderFlags;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiOrderStatusRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiOrderStatusResponse;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiPastTradesRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiTradeResponse;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

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
      GeminiOrderStatusResponse[] activeOrders =
          gemini.activeOrders(
              apiKey,
              payloadCreator,
              signatureCreator,
              new GeminiNonceOnlyRequest(
                  "/v1/orders", String.valueOf(exchange.getNonceFactory().createValue())));
      return activeOrders;
    } catch (GeminiException e) {
      throw handleException(e);
    }
  }

  public GeminiOrderStatusResponse placeGeminiLimitOrder(
      LimitOrder limitOrder, GeminiOrderType GeminiOrderType) throws IOException {

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
      if (flags.contains(GeminiOrderFlags.FILL_OR_KILL)) {
        list.add("fill-or-kill");
      }
      if (flags.contains(GeminiOrderFlags.AUCTION_ONLY)) {
        list.add("auction-only");
      }
      if (flags.contains(GeminiOrderFlags.INDICATION_OF_INTEREST)) {
        list.add("indication-of-interest");
      }
      options = list.toArray();
    }

    GeminiNewOrderRequest request =
        new GeminiNewOrderRequest(
            String.valueOf(exchange.getNonceFactory().createValue()),
            limitOrder.getUserReference(),
            pair,
            limitOrder.getOriginalAmount(),
            limitOrder.getLimitPrice(),
            "Gemini",
            type,
            orderType,
            options);

    try {
      GeminiOrderStatusResponse newOrder =
          gemini.newOrder(apiKey, payloadCreator, signatureCreator, request);
      return newOrder;
    } catch (GeminiException e) {
      throw handleException(e);
    }
  }

  public boolean cancelGeminiOrder(String orderId) throws IOException {
    try {
      gemini.cancelOrders(
          apiKey,
          payloadCreator,
          signatureCreator,
          new GeminiCancelOrderRequest(
              String.valueOf(exchange.getNonceFactory().createValue()), Long.valueOf(orderId)));
      return true;
    } catch (GeminiException e) {
      if (e.getMessage().equals("Order could not be cancelled.")) {
        return false;
      } else {
        throw handleException(e);
      }
    }
  }

  public GeminiCancelAllOrdersResponse cancelAllGeminiOrders(boolean sessionOnly, String accountId)
      throws IOException {
    try {
      if (sessionOnly) {
        return gemini.cancelAllSessionOrders(
            apiKey,
            payloadCreator,
            signatureCreator,
            new GeminiCancelAllOrdersRequest(
                String.valueOf(exchange.getNonceFactory().createValue()), accountId));
      } else {
        return gemini.cancelAllOrders(
            apiKey,
            payloadCreator,
            signatureCreator,
            new GeminiCancelAllOrdersRequest(
                String.valueOf(exchange.getNonceFactory().createValue()), accountId));
      }
    } catch (GeminiException e) {
      throw handleException(e);
    }
  }

  public GeminiOrderStatusResponse getGeminiOrderStatus(String orderId) throws IOException {
    try {
      GeminiOrderStatusResponse orderStatus =
          gemini.orderStatus(
              apiKey,
              payloadCreator,
              signatureCreator,
              new GeminiOrderStatusRequest(
                  String.valueOf(exchange.getNonceFactory().createValue()), Long.valueOf(orderId)));
      return orderStatus;
    } catch (GeminiException e) {
      throw handleException(e);
    }
  }

  public GeminiOrderStatusResponse getGeminiOrderStatus(OrderQueryParams params)
      throws IOException {
    Long orderId = null;
    String clientOrderId = null;
    boolean includeTrades = false;
    String account = null;

    if (params instanceof GeminiTradeService.GeminiOrderQueryParams) {
      orderId = Long.valueOf(params.getOrderId());

      // If both types of ids are included in the params, default to using orderId
      if (orderId == null) {
        clientOrderId = ((GeminiTradeService.GeminiOrderQueryParams) params).getClientOrderId();
      }

      includeTrades = ((GeminiTradeService.GeminiOrderQueryParams) params).isIncludeTrades();
      account = ((GeminiTradeService.GeminiOrderQueryParams) params).getAccount();
    }

    try {
      return gemini.orderStatus(
          apiKey,
          payloadCreator,
          signatureCreator,
          orderId == null
              ? new GeminiOrderStatusRequest(
                  String.valueOf(exchange.getNonceFactory().createValue()),
                  clientOrderId,
                  includeTrades,
                  account)
              : new GeminiOrderStatusRequest(
                  String.valueOf(exchange.getNonceFactory().createValue()),
                  orderId,
                  includeTrades,
                  account));
    } catch (GeminiException e) {
      throw handleException(e);
    }
  }

  public GeminiTradeResponse[] getGeminiTradeHistory(String symbol, long timestamp, Integer limit)
      throws IOException {

    try {
      GeminiTradeResponse[] trades =
          gemini.pastTrades(
              apiKey,
              payloadCreator,
              signatureCreator,
              new GeminiPastTradesRequest(
                  String.valueOf(exchange.getNonceFactory().createValue()),
                  symbol,
                  timestamp,
                  limit));
      return trades;
    } catch (GeminiException e) {
      throw handleException(e);
    }
  }

  public GeminiOrderStatusResponse heartBeat() throws IOException {
    try {
      GeminiOrderStatusResponse orderStatus =
          gemini.heartBeat(
              apiKey,
              payloadCreator,
              signatureCreator,
              new GeminiNonceOnlyRequest(
                  "/v1/heartbeat", String.valueOf(exchange.getNonceFactory().createValue())));
      return orderStatus;
    } catch (GeminiException e) {
      throw handleException(e);
    }
  }
}
