/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.btcchina.service.polling;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.btcchina.BTCChina;
import com.xeiam.xchange.btcchina.BTCChinaAdapters;
import com.xeiam.xchange.btcchina.BTCChinaUtils;
import com.xeiam.xchange.btcchina.dto.BTCChinaResponse;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaOrders;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaBuyOrderRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaCancelOrderRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaGetOrdersRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaSellOrderRequest;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaBooleanResponse;
import com.xeiam.xchange.btcchina.service.BTCChinaDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.service.polling.PollingTradeService;
import com.xeiam.xchange.utils.Assert;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import java.io.IOException;

public class BTCChinaPollingTradeService extends BasePollingExchangeService implements PollingTradeService {

  /**
   * Configured from the super class reading of the exchange specification
   */
  private final BTCChina btcchina;
  private ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchangeSpecification
   */
  public BTCChinaPollingTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");
    this.btcchina = RestProxyFactory.createProxy(BTCChina.class, exchangeSpecification.getSslUri());
    signatureCreator = BTCChinaDigest.createInstance(exchangeSpecification.getApiKey(), exchangeSpecification.getSecretKey());
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    BTCChinaResponse<BTCChinaOrders> response = btcchina.getOrders(signatureCreator, BTCChinaUtils.getNonce(), new BTCChinaGetOrdersRequest());
    return BTCChinaAdapters.adaptOpenOrders(response.getResult().getOrders());
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    verify(limitOrder.getTradableIdentifier(), limitOrder.getTransactionCurrency());

    long nonce = BTCChinaUtils.getNonce();

    BTCChinaBooleanResponse response = null;

    if (limitOrder.getType() == OrderType.BID) {

      response = btcchina.buyOrder(signatureCreator, nonce, new BTCChinaBuyOrderRequest(limitOrder.getLimitPrice().getAmount(), limitOrder.getTradableAmount()));

    } else if (limitOrder.getType() == OrderType.ASK) {

      response = btcchina.sellOrder(signatureCreator, nonce, new BTCChinaSellOrderRequest(limitOrder.getLimitPrice().getAmount(), limitOrder.getTradableAmount()));

    }

    return "" + response.getId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    BTCChinaBooleanResponse response = btcchina.cancelOrder(signatureCreator, BTCChinaUtils.getNonce(), new BTCChinaCancelOrderRequest(Long.parseLong(orderId)));
    return response.getResult();
  }

  @Override
  public Trades getTradeHistory(final Object... arguments) throws IOException {

    throw new NotYetImplementedForExchangeException();
  }

  /**
   * Verify
   *
   * @param tradableIdentifier The tradable identifier (e.g. BTC in BTC/USD)
   * @param currency           The transaction currency (e.g. USD in BTC/USD)
   */
  private void verify(String tradableIdentifier, String currency) {

    Assert.notNull(tradableIdentifier, "tradableIdentifier cannot be null");
    Assert.notNull(currency, "currency cannot be null");
    Assert.isTrue(BTCChinaUtils.isValidCurrencyPair(new CurrencyPair(tradableIdentifier, currency)), "currencyPair is not valid:" + tradableIdentifier + " " + currency);
  }

}
