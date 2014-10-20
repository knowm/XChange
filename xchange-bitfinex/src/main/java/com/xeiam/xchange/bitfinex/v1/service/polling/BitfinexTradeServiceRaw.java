package com.xeiam.xchange.bitfinex.v1.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitfinex.v1.BitfinexAuthenticated;
import com.xeiam.xchange.bitfinex.v1.BitfinexOrderType;
import com.xeiam.xchange.bitfinex.v1.BitfinexUtils;
import com.xeiam.xchange.bitfinex.v1.dto.BitfinexException;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexActiveCreditsRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexActivePositionsResponse;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexCancelOfferRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexCancelOrderRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexCreditResponse;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexNewHiddenOrderRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexNewOfferRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexNewOrderRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexNonceOnlyRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexOfferStatusRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexOfferStatusResponse;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexOrderStatusRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexOrderStatusResponse;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexPastTradesRequest;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexTradeResponse;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.FixedRateLoanOrder;
import com.xeiam.xchange.dto.trade.FloatingRateLoanOrder;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;

public class BitfinexTradeServiceRaw extends BitfinexBasePollingService<BitfinexAuthenticated> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BitfinexTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(BitfinexAuthenticated.class, exchangeSpecification);
  }

  public BitfinexOrderStatusResponse[] getBitfinexOpenOrders() throws IOException {

    try {
      BitfinexOrderStatusResponse[] activeOrders = bitfinex.activeOrders(apiKey, payloadCreator, signatureCreator, new BitfinexNonceOnlyRequest("/v1/orders", String.valueOf(nextNonce())));
      return activeOrders;
    } catch (BitfinexException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public BitfinexOfferStatusResponse[] getBitfinexOpenOffers() throws IOException {

    try {
      BitfinexOfferStatusResponse[] activeOffers = bitfinex.activeOffers(apiKey, payloadCreator, signatureCreator, new BitfinexNonceOnlyRequest("/v1/offers", String.valueOf(nextNonce())));
      return activeOffers;
    } catch (BitfinexException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public BitfinexOrderStatusResponse placeBitfinexMarketOrder(MarketOrder marketOrder, BitfinexOrderType bitfinexOrderType) throws IOException {

    String pair = BitfinexUtils.toPairString(marketOrder.getCurrencyPair());
    String type = marketOrder.getType().equals(Order.OrderType.BID) ? "buy" : "sell";
    String orderType = bitfinexOrderType.toString();

    try {
      BitfinexOrderStatusResponse newOrder =
          bitfinex.newOrder(apiKey, payloadCreator, signatureCreator, new BitfinexNewOrderRequest(String.valueOf(nextNonce()), pair, marketOrder.getTradableAmount(), BigDecimal.ONE, "bitfinex", type,
              orderType));
      return newOrder;
    } catch (BitfinexException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public BitfinexOrderStatusResponse placeBitfinexLimitOrder(LimitOrder limitOrder, BitfinexOrderType bitfinexOrderType, boolean hidden) throws IOException {

    String pair = BitfinexUtils.toPairString(limitOrder.getCurrencyPair());
    String type = limitOrder.getType().equals(Order.OrderType.BID) ? "buy" : "sell";
    String orderType = bitfinexOrderType.toString();

    BitfinexNewOrderRequest request;
    if (hidden) {
      request = new BitfinexNewHiddenOrderRequest(String.valueOf(nextNonce()), pair, limitOrder.getTradableAmount(), limitOrder.getLimitPrice(), "bitfinex", type, orderType);
    }
    else {
      request = new BitfinexNewOrderRequest(String.valueOf(nextNonce()), pair, limitOrder.getTradableAmount(), limitOrder.getLimitPrice(), "bitfinex", type, orderType);
    }

    try {
      BitfinexOrderStatusResponse newOrder = bitfinex.newOrder(apiKey, payloadCreator, signatureCreator, request);
      return newOrder;
    } catch (BitfinexException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public BitfinexOfferStatusResponse placeBitfinexFixedRateLoanOrder(FixedRateLoanOrder loanOrder, BitfinexOrderType orderType) throws IOException {

    String direction = loanOrder.getType() == OrderType.BID ? "loan" : "lend";

    try {
      BitfinexOfferStatusResponse newOrderResponse =
          bitfinex.newOffer(apiKey, payloadCreator, signatureCreator, new BitfinexNewOfferRequest(String.valueOf(nextNonce()), loanOrder.getCurrency(), loanOrder.getTradableAmount(), loanOrder
              .getRate(), loanOrder.getDayPeriod(), direction));
      return newOrderResponse;
    } catch (BitfinexException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public BitfinexOfferStatusResponse placeBitfinexFloatingRateLoanOrder(FloatingRateLoanOrder loanOrder, BitfinexOrderType orderType) throws IOException {

    String direction = loanOrder.getType() == OrderType.BID ? "loan" : "lend";

    try {
      BitfinexOfferStatusResponse newOrderResponse =
          bitfinex.newOffer(apiKey, payloadCreator, signatureCreator, new BitfinexNewOfferRequest(String.valueOf(nextNonce()), loanOrder.getCurrency(), loanOrder.getTradableAmount(), new BigDecimal(
              "0.0"), loanOrder.getDayPeriod(), direction));
      return newOrderResponse;
    } catch (BitfinexException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public boolean cancelBitfinexOrder(String orderId) throws IOException {

    try {
      bitfinex.cancelOrders(apiKey, payloadCreator, signatureCreator, new BitfinexCancelOrderRequest(String.valueOf(nextNonce()), Integer.valueOf(orderId)));
      return true;
    } catch (BitfinexException e) {
      if (e.getMessage().equals("Order could not be cancelled.")) {
        return false;
      }
      else {
        throw new ExchangeException(e.getMessage());
      }
    }
  }

  public BitfinexOfferStatusResponse cancelBitfinexOffer(String offerId) throws IOException {

    try {
      BitfinexOfferStatusResponse cancelResponse =
          bitfinex.cancelOffer(apiKey, payloadCreator, signatureCreator, new BitfinexCancelOfferRequest(String.valueOf(nextNonce()), Integer.valueOf(offerId)));
      return cancelResponse;
    } catch (BitfinexException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public BitfinexOrderStatusResponse getBitfinexOrderStatus(String orderId) throws IOException {

    try {
      BitfinexOrderStatusResponse orderStatus = bitfinex.orderStatus(apiKey, payloadCreator, signatureCreator, new BitfinexOrderStatusRequest(String.valueOf(nextNonce()), Integer.valueOf(orderId)));
      return orderStatus;
    } catch (BitfinexException e) {
      throw new ExchangeException(e.getMessage());
    }

  }

  public BitfinexOfferStatusResponse getBitfinexOfferStatusResponse(String offerId) throws IOException {

    try {
      BitfinexOfferStatusResponse offerStatus = bitfinex.offerStatus(apiKey, payloadCreator, signatureCreator, new BitfinexOfferStatusRequest(String.valueOf(nextNonce()), Integer.valueOf(offerId)));
      return offerStatus;
    } catch (BitfinexException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public BitfinexTradeResponse[] getBitfinexTradeHistory(String symbol, long timestamp, int limit) throws IOException {

    try {
      BitfinexTradeResponse[] trades = bitfinex.pastTrades(apiKey, payloadCreator, signatureCreator, new BitfinexPastTradesRequest(String.valueOf(nextNonce()), symbol, timestamp, limit));
      return trades;
    } catch (BitfinexException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public BitfinexCreditResponse[] getBitfinexActiveCredits() throws IOException {

    try {
      BitfinexCreditResponse[] credits = bitfinex.activeCredits(apiKey, payloadCreator, signatureCreator, new BitfinexActiveCreditsRequest(String.valueOf(nextNonce())));
      return credits;
    } catch (BitfinexException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public BitfinexActivePositionsResponse[] getBitfinexActivePositions() throws IOException {

    try {
      BitfinexActivePositionsResponse[] activePositions =
          bitfinex.activePositions(apiKey, payloadCreator, signatureCreator, new BitfinexNonceOnlyRequest("/v1/positions", String.valueOf(nextNonce())));
      return activePositions;
    } catch (BitfinexException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

}
