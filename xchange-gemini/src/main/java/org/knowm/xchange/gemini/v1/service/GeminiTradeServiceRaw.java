package org.knowm.xchange.gemini.v1.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.FixedRateLoanOrder;
import org.knowm.xchange.dto.trade.FloatingRateLoanOrder;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.gemini.v1.GeminiOrderType;
import org.knowm.xchange.gemini.v1.GeminiUtils;
import org.knowm.xchange.gemini.v1.dto.GeminiException;
import org.knowm.xchange.gemini.v1.dto.account.GeminiWithdrawalRequest;
import org.knowm.xchange.gemini.v1.dto.account.GeminiWithdrawalResponse;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiActiveCreditsRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiActivePositionsResponse;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiCancelOfferRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiCancelOrderMultiRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiCancelOrderRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiCreditResponse;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiNewHiddenOrderRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiNewOfferRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiNewOrder;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiNewOrderMultiRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiNewOrderMultiResponse;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiNewOrderRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiNonceOnlyRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiOfferStatusRequest;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiOfferStatusResponse;
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

  public GeminiOfferStatusResponse[] getGeminiOpenOffers() throws IOException {

    try {
      GeminiOfferStatusResponse[] activeOffers = Gemini.activeOffers(apiKey, payloadCreator, signatureCreator,
          new GeminiNonceOnlyRequest("/v1/offers", String.valueOf(exchange.getNonceFactory().createValue())));
      return activeOffers;
    } catch (GeminiException e) {
      throw new ExchangeException(e);
    }
  }

  public GeminiOrderStatusResponse placeGeminiMarketOrder(MarketOrder marketOrder, GeminiOrderType GeminiOrderType) throws IOException {

    String pair = GeminiUtils.toPairString(marketOrder.getCurrencyPair());
    String type = marketOrder.getType().equals(Order.OrderType.BID) ? "buy" : "sell";
    String orderType = GeminiOrderType.toString();

    try {
      GeminiOrderStatusResponse newOrder = Gemini.newOrder(apiKey, payloadCreator, signatureCreator,
          new GeminiNewOrderRequest(String.valueOf(exchange.getNonceFactory().createValue()), pair, marketOrder.getTradableAmount(), BigDecimal.ONE,
              "Gemini", type, orderType));
      return newOrder;
    } catch (GeminiException e) {
      throw new ExchangeException(e);
    }
  }

  public GeminiOrderStatusResponse placeGeminiLimitOrder(LimitOrder limitOrder, GeminiOrderType GeminiOrderType, boolean hidden) throws IOException {

    String pair = GeminiUtils.toPairString(limitOrder.getCurrencyPair());
    String type = limitOrder.getType().equals(Order.OrderType.BID) ? "buy" : "sell";
    String orderType = GeminiOrderType.toString();

    GeminiNewOrderRequest request;
    if (hidden) {
      request = new GeminiNewHiddenOrderRequest(String.valueOf(exchange.getNonceFactory().createValue()), pair, limitOrder.getTradableAmount(),
          limitOrder.getLimitPrice(), "Gemini", type, orderType);
    } else {
      request = new GeminiNewOrderRequest(String.valueOf(exchange.getNonceFactory().createValue()), pair, limitOrder.getTradableAmount(),
          limitOrder.getLimitPrice(), "Gemini", type, orderType);
    }

    try {
      GeminiOrderStatusResponse newOrder = Gemini.newOrder(apiKey, payloadCreator, signatureCreator, request);
      return newOrder;
    } catch (GeminiException e) {
      throw new ExchangeException(e);
    }
  }

  public GeminiNewOrderMultiResponse placeGeminiOrderMulti(List<? extends Order> orders, GeminiOrderType GeminiOrderType) throws IOException {

    GeminiNewOrder[] GeminiOrders = new GeminiNewOrder[orders.size()];

    for (int i = 0; i < GeminiOrders.length; i++) {
      Order o = orders.get(i);
      if (o instanceof LimitOrder) {
        LimitOrder limitOrder = (LimitOrder) o;
        String pair = GeminiUtils.toPairString(limitOrder.getCurrencyPair());
        String type = limitOrder.getType().equals(Order.OrderType.BID) ? "buy" : "sell";
        String orderType = GeminiOrderType.toString();
        GeminiOrders[i] = new GeminiNewOrder(pair, "Gemini", type, orderType, limitOrder.getTradableAmount(), limitOrder.getLimitPrice());
      } else if (o instanceof MarketOrder) {
        MarketOrder marketOrder = (MarketOrder) o;
        String pair = GeminiUtils.toPairString(marketOrder.getCurrencyPair());
        String type = marketOrder.getType().equals(Order.OrderType.BID) ? "buy" : "sell";
        String orderType = GeminiOrderType.toString();
        GeminiOrders[i] = new GeminiNewOrder(pair, "Gemini", type, orderType, marketOrder.getTradableAmount(), BigDecimal.ONE);
      }
    }

    GeminiNewOrderMultiRequest request = new GeminiNewOrderMultiRequest(String.valueOf(exchange.getNonceFactory().createValue()), GeminiOrders);
    try {
      GeminiNewOrderMultiResponse response = Gemini.newOrderMulti(apiKey, payloadCreator, signatureCreator, request);
      return response;
    } catch (GeminiException e) {
      throw new ExchangeException(e);
    }
  }

  public GeminiOfferStatusResponse placeGeminiFixedRateLoanOrder(FixedRateLoanOrder loanOrder, GeminiOrderType orderType) throws IOException {

    String direction = loanOrder.getType() == OrderType.BID ? "loan" : "lend";

    try {
      GeminiOfferStatusResponse newOrderResponse = Gemini.newOffer(apiKey, payloadCreator, signatureCreator,
          new GeminiNewOfferRequest(String.valueOf(exchange.getNonceFactory().createValue()), loanOrder.getCurrency(), loanOrder.getTradableAmount(),
              loanOrder.getRate(), loanOrder.getDayPeriod(), direction));
      return newOrderResponse;
    } catch (GeminiException e) {
      throw new ExchangeException(e);
    }
  }

  public GeminiOfferStatusResponse placeGeminiFloatingRateLoanOrder(FloatingRateLoanOrder loanOrder, GeminiOrderType orderType) throws IOException {

    String direction = loanOrder.getType() == OrderType.BID ? "loan" : "lend";

    try {
      GeminiOfferStatusResponse newOrderResponse = Gemini.newOffer(apiKey, payloadCreator, signatureCreator,
          new GeminiNewOfferRequest(String.valueOf(exchange.getNonceFactory().createValue()), loanOrder.getCurrency(), loanOrder.getTradableAmount(),
              new BigDecimal("0.0"), loanOrder.getDayPeriod(), direction));
      return newOrderResponse;
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

  public boolean cancelGeminiOrderMulti(List<String> orderIds) throws IOException {

    int[] cancelOrderIds = new int[orderIds.size()];

    for (int i = 0; i < cancelOrderIds.length; i++) {
      cancelOrderIds[i] = Integer.valueOf(orderIds.get(i));
    }

    try {
      Gemini.cancelOrderMulti(apiKey, payloadCreator, signatureCreator,
          new GeminiCancelOrderMultiRequest(String.valueOf(exchange.getNonceFactory().createValue()), cancelOrderIds));
      return true;
    } catch (GeminiException e) {
      throw new ExchangeException(e);
    }
  }

  public GeminiOfferStatusResponse cancelGeminiOffer(String offerId) throws IOException {

    try {
      GeminiOfferStatusResponse cancelResponse = Gemini.cancelOffer(apiKey, payloadCreator, signatureCreator,
          new GeminiCancelOfferRequest(String.valueOf(exchange.getNonceFactory().createValue()), Integer.valueOf(offerId)));
      return cancelResponse;
    } catch (GeminiException e) {
      throw new ExchangeException(e);
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

  public GeminiOfferStatusResponse getGeminiOfferStatusResponse(String offerId) throws IOException {

    try {
      GeminiOfferStatusResponse offerStatus = Gemini.offerStatus(apiKey, payloadCreator, signatureCreator,
          new GeminiOfferStatusRequest(String.valueOf(exchange.getNonceFactory().createValue()), Integer.valueOf(offerId)));
      return offerStatus;
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

  public GeminiCreditResponse[] getGeminiActiveCredits() throws IOException {

    try {
      GeminiCreditResponse[] credits = Gemini.activeCredits(apiKey, payloadCreator, signatureCreator,
          new GeminiActiveCreditsRequest(String.valueOf(exchange.getNonceFactory().createValue())));
      return credits;
    } catch (GeminiException e) {
      throw new ExchangeException(e);
    }
  }

  public String withdraw(String withdrawType, String walletSelected, BigDecimal amount, String address) throws IOException {

    GeminiWithdrawalResponse[] withdrawRepsonse = Gemini.withdraw(apiKey, payloadCreator, signatureCreator,
        new GeminiWithdrawalRequest(String.valueOf(exchange.getNonceFactory().createValue()), withdrawType, walletSelected, amount, address));
    return withdrawRepsonse[0].getWithdrawalId();
  }

  public GeminiActivePositionsResponse[] getGeminiActivePositions() throws IOException {

    try {
      GeminiActivePositionsResponse[] activePositions = Gemini.activePositions(apiKey, payloadCreator, signatureCreator,
          new GeminiNonceOnlyRequest("/v1/positions", String.valueOf(exchange.getNonceFactory().createValue())));
      return activePositions;
    } catch (GeminiException e) {
      throw new ExchangeException(e);
    }
  }
}
