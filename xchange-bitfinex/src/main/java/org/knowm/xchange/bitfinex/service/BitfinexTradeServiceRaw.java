package org.knowm.xchange.bitfinex.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitfinex.dto.BitfinexException;
import org.knowm.xchange.bitfinex.v1.BitfinexOrderType;
import org.knowm.xchange.bitfinex.v1.BitfinexUtils;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexWithdrawalRequest;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexWithdrawalResponse;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexAccountInfosResponse;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexActiveCreditsRequest;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexActivePositionsResponse;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexCancelAllOrdersRequest;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexCancelOfferRequest;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexCancelOrderMultiRequest;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexCancelOrderRequest;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexCreditResponse;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexFundingTradeResponse;
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
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexOrdersHistoryRequest;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexPastFundingTradesRequest;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexPastTradesRequest;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexReplaceOrderRequest;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexTradeResponse;
import org.knowm.xchange.bitfinex.v2.dto.EmptyRequest;
import org.knowm.xchange.bitfinex.v2.dto.trade.ActiveOrder;
import org.knowm.xchange.bitfinex.v2.dto.trade.OrderTrade;
import org.knowm.xchange.bitfinex.v2.dto.trade.Position;
import org.knowm.xchange.bitfinex.v2.dto.trade.Trade;
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

  public BitfinexAccountInfosResponse[] getBitfinexAccountInfos() throws IOException {
    return bitfinex.accountInfos(
        apiKey,
        payloadCreator,
        signatureCreator,
        new BitfinexNonceOnlyRequest(
            "/v1/account_infos", String.valueOf(exchange.getNonceFactory().createValue())));
  }

  public BitfinexOrderStatusResponse[] getBitfinexOpenOrders() throws IOException {
    BitfinexOrderStatusResponse[] activeOrders =
        bitfinex.activeOrders(
            apiKey,
            payloadCreator,
            signatureCreator,
            new BitfinexNonceOnlyRequest(
                "/v1/orders", String.valueOf(exchange.getNonceFactory().createValue())));
    return activeOrders;
  }

  public BitfinexOrderStatusResponse[] getBitfinexOrdersHistory(long limit) throws IOException {
    BitfinexOrderStatusResponse[] orders =
        bitfinex.ordersHist(
            apiKey,
            payloadCreator,
            signatureCreator,
            new BitfinexOrdersHistoryRequest(
                String.valueOf(exchange.getNonceFactory().createValue()), limit));
    return orders;
  }

  public BitfinexOfferStatusResponse[] getBitfinexOpenOffers() throws IOException {
    BitfinexOfferStatusResponse[] activeOffers =
        bitfinex.activeOffers(
            apiKey,
            payloadCreator,
            signatureCreator,
            new BitfinexNonceOnlyRequest(
                "/v1/offers", String.valueOf(exchange.getNonceFactory().createValue())));
    return activeOffers;
  }

  public BitfinexOrderStatusResponse placeBitfinexMarketOrder(
      MarketOrder marketOrder, BitfinexOrderType bitfinexOrderType) throws IOException {

    String pair = BitfinexUtils.toPairStringV1(marketOrder.getCurrencyPair());
    String type =
        (marketOrder.getType().equals(OrderType.BID)
                || marketOrder.getType().equals(OrderType.EXIT_ASK))
            ? "buy"
            : "sell";
    String orderType = bitfinexOrderType.toString();

    BitfinexOrderStatusResponse newOrder =
        bitfinex.newOrder(
            apiKey,
            payloadCreator,
            signatureCreator,
            new BitfinexNewOrderRequest(
                String.valueOf(exchange.getNonceFactory().createValue()),
                pair,
                marketOrder.getOriginalAmount(),
                BigDecimal.ONE,
                "bitfinex",
                type,
                orderType,
                null));
    return newOrder;
  }

  public BitfinexOrderStatusResponse placeBitfinexLimitOrder(
      LimitOrder limitOrder, BitfinexOrderType orderType) throws IOException {

    return sendLimitOrder(limitOrder, orderType, Long.MIN_VALUE);
  }

  public BitfinexOrderStatusResponse replaceBitfinexLimitOrder(
      LimitOrder limitOrder, BitfinexOrderType orderType, long replaceOrderId) throws IOException {
    if (limitOrder instanceof BitfinexLimitOrder
        && ((BitfinexLimitOrder) limitOrder).getOcoStopLimit() != null) {
      throw new ExchangeException("OCO orders are not yet editable");
    }
    return sendLimitOrder(limitOrder, orderType, replaceOrderId);
  }

  private BitfinexOrderStatusResponse sendLimitOrder(
      LimitOrder limitOrder, BitfinexOrderType bitfinexOrderType, long replaceOrderId)
      throws IOException {

    String pair = BitfinexUtils.toPairStringV1(limitOrder.getCurrencyPair());
    String type =
        (limitOrder.getType().equals(Order.OrderType.BID)
                || limitOrder.getType().equals(Order.OrderType.EXIT_ASK))
            ? "buy"
            : "sell";
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
      BigDecimal ocoAmount =
          limitOrder instanceof BitfinexLimitOrder
              ? ((BitfinexLimitOrder) limitOrder).getOcoStopLimit()
              : null;
      BitfinexNewOrderRequest request =
          new BitfinexNewOrderRequest(
              String.valueOf(exchange.getNonceFactory().createValue()),
              pair,
              limitOrder.getOriginalAmount(),
              limitOrder.getLimitPrice(),
              "bitfinex",
              type,
              orderType,
              isHidden,
              isPostOnly,
              ocoAmount);
      response = bitfinex.newOrder(apiKey, payloadCreator, signatureCreator, request);

    } else { // order amend
      boolean useRemaining = limitOrder.hasFlag(BitfinexOrderFlags.USE_REMAINING);

      BitfinexReplaceOrderRequest request =
          new BitfinexReplaceOrderRequest(
              String.valueOf(exchange.getNonceFactory().createValue()),
              replaceOrderId,
              pair,
              limitOrder.getOriginalAmount(),
              limitOrder.getLimitPrice(),
              "bitfinex",
              type,
              orderType,
              isHidden,
              isPostOnly,
              useRemaining);
      response = bitfinex.replaceOrder(apiKey, payloadCreator, signatureCreator, request);
    }

    if (limitOrder instanceof BitfinexLimitOrder) {
      BitfinexLimitOrder bitfinexOrder = (BitfinexLimitOrder) limitOrder;
      bitfinexOrder.setResponse(response);
    }
    return response;
  }

  public BitfinexNewOrderMultiResponse placeBitfinexOrderMulti(
      List<? extends Order> orders, BitfinexOrderType bitfinexOrderType) throws IOException {

    BitfinexNewOrder[] bitfinexOrders = new BitfinexNewOrder[orders.size()];

    for (int i = 0; i < bitfinexOrders.length; i++) {
      Order o = orders.get(i);
      if (o instanceof LimitOrder) {
        LimitOrder limitOrder = (LimitOrder) o;
        String pair = BitfinexUtils.toPairStringV1(limitOrder.getCurrencyPair());
        String type =
            (limitOrder.getType().equals(OrderType.BID)
                    || limitOrder.getType().equals(OrderType.EXIT_ASK))
                ? "buy"
                : "sell";
        String orderType = bitfinexOrderType.toString();
        bitfinexOrders[i] =
            new BitfinexNewOrder(
                pair,
                "bitfinex",
                type,
                orderType,
                limitOrder.getOriginalAmount(),
                limitOrder.getLimitPrice());
      } else if (o instanceof MarketOrder) {
        MarketOrder marketOrder = (MarketOrder) o;
        String pair = BitfinexUtils.toPairStringV1(marketOrder.getCurrencyPair());
        String type =
            (marketOrder.getType().equals(OrderType.BID)
                    || marketOrder.getType().equals(OrderType.EXIT_ASK))
                ? "buy"
                : "sell";
        String orderType = bitfinexOrderType.toString();
        bitfinexOrders[i] =
            new BitfinexNewOrder(
                pair, "bitfinex", type, orderType, marketOrder.getOriginalAmount(), BigDecimal.ONE);
      }
    }

    BitfinexNewOrderMultiRequest request =
        new BitfinexNewOrderMultiRequest(
            String.valueOf(exchange.getNonceFactory().createValue()), bitfinexOrders);
    BitfinexNewOrderMultiResponse response =
        bitfinex.newOrderMulti(apiKey, payloadCreator, signatureCreator, request);
    return response;
  }

  public BitfinexOfferStatusResponse placeBitfinexFixedRateLoanOrder(
      FixedRateLoanOrder loanOrder, BitfinexOrderType orderType) throws IOException {

    String direction = loanOrder.getType() == OrderType.BID ? "loan" : "lend";

    BitfinexOfferStatusResponse newOrderResponse =
        bitfinex.newOffer(
            apiKey,
            payloadCreator,
            signatureCreator,
            new BitfinexNewOfferRequest(
                String.valueOf(exchange.getNonceFactory().createValue()),
                loanOrder.getCurrency(),
                loanOrder.getOriginalAmount(),
                loanOrder.getRate(),
                loanOrder.getDayPeriod(),
                direction));
    return newOrderResponse;
  }

  public BitfinexOfferStatusResponse placeBitfinexFloatingRateLoanOrder(
      FloatingRateLoanOrder loanOrder, BitfinexOrderType orderType) throws IOException {

    String direction = loanOrder.getType() == OrderType.BID ? "loan" : "lend";

    BitfinexOfferStatusResponse newOrderResponse =
        bitfinex.newOffer(
            apiKey,
            payloadCreator,
            signatureCreator,
            new BitfinexNewOfferRequest(
                String.valueOf(exchange.getNonceFactory().createValue()),
                loanOrder.getCurrency(),
                loanOrder.getOriginalAmount(),
                new BigDecimal("0.0"),
                loanOrder.getDayPeriod(),
                direction));
    return newOrderResponse;
  }

  public boolean cancelBitfinexOrder(String orderId) throws IOException {

    try {
      bitfinex.cancelOrders(
          apiKey,
          payloadCreator,
          signatureCreator,
          new BitfinexCancelOrderRequest(
              String.valueOf(exchange.getNonceFactory().createValue()), Long.valueOf(orderId)));
      return true;
    } catch (BitfinexException e) {
      if (e.getMessage().equals("Order could not be cancelled.")) {
        return false;
      } else {
        throw e;
      }
    }
  }

  public boolean cancelAllBitfinexOrders() throws IOException {

    try {
      bitfinex.cancelAllOrders(
          apiKey,
          payloadCreator,
          signatureCreator,
          new BitfinexCancelAllOrdersRequest(
              String.valueOf(exchange.getNonceFactory().createValue())));
      return true;
    } catch (BitfinexException e) {
      if (e.getMessage().equals("Orders could not be cancelled.")) {
        return false;
      } else {
        throw e;
      }
    }
  }

  public boolean cancelBitfinexOrderMulti(List<String> orderIds) throws IOException {

    long[] cancelOrderIds = new long[orderIds.size()];

    for (int i = 0; i < cancelOrderIds.length; i++) {
      cancelOrderIds[i] = Long.valueOf(orderIds.get(i));
    }

    bitfinex.cancelOrderMulti(
        apiKey,
        payloadCreator,
        signatureCreator,
        new BitfinexCancelOrderMultiRequest(
            String.valueOf(exchange.getNonceFactory().createValue()), cancelOrderIds));
    return true;
  }

  public BitfinexOfferStatusResponse cancelBitfinexOffer(String offerId) throws IOException {

    BitfinexOfferStatusResponse cancelResponse =
        bitfinex.cancelOffer(
            apiKey,
            payloadCreator,
            signatureCreator,
            new BitfinexCancelOfferRequest(
                String.valueOf(exchange.getNonceFactory().createValue()), Long.valueOf(offerId)));
    return cancelResponse;
  }

  public BitfinexOrderStatusResponse getBitfinexOrderStatus(String orderId) throws IOException {

    BitfinexOrderStatusResponse orderStatus =
        bitfinex.orderStatus(
            apiKey,
            payloadCreator,
            signatureCreator,
            new BitfinexOrderStatusRequest(
                String.valueOf(exchange.getNonceFactory().createValue()), Long.valueOf(orderId)));
    return orderStatus;
  }

  public BitfinexOfferStatusResponse getBitfinexOfferStatusResponse(String offerId)
      throws IOException {

    BitfinexOfferStatusResponse offerStatus =
        bitfinex.offerStatus(
            apiKey,
            payloadCreator,
            signatureCreator,
            new BitfinexOfferStatusRequest(
                String.valueOf(exchange.getNonceFactory().createValue()), Long.valueOf(offerId)));
    return offerStatus;
  }

  public BitfinexFundingTradeResponse[] getBitfinexFundingHistory(
      String symbol, Date until, int limit_trades) throws IOException {

    BitfinexFundingTradeResponse[] fundingTrades =
        bitfinex.pastFundingTrades(
            apiKey,
            payloadCreator,
            signatureCreator,
            new BitfinexPastFundingTradesRequest(
                String.valueOf(exchange.getNonceFactory().createValue()),
                symbol,
                until,
                limit_trades));
    return fundingTrades;
  }

  public BitfinexTradeResponse[] getBitfinexTradeHistory(
      String symbol, long startTime, Long endTime, Integer limit, Integer reverse)
      throws IOException {

    BitfinexTradeResponse[] trades =
        bitfinex.pastTrades(
            apiKey,
            payloadCreator,
            signatureCreator,
            new BitfinexPastTradesRequest(
                String.valueOf(exchange.getNonceFactory().createValue()),
                symbol,
                startTime,
                endTime,
                limit,
                reverse));
    return trades;
  }

  public BitfinexCreditResponse[] getBitfinexActiveCredits() throws IOException {

    BitfinexCreditResponse[] credits =
        bitfinex.activeCredits(
            apiKey,
            payloadCreator,
            signatureCreator,
            new BitfinexActiveCreditsRequest(
                String.valueOf(exchange.getNonceFactory().createValue())));
    return credits;
  }

  public String withdraw(
      String withdrawType, String walletSelected, BigDecimal amount, String address)
      throws IOException {
    return withdraw(withdrawType, walletSelected, amount, address, null);
  }

  public String withdraw(
      String withdrawType,
      String walletSelected,
      BigDecimal amount,
      String address,
      String paymentId)
      throws IOException {

    BitfinexWithdrawalResponse[] withdrawRepsonse =
        bitfinex.withdraw(
            apiKey,
            payloadCreator,
            signatureCreator,
            new BitfinexWithdrawalRequest(
                String.valueOf(exchange.getNonceFactory().createValue()),
                withdrawType,
                walletSelected,
                amount,
                address,
                paymentId));
    return withdrawRepsonse[0].getWithdrawalId();
  }

  public BitfinexActivePositionsResponse[] getBitfinexActivePositions() throws IOException {
    BitfinexActivePositionsResponse[] activePositions =
        bitfinex.activePositions(
            apiKey,
            payloadCreator,
            signatureCreator,
            new BitfinexNonceOnlyRequest(
                "/v1/positions", String.valueOf(exchange.getNonceFactory().createValue())));
    return activePositions;
  }

  public List<Position> getBitfinexActivePositionsV2() throws IOException {
    return bitfinexV2.activePositions(
        exchange.getNonceFactory(), apiKey, signatureV2, EmptyRequest.INSTANCE);
  }

  public List<Trade> getBitfinexTradesV2(
      String symbol, Long startTimeMillis, Long endTimeMillis, Long limit, Long sort)
      throws IOException {
    if (StringUtils.isBlank(symbol)) {
      return bitfinexV2.getTrades(
          exchange.getNonceFactory(),
          apiKey,
          signatureV2,
          startTimeMillis,
          endTimeMillis,
          limit,
          sort,
          EmptyRequest.INSTANCE);
    }

    return bitfinexV2.getTrades(
        exchange.getNonceFactory(),
        apiKey,
        signatureV2,
        symbol,
        startTimeMillis,
        endTimeMillis,
        limit,
        sort,
        EmptyRequest.INSTANCE);
  }

  public List<ActiveOrder> getBitfinexActiveOrdesV2(String symbol) throws IOException {
    if (symbol == null) {
      symbol = ""; // for empty symbol all active orders are returned
    }
    return bitfinexV2.getActiveOrders(
        exchange.getNonceFactory(), apiKey, signatureV2, symbol, EmptyRequest.INSTANCE);
  }

  public List<OrderTrade> getBitfinexOrderTradesV2(final String symbol, final Long orderId)
      throws IOException {
    if (symbol == null || orderId == null)
      throw new NullPointerException(
          String.format(
              "Invalid request fields symbol [%s] and orderId [%s] are mandatory for get order trades call",
              symbol, orderId));

    return bitfinexV2.getOrderTrades(
        exchange.getNonceFactory(), apiKey, signatureV2, symbol, orderId, EmptyRequest.INSTANCE);
  }
}
