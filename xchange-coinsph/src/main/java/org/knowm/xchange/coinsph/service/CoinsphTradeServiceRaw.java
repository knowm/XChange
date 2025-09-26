package org.knowm.xchange.coinsph.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.coinsph.CoinsphAdapters;
import org.knowm.xchange.coinsph.CoinsphExchange;
import org.knowm.xchange.coinsph.dto.CoinsphException;
import org.knowm.xchange.coinsph.dto.trade.CoinsphOrder;
import org.knowm.xchange.coinsph.dto.trade.CoinsphUserTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;

public class CoinsphTradeServiceRaw extends CoinsphBaseService {

  protected CoinsphTradeServiceRaw(
      CoinsphExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  public List<CoinsphOrder> getCoinsphOpenOrders(CurrencyPair currencyPair)
      throws IOException, CoinsphException {
    return decorateApiCall(
            () ->
                coinsphAuthenticated.getOpenOrders(
                    apiKey,
                    timestampFactory,
                    signatureCreator,
                    currencyPair != null ? CoinsphAdapters.toSymbol(currencyPair) : null,
                    exchange.getRecvWindow()))
        .call();
  }

  public CoinsphOrder placeCoinsphMarketOrder(MarketOrder marketOrder)
      throws IOException, CoinsphException {
    final String symbol = CoinsphAdapters.toSymbol(marketOrder.getCurrencyPair());
    final org.knowm.xchange.coinsph.dto.trade.CoinsphOrderSide side =
        CoinsphAdapters.toSide(marketOrder.getType());
    final org.knowm.xchange.coinsph.dto.trade.CoinsphOrderType type =
        org.knowm.xchange.coinsph.dto.trade.CoinsphOrderType.MARKET; // Explicitly MARKET

    final BigDecimal finalQuantity;
    final BigDecimal finalQuoteOrderQty;

    if (marketOrder.hasFlag(CoinsphAdapters.CoinsphOrderFlags.QUOTE_ORDER_QTY)) {
      finalQuoteOrderQty = marketOrder.getOriginalAmount();
      finalQuantity = null;
    } else {
      finalQuantity = marketOrder.getOriginalAmount();
      finalQuoteOrderQty = null;
    }

    final String finalNewClientOrderId = marketOrder.getUserReference();
    final Long finalRecvWindow = exchange.getRecvWindow();
    // timeInForce, price, stopPrice are null for basic MARKET orders

    return decorateApiCall(
            () ->
                coinsphAuthenticated.newOrder(
                    apiKey,
                    symbol,
                    side,
                    type,
                    null, // timeInForce
                    finalQuantity,
                    finalQuoteOrderQty,
                    null, // price
                    finalNewClientOrderId,
                    null, // stopPrice
                    finalRecvWindow,
                    timestampFactory,
                    signatureCreator))
        .call();
  }

  public CoinsphOrder placeCoinsphLimitOrder(LimitOrder limitOrder)
      throws IOException, CoinsphException {
    final String symbol = CoinsphAdapters.toSymbol(limitOrder.getCurrencyPair());
    final org.knowm.xchange.coinsph.dto.trade.CoinsphOrderSide side =
        CoinsphAdapters.toSide(limitOrder.getType());
    final org.knowm.xchange.coinsph.dto.trade.CoinsphOrderType type =
        org.knowm.xchange.coinsph.dto.trade.CoinsphOrderType.LIMIT; // Explicitly LIMIT

    org.knowm.xchange.coinsph.dto.trade.CoinsphTimeInForce initialTimeInForce =
        org.knowm.xchange.coinsph.dto.trade.CoinsphTimeInForce.GTC; // Default
    for (Order.IOrderFlags flag : limitOrder.getOrderFlags()) {
      if (flag instanceof org.knowm.xchange.coinsph.dto.trade.CoinsphTimeInForce) {
        initialTimeInForce = (org.knowm.xchange.coinsph.dto.trade.CoinsphTimeInForce) flag;
        break;
      }
    }
    final org.knowm.xchange.coinsph.dto.trade.CoinsphTimeInForce finalTimeInForce =
        initialTimeInForce;

    final BigDecimal finalQuantity = limitOrder.getOriginalAmount();
    final BigDecimal finalPrice = limitOrder.getLimitPrice();
    final String finalNewClientOrderId = limitOrder.getUserReference();
    final Long finalRecvWindow = exchange.getRecvWindow();
    // quoteOrderQty and stopPrice are null for basic LIMIT orders

    return decorateApiCall(
            () ->
                coinsphAuthenticated.newOrder(
                    apiKey,
                    symbol,
                    side,
                    type,
                    finalTimeInForce,
                    finalQuantity,
                    null, // quoteOrderQty
                    finalPrice,
                    finalNewClientOrderId,
                    null, // stopPrice
                    finalRecvWindow,
                    timestampFactory,
                    signatureCreator))
        .call();
  }

  public CoinsphOrder placeCoinsphStopOrder(org.knowm.xchange.dto.trade.StopOrder stopOrder)
      throws IOException, CoinsphException {
    final String symbol = CoinsphAdapters.toSymbol(stopOrder.getCurrencyPair());
    final org.knowm.xchange.coinsph.dto.trade.CoinsphOrderSide side =
        CoinsphAdapters.toSide(stopOrder.getType());

    // Determine final values for lambda
    final org.knowm.xchange.coinsph.dto.trade.CoinsphOrderType finalType;
    final BigDecimal finalPrice;
    org.knowm.xchange.coinsph.dto.trade.CoinsphTimeInForce initialTimeInForce = null;

    // Infer type: STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT
    // Defaulting to STOP_LOSS variants. User can use flags for TAKE_PROFIT.
    if (stopOrder.getLimitPrice() != null) {
      finalType = org.knowm.xchange.coinsph.dto.trade.CoinsphOrderType.STOP_LOSS_LIMIT;
      finalPrice = stopOrder.getLimitPrice();
      initialTimeInForce =
          org.knowm.xchange.coinsph.dto.trade.CoinsphTimeInForce.GTC; // Default for limit part
      for (Order.IOrderFlags flag : stopOrder.getOrderFlags()) {
        if (flag instanceof org.knowm.xchange.coinsph.dto.trade.CoinsphTimeInForce) {
          initialTimeInForce = (org.knowm.xchange.coinsph.dto.trade.CoinsphTimeInForce) flag;
          break;
        }
      }
    } else {
      finalType = org.knowm.xchange.coinsph.dto.trade.CoinsphOrderType.STOP_LOSS;
      finalPrice = null;
      // initialTimeInForce remains null for market-triggering stop orders
    }
    final org.knowm.xchange.coinsph.dto.trade.CoinsphTimeInForce finalTimeInForce =
        initialTimeInForce;

    final BigDecimal finalQuantity;
    final BigDecimal finalQuoteOrderQty;
    if (stopOrder.hasFlag(CoinsphAdapters.CoinsphOrderFlags.QUOTE_ORDER_QTY)
        && stopOrder.getLimitPrice() == null) {
      finalQuoteOrderQty = stopOrder.getOriginalAmount();
      finalQuantity = null;
    } else {
      finalQuantity = stopOrder.getOriginalAmount();
      finalQuoteOrderQty = null;
    }

    final BigDecimal finalStopPriceValue = stopOrder.getStopPrice();
    final String finalNewClientOrderId = stopOrder.getUserReference();
    final Long finalRecvWindow = exchange.getRecvWindow();

    return decorateApiCall(
            () ->
                coinsphAuthenticated.newOrder(
                    apiKey,
                    symbol,
                    side,
                    finalType,
                    finalTimeInForce,
                    finalQuantity,
                    finalQuoteOrderQty,
                    finalPrice, // This is the limit price for _LIMIT variants
                    finalNewClientOrderId,
                    finalStopPriceValue, // This is the stopPrice
                    finalRecvWindow,
                    timestampFactory,
                    signatureCreator))
        .call();
  }

  public boolean cancelCoinsphOrder(CancelOrderParams params) throws IOException, CoinsphException {
    String symbol = null;
    Long orderId = null;
    String clientOrderId = null;

    if (params instanceof CancelOrderByIdParams) {
      orderId = Long.valueOf(((CancelOrderByIdParams) params).getOrderId());
    } else {
      // Coins.ph requires symbol for cancellation
      throw new IllegalArgumentException(
          "CancelOrderParams must implement CancelOrderByIdParams and CancelOrderByCurrencyPair for Coins.ph");
    }

    if (params instanceof CancelOrderByCurrencyPair) {
      symbol = CoinsphAdapters.toSymbol(((CancelOrderByCurrencyPair) params).getCurrencyPair());
    } else {
      throw new IllegalArgumentException(
          "CancelOrderParams must implement CancelOrderByCurrencyPair for Coins.ph");
    }
    final String finalSymbol = symbol;
    final Long finalOrderId = orderId;

    CoinsphOrder cancelledOrder =
        decorateApiCall(
                () ->
                    coinsphAuthenticated.cancelOrder(
                        apiKey,
                        timestampFactory,
                        signatureCreator,
                        finalSymbol,
                        finalOrderId,
                        clientOrderId, // origClientOrderId
                        exchange.getRecvWindow()))
            .call();

    // Coins.ph cancel returns the cancelled order. We need to check status.
    return "CANCELED".equalsIgnoreCase(cancelledOrder.getStatus())
        || "EXPIRED".equalsIgnoreCase(cancelledOrder.getStatus());
  }

  public CoinsphOrder getCoinsphOrderStatus(String orderId, String symbol)
      throws IOException, CoinsphException {
    return decorateApiCall(
            () ->
                coinsphAuthenticated.getOrderStatus(
                    apiKey,
                    timestampFactory,
                    signatureCreator,
                    symbol,
                    Long.valueOf(orderId),
                    null, // origClientOrderId
                    exchange.getRecvWindow()))
        .call();
  }

  public List<CoinsphUserTrade> getCoinsphUserTrades(
      String symbol,
      Long orderId, // Optional, not a direct filter in Coins.ph API but can be used post-fetch
      Long startTime,
      Long endTime,
      Long fromTradeId,
      Integer limit)
      throws IOException, CoinsphException {

    return decorateApiCall(
            () ->
                coinsphAuthenticated.getMyTrades(
                    apiKey,
                    timestampFactory,
                    signatureCreator,
                    symbol,
                    orderId, // Pass along if provided, though API might ignore or use differently
                    startTime,
                    endTime,
                    fromTradeId,
                    limit,
                    exchange.getRecvWindow()))
        .call();
  }
}
