package com.xeiam.xchange.kraken.service.polling;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
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
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.utils.Assert;

public class KrakenTradeServiceRaw extends BasePollingExchangeService {

  private KrakenAuthenticated krakenAuthenticated;
  private ParamsDigest signatureCreator;

  public KrakenTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");
    krakenAuthenticated = RestProxyFactory.createProxy(KrakenAuthenticated.class, exchangeSpecification.getSslUri());
    signatureCreator = KrakenDigest.createInstance(exchangeSpecification.getSecretKey());
  }

  public Map<String, KrakenOpenOrder> openOrders() throws IOException {

    KrakenOpenOrdersResult result = krakenAuthenticated.openOrders(exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce(), null, null);

    if (!result.isSuccess())
      throw new ExchangeException(Arrays.toString(result.getError()));

    return result.getResult().getOrders();
  }

  public String addMarketOrder(MarketOrder marketOrder) throws IOException {

    String krakenCurrencyPair = KrakenUtils.createKrakenCurrencyPair(marketOrder.getTradableIdentifier(), marketOrder.getTransactionCurrency());
    KrakenOrderResult result =
        krakenAuthenticated.addOrder(exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce(), krakenCurrencyPair, KrakenUtils.getKrakenOrderType(marketOrder.getType()), "market",
            null, marketOrder.getTradableAmount().toString());

    if (!result.isSuccess())
      throw new ExchangeException(Arrays.toString(result.getError()));

    return result.getResult().getTxid();
  }

  public String addLimitOrder(LimitOrder limitOrder) throws IOException {

    String krakenCurrencyPair = KrakenUtils.createKrakenCurrencyPair(limitOrder.getTradableIdentifier(), limitOrder.getTransactionCurrency());
    KrakenOrderResult result =
        krakenAuthenticated.addOrder(exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce(), krakenCurrencyPair, KrakenUtils.getKrakenOrderType(limitOrder.getType()), "limit",
            limitOrder.getLimitPrice().getAmount().toString(), limitOrder.getTradableAmount().toString());

    if (!result.isSuccess())
      throw new ExchangeException(Arrays.toString(result.getError()));

    return result.getResult().getTxid();
  }

  public int cancelOrderWith(String orderId) throws IOException {

    KrakenCancelOrderResult result = krakenAuthenticated.cancelOrder(exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce(), orderId);

    if (!result.isSuccess())
      throw new ExchangeException(Arrays.toString(result.getError()));

    return result.getResult().getCount();
  }
}
