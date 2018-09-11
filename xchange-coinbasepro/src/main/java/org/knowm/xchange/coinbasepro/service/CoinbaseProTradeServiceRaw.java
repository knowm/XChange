package org.knowm.xchange.coinbasepro.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbasepro.CoinbaseProAdapters;
import org.knowm.xchange.coinbasepro.dto.CoinbaseProException;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProFill;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProIdResponse;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProOrder;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProPlaceLimitOrder;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProPlaceMarketOrder;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProPlaceOrder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamTransactionId;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import si.mazi.rescu.SynchronizedValueFactory;

public class CoinbaseProTradeServiceRaw extends CoinbaseProBaseService {

  private final SynchronizedValueFactory<Long> nonceFactory;

  public CoinbaseProTradeServiceRaw(Exchange exchange) {

    super(exchange);
    this.nonceFactory = exchange.getNonceFactory();
  }

  public CoinbaseProOrder[] getCoinbaseProOpenOrders() throws IOException {

    try {
      return coinbasePro.getListOrders(apiKey, digest, nonceFactory, passphrase);
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

    if (tradeHistoryParams instanceof GdaxTradeHistoryParams) {
      GdaxTradeHistoryParams historyParams = (GdaxTradeHistoryParams) tradeHistoryParams;
      orderId = historyParams.txId;
      CurrencyPair currencyPair = historyParams.getCurrencyPair();
      if (currencyPair != null) {
        productId = CoinbaseProAdapters.adaptProductID(currencyPair);
      }
      afterTradeId = historyParams.afterTradeId;
      beforeTradeId = historyParams.beforeTradeId;
    } else if (tradeHistoryParams instanceof TradeHistoryParamTransactionId) {
      TradeHistoryParamTransactionId tnxIdParams =
          (TradeHistoryParamTransactionId) tradeHistoryParams;
      orderId = tnxIdParams.getTransactionId();
    } else if (tradeHistoryParams instanceof TradeHistoryParamCurrencyPair) {
      TradeHistoryParamCurrencyPair ccyPairParams =
          (TradeHistoryParamCurrencyPair) tradeHistoryParams;
      CurrencyPair currencyPair = ccyPairParams.getCurrencyPair();
      if (currencyPair != null) {
        productId = CoinbaseProAdapters.adaptProductID(currencyPair);
      }
    }
    try {
      return coinbasePro.getFills(
          apiKey,
          digest,
          nonceFactory,
          passphrase,
          afterTradeId,
          beforeTradeId,
          orderId,
          productId);
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
      return coinbasePro.placeOrder(order, apiKey, digest, nonceFactory, passphrase);
    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
  }

  public boolean cancelCoinbaseProOrder(String id) throws IOException {

    try {
      coinbasePro.cancelOrder(id, apiKey, digest, nonceFactory, passphrase);
    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
    return true;
  }

  public CoinbaseProOrder getOrder(String id) throws IOException {

    try {
      return coinbasePro.getOrder(id, apiKey, digest, nonceFactory, passphrase);
    } catch (CoinbaseProException e) {
      throw handleError(e);
    }
  }

  public static class GdaxTradeHistoryParams
      implements TradeHistoryParamTransactionId, TradeHistoryParamCurrencyPair {

    private CurrencyPair currencyPair;
    private String txId;
    private Integer afterTradeId;
    private Integer beforeTradeId;

    public Integer getAfterTradeId() {
      return afterTradeId;
    }

    public void setAfterTradeId(Integer startingOrderId) {
      this.afterTradeId = startingOrderId;
    }

    public Integer getBeforeTradeId() {
      return beforeTradeId;
    }

    public void setBeforeTradeId(Integer beforeTradeId) {
      this.beforeTradeId = beforeTradeId;
    }

    @Override
    public CurrencyPair getCurrencyPair() {
      return currencyPair;
    }

    @Override
    public void setCurrencyPair(CurrencyPair currencyPair) {
      this.currencyPair = currencyPair;
    }

    @Override
    public String getTransactionId() {
      return txId;
    }

    @Override
    public void setTransactionId(String txId) {
      this.txId = txId;
    }
  }
}
