package org.knowm.xchange.coinbasepro.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.coinbasepro.CoinbaseProAdapters;
import org.knowm.xchange.coinbasepro.CoinbaseProExchange;
import org.knowm.xchange.coinbasepro.dto.CoinbaseProException;
import org.knowm.xchange.coinbasepro.dto.trade.*;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamTransactionId;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import si.mazi.rescu.SynchronizedValueFactory;

import static org.knowm.xchange.coinbasepro.CoinbaseProResilience.PRIVATE_PER_SECOND_RATE_LIMITER;

public class CoinbaseProTradeServiceRaw extends CoinbaseProBaseService {

  private final SynchronizedValueFactory<Long> nonceFactory;

  public CoinbaseProTradeServiceRaw(
      CoinbaseProExchange exchange, ResilienceRegistries resilienceRegistries) {

    super(exchange, resilienceRegistries);
    this.nonceFactory = exchange.getNonceFactory();
  }

  public CoinbaseProOrder[] getCoinbaseProOpenOrders() throws IOException {

    try {
      return decorateApiCall(
              () -> coinbasePro.getListOrders(apiKey, digest, nonceFactory, passphrase))
          .withRetry(retry("getCoinbaseProOpenOrders"))
          .withRateLimiter(rateLimiter(PRIVATE_PER_SECOND_RATE_LIMITER))
          .call();
    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
  }

  public CoinbaseProFill[] getCoinbaseProFills(TradeHistoryParams tradeHistoryParams)
      throws IOException {

    String orderId = null;
    String productId = null;
    Integer afterTradeId = null;
    Integer beforeTradeId = null;
    Integer limit = null;

    if (tradeHistoryParams instanceof CoinbaseProTradeHistoryParams) {
      CoinbaseProTradeHistoryParams historyParams =
          (CoinbaseProTradeHistoryParams) tradeHistoryParams;
      afterTradeId = historyParams.getAfterTradeId();
      beforeTradeId = historyParams.getBeforeTradeId();
    }

    if (tradeHistoryParams instanceof TradeHistoryParamTransactionId) {
      TradeHistoryParamTransactionId tnxIdParams =
          (TradeHistoryParamTransactionId) tradeHistoryParams;
      orderId = tnxIdParams.getTransactionId();
    }

    if (tradeHistoryParams instanceof TradeHistoryParamCurrencyPair) {
      TradeHistoryParamCurrencyPair ccyPairParams =
          (TradeHistoryParamCurrencyPair) tradeHistoryParams;
      CurrencyPair currencyPair = ccyPairParams.getCurrencyPair();
      if (currencyPair != null) {
        productId = CoinbaseProAdapters.adaptProductID(currencyPair);
      }
    }

    if (tradeHistoryParams instanceof TradeHistoryParamLimit) {
      TradeHistoryParamLimit limitParams = (TradeHistoryParamLimit) tradeHistoryParams;
      limit = limitParams.getLimit();
    }

    try {
      Integer finalAfterTradeId = afterTradeId;
      Integer finalBeforeTradeId = beforeTradeId;
      Integer finalLimit = limit;
      String finalProductId = productId;
      String finalOrderId = orderId;
      return decorateApiCall(
              () ->
                  coinbasePro.getFills(
                      apiKey,
                      digest,
                      nonceFactory,
                      passphrase,
                      finalAfterTradeId,
                      finalBeforeTradeId,
                      finalLimit,
                      finalOrderId,
                      finalProductId))
          .withRetry(retry("getCoinbaseProFills"))
          .withRateLimiter(rateLimiter(PRIVATE_PER_SECOND_RATE_LIMITER))
          .call();
    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
  }

  /** @deprecated Use @link {@link #placeCoinbaseProOrder} */
  public CoinbaseProIdResponse placeCoinbaseProLimitOrder(LimitOrder limitOrder)
      throws IOException {

    CoinbaseProPlaceLimitOrder coinbaseProLimitOrder =
        CoinbaseProAdapters.adaptCoinbaseProPlaceLimitOrder(limitOrder);
    return placeCoinbaseProOrder(coinbaseProLimitOrder);
  }

  /** @deprecated Use {@link #placeCoinbaseProOrder} */
  public CoinbaseProIdResponse placeCoinbaseProMarketOrder(MarketOrder marketOrder)
      throws IOException {

    CoinbaseProPlaceMarketOrder coinbaseProMarketOrder =
        CoinbaseProAdapters.adaptCoinbaseProPlaceMarketOrder(marketOrder);
    return placeCoinbaseProOrder(coinbaseProMarketOrder);
  }

  /** @deprecated Use {@link #placeCoinbaseProOrder} */
  public CoinbaseProIdResponse placeCoinbaseProStopOrder(StopOrder stopOrder) throws IOException {
    CoinbaseProPlaceOrder coinbaseProStopOrder =
        CoinbaseProAdapters.adaptCoinbaseProStopOrder(stopOrder);
    return placeCoinbaseProOrder(coinbaseProStopOrder);
  }

  public CoinbaseProIdResponse placeCoinbaseProOrder(CoinbaseProPlaceOrder order)
      throws IOException {
    try {
      return decorateApiCall(
              () -> coinbasePro.placeOrder(order, apiKey, digest, nonceFactory, passphrase))
          .withRetry(retry("placeCoinbaseProOrder"))
          .withRateLimiter(rateLimiter(PRIVATE_PER_SECOND_RATE_LIMITER))
          .call();
    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
  }

  public boolean cancelCoinbaseProOrder(String id) throws IOException {

    try {
      coinbasePro.cancelOrder(id, apiKey, digest, nonceFactory, passphrase);

      // The decorateApiCall method needs to accept a void type for the return.
      // Once that's done, the below can be commented out.
      // Cancel order is probably low traffic compared to the rest of the api,
      // so ok to not migrate this now.

      //      decorateApiCall(
      //              () -> coinbasePro.cancelOrder(id, apiKey, digest, nonceFactory, passphrase))
      //          .withRetry(retry("cancelCoinbaseProOrder"))
      //          .withRateLimiter(rateLimiter(PRIVATE_PER_SECOND_RATE_LIMITER))
      //          .call();
    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
    return true;
  }

  public CoinbaseProOrder getOrder(String id) throws IOException {

    try {
      return decorateApiCall(
              () -> coinbasePro.getOrder(id, apiKey, digest, nonceFactory, passphrase))
          .withRetry(retry("getOrder"))
          .withRateLimiter(rateLimiter(PRIVATE_PER_SECOND_RATE_LIMITER))
          .call();
    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
  }

  public CoinbaseProOrder[] getOrders(String status) throws IOException {

    try {
      return decorateApiCall(
              () -> coinbasePro.getListOrders(apiKey, digest, nonceFactory, passphrase, status))
          .withRetry(retry("getOrders"))
          .withRateLimiter(rateLimiter(PRIVATE_PER_SECOND_RATE_LIMITER))
          .call();
    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
  }
}
