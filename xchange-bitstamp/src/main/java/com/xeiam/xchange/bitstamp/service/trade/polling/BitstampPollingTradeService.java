/**
 * Copyright (C) 2013 Matija Mazi
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.bitstamp.service.trade.polling;

import static com.xeiam.xchange.dto.Order.OrderType.ASK;
import static com.xeiam.xchange.dto.Order.OrderType.BID;

import java.util.ArrayList;
import java.util.List;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.bitstamp.BitStamp;
import com.xeiam.xchange.bitstamp.dto.trade.BitstampOrder;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.rest.RestProxyFactory;
import com.xeiam.xchange.service.streaming.BasePollingExchangeService;
import com.xeiam.xchange.service.trade.polling.PollingTradeService;

/**
 * @author Matija Mazi
 */
public class BitstampPollingTradeService extends BasePollingExchangeService implements PollingTradeService {

  private BitStamp bitstamp;

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BitstampPollingTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.bitstamp = RestProxyFactory.createProxy(BitStamp.class, exchangeSpecification.getSslUri());
  }

  @Override
  public OpenOrders getOpenOrders() {

    BitstampOrder[] openOrders = bitstamp.getOpenOrders(exchangeSpecification.getUserName(), exchangeSpecification.getPassword());
    List<LimitOrder> orders = new ArrayList<LimitOrder>();
    for (BitstampOrder oo : openOrders) {
      orders.add(new LimitOrder(oo.getType() == 0 ? BID : ASK, oo.getAmount(), "BTC", "USD", Integer.toString(oo.getId()), BigMoney.of(CurrencyUnit.USD, oo.getPrice())));
    }
    return new OpenOrders(orders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) {

    throw new NotAvailableFromExchangeException("Placing market orders not supported by Bitstamp API.");
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) {

    BitstampOrder ord;
    if (limitOrder.getType() == BID) {
      ord = bitstamp.buy(exchangeSpecification.getUserName(), exchangeSpecification.getPassword(), limitOrder.getTradableAmount(), limitOrder.getLimitPrice().getAmount());
    } else {
      ord = bitstamp.sell(exchangeSpecification.getUserName(), exchangeSpecification.getPassword(), limitOrder.getTradableAmount(), limitOrder.getLimitPrice().getAmount());
    }
    return Integer.toString(ord.getId());
  }

  @Override
  public boolean cancelOrder(String orderId) {

    return bitstamp.cancelOrder(exchangeSpecification.getUserName(), exchangeSpecification.getPassword(), Integer.parseInt(orderId)).equals(true);
  }

}
