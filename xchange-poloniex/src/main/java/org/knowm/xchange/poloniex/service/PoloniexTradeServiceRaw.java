package org.knowm.xchange.poloniex.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.poloniex.PoloniexAuthenticated;
import org.knowm.xchange.poloniex.PoloniexException;
import org.knowm.xchange.poloniex.PoloniexUtils;
import org.knowm.xchange.poloniex.dto.trade.PoloniexAccountBalance;
import org.knowm.xchange.poloniex.dto.trade.PoloniexMarginAccountResponse;
import org.knowm.xchange.poloniex.dto.trade.PoloniexMarginPostionResponse;
import org.knowm.xchange.poloniex.dto.trade.PoloniexMoveResponse;
import org.knowm.xchange.poloniex.dto.trade.PoloniexOpenOrder;
import org.knowm.xchange.poloniex.dto.trade.PoloniexOrderFlags;
import org.knowm.xchange.poloniex.dto.trade.PoloniexTradeResponse;
import org.knowm.xchange.poloniex.dto.trade.PoloniexUserTrade;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author Zach Holmes
 */

public class PoloniexTradeServiceRaw extends PoloniexBaseService {

  public PoloniexTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public Map<String, PoloniexOpenOrder[]> returnOpenOrders() throws IOException {
    return poloniexAuthenticated.returnOpenOrders(apiKey, signatureCreator, exchange.getNonceFactory(), PoloniexAuthenticated.AllPairs.all);
  }

  PoloniexUserTrade[] returnOrderTrades(String orderId) throws IOException {
    return poloniexAuthenticated.returnOrderTrades(apiKey, signatureCreator, exchange.getNonceFactory(), orderId);
  }

  public PoloniexOpenOrder[] returnOpenOrders(CurrencyPair currencyPair) throws IOException {
    return poloniexAuthenticated.returnOpenOrders(apiKey, signatureCreator, exchange.getNonceFactory(), PoloniexUtils.toPairString(currencyPair));
  }

  public PoloniexUserTrade[] returnTradeHistory(CurrencyPair currencyPair, Long startTime, Long endTime) throws IOException {

    return poloniexAuthenticated.returnTradeHistory(apiKey, signatureCreator, exchange.getNonceFactory(), PoloniexUtils.toPairString(currencyPair),
        startTime, endTime);
  }

  public HashMap<String, PoloniexUserTrade[]> returnTradeHistory(Long startTime, Long endTime) throws IOException {

    String ignore = null; // only used so PoloniexAuthenticated.returnTradeHistory can be overloaded
    return poloniexAuthenticated.returnTradeHistory(apiKey, signatureCreator, exchange.getNonceFactory(), "all", startTime, endTime, ignore);
  }

  public PoloniexMarginAccountResponse returnMarginAccountSummary() throws IOException {
    return poloniexAuthenticated.returnMarginAccountSummary(apiKey, signatureCreator, exchange.getNonceFactory());
  }

  public PoloniexMarginPostionResponse returnMarginPosition(CurrencyPair currencyPair) throws IOException {
    return poloniexAuthenticated.getMarginPosition(apiKey, signatureCreator, exchange.getNonceFactory(), PoloniexUtils.toPairString(currencyPair));
  }

  public Map<String, PoloniexMarginPostionResponse> returnAllMarginPositions() throws IOException {
    return poloniexAuthenticated.getMarginPosition(apiKey, signatureCreator, exchange.getNonceFactory(), PoloniexAuthenticated.AllPairs.all);
  }

  public PoloniexAccountBalance returnAvailableAccountBalances(String account) throws IOException {
    return poloniexAuthenticated.returnAvailableAccountBalances(apiKey, signatureCreator, exchange.getNonceFactory(), account);
  }

  public Map<String, Map<String, BigDecimal>> returnTradableBalances() throws IOException {
    return poloniexAuthenticated.returnTradableBalances(apiKey, signatureCreator, exchange.getNonceFactory());
  }

  public PoloniexAccountBalance[] returnAllAvailableAccountBalances() throws IOException {
    return poloniexAuthenticated.returnAvailableAccountBalances(apiKey, signatureCreator, exchange.getNonceFactory());
  }

  public PoloniexTradeResponse buy(LimitOrder limitOrder) throws IOException {
    return orderEntry(limitOrder, "buy");
  }

  public PoloniexTradeResponse sell(LimitOrder limitOrder) throws IOException {
    return orderEntry(limitOrder, "sell");
  }

  private PoloniexTradeResponse orderEntry(LimitOrder limitOrder, String name) throws IOException {
    Integer fillOrKill;
    if (limitOrder.hasFlag(PoloniexOrderFlags.FILL_OR_KILL)) {
      fillOrKill = 1;
    } else {
      fillOrKill = null;
    }

    Integer immediateOrCancel;
    if (limitOrder.hasFlag(PoloniexOrderFlags.IMMEDIATE_OR_CANCEL)) {
      immediateOrCancel = 1;
    } else {
      immediateOrCancel = null;
    }

    Integer postOnly;
    if (limitOrder.hasFlag(PoloniexOrderFlags.POST_ONLY)) {
      postOnly = 1;
    } else {
      postOnly = null;
    }

    Double lendingRate = null;
    //TODO need to be able to specify the rate at which you would like to borrow margin.

    try {
      if (limitOrder.hasFlag(PoloniexOrderFlags.MARGIN)) {
        name = "margin" + name.substring(0, 1).toUpperCase() + name.substring(1);

        Method marginMethod = PoloniexAuthenticated.class.getDeclaredMethod(name, String.class, ParamsDigest.class, SynchronizedValueFactory.class,
            String.class, String.class, String.class, Double.class);
        PoloniexTradeResponse response = (PoloniexTradeResponse) marginMethod.invoke(poloniexAuthenticated, apiKey, signatureCreator,
            exchange.getNonceFactory(), limitOrder.getOriginalAmount().toPlainString(), limitOrder.getLimitPrice().toPlainString(),
            PoloniexUtils.toPairString(limitOrder.getCurrencyPair()), lendingRate);
        return response;
      } else {
        Method method = PoloniexAuthenticated.class.getDeclaredMethod(name, String.class, ParamsDigest.class, SynchronizedValueFactory.class,
            String.class, String.class, String.class, Integer.class, Integer.class, Integer.class);
        PoloniexTradeResponse response = (PoloniexTradeResponse) method.invoke(poloniexAuthenticated, apiKey, signatureCreator,
            exchange.getNonceFactory(), limitOrder.getOriginalAmount().toPlainString(), limitOrder.getLimitPrice().toPlainString(),
            PoloniexUtils.toPairString(limitOrder.getCurrencyPair()), fillOrKill, immediateOrCancel, postOnly);
        return response;
      }
    } catch (PoloniexException e) {
      throw new ExchangeException(e.getError(), e);
    } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new ExchangeException(e.getMessage(), e);
    }
  }

  public PoloniexMoveResponse move(String orderId, BigDecimal originalAmount, BigDecimal limitPrice, PoloniexOrderFlags flag) throws IOException {

    Integer immediateOrCancel;
    if (flag == PoloniexOrderFlags.IMMEDIATE_OR_CANCEL) {
      immediateOrCancel = 1;
    } else {
      immediateOrCancel = null;
    }

    Integer postOnly;
    if (flag == PoloniexOrderFlags.POST_ONLY) {
      postOnly = 1;
    } else {
      postOnly = null;
    }

    try {
      return poloniexAuthenticated.moveOrder(apiKey, signatureCreator, exchange.getNonceFactory(), orderId, originalAmount.toPlainString(),
          limitPrice.toPlainString(), immediateOrCancel, postOnly);
    } catch (PoloniexException e) {
      throw new ExchangeException(e.getError(), e);
    }
  }

  public PoloniexMoveResponse move(String orderId, BigDecimal originalAmount, BigDecimal limitPrice) throws IOException {

    return move(orderId, originalAmount, limitPrice, null);
  }

  public boolean cancel(String orderId) throws IOException {
    HashMap<String, String> response = poloniexAuthenticated.cancelOrder(apiKey, signatureCreator, exchange.getNonceFactory(), orderId);
    if (response.containsKey("error")) {
      throw new ExchangeException(response.get("error"));
    } else {
      return response.get("success").equals("1");
    }
  }

  public HashMap<String, String> getFeeInfo() throws IOException {
    HashMap<String, String> response = poloniexAuthenticated.returnFeeInfo(apiKey, signatureCreator, exchange.getNonceFactory());
    if (response.containsKey("error")) {
      throw new ExchangeException(response.get("error"));
    }
    return response;
  }

}
