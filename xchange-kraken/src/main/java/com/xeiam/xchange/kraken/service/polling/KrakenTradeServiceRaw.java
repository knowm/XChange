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
import com.xeiam.xchange.kraken.dto.trade.KrakenOpenOrder;
import com.xeiam.xchange.kraken.dto.trade.KrakenOpenOrdersResult;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrderResult;
import com.xeiam.xchange.kraken.service.KrakenDigest;

public class KrakenTradeServiceRaw extends BaseKrakenService {

  private KrakenAuthenticated krakenAuthenticated;
  private ParamsDigest signatureCreator;

  public KrakenTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    krakenAuthenticated = RestProxyFactory.createProxy(KrakenAuthenticated.class, exchangeSpecification.getSslUri());
    signatureCreator = KrakenDigest.createInstance(exchangeSpecification.getSecretKey());
  }

  public Map<String, KrakenOpenOrder> getKrakenOpenOrders() throws IOException {

    KrakenOpenOrdersResult result = krakenAuthenticated.openOrders(exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce(), null, null);

    return checkResult(result).getOrders();
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
