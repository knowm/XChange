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
package com.xeiam.xchange.bter.service.polling;

import java.io.IOException;
import java.util.Collection;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.bter.BTERAdapters;
import com.xeiam.xchange.bter.dto.trade.BTEROpenOrders;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.PollingTradeService;

public class BTERPollingTradeService extends BTERPollingTradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BTERPollingTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    BTEROpenOrders openOrders = super.getBTEROpenOrders();
    Collection<CurrencyPair> currencyPairs = super.getExchangeSymbols();

    return BTERAdapters.adaptOpenOrders(openOrders, currencyPairs);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  /**
   * Submits a Limit Order to be executed on the BTER Exchange for the desired market defined
   * by {@code CurrencyPair}. WARNING - BTER will return true regardless of whether or not an
   * order actually gets created. The reason for this is that orders are simply submitted to a
   * queue in their back-end. One example for why an order might not get created is because there
   * are insufficient funds. The best attempt you can make to confirm that the order was created is
   * to poll {@link #getOpenOrders}. However, if the order is created and executed before it is
   * caught in its open state from calling {@link #getOpenOrders} then the only way to confirm
   * would be confirm the expected difference in funds available for your account.
   * 
   * @return String "true"/"false" Used to determine if the order request was submitted successfully.
   */
  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    return String.valueOf(super.placeBTERLimitOrder(limitOrder));
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    return super.cancelOrder(orderId);
  }

  @Override
  public Trades getTradeHistory(Object... args) throws IOException {

    throw new NotAvailableFromExchangeException();

  }
}
