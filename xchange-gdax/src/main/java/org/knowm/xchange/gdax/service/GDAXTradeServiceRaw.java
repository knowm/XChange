package org.knowm.xchange.gdax.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.gdax.dto.GDAXException;
import org.knowm.xchange.gdax.dto.trade.GDAXFill;
import org.knowm.xchange.gdax.dto.trade.GDAXIdResponse;
import org.knowm.xchange.gdax.dto.trade.GDAXOrder;
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
    Integer startingOrderId = null;

    if (tradeHistoryParams instanceof GdaxTradeHistoryParams) {
      GdaxTradeHistoryParams historyParams = (GdaxTradeHistoryParams) tradeHistoryParams;
      orderId = historyParams.txId;
      CurrencyPair currencyPair = historyParams.getCurrencyPair();
      if (currencyPair != null) {
        productId = toProductId(currencyPair);
      }
      startingOrderId = historyParams.paginationOrderId;
    } else if (tradeHistoryParams instanceof TradeHistoryParamTransactionId) {
      TradeHistoryParamTransactionId tnxIdParams = (TradeHistoryParamTransactionId) tradeHistoryParams;
      orderId = tnxIdParams.getTransactionId();
    } else if (tradeHistoryParams instanceof TradeHistoryParamCurrencyPair) {
      TradeHistoryParamCurrencyPair ccyPairParams = (TradeHistoryParamCurrencyPair) tradeHistoryParams;
      CurrencyPair currencyPair = ccyPairParams.getCurrencyPair();
      if (currencyPair != null) {
        productId = toProductId(currencyPair);
      }
    }
    try {
      return gdax.getFills(apiKey, digest, nonceFactory, passphrase, startingOrderId, orderId, productId);
    } catch (GDAXException e) {
      throw handleError(e);
    }
  }

  public GDAXIdResponse placeGDAXLimitOrder(LimitOrder limitOrder) throws IOException {

    String side = side(limitOrder.getType());
    String productId = toProductId(limitOrder.getCurrencyPair());

    try {
      return gdax.placeLimitOrder(new GDAXPlaceOrder(limitOrder.getOriginalAmount(), limitOrder.getLimitPrice(), side, productId, "limit", limitOrder.getOrderFlags()),
          apiKey, digest, nonceFactory, passphrase);
    } catch (GDAXException e) {
      throw handleError(e);
    }
  }

  public GDAXIdResponse placeGDAXMarketOrder(MarketOrder marketOrder) throws IOException {

    String side = side(marketOrder.getType());
    String productId = toProductId(marketOrder.getCurrencyPair());

    try {
      return gdax.placeMarketOrder(new GDAXPlaceOrder(marketOrder.getOriginalAmount(), null, side, productId, "market", marketOrder.getOrderFlags
              ()), apiKey, digest,
          nonceFactory, passphrase);
    } catch (GDAXException e) {
      throw handleError(e);
    }
  }
  
  public GDAXIdResponse placeGDAXStopOrder(StopOrder stopOrder) throws IOException {

    String side = side(stopOrder.getType());
    String productId = toProductId(stopOrder.getCurrencyPair());

    try {
      return gdax.placeStopOrder(new GDAXPlaceOrder(stopOrder.getOriginalAmount(), stopOrder.getAveragePrice(), side, productId, "stop", stopOrder.getOrderFlags
              ()), apiKey, digest,
          nonceFactory, passphrase);
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

  private static String side(OrderType type) {
    return type.equals(OrderType.BID) ? "buy" : "sell";
  }

  private static String toProductId(CurrencyPair currencyPair) {
    return currencyPair.base.getCurrencyCode() + "-" + currencyPair.counter.getCurrencyCode();
  }

  public static class GdaxTradeHistoryParams implements TradeHistoryParamTransactionId, TradeHistoryParamCurrencyPair {

    private CurrencyPair currencyPair;
    private String txId;
    private Integer paginationOrderId;

    public Integer getPaginationOrderId() {
      return paginationOrderId;
    }

    public void setPaginationOrderId(Integer startingOrderId) {
      this.paginationOrderId = startingOrderId;
    }

    @Override
    public void setCurrencyPair(CurrencyPair currencyPair) {
      this.currencyPair = currencyPair;
    }

    @Override
    public CurrencyPair getCurrencyPair() {
      return currencyPair;
    }

    @Override
    public void setTransactionId(String txId) {
      this.txId = txId;
    }

    @Override
    public String getTransactionId() {
      return txId;
    }
  }
}
