package com.xeiam.xchange.kraken.service.polling;

import java.io.IOException;
import java.util.Map;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.kraken.KrakenAuthenticated;
import com.xeiam.xchange.kraken.KrakenUtils;
import com.xeiam.xchange.kraken.dto.trade.KrakenCancelOrderResult;
import com.xeiam.xchange.kraken.dto.trade.KrakenClosedOrdersResult;
import com.xeiam.xchange.kraken.dto.trade.KrakenOpenOrdersResult;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrder;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrderResult;
import com.xeiam.xchange.kraken.dto.trade.KrakenQueryOrderResult;
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

    KrakenClosedOrdersResult result = krakenAuthenticated.closedOrders(includeTrades, userRef, start, end, offset, closeTime, exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce());

    return checkResult(result).getOrders();
  }

  public Map<String, KrakenOrder> queryKrakenOrders(boolean includeTrades, String userRef, String... transactionIds) throws IOException {

    StringBuilder commaDelimitedTransactionIds = new StringBuilder();
    if (transactionIds != null) {
      boolean started = false;
      for (String transactionId : transactionIds) {
        commaDelimitedTransactionIds.append((started) ? "," : "").append(transactionId);
        started = true;
      }
    }
    
    KrakenQueryOrderResult result = krakenAuthenticated.queryOrders(includeTrades, userRef, commaDelimitedTransactionIds.toString(), exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce());

    return checkResult(result);
  }
  
  public String placeKrakenMarketOrder(MarketOrder marketOrder) throws IOException {

    String krakenCurrencyPair = KrakenUtils.createKrakenCurrencyPair(marketOrder.getTradableIdentifier(), marketOrder.getTransactionCurrency());
    KrakenOrderResult result =
        krakenAuthenticated.addOrder(exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce(), krakenCurrencyPair, KrakenUtils.getKrakenOrderType(marketOrder.getType()), "market",
            null, marketOrder.getTradableAmount().toString());

    return checkResult(result).getTxid();
  }

  public String placeKrakenLimitOrder(LimitOrder limitOrder) throws IOException {

    String krakenCurrencyPair = KrakenUtils.createKrakenCurrencyPair(limitOrder.getTradableIdentifier(), limitOrder.getTransactionCurrency());
    KrakenOrderResult result =
        krakenAuthenticated.addOrder(exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce(), krakenCurrencyPair, KrakenUtils.getKrakenOrderType(limitOrder.getType()), "limit",
            limitOrder.getLimitPrice().getAmount().toString(), limitOrder.getTradableAmount().toString());

    return checkResult(result).getTxid();
  }

  public int cancelKrakenOrder(String orderId) throws IOException {

    KrakenCancelOrderResult result = krakenAuthenticated.cancelOrder(exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce(), orderId);

    return checkResult(result).getCount();
  }
}
