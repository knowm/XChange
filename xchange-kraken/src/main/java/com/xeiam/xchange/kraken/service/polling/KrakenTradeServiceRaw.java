package com.xeiam.xchange.kraken.service.polling;

import java.io.IOException;
import java.util.Map;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.kraken.KrakenAuthenticated;
import com.xeiam.xchange.kraken.KrakenUtils;
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
import com.xeiam.xchange.kraken.service.KrakenDigest;

public class KrakenTradeServiceRaw extends BaseKrakenService {

  private KrakenAuthenticated krakenAuthenticated;
  private ParamsDigest signatureCreator;

  public KrakenTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    krakenAuthenticated = RestProxyFactory.createProxy(KrakenAuthenticated.class, exchangeSpecification.getSslUri());
    signatureCreator = KrakenDigest.createInstance(exchangeSpecification.getSecretKey());
  }

  public Map<String, KrakenOrder> getKrakenOpenOrders() throws IOException {

    return getKrakenOpenOrders(false, null);
  }

  public Map<String, KrakenOrder> getKrakenOpenOrders(boolean includeTrades, String userRef) throws IOException {

    KrakenOpenOrdersResult result = krakenAuthenticated.openOrders(includeTrades, userRef, exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce());

    return checkResult(result).getOrders();
  }

  public Map<String, KrakenOrder> getKrakenClosedOrders() throws IOException {

    return getKrakenClosedOrders(false, null, null, null, null, null);
  }

  public Map<String, KrakenOrder> getKrakenClosedOrders(boolean includeTrades, String userRef, String start, String end, String offset, String closeTime) throws IOException {

    KrakenClosedOrdersResult result =
        krakenAuthenticated.closedOrders(includeTrades, userRef, start, end, offset, closeTime, exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce());

    return checkResult(result).getOrders();
  }

  public Map<String, KrakenOrder> queryKrakenOrders(String... transactionIds) throws IOException {

    return queryKrakenOrders(false, null, transactionIds);
  }

  public Map<String, KrakenOrder> queryKrakenOrders(boolean includeTrades, String userRef, String... transactionIds) throws IOException {

    KrakenQueryOrderResult result =
        krakenAuthenticated.queryOrders(includeTrades, userRef, createDelimitedString(transactionIds), exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce());

    return checkResult(result);
  }

  public Map<String, KrakenTrade> getKrakenTradeHistory() throws IOException {

    return getKrakenTradeHistory(null, false, null, null, null);
  }

  public Map<String, KrakenTrade> getKrakenTradeHistory(String type, boolean includeTrades, String start, String end, String offset) throws IOException {

    KrakenTradeHistoryResult result = krakenAuthenticated.tradeHistory(type, includeTrades, start, end, offset, exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce());

    return checkResult(result).getTrades();
  }

  public Map<String, KrakenTrade> queryKrakenTrades(String... transactionIds) throws IOException {

    return queryKrakenTrades(false, transactionIds);
  }

  public Map<String, KrakenTrade> queryKrakenTrades(boolean includeTrades, String... transactionIds) throws IOException {

    KrakenQueryTradeResult result = krakenAuthenticated.queryTrades(includeTrades, createDelimitedString(transactionIds), exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce());

    return checkResult(result);
  }

  public Map<String, KrakenOpenPosition> getOpenPositions() throws IOException {

    return getOpenPositions(false);
  }

  public Map<String, KrakenOpenPosition> getOpenPositions(boolean doCalcs, String... transactionIds) throws IOException {

    KrakenOpenPositionsResult result = krakenAuthenticated.openPositions(createDelimitedString(transactionIds), doCalcs, exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce());

    return checkResult(result);
  }

  public KrakenOrderResponse placeKrakenMarketOrder(MarketOrder marketOrder) throws IOException {

    CurrencyPair currencyPair = new CurrencyPair(marketOrder.getTradableIdentifier(), marketOrder.getTransactionCurrency());
    KrakenType type = KrakenUtils.getKrakenOrderType(marketOrder.getType());
    KrakenOrderBuilder orderBuilder = KrakenStandardOrder.getMarketOrderBuilder(currencyPair, type, marketOrder.getTradableAmount());

    return placeKrakentOrder(orderBuilder.buildOrder());
  }

  public KrakenOrderResponse placeKrakenLimitOrder(LimitOrder limitOrder) throws IOException {

    CurrencyPair currencyPair = new CurrencyPair(limitOrder.getTradableIdentifier(), limitOrder.getTransactionCurrency());
    KrakenType type = KrakenUtils.getKrakenOrderType(limitOrder.getType());
    KrakenOrderBuilder orderBuilder = KrakenStandardOrder.getLimitOrderBuilder(currencyPair, type, limitOrder.getLimitPrice().getAmount().toString(), limitOrder.getTradableAmount());

    return placeKrakentOrder(orderBuilder.buildOrder());
  }

  public KrakenOrderResponse placeKrakentOrder(KrakenStandardOrder order) throws IOException {

    KrakenOrderResult result = null;
    if (!order.isValidateOnly()) {
      result =
          krakenAuthenticated.addOrder(order.getAssetPair(), order.getType().toString(), order.getOrderType().toString(), order.getPrice(), order.getSecondaryPrice(), order.getVolume().toString(),
              order.getLeverage(), order.getPositionTxId(), delimitSet(order.getOrderFlags()), order.getStartTime(), order.getExpireTime(), order.getUserRefId(), order.getCloseOrder(),
              exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce());
    }
    else {
      result =
          krakenAuthenticated.addOrderValidateOnly(order.getAssetPair(), order.getType().toString(), order.getOrderType().toString(), order.getPrice(), order.getSecondaryPrice(), order.getVolume()
              .toString(), order.getLeverage(), order.getPositionTxId(), delimitSet(order.getOrderFlags()), order.getStartTime(), order.getExpireTime(), order.getUserRefId(), true, order
              .getCloseOrder(), exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce());
    }

    return checkResult(result);
  }

  public KrakenCancelOrderResponse cancelKrakenOrder(String orderId) throws IOException {

    KrakenCancelOrderResult result = krakenAuthenticated.cancelOrder(exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce(), orderId);

    return checkResult(result);
  }
}
