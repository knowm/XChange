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
package com.xeiam.xchange.bitstamp.service.polling;

import static com.xeiam.xchange.dto.Order.OrderType.ASK;
import static com.xeiam.xchange.dto.Order.OrderType.BID;

import java.util.ArrayList;
import java.util.List;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.bitstamp.BitstampAdapters;
import com.xeiam.xchange.bitstamp.BitstampAuthenticated;
import com.xeiam.xchange.bitstamp.BitstampUtils;
import com.xeiam.xchange.bitstamp.dto.trade.BitstampOrder;
import com.xeiam.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import com.xeiam.xchange.bitstamp.service.BitstampDigest;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.service.polling.PollingTradeService;

/**
 * @author Matija Mazi
 */
public class BitstampPollingTradeService extends BasePollingExchangeService implements PollingTradeService {

  private BitstampAuthenticated bitstampAuthenticated;
  private ParamsDigest signatureCreator;
  /**
   * Constructor
   * 
   * @param exchangeSpecification
   *          The {@link ExchangeSpecification}
   */
  public BitstampPollingTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.bitstampAuthenticated = RestProxyFactory.createProxy(BitstampAuthenticated.class, exchangeSpecification.getSslUri());
    this.signatureCreator= BitstampDigest.createInstance(exchangeSpecification.getSecretKey(), exchangeSpecification.getUserName(), exchangeSpecification.getApiKey());
  }

  @Override
  public OpenOrders getOpenOrders() {

    BitstampOrder[] openOrders = bitstampAuthenticated.getOpenOrders(exchangeSpecification.getApiKey(),signatureCreator,BitstampUtils.getNonce());
    List<LimitOrder> orders = new ArrayList<LimitOrder>();
    for (BitstampOrder bitstampOrder : openOrders) {
      orders.add(new LimitOrder(bitstampOrder.getType() == 0 ? BID : ASK, bitstampOrder.getAmount(), "BTC", "USD", Integer.toString(bitstampOrder.getId()), BigMoney.of(CurrencyUnit.USD, bitstampOrder
          .getPrice())));
    }
    return new OpenOrders(orders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) {

    BitstampOrder bitstampOrder;
    if (limitOrder.getType() == BID) {
      bitstampOrder = bitstampAuthenticated.buy(exchangeSpecification.getApiKey(),signatureCreator,BitstampUtils.getNonce(), limitOrder.getTradableAmount(), limitOrder.getLimitPrice().getAmount());
    }
    else {
      bitstampOrder = bitstampAuthenticated.sell(exchangeSpecification.getApiKey(),signatureCreator,BitstampUtils.getNonce(), limitOrder.getTradableAmount(), limitOrder.getLimitPrice().getAmount());
    }
    return Integer.toString(bitstampOrder.getId());
  }

  @Override
  public boolean cancelOrder(String orderId) {

    return bitstampAuthenticated.cancelOrder(exchangeSpecification.getApiKey(),signatureCreator,BitstampUtils.getNonce(), Integer.parseInt(orderId)).equals(true);
  }

  @Override
  public Trades getTradeHistory(final Object... arguments) {

    Long numberOfTransactions = Long.MAX_VALUE;
    try {
      numberOfTransactions = (Long) arguments[0];
    } catch (ArrayIndexOutOfBoundsException e) {
      // ignore, can happen if no arg given.
    }

    BitstampUserTransaction[] bitstampUserTransactions = bitstampAuthenticated.getUserTransactions(exchangeSpecification.getApiKey(),signatureCreator,BitstampUtils.getNonce(), numberOfTransactions);

    return BitstampAdapters.adaptTradeHistory(bitstampUserTransactions);
  }

}
