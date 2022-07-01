package org.knowm.xchange.bankera.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bankera.BankeraAdapters;
import org.knowm.xchange.bankera.BankeraExchange;
import org.knowm.xchange.bankera.dto.BankeraException;
import org.knowm.xchange.bankera.dto.BaseBankeraRequest;
import org.knowm.xchange.bankera.dto.CreateOrderRequest;
import org.knowm.xchange.bankera.dto.trade.BankeraOpenOrders;
import org.knowm.xchange.bankera.dto.trade.BankeraOrder;
import org.knowm.xchange.bankera.dto.trade.BankeraUserTrades;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamLimit;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamOffset;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class BankeraTradeServiceRaw extends BankeraBaseService {

  public BankeraTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public BankeraOpenOrders getBankeraOpenOrders(OpenOrdersParams params) throws IOException {
    try {
      BankeraExchange bankeraExchange = (BankeraExchange) exchange;
      String auth = "Bearer " + bankeraExchange.getToken().getAccessToken();
      String market = null;
      Integer limit = 100;
      Integer offset = 0;
      if (params instanceof OpenOrdersParamCurrencyPair) {
        CurrencyPair currencyPair = ((OpenOrdersParamCurrencyPair) params).getCurrencyPair();
        market = getMarketNameFromPair(currencyPair);
      }
      if (params instanceof OpenOrdersParamLimit) {
        limit = ((OpenOrdersParamLimit) params).getLimit();
      }
      if (params instanceof OpenOrdersParamOffset) {
        offset = ((OpenOrdersParamOffset) params).getOffset();
      }
      return bankeraAuthenticated.getOpenOrders(auth, market, limit, offset);
    } catch (BankeraException e) {
      throw BankeraAdapters.adaptError(e);
    }
  }

  public BankeraOrder placeBankeraLimitOrder(LimitOrder limitOrder) throws IOException {
    try {
      BankeraExchange bankeraExchange = (BankeraExchange) exchange;
      String auth = "Bearer " + bankeraExchange.getToken().getAccessToken();
      String market = getMarketNameFromPair(limitOrder.getCurrencyPair());

      return bankeraAuthenticated.placeOrder(
          auth,
          new CreateOrderRequest(
              market,
              (limitOrder.getType() == Order.OrderType.BID
                  ? CreateOrderRequest.Side.BUY.getSide()
                  : CreateOrderRequest.Side.SELL.getSide()),
              limitOrder.getOriginalAmount(),
              limitOrder.getLimitPrice(),
              limitOrder.getId(),
              exchange.getNonceFactory().createValue()));
    } catch (BankeraException e) {
      throw BankeraAdapters.adaptError(e);
    }
  }

  public BankeraOrder placeBankeraMarketOrder(MarketOrder marketOrder) throws IOException {

    try {
      BankeraExchange bankeraExchange = (BankeraExchange) exchange;
      String auth = "Bearer " + bankeraExchange.getToken().getAccessToken();
      String market = getMarketNameFromPair(marketOrder.getCurrencyPair());

      return bankeraAuthenticated.placeOrder(
          auth,
          new CreateOrderRequest(
              market,
              (marketOrder.getType() == Order.OrderType.BID
                  ? CreateOrderRequest.Side.BUY.getSide()
                  : CreateOrderRequest.Side.SELL.getSide()),
              marketOrder.getOriginalAmount(),
              marketOrder.getId(),
              exchange.getNonceFactory().createValue()));
    } catch (BankeraException e) {
      throw BankeraAdapters.adaptError(e);
    }
  }

  public BankeraOrder cancelBankeraOrder(String orderId) throws IOException {

    try {
      BankeraExchange bankeraExchange = (BankeraExchange) exchange;
      String auth = "Bearer " + bankeraExchange.getToken().getAccessToken();
      return bankeraAuthenticated.cancelOrder(
          auth,
          Long.valueOf(orderId),
          new BaseBankeraRequest(exchange.getNonceFactory().createValue()));
    } catch (BankeraException e) {
      throw BankeraAdapters.adaptError(e);
    }
  }

  public List<BankeraOrder> cancelAllBankeraOrders() throws IOException {

    try {
      BankeraExchange bankeraExchange = (BankeraExchange) exchange;
      String auth = "Bearer " + bankeraExchange.getToken().getAccessToken();
      return bankeraAuthenticated.cancelAllOrders(auth);
    } catch (BankeraException e) {
      throw BankeraAdapters.adaptError(e);
    }
  }

  public BankeraUserTrades getUserTrades(CurrencyPair currencyPair) throws IOException {

    try {
      BankeraExchange bankeraExchange = (BankeraExchange) exchange;
      String auth = "Bearer " + bankeraExchange.getToken().getAccessToken();
      String market = getMarketNameFromPair(currencyPair);
      return bankeraAuthenticated.getUserTrades(auth, market);
    } catch (BankeraException e) {
      throw BankeraAdapters.adaptError(e);
    }
  }

  public BankeraOrder getUserOrder(String orderId) throws IOException {

    try {
      BankeraExchange bankeraExchange = (BankeraExchange) exchange;
      String auth = "Bearer " + bankeraExchange.getToken().getAccessToken();
      return bankeraAuthenticated.getUserOrder(auth, orderId);
    } catch (BankeraException e) {
      throw BankeraAdapters.adaptError(e);
    }
  }

  private static String getMarketNameFromPair(CurrencyPair pair) {

    return pair == null
        ? null
        : new StringBuilder()
            .append(pair.base.getCurrencyCode())
            .append("-")
            .append(pair.counter.getCurrencyCode())
            .toString();
  }
}
