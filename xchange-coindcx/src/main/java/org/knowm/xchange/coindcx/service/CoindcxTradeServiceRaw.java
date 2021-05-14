package org.knowm.xchange.coindcx.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindcx.dto.*;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;

public class CoindcxTradeServiceRaw extends CoindcxBaseService {

  private final CoindcxHmacPostBodyDigest signatureCreator;
  /**
   * Constructor
   *
   * @param exchange
   */
  public CoindcxTradeServiceRaw(Exchange exchange) {
    super(exchange);
    signatureCreator=  CoindcxHmacPostBodyDigest.createInstance(
            exchange.getExchangeSpecification().getSecretKey());
  }

  public CoindcxOrderStatusResponse placeCoindcxLimitOrder(
      LimitOrder limitOrder, CoindcxOrderType GeminiOrderType) throws IOException {

    String pair = CoindcxUtils.toPairString(new CurrencyPair(limitOrder.getInstrument().toString()));
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
      String signature = signatureCreator.digestParams(request);
      CoindcxOrderStatusResponse newOrder = coindcx.newOrder(apiKey, signature,request);

      return newOrder;
    } catch (CoindcxException e) {
      throw handleException(e);
    }
  }

  public CoindcxOrderStatusResponse placeCoindcxMarketOrder(
          MarketOrder marketOrder, CoindcxOrderType GeminiOrderType) throws IOException {

    String pair = CoindcxUtils.toPairString(new CurrencyPair(marketOrder.getInstrument().toString())).toUpperCase();
    String type = marketOrder.getType().equals(Order.OrderType.BID) ? "buy" : "sell";
    String orderType = GeminiOrderType.toString();

    CoindcxNewOrderRequest request =
            new CoindcxNewOrderRequest(
                    type,
                    orderType,
                    pair,
                    BigDecimal.ZERO,
                    marketOrder.getOriginalAmount(),
                    new Date().getTime());

    try {
      String signature = signatureCreator.digestParams(request);
      CoindcxOrderStatusResponse newOrder = coindcx.newOrder(apiKey, signature,request);

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
      CoindcxOrderStatus coindcxOrderStatus=new CoindcxOrderStatus(orderId);
      String signature = signatureCreator.digestParams(coindcxOrderStatus);
      CoindcxOrderStatusResponse orderStatus =
          coindcx.orderStatus("application/json",apiKey, signature);

      return orderStatus;
    } catch (CoindcxException e) {
      throw handleException(e);
    }
  }

  public List<CoindcxTradeHistoryResponse> getCoindcxAccountTradeHistory(CoindcxAccountTradeHistory accountHistory) throws IOException {
    try {

      String signature = signatureCreator.digestParams(accountHistory);
      List<CoindcxTradeHistoryResponse> tradeHistory =
              coindcx.getAccountTradeHistory("application/json",
                      apiKey, signature);

      return tradeHistory;
    } catch (CoindcxException e) {
      throw handleException(e);
    }
  }
}
