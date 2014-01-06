/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.kraken.service.polling;

import java.io.IOException;
import java.util.Arrays;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.kraken.KrakenAdapters;
import com.xeiam.xchange.kraken.KrakenAuthenticated;
import com.xeiam.xchange.kraken.KrakenUtils;
import com.xeiam.xchange.kraken.dto.trade.KrakenCancelOrderResult;
import com.xeiam.xchange.kraken.dto.trade.KrakenOpenOrdersResult;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrderResult;
import com.xeiam.xchange.kraken.service.KrakenDigest;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.service.polling.PollingTradeService;
import com.xeiam.xchange.utils.Assert;

public class KrakenPollingTradeService extends BasePollingExchangeService implements PollingTradeService {

  private KrakenAuthenticated krakenAuthenticated;
  private ParamsDigest signatureCreator;

  public KrakenPollingTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");
    krakenAuthenticated = RestProxyFactory.createProxy(KrakenAuthenticated.class, exchangeSpecification.getSslUri());
    signatureCreator = KrakenDigest.createInstance(exchangeSpecification.getSecretKey());
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    KrakenOpenOrdersResult result = krakenAuthenticated.listOrders(exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce(), null, null);
    if (!result.isSuccess()) {
      throw new ExchangeException(Arrays.toString(result.getError()));
    }
    return KrakenAdapters.adaptOpenOrders(result.getResult().getOrders());
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    KrakenOrderResult result =
        krakenAuthenticated.addOrder(exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce(), KrakenUtils.createKrakenCurrencyPair(marketOrder.getTradableIdentifier(), marketOrder
            .getTransactionCurrency()), KrakenUtils.getKrakenOrderType(marketOrder.getType()), "market", null, marketOrder.getTradableAmount().toString());
    if (!result.isSuccess()) {
      throw new ExchangeException(Arrays.toString(result.getError()));
    }
    return result.getResult().getTxid();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    KrakenOrderResult result =
        krakenAuthenticated.addOrder(exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce(), KrakenUtils.createKrakenCurrencyPair(limitOrder.getTradableIdentifier(), limitOrder
            .getTransactionCurrency()), KrakenUtils.getKrakenOrderType(limitOrder.getType()), "limit", limitOrder.getLimitPrice().getAmount().toString(), limitOrder.getTradableAmount().toString());
    if (!result.isSuccess()) {
      throw new ExchangeException(Arrays.toString(result.getError()));
    }
    return result.getResult().getTxid();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    KrakenCancelOrderResult result = krakenAuthenticated.cancelOrder(exchangeSpecification.getApiKey(), signatureCreator, KrakenUtils.getNonce(), orderId);
    if (!result.isSuccess()) {
      return false;
    }
    else {
      return result.getResult().getCount() > 0;
    }
  }

  @Override
  public Trades getTradeHistory(Object... arguments) throws IOException {

    throw new NotYetImplementedForExchangeException();
  }

}
