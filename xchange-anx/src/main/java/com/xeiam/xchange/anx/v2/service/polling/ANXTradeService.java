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
package com.xeiam.xchange.anx.v2.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.anx.ANXUtils;
import com.xeiam.xchange.anx.v2.ANXAdapters;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.PollingTradeService;
import com.xeiam.xchange.utils.Assert;

/**
 * @author timmolter
 */
public class ANXTradeService extends ANXTradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public ANXTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    return new OpenOrders(ANXAdapters.adaptOrders(getANXOpenOrders()));
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    return placeANXMarketOrder(marketOrder).getDataString();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    // Validation
    Assert.notNull(limitOrder.getLimitPrice(), "getLimitPrice() cannot be null");
    Assert.notNull(limitOrder.getTradableAmount(), "getTradableAmount() cannot be null");

    if (limitOrder.getTradableAmount().scale() > 8) {
      throw new IllegalArgumentException("tradableAmount scale exceeds max");
    }

    if (limitOrder.getLimitPrice().scale() > ANXUtils.getMaxPriceScale(limitOrder.getCurrencyPair())) {
      throw new IllegalArgumentException("price scale exceeds max");
    }

    String type = limitOrder.getType().equals(OrderType.BID) ? "bid" : "ask";

    BigDecimal amount = limitOrder.getTradableAmount();
    BigDecimal price = limitOrder.getLimitPrice();

    return placeANXLimitOrder(limitOrder.getCurrencyPair(), type, amount, price).getDataString();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    Assert.notNull(orderId, "orderId cannot be null");

    return cancelANXOrder(orderId).getResult().equals("success");
  }

  @Override
  public Trades getTradeHistory(Object... args) throws IOException {

    throw new NotYetImplementedForExchangeException();
  }
}
