package org.knowm.xchange.gdax.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.gdax.GDAXAdapters;
import org.knowm.xchange.gdax.dto.GDAXException;
import org.knowm.xchange.gdax.dto.trade.GDAXFill;
import org.knowm.xchange.gdax.dto.trade.GDAXIdResponse;
import org.knowm.xchange.gdax.dto.trade.GDAXOrder;
import org.knowm.xchange.gdax.dto.trade.GDAXPlaceLimitOrder;
import org.knowm.xchange.gdax.dto.trade.GDAXPlaceMarketOrder;
import org.knowm.xchange.gdax.dto.trade.GDAXPlaceOrder;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamTransactionId;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import si.mazi.rescu.SynchronizedValueFactory;

public class GDAXTradeServiceRaw extends GDAXBaseService {

  private final SynchronizedValueFactory<Long> nonceFactory;

  public GDAXTradeServiceRaw(Exchange exchange) {

    super(exchange);
    this.nonceFactory = exchange.getNonceFactory();
  }

  public GDAXOrder[] getGDAXOpenOrders() throws IOException {

    try {
      return gdax.getListOrders(apiKey, digest, nonceFactory, passphrase);
    } catch (GDAXException e) {
      throw handleError(e);
    }
  }

  public GDAXFill[] getGDAXFills(TradeHistoryParams tradeHistoryParams) throws IOException {

    String orderId = null;
    String productId = null;
    Integer afterTradeId = null;
    Integer beforeTradeId = null;

    if (tradeHistoryParams instanceof GdaxTradeHistoryParams) {
      GdaxTradeHistoryParams historyParams = (GdaxTradeHistoryParams) tradeHistoryParams;
      orderId = historyParams.txId;
      CurrencyPair currencyPair = historyParams.getCurrencyPair();
      if (currencyPair != null) {
        productId = GDAXAdapters.adaptProductID(currencyPair);
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
        productId = GDAXAdapters.adaptProductID(currencyPair);
      }
    }
    try {
      return gdax.getFills(
          apiKey,
          digest,
          nonceFactory,
          passphrase,
          afterTradeId,
          beforeTradeId,
          orderId,
          productId);
    } catch (GDAXException e) {
      throw handleError(e);
    }
  }

  /** @deprecated Use @link {@link #placeGDAXOrder} */
  public GDAXIdResponse placeGDAXLimitOrder(LimitOrder limitOrder) throws IOException {

    GDAXPlaceLimitOrder gdaxLimitOrder = GDAXAdapters.adaptGDAXPlaceLimitOrder(limitOrder);
    return placeGDAXOrder(gdaxLimitOrder);
  }

  /** @deprecated Use {@link #placeGDAXOrder} */
  public GDAXIdResponse placeGDAXMarketOrder(MarketOrder marketOrder) throws IOException {

    GDAXPlaceMarketOrder gdaxMarketOrder = GDAXAdapters.adaptGDAXPlaceMarketOrder(marketOrder);
    return placeGDAXOrder(gdaxMarketOrder);
  }

  /** @deprecated Use {@link #placeGDAXOrder} */
  public GDAXIdResponse placeGDAXStopOrder(StopOrder stopOrder) throws IOException {
    GDAXPlaceOrder gdaxStopOrder = GDAXAdapters.adaptGDAXStopOrder(stopOrder);
    return placeGDAXOrder(gdaxStopOrder);
  }

  public GDAXIdResponse placeGDAXOrder(GDAXPlaceOrder order) throws IOException {
    try {
      return gdax.placeOrder(order, apiKey, digest, nonceFactory, passphrase);
    } catch (GDAXException e) {
      throw handleError(e);
    }
  }

  public boolean cancelGDAXOrder(String id) throws IOException {

    try {
      gdax.cancelOrder(id, apiKey, digest, nonceFactory, passphrase);
    } catch (GDAXException e) {
      throw handleError(e);
    }
    return true;
  }

  public GDAXOrder getOrder(String id) throws IOException {

    try {
      return gdax.getOrder(id, apiKey, digest, nonceFactory, passphrase);
    } catch (GDAXException e) {
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
