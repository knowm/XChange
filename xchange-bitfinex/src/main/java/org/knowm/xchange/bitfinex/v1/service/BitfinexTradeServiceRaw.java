package org.knowm.xchange.bitfinex.v1.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitfinex.v1.BitfinexOrderType;
import org.knowm.xchange.bitfinex.v1.BitfinexUtils;
import org.knowm.xchange.bitfinex.v1.dto.BitfinexException;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexWithdrawalRequest;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexWithdrawalResponse;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexActiveCreditsRequest;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexActivePositionsResponse;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexCancelOfferRequest;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexCancelOrderMultiRequest;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexCancelOrderRequest;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexCreditResponse;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexLimitOrder;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexNewOfferRequest;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexNewOrder;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexNewOrderMultiRequest;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexNewOrderMultiResponse;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexNewOrderRequest;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexNonceOnlyRequest;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexOfferStatusRequest;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexOfferStatusResponse;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexOrderFlags;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexOrderStatusRequest;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexOrderStatusResponse;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexPastTradesRequest;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexReplaceOrderRequest;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexTradeResponse;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.FixedRateLoanOrder;
import org.knowm.xchange.dto.trade.FloatingRateLoanOrder;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.exceptions.ExchangeException;

public class BitfinexTradeServiceRaw extends BitfinexBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitfinexTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public BitfinexOrderStatusResponse[] getBitfinexOpenOrders() throws IOException {

    try {
      BitfinexOrderStatusResponse[] activeOrders = bitfinex.activeOrders(apiKey, payloadCreator, signatureCreator,
          new BitfinexNonceOnlyRequest("/v1/orders", String.valueOf(exchange.getNonceFactory().createValue())));
      return activeOrders;
    } catch (BitfinexException e) {
      throw new ExchangeException(e);
    }
  }

  public BitfinexOfferStatusResponse[] getBitfinexOpenOffers() throws IOException {

    try {
      BitfinexOfferStatusResponse[] activeOffers = bitfinex.activeOffers(apiKey, payloadCreator, signatureCreator,
          new BitfinexNonceOnlyRequest("/v1/offers", String.valueOf(exchange.getNonceFactory().createValue())));
      return activeOffers;
    } catch (BitfinexException e) {
      throw new ExchangeException(e);
    }
  }

  public BitfinexOrderStatusResponse placeBitfinexMarketOrder(MarketOrder marketOrder, BitfinexOrderType bitfinexOrderType) throws IOException {

    String pair = BitfinexUtils.toPairString(marketOrder.getCurrencyPair());
    String type = marketOrder.getType().equals(Order.OrderType.BID) ? "buy" : "sell";
    String orderType = bitfinexOrderType.toString();

    try {
      BitfinexOrderStatusResponse newOrder = bitfinex.newOrder(apiKey, payloadCreator, signatureCreator,
          new BitfinexNewOrderRequest(String.valueOf(exchange.getNonceFactory().createValue()), pair, marketOrder.getTradableAmount(), BigDecimal.ONE,
              "bitfinex", type, orderType));
      return newOrder;
    } catch (BitfinexException e) {
      throw new ExchangeException(e);
    }
  }

  public BitfinexOrderStatusResponse placeBitfinexLimitOrder(LimitOrder limitOrder, BitfinexOrderType orderType) throws IOException {

    return sendLimitOrder(limitOrder, orderType, Long.MIN_VALUE);
  }

  public BitfinexOrderStatusResponse replaceBitfinexLimitOrder(LimitOrder limitOrder, BitfinexOrderType orderType, long replaceOrderId)
      throws IOException {

    return sendLimitOrder(limitOrder, orderType, replaceOrderId);
  }

  private BitfinexOrderStatusResponse sendLimitOrder(LimitOrder limitOrder, BitfinexOrderType bitfinexOrderType, long replaceOrderId)
      throws IOException {

    String pair = BitfinexUtils.toPairString(limitOrder.getCurrencyPair());
    String type = limitOrder.getType().equals(Order.OrderType.BID) ? "buy" : "sell";
    String orderType = bitfinexOrderType.toString();

    boolean isHidden;
    if (limitOrder.hasFlag(BitfinexOrderFlags.HIDDEN)) {
      isHidden = true;
    } else {
      isHidden = false;
    }
    boolean isPostOnly;
    if (limitOrder.hasFlag(BitfinexOrderFlags.POST_ONLY)) {
      isPostOnly = true;
    } else {
      isPostOnly = false;
    }

    BitfinexOrderStatusResponse response;
    if (replaceOrderId == Long.MIN_VALUE) { // order entry
      BitfinexNewOrderRequest request = new BitfinexNewOrderRequest(String.valueOf(exchange.getNonceFactory().createValue()), pair,
          limitOrder.getTradableAmount(), limitOrder.getLimitPrice(), "bitfinex", type, orderType, isHidden, isPostOnly);
      try {
        response = bitfinex.newOrder(apiKey, payloadCreator, signatureCreator, request);
      } catch (BitfinexException e) {
        throw new ExchangeException(e);
      }

    } else { // order amend
      boolean useRemaining;
      if (limitOrder.hasFlag(BitfinexOrderFlags.USE_REMAINING)) {
        useRemaining = true;
      } else {
        useRemaining = false;
      }

      BitfinexReplaceOrderRequest request = new BitfinexReplaceOrderRequest(String.valueOf(exchange.getNonceFactory().createValue()), replaceOrderId,
          pair, limitOrder.getTradableAmount(), limitOrder.getLimitPrice(), "bitfinex", type, orderType, isHidden, isPostOnly, useRemaining);
      try {
        response = bitfinex.replaceOrder(apiKey, payloadCreator, signatureCreator, request);
      } catch (BitfinexException e) {
        throw new ExchangeException(e);
      }
    }

    if (limitOrder instanceof BitfinexLimitOrder) {
      BitfinexLimitOrder bitfinexOrder = (BitfinexLimitOrder) limitOrder;
      bitfinexOrder.setResponse(response);
    }
    return response;
  }

  public BitfinexNewOrderMultiResponse placeBitfinexOrderMulti(List<? extends Order> orders, BitfinexOrderType bitfinexOrderType) throws IOException {

    BitfinexNewOrder[] bitfinexOrders = new BitfinexNewOrder[orders.size()];

    for (int i = 0; i < bitfinexOrders.length; i++) {
      Order o = orders.get(i);
      if (o instanceof LimitOrder) {
        LimitOrder limitOrder = (LimitOrder) o;
        String pair = BitfinexUtils.toPairString(limitOrder.getCurrencyPair());
        String type = limitOrder.getType().equals(Order.OrderType.BID) ? "buy" : "sell";
        String orderType = bitfinexOrderType.toString();
        bitfinexOrders[i] = new BitfinexNewOrder(pair, "bitfinex", type, orderType, limitOrder.getTradableAmount(), limitOrder.getLimitPrice());
      } else if (o instanceof MarketOrder) {
        MarketOrder marketOrder = (MarketOrder) o;
        String pair = BitfinexUtils.toPairString(marketOrder.getCurrencyPair());
        String type = marketOrder.getType().equals(Order.OrderType.BID) ? "buy" : "sell";
        String orderType = bitfinexOrderType.toString();
        bitfinexOrders[i] = new BitfinexNewOrder(pair, "bitfinex", type, orderType, marketOrder.getTradableAmount(), BigDecimal.ONE);
      }
    }

    BitfinexNewOrderMultiRequest request = new BitfinexNewOrderMultiRequest(String.valueOf(exchange.getNonceFactory().createValue()), bitfinexOrders);
    try {
      BitfinexNewOrderMultiResponse response = bitfinex.newOrderMulti(apiKey, payloadCreator, signatureCreator, request);
      return response;
    } catch (BitfinexException e) {
      throw new ExchangeException(e);
    }
  }

  public BitfinexOfferStatusResponse placeBitfinexFixedRateLoanOrder(FixedRateLoanOrder loanOrder, BitfinexOrderType orderType) throws IOException {

    String direction = loanOrder.getType() == OrderType.BID ? "loan" : "lend";

    try {
      BitfinexOfferStatusResponse newOrderResponse = bitfinex.newOffer(apiKey, payloadCreator, signatureCreator,
          new BitfinexNewOfferRequest(String.valueOf(exchange.getNonceFactory().createValue()), loanOrder.getCurrency(),
              loanOrder.getTradableAmount(), loanOrder.getRate(), loanOrder.getDayPeriod(), direction));
      return newOrderResponse;
    } catch (BitfinexException e) {
      throw new ExchangeException(e);
    }
  }

  public BitfinexOfferStatusResponse placeBitfinexFloatingRateLoanOrder(FloatingRateLoanOrder loanOrder, BitfinexOrderType orderType)
      throws IOException {

    String direction = loanOrder.getType() == OrderType.BID ? "loan" : "lend";

    try {
      BitfinexOfferStatusResponse newOrderResponse = bitfinex.newOffer(apiKey, payloadCreator, signatureCreator,
          new BitfinexNewOfferRequest(String.valueOf(exchange.getNonceFactory().createValue()), loanOrder.getCurrency(),
              loanOrder.getTradableAmount(), new BigDecimal("0.0"), loanOrder.getDayPeriod(), direction));
      return newOrderResponse;
    } catch (BitfinexException e) {
      throw new ExchangeException(e);
    }
  }

  public boolean cancelBitfinexOrder(String orderId) throws IOException {

    try {
      bitfinex.cancelOrders(apiKey, payloadCreator, signatureCreator,
          new BitfinexCancelOrderRequest(String.valueOf(exchange.getNonceFactory().createValue()), Integer.valueOf(orderId)));
      return true;
    } catch (BitfinexException e) {
      if (e.getMessage().equals("Order could not be cancelled.")) {
        return false;
      } else {
        throw new ExchangeException(e);
      }
    }
  }

  public boolean cancelBitfinexOrderMulti(List<String> orderIds) throws IOException {

    int[] cancelOrderIds = new int[orderIds.size()];

    for (int i = 0; i < cancelOrderIds.length; i++) {
      cancelOrderIds[i] = Integer.valueOf(orderIds.get(i));
    }

    try {
      bitfinex.cancelOrderMulti(apiKey, payloadCreator, signatureCreator,
          new BitfinexCancelOrderMultiRequest(String.valueOf(exchange.getNonceFactory().createValue()), cancelOrderIds));
      return true;
    } catch (BitfinexException e) {
      throw new ExchangeException(e);
    }
  }

  public BitfinexOfferStatusResponse cancelBitfinexOffer(String offerId) throws IOException {

    try {
      BitfinexOfferStatusResponse cancelResponse = bitfinex.cancelOffer(apiKey, payloadCreator, signatureCreator,
          new BitfinexCancelOfferRequest(String.valueOf(exchange.getNonceFactory().createValue()), Integer.valueOf(offerId)));
      return cancelResponse;
    } catch (BitfinexException e) {
      throw new ExchangeException(e);
    }
  }

  public BitfinexOrderStatusResponse getBitfinexOrderStatus(String orderId) throws IOException {

    try {
      BitfinexOrderStatusResponse orderStatus = bitfinex.orderStatus(apiKey, payloadCreator, signatureCreator,
          new BitfinexOrderStatusRequest(String.valueOf(exchange.getNonceFactory().createValue()), Integer.valueOf(orderId)));
      return orderStatus;
    } catch (BitfinexException e) {
      throw new ExchangeException(e);
    }

  }

  public BitfinexOfferStatusResponse getBitfinexOfferStatusResponse(String offerId) throws IOException {

    try {
      BitfinexOfferStatusResponse offerStatus = bitfinex.offerStatus(apiKey, payloadCreator, signatureCreator,
          new BitfinexOfferStatusRequest(String.valueOf(exchange.getNonceFactory().createValue()), Integer.valueOf(offerId)));
      return offerStatus;
    } catch (BitfinexException e) {
      throw new ExchangeException(e);
    }
  }

  public BitfinexTradeResponse[] getBitfinexTradeHistory(String symbol, long timestamp, int limit) throws IOException {

    try {
      BitfinexTradeResponse[] trades = bitfinex.pastTrades(apiKey, payloadCreator, signatureCreator,
          new BitfinexPastTradesRequest(String.valueOf(exchange.getNonceFactory().createValue()), symbol, timestamp, limit));
      return trades;
    } catch (BitfinexException e) {
      throw new ExchangeException(e);
    }
  }

  public BitfinexCreditResponse[] getBitfinexActiveCredits() throws IOException {

    try {
      BitfinexCreditResponse[] credits = bitfinex.activeCredits(apiKey, payloadCreator, signatureCreator,
          new BitfinexActiveCreditsRequest(String.valueOf(exchange.getNonceFactory().createValue())));
      return credits;
    } catch (BitfinexException e) {
      throw new ExchangeException(e);
    }
  }

  public String withdraw(String withdrawType, String walletSelected, BigDecimal amount, String address) throws IOException {
     return withdraw(withdrawType, walletSelected, amount, address, null);
  }
  
  public String withdraw(String withdrawType, String walletSelected, BigDecimal amount, String address, String paymentId) throws IOException {

    BitfinexWithdrawalResponse[] withdrawRepsonse = bitfinex.withdraw(apiKey, payloadCreator, signatureCreator,
        new BitfinexWithdrawalRequest(String.valueOf(exchange.getNonceFactory().createValue()), withdrawType, walletSelected, amount, address, paymentId));
    return withdrawRepsonse[0].getWithdrawalId();
  }

  public BitfinexActivePositionsResponse[] getBitfinexActivePositions() throws IOException {

    try {
      BitfinexActivePositionsResponse[] activePositions = bitfinex.activePositions(apiKey, payloadCreator, signatureCreator,
          new BitfinexNonceOnlyRequest("/v1/positions", String.valueOf(exchange.getNonceFactory().createValue())));
      return activePositions;
    } catch (BitfinexException e) {
      throw new ExchangeException(e);
    }
  }
}
