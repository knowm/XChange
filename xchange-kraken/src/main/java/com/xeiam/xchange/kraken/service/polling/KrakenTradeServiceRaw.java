package com.xeiam.xchange.kraken.service.polling;

import java.io.IOException;
import java.util.Map;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.kraken.KrakenAuthenticated;
import com.xeiam.xchange.kraken.dto.trade.KrakenOpenPosition;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrder;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrderResponse;
import com.xeiam.xchange.kraken.dto.trade.KrakenStandardOrder;
import com.xeiam.xchange.kraken.dto.trade.KrakenStandardOrder.KrakenOrderBuilder;
import com.xeiam.xchange.kraken.dto.trade.KrakenTrade;
import com.xeiam.xchange.kraken.dto.trade.KrakenType;
import com.xeiam.xchange.kraken.dto.trade.results.KrakenCancelOrderResult;
import com.xeiam.xchange.kraken.dto.trade.results.KrakenCancelOrderResult.KrakenCancelOrderResponse;
import com.xeiam.xchange.kraken.dto.trade.results.KrakenClosedOrdersResult;
import com.xeiam.xchange.kraken.dto.trade.results.KrakenOpenOrdersResult;
import com.xeiam.xchange.kraken.dto.trade.results.KrakenOpenPositionsResult;
import com.xeiam.xchange.kraken.dto.trade.results.KrakenOrderResult;
import com.xeiam.xchange.kraken.dto.trade.results.KrakenQueryOrderResult;
import com.xeiam.xchange.kraken.dto.trade.results.KrakenQueryTradeResult;
import com.xeiam.xchange.kraken.dto.trade.results.KrakenTradeHistoryResult;

public class KrakenTradeServiceRaw extends KrakenBasePollingService<KrakenAuthenticated> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public KrakenTradeServiceRaw(ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Long> nonceFactory) {

    super(KrakenAuthenticated.class, exchangeSpecification, nonceFactory);
  }

  public Map<String, KrakenOrder> getKrakenOpenOrders() throws IOException {

    return getKrakenOpenOrders(false, null);
  }

  public Map<String, KrakenOrder> getKrakenOpenOrders(boolean includeTrades, String userRef) throws IOException {

    KrakenOpenOrdersResult result = kraken.openOrders(includeTrades, userRef, exchangeSpecification.getApiKey(), signatureCreator, nextNonce());

    return checkResult(result).getOrders();
  }

  public Map<String, KrakenOrder> getKrakenClosedOrders() throws IOException {

    return getKrakenClosedOrders(false, null, null, null, null, null);
  }

  public Map<String, KrakenOrder> getKrakenClosedOrders(boolean includeTrades, String userRef, String start, String end, String offset, String closeTime) throws IOException {

    KrakenClosedOrdersResult result = kraken.closedOrders(includeTrades, userRef, start, end, offset, closeTime, exchangeSpecification.getApiKey(), signatureCreator, nextNonce());

    return checkResult(result).getOrders();
  }

  public Map<String, KrakenOrder> queryKrakenOrders(String... transactionIds) throws IOException {

    return queryKrakenOrders(false, null, transactionIds);
  }

  public KrakenQueryOrderResult queryKrakenOrdersResult(boolean includeTrades, String userRef, String... transactionIds) throws IOException {

    KrakenQueryOrderResult krakenQueryOrderResult = kraken.queryOrders(includeTrades, userRef, createDelimitedString(transactionIds), exchangeSpecification.getApiKey(), signatureCreator, nextNonce());

    return krakenQueryOrderResult;
  }

  public Map<String, KrakenOrder> queryKrakenOrders(boolean includeTrades, String userRef, String... transactionIds) throws IOException {

    KrakenQueryOrderResult result = kraken.queryOrders(includeTrades, userRef, createDelimitedString(transactionIds), exchangeSpecification.getApiKey(), signatureCreator, nextNonce());

    return checkResult(result);
  }

  public Map<String, KrakenTrade> getKrakenTradeHistory() throws IOException {

    return getKrakenTradeHistory(null, false, null, null, null);
  }

  public Map<String, KrakenTrade> getKrakenTradeHistory(String type, boolean includeTrades, String start, String end, String offset) throws IOException {

    KrakenTradeHistoryResult result = kraken.tradeHistory(type, includeTrades, start, end, offset, exchangeSpecification.getApiKey(), signatureCreator, nextNonce());

    return checkResult(result).getTrades();
  }

  public Map<String, KrakenTrade> queryKrakenTrades(String... transactionIds) throws IOException {

    return queryKrakenTrades(false, transactionIds);
  }

  public Map<String, KrakenTrade> queryKrakenTrades(boolean includeTrades, String... transactionIds) throws IOException {

    KrakenQueryTradeResult result = kraken.queryTrades(includeTrades, createDelimitedString(transactionIds), exchangeSpecification.getApiKey(), signatureCreator, nextNonce());

    return checkResult(result);
  }

  public Map<String, KrakenOpenPosition> getOpenPositions() throws IOException {

    return getOpenPositions(false);
  }

  public Map<String, KrakenOpenPosition> getOpenPositions(boolean doCalcs, String... transactionIds) throws IOException {

    KrakenOpenPositionsResult result = kraken.openPositions(createDelimitedString(transactionIds), doCalcs, exchangeSpecification.getApiKey(), signatureCreator, nextNonce());

    return checkResult(result);
  }

  public KrakenOrderResponse placeKrakenMarketOrder(MarketOrder marketOrder) throws IOException {

    KrakenType type = KrakenType.fromOrderType(marketOrder.getType());
    KrakenOrderBuilder orderBuilder = KrakenStandardOrder.getMarketOrderBuilder(marketOrder.getCurrencyPair(), type, marketOrder.getTradableAmount());

    return placeKrakenOrder(orderBuilder.buildOrder());
  }

  public KrakenOrderResponse placeKrakenLimitOrder(LimitOrder limitOrder) throws IOException {

    KrakenType type = KrakenType.fromOrderType(limitOrder.getType());
    KrakenOrderBuilder krakenOrderBuilder = KrakenStandardOrder.getLimitOrderBuilder(limitOrder.getCurrencyPair(), type, limitOrder.getLimitPrice().toString(), limitOrder.getTradableAmount());

    return placeKrakenOrder(krakenOrderBuilder.buildOrder());
  }

  public KrakenOrderResponse placeKrakenOrder(KrakenStandardOrder krakenStandardOrder) throws IOException {

    KrakenOrderResult result = null;
    if (!krakenStandardOrder.isValidateOnly()) {
      result =
          kraken.addOrder(createKrakenCurrencyPair(krakenStandardOrder.getAssetPair()), krakenStandardOrder.getType().toString(), krakenStandardOrder.getOrderType().toString(), krakenStandardOrder
              .getPrice(), krakenStandardOrder.getSecondaryPrice(), krakenStandardOrder.getVolume().toString(), krakenStandardOrder.getLeverage(), krakenStandardOrder.getPositionTxId(),
              delimitSet(krakenStandardOrder.getOrderFlags()), krakenStandardOrder.getStartTime(), krakenStandardOrder.getExpireTime(), krakenStandardOrder.getUserRefId(), krakenStandardOrder
                  .getCloseOrder(), exchangeSpecification.getApiKey(), signatureCreator, nextNonce());
    }
    else {
      result =
          kraken.addOrderValidateOnly(createKrakenCurrencyPair(krakenStandardOrder.getAssetPair()), krakenStandardOrder.getType().toString(), krakenStandardOrder.getOrderType().toString(),
              krakenStandardOrder.getPrice(), krakenStandardOrder.getSecondaryPrice(), krakenStandardOrder.getVolume().toString(), krakenStandardOrder.getLeverage(), krakenStandardOrder
                  .getPositionTxId(), delimitSet(krakenStandardOrder.getOrderFlags()), krakenStandardOrder.getStartTime(), krakenStandardOrder.getExpireTime(), krakenStandardOrder.getUserRefId(),
              true, krakenStandardOrder.getCloseOrder(), exchangeSpecification.getApiKey(), signatureCreator, nextNonce());
    }

    return checkResult(result);
  }

  public KrakenCancelOrderResponse cancelKrakenOrder(String orderId) throws IOException {

    KrakenCancelOrderResult result = kraken.cancelOrder(exchangeSpecification.getApiKey(), signatureCreator, nextNonce(), orderId);

    return checkResult(result);
  }
}
